/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import com.dockerandroid.BuildConfig;
import com.dockerandroid.data.cache.CacheManager;
import com.dockerandroid.data.database.DBManager;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.exception.ADException;
import com.dockerandroid.exception.CrashHandler;
import com.dockerandroid.misc.Constant;
import com.dockerandroid.misc.MiscHolder;
import com.dockerandroid.util.StatisticsUtil;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.onlineconfig.OnlineConfigAgent;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Created by saymagic on 2016/1/4.
 */
@Singleton
public class DataManager {

    private Context mContext;

    private DBManager mDBManager;

    private CacheManager mCacheManager;

    private float mBuildTime;

    private String mFlavor;

    public volatile static boolean sInited = false;

    @Inject
    public DataManager(Context context, DBManager dbManager, CacheManager cacheManager) {
        this.mContext = context;
        this.mDBManager = dbManager;
        this.mCacheManager = cacheManager;
    }

    @DebugLog
    public Long init() throws PackageManager.NameNotFoundException {
        if (sInited) {
            Logger.e("what's up man? DataManager already init!!!");
        }
        long startTime = SystemClock.currentThreadTimeMillis();
        Logger.d("DataManager start init");
        ApplicationInfo appInfo = mContext.getPackageManager()
                .getApplicationInfo(mContext.getPackageName(),
                        PackageManager.GET_META_DATA);
        mBuildTime = appInfo.metaData.getFloat(Constant.APP_BUILD_TIME);
        mFlavor = appInfo.metaData.getString(Constant.APP_FLAVOR);
        mDBManager.getAllServerInfo()
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe((serverInfoDpos) -> addServerInfosForCache(serverInfoDpos));
        initFir();
        initCrash();
        initUmeng();
        long endTime = SystemClock.currentThreadTimeMillis();
        return endTime - startTime;
    }

    private void initCrash() {
        CrashHandler.INSTANCE.init(mContext, this);
    }

    private void initFir() {
        if (isFirBugEnable()) {
        }
    }

    private void initUmeng() {
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        AnalyticsConfig.enableEncrypt(BuildConfig.UMENG_ENCRYPT_LOG);
        MobclickAgent.setCatchUncaughtExceptions(isUmengBugEnable());
        OnlineConfigAgent.getInstance().setDebugMode(BuildConfig.DEBUG);
        OnlineConfigAgent.getInstance().updateOnlineConfig(MiscHolder.getApplicationContext());
    }

    public String getFlavor() {
        return mFlavor;
    }

    public float getBuildTime() {
        return mBuildTime;
    }

    public boolean isFirBugEnable() {
        return BuildConfig.FIR_UPLOAD_ERROR && OnlineConfig.isFirBugOnlineEnable();
    }

    public boolean isUmengBugEnable() {
        return BuildConfig.UMENG_UPLOAD_ERROR && OnlineConfig.isUmengBugOnlineEnable();
    }

    public  boolean isSilentUpdate() {
        return OnlineConfig.isSilentUpdate();
    }

    public  boolean isUpdateOnlyWifi() {
        return OnlineConfig.isUpdateOnlyWifi();
    }

    public Observable<Integer> deleteServerInfo(int id) {
        Observable<Integer> result;
        try {
            result = mDBManager.deleteServerInfo(id);
            mCacheManager.removeServer(id);
        } catch (Exception e) {
            return Observable.error(e);
        }
        return result == null ? Observable.error(ADException.generateRuntimeException()) : result;
    }

    public Observable<ServerInfoDpo> addServerInfoAfterLogin(ServerInfoDpo serverInfoDpo) {
        ServerInfoDpo obs = mDBManager.addServerInfo(serverInfoDpo);
        if (obs == null) {
            Observable.error(new Exception());
        }
        addServerInfoForCache(serverInfoDpo);
        setCurrentServerId(serverInfoDpo.getId());
        StatisticsUtil.login(serverInfoDpo.transformToDbo());
        return Observable.just(obs);
    }

    public synchronized void setCurrentServerId(int id) {
        mCacheManager.setCurrentServerId(id);
    }

    public synchronized int getCurrentServerId() {
        return mCacheManager.getCurrentServerId();
    }

    public void addServerInfosForCache(List<ServerInfoDpo> infoDpos) {
        mCacheManager.addServerInfos(infoDpos);
    }

    public Observable<ServerInfo> addServerInfoForCache(ServerInfoDpo infoDpo) {
        ServerInfo info = infoDpo.transformToDbo();
        mCacheManager.addServerInfo(infoDpo.getId(), info);

        return Observable.just(info);
    }

    public synchronized ServerInfo getCurrentServerInfo() {
        return mCacheManager.getCurrentServerInfo();
    }

    public Observable<ServerInfoDpo> getCurrentServerInfoDpo() {
        return mDBManager.getServerInfoDpo(mCacheManager.getCurrentServerId());
    }

    public Set<Integer> getAllServerIds() {
        return mCacheManager.getAllServerIds();
    }

    public Map<Integer, ServerInfo> getAllServerInfos() {
        Map<Integer, ServerInfo> infos = mCacheManager.getServerInfos();
        return infos == null ? Collections.EMPTY_MAP : infos;
    }

    public int getCurrentServerSize() {
        return mCacheManager.getCurrentServerSize();
    }
}
