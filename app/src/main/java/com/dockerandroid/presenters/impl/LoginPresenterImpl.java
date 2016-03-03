/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters.impl;

import android.text.TextUtils;

import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.api.DockerApiService;
import com.dockerandroid.data.api.ServiceApiFactory;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.domain.usecase.LoginUseCase;
import com.dockerandroid.exception.ADException;
import com.dockerandroid.presenters.LoginPresenter;
import com.dockerandroid.rx.rxbus.RxBus;
import com.dockerandroid.util.Objects;
import com.dockerandroid.util.RegularUtil;
import com.dockerandroid.views.impl.LoginView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by saymagic on 16/2/3.
 */
public class LoginPresenterImpl implements LoginPresenter<LoginView> {

    private LoginView mLoginView;

    private LoginUseCase mLoginUseCase;

    private ServiceApiFactory mApiFactory;

    private DataManager mDataManager;

    private RxBus mRxBus;

    @Inject
    public LoginPresenterImpl(ServiceApiFactory mApiFactory, DataManager dataManager, RxBus bus) {
        this.mApiFactory = mApiFactory;
        this.mDataManager = dataManager;
        this.mRxBus = bus;
    }

    @Override
    public void attachView(LoginView view) {
        Objects.requireNonNull(view);
        mLoginView = view;
    }

    @Override
    public void detachView() {
    }

    @Override
    public void doLogin(ServerInfo serverInfo) {
        Objects.requireNonNull(serverInfo);
        mLoginView.startLoading();
        DockerApiService service = mApiFactory.getDockerApiService(serverInfo);
        mLoginUseCase = new LoginUseCase(service, mDataManager);
        mLoginUseCase.subscribe(serverInfo, new LoginSubscriber());
    }

    @Override
    public boolean validate(String ip, String port) {
        if (TextUtils.isEmpty(ip)) {
            mLoginView.shakeHostTextView();
            return false;
        }
        if (TextUtils.isEmpty(port)) {
            mLoginView.shakePortTextView();
            return false;
        }
//        if (!RegularUtil.isIpAddress(ip)) {
//            return false;
//        }
        if (!RegularUtil.isInt(port)) {
            mLoginView.shakePortTextView();
            return false;
        }
        return true;
    }

    final class LoginSubscriber extends Subscriber<ServerInfoDpo> {

        @Override
        public void onCompleted() {
            mLoginView.stopLoading();
        }

        @Override
        public void onError(Throwable e) {
            handleError(e);
        }

        @Override
        public void onNext(ServerInfoDpo serverInfoDpo) {
            if (serverInfoDpo == null) {
                Observable.error(ADException.creapteException(new NullPointerException()));
            }
            mLoginView.showDone(serverInfoDpo);
        }

    }

    private void handleError(Throwable e) {
//        if (e instanceof RetrofitError) {
//            RetrofitError error = (RetrofitError) e;
//            e = error.getCause();
//        }
        mLoginView.showError(ADException.getErrorDes(e));
        mLoginView.stopLoading();
    }


}
