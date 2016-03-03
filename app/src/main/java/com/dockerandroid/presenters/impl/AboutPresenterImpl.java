/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters.impl;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.dockerandroid.R;
import com.dockerandroid.data.OnlineConfig;
import com.dockerandroid.misc.MiscHolder;
import com.dockerandroid.presenters.AboutPresenter;
import com.dockerandroid.util.DateUtil;
import com.dockerandroid.util.Objects;
import com.dockerandroid.util.StatisticsUtil;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.AboutView;
import com.orhanobut.logger.Logger;

/**
 * Created by saymagic on 2016/2/25.
 */
public class AboutPresenterImpl implements AboutPresenter {

    private AboutView mView;

    @Override
    public void initVersion() {
        if (mView == null) {
            return;
        }
        try {
            PackageManager pm = MiscHolder.getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(MiscHolder.getApplicationContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            mView.setVersionText("V" + pi.versionName);
            return;
        }catch (PackageManager.NameNotFoundException e){
            Logger.e(e, "initVersion error");
        }
        mView.setVersionText("V1.0.0");
    }

    @Override
    public void initCopyRight() {
        if (mView == null) {
            return;
        }
        mView.setCopyrightText(UIUtil.getString(R.string.about_copyright, DateUtil.getCurrentYear()));
        mView.setCopyrightClickable(OnlineConfig.getCopyrightUrl());
    }

    @Override
    public void checkUpdate() {
    }

    @Override
    public void doShare() {
        if (mView == null) {
            return;
        }
        mView.showShareDialog();
        StatisticsUtil.share();
    }

    @Override
    public void attachView(AboutView view) {
        Objects.requireNonNull(view);
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
