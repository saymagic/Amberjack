/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;


import com.dockerandroid.App;
import com.dockerandroid.dagger.module.AboutModule;
import com.dockerandroid.dagger.module.ApiModule;
import com.dockerandroid.dagger.module.AppModule;
import com.dockerandroid.dagger.module.LaunchModule;
import com.dockerandroid.dagger.module.LoginModule;
import com.dockerandroid.dagger.module.MainActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by saymagic on 2016/1/6.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class
})
public interface AppComponent {

    LoginComponent create(LoginModule module);

    LaunchComponent create(LaunchModule module);

    MainActivityComponent create(MainActivityModule module);

    AboutComponent create(AboutModule module);

    App inject(App app);

}
