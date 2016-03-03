/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.api;

import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.util.Objects;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by saymagic on 2016/1/7.
 */
public class ServiceApiFactory implements ServiceProvider {

    OkHttpClient mOkHttpClient;

    private Map<String, DockerApiService> mApiServiceCache = new HashMap<String, DockerApiService>();

    public ServiceApiFactory(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    public Retrofit createRestAdapter(String endPoint) {
        return new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    @Override
    public DockerApiService getDockerApiService(String endPoint) {
        if (mApiServiceCache.containsKey(endPoint)) {
            return mApiServiceCache.get(endPoint);
        }
        DockerApiService service = createRestAdapter(endPoint).create(DockerApiService.class);
        mApiServiceCache.put(endPoint, service);
        return service;
    }

    public DockerApiService getDockerApiService(ServerInfo serverInfo) {
        Objects.requireNonNull(serverInfo);
        return getDockerApiService(serverInfo.toEndPoint());
    }
}
