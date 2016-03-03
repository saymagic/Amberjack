/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;

import com.dockerandroid.dagger.module.ContainerFragmentModule;
import com.dockerandroid.dagger.scope.FragmentScope;
import com.dockerandroid.ui.fragment.ContainerFragment;

import dagger.Subcomponent;

/**
 * Created by saymagic on 16/2/9.
 */
@FragmentScope
@Subcomponent(modules = ContainerFragmentModule.class)
public interface ContainerFragmentComponent {

    ContainerFragment inject(ContainerFragment containerFragment);

}
