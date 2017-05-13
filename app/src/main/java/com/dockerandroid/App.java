/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.component.AppComponent;
import com.dockerandroid.dagger.component.DaggerAppComponent;
import com.dockerandroid.dagger.module.AppModule;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.exception.ADException;
import com.dockerandroid.misc.Constant;
import com.dockerandroid.misc.MiscHolder;
import com.dockerandroid.util.StatisticsUtil;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by saymagic on 2016/1/4.
 */
public class App extends MultiDexApplication {

    @Inject
    DataManager mDataManager;

    private RefWatcher refWatcher;

    public RefWatcher getRefWatcher() {
        if (refWatcher == null) {
            refWatcher = LeakCanary.install(this);
        }
        return this.refWatcher;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static App get() {
        return (App) MiscHolder.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        MiscHolder.setApplicationContext(this);
        initLog();
        Stetho.initializeWithDefaults(this);
        refWatcher = LeakCanary.install(this);
        initAppComponent();
        initData();
    }

    private void initData() {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    subscriber.onNext(mDataManager.init());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        DataManager.sInited = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, "What a pity! data init error!");
                        ADException.throwRuntimeException(e);
                    }

                    @Override
                    public void onNext(Long time) {
                        StatisticsUtil.launch(time, System.currentTimeMillis(), mDataManager.getCurrentServerSize());
                    }
                });
    }


    @DebugLog
    private void initLog() {
        Logger.init(Constant.TAG);
    }

    private void initAppComponent() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
        DaggerManager.INSTANCE.setAppComponent(appComponent);
    }

}
