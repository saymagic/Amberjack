/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.domain.usecase;

import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.api.DockerApiService;
import com.dockerandroid.data.api.dto.ServerInfoResponse;
import com.dockerandroid.data.api.dto.ServerVersionResponse;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.fernandocejas.frodo.annotation.RxLogObservable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saymagic on 16/1/27.
 */
public class LoginUseCase extends UseCase<ServerInfoDpo, ServerInfo> {

    DockerApiService mService;

    DataManager mDataManager;

    public LoginUseCase(DockerApiService service, DataManager dataManager) {
        this.mService = service;
        this.mDataManager = dataManager;
    }

    @Override
    @RxLogObservable
    public Observable<ServerInfoDpo> execute(ServerInfo params) {
        return Observable.zip(mService.getInfo().first(), mService.getVersion().first(),
                (serverInfoResponse, serverVersionResponse) -> zipInfoAndVersion(serverInfoResponse, serverVersionResponse))
                .map((serverInfo) -> serverInfo.setServerInfo(params))
                .concatMap((serverInfoDpo) ->  mDataManager.addServerInfoAfterLogin(serverInfoDpo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public ServerInfoDpo zipInfoAndVersion(ServerInfoResponse info, ServerVersionResponse version) {
        ServerInfoDpo dpo = info.transformToDpo();
        dpo.setVersion(version.getVersion());
        dpo.setApiVersion(version.getApiVersion());
        dpo.setOs(version.getOs());
        dpo.setGitCommit(version.getGitCommit());
        dpo.setGoVersion(version.getGoVersion());
        dpo.setArch(version.getArch());
        return dpo;
    }
}
