/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;

import com.dockerandroid.dagger.module.ServerInfoModule;
import com.dockerandroid.dagger.scope.FragmentScope;
import com.dockerandroid.ui.fragment.ServerInfoFragment;

import dagger.Subcomponent;

/**
 * Created by saymagic on 2016/2/20.
 */
@FragmentScope
@Subcomponent(modules = ServerInfoModule.class)
public interface ServerInfoComponent {

    ServerInfoFragment inject(ServerInfoFragment fragment);

}
