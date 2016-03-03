/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.module;

import com.dockerandroid.dagger.scope.ActivityScope;
import com.dockerandroid.presenters.AboutPresenter;
import com.dockerandroid.presenters.impl.AboutPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saymagic on 2016/2/25.
 */
@Module
public class AboutModule {

    @Provides
    @ActivityScope
    AboutPresenter provideAboutPresenter() {
        return new AboutPresenterImpl();
    }
}
