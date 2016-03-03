/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger;

import com.dockerandroid.dagger.component.AppComponent;
import com.dockerandroid.dagger.component.MainActivityComponent;
import com.dockerandroid.util.Objects;

import dagger.Lazy;

/**
 * Created by saymagic on 2016/1/6.
 */
public class DaggerManager {

    private  AppComponent appComponent;

    private MainActivityComponent mMainActivityComponent;

    public static DaggerManager INSTANCE = new DaggerManager();

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(AppComponent appComponent) {
        Objects.requireNonNull(appComponent);
        this.appComponent = appComponent;
    }

    public MainActivityComponent getMainActivityComponent() {
        return mMainActivityComponent;
    }

    public void setMainActivityComponent(MainActivityComponent activityComponent) {
        mMainActivityComponent = activityComponent;
    }
}
