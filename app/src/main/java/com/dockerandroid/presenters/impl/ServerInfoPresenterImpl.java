/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters.impl;

import com.dockerandroid.R;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.presenters.ServerInfoPresenter;
import com.dockerandroid.ui.UIController;
import com.dockerandroid.ui.activity.MainActivity;
import com.dockerandroid.util.StatisticsUtil;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.ServerInfoView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Set;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saymagic on 2016/2/20.
 */
public class ServerInfoPresenterImpl implements ServerInfoPresenter<ServerInfoView> {

    private ServerInfoView mView;

    private DataManager mDataManager;

    public ServerInfoPresenterImpl(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ServerInfoView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void init() {
        mDataManager.getCurrentServerInfoDpo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ServerInfoDpo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, "server info init error");
                    }

                    @Override
                    public void onNext(ServerInfoDpo serverInfoDpo) {
                        mView.setInfoDpo(serverInfoDpo);
                    }
                });
    }

    @Override
    public void deleteServer() {
        mDataManager.deleteServerInfo(mDataManager.getCurrentServerId())
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((integer) -> {
                            mView.showToast(UIUtil.getString(R.string.drawer_delete_success));
                            Set<Integer> ids = mDataManager.getAllServerIds();
                            if (ids == null || ids.isEmpty() || ids.size() <= 0) {
                                mView.gotoLoginActivity();
                            } else {
                                mView.getBaseActivity().finish();
                                mDataManager.setCurrentServerId(new ArrayList<>(ids).get(0));
                                UIController.startActivity(mView.getBaseActivity(), MainActivity.class);
                            }
                        },
                        (error) -> mView.showToast(UIUtil.getString(R.string.drawer_delete_fail)),
                        () -> StatisticsUtil.deleteServer());
    }
}
