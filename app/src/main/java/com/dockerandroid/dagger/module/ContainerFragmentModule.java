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
import com.dockerandroid.presenters.ContainerPresenter;
import com.dockerandroid.presenters.impl.ContainerPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saymagic on 16/2/9.
 */
@Module
public class ContainerFragmentModule {

    @Provides
    ContainerPresenter provideContainerPresenter(ServiceApiFactory serviceApiFactory, DataManager manager) {
        return new ContainerPresenterImpl(serviceApiFactory, manager);
    }

}
