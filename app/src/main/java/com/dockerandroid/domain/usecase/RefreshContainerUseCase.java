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
import com.dockerandroid.data.dbo.Container;
import com.dockerandroid.data.dbo.ServerInfo;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saymagic on 16/2/9.
 */
public class RefreshContainerUseCase extends UseCase<List<Container>, ServerInfo> {

    DockerApiService mService;

    public RefreshContainerUseCase(@NonNull DockerApiService apiService) {
        mService = apiService;
    }

    @Override
    @RxLogObservable
    protected Observable<List<Container>> execute(ServerInfo info) {
        return mService.getContainers()
                .flatMap(containerResponses -> Observable.from(containerResponses))
                .map(containerResponse -> containerResponse.transformToDbo())
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
