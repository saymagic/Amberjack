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
 * Created by saymagic on 2016/2/18.
 */
public class ImageOperateUseCase extends UseCase<EmptyResponse, String> {

    public static final int IMAGE_UNSUPPORT = 0x0;
    public static final int IMAGE_REMOVE = 0x1;

    DockerApiService mService;
    int mType;

    public ImageOperateUseCase(DockerApiService service, int type) {
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

    public Observable<EmptyResponse> getObservable(String name) {
        switch (mType) {
            case IMAGE_REMOVE:
                return mService.removeImage(name, true);
            case IMAGE_UNSUPPORT:
                return Observable.error(ADException.creapteException(new UnsupportedOperationException()));
        }
        return Observable.error(ADException.creapteException(new UnsupportedOperationException()));
    }

}
