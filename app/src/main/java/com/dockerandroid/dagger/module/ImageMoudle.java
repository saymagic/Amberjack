/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.module;

import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.api.ServiceApiFactory;
import com.dockerandroid.presenters.ImagePresenter;
import com.dockerandroid.presenters.impl.ImagePresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saymagic on 2016/2/17.
 */
@Module
public class ImageMoudle {

    @Provides
    ImagePresenter provideContainerPresenter(ServiceApiFactory serviceApiFactory, DataManager manager) {
        return new ImagePresenterImpl(serviceApiFactory, manager);
    }

}
