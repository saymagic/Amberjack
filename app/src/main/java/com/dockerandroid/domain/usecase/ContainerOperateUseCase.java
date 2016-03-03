/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.domain.usecase;

import android.support.annotation.NonNull;

import com.dockerandroid.data.api.DockerApiService;
import com.dockerandroid.data.api.dto.EmptyResponse;
import com.dockerandroid.exception.ADException;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saymagic on 2016/2/16.
 */
public class ContainerOperateUseCase extends UseCase<EmptyResponse, String> {

    public static final int CONTAINER_UNSUPPORT = 0x0;
    public static final int CONTAINER_KILL = 0x1;
    public static final int CONTAINER_RESTART = 0x2;
    public static final int CONTAINER_REMOVE = 0x3;
    public static final int CONTAINER_PAUSE = 0x4;
    public static final int CONTAINER_UNPAUSE = 0x5;

    DockerApiService mService;
    int mType;

    public ContainerOperateUseCase(DockerApiService service, int type) {
        mService = service;
        mType = type;
    }

    @Override
    @RxLogObservable
    protected Observable<EmptyResponse> execute(@NonNull String params) {
        return getObservable(params)
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<EmptyResponse> getObservable(String id) {
        switch (mType) {
            case CONTAINER_KILL:
                return mService.killContainter(id);
            case CONTAINER_REMOVE:
                return mService.removeContainter(id);
            case CONTAINER_RESTART:
                return mService.restartContainter(id);
            case CONTAINER_PAUSE:
                return mService.pauseContainter(id);
            case CONTAINER_UNPAUSE:
                return mService.unPauseContainter(id);
            case CONTAINER_UNSUPPORT:
                return Observable.error(ADException.creapteException(new UnsupportedOperationException()));
        }
        return Observable.error(ADException.creapteException(new UnsupportedOperationException()));
    }
}
