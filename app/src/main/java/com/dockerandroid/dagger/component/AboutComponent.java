/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;

import com.dockerandroid.dagger.module.AboutModule;
import com.dockerandroid.dagger.scope.ActivityScope;
import com.dockerandroid.ui.activity.AboutActivity;

import dagger.Subcomponent;

/**
 * Created by saymagic on 2016/2/25.
 */
@ActivityScope
@Subcomponent(modules = AboutModule.class)
public interface AboutComponent {

    AboutActivity inject(AboutActivity aboutActivity);

}
