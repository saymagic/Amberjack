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
import com.dockerandroid.data.dbo.Image;
import com.dockerandroid.data.dbo.ServerInfo;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saymagic on 2016/2/17.
 */
public class RefreshImageUseCase extends UseCase<List<Image>, ServerInfo> {

    DockerApiService mService;

    public RefreshImageUseCase(@NonNull DockerApiService apiService) {
        mService = apiService;
    }

    @Override
    @RxLogObservable
    protected Observable<List<Image>> execute(@NonNull ServerInfo params) {
        return mService.getImages()
                .flatMap(imageResponses -> Observable.from(imageResponses))
                .map(imageResponse -> imageResponse.transformToDbo())
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
