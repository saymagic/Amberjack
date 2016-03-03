/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.module;

import android.app.Application;
import android.content.Context;

import com.dockerandroid.rx.rxbus.RxBus;
import com.umeng.fb.model.UserInfo;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by saymagic on 2016/1/6.
 */
@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    public RxBus provideRxBus() {
        return new RxBus<Object, Object>();
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return mApplication.getApplicationContext();
    }

}
