/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.module;

import com.dockerandroid.data.api.ServiceApiFactory;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by saymagic on 2016/1/6.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new StethoInterceptor()).build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    public ServiceApiFactory provideServiceApiFactory(OkHttpClient okHttpClient) {
        return new ServiceApiFactory(okHttpClient);
    }


}
