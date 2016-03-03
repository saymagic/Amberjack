/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;

import com.dockerandroid.dagger.module.LoginModule;
import com.dockerandroid.dagger.scope.ActivityScope;
import com.dockerandroid.ui.activity.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by saymagic on 16/1/27.
 */
@ActivityScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {

    LoginActivity inject(LoginActivity loginActivity);

}
