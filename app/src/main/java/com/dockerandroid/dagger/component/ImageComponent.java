/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;

import com.dockerandroid.dagger.module.ImageMoudle;
import com.dockerandroid.dagger.scope.FragmentScope;
import com.dockerandroid.ui.fragment.ImageFragment;

import dagger.Subcomponent;

/**
 * Created by saymagic on 2016/2/17.
 */
@FragmentScope
@Subcomponent(modules = ImageMoudle.class)
public interface ImageComponent {

    ImageFragment inject(ImageFragment imageFragment);

}
