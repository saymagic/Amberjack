/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;


import com.dockerandroid.dagger.module.LaunchModule;
import com.dockerandroid.dagger.scope.ActivityScope;
import com.dockerandroid.ui.activity.LaunchActivity;

import dagger.Subcomponent;


/**
 * Created by saymagic on 2016/1/7.
 */
@ActivityScope
@Subcomponent(modules = LaunchModule.class)
public interface LaunchComponent {

    LaunchActivity inject(LaunchActivity launchActivity);

}
