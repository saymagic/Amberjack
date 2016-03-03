/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.component;

import com.dockerandroid.dagger.module.ContainerFragmentModule;
import com.dockerandroid.dagger.module.ImageMoudle;
import com.dockerandroid.dagger.module.MainActivityModule;
import com.dockerandroid.dagger.module.ServerInfoModule;
import com.dockerandroid.dagger.scope.ActivityScope;
import com.dockerandroid.ui.activity.MainActivity;
import dagger.Subcomponent;

/**
 * Created by saymagic on 2016/1/6.
 */
@ActivityScope
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivityComponent {

    MainActivity inject(MainActivity mainActivity);

    ContainerFragmentComponent create(ContainerFragmentModule module);

    ImageComponent create(ImageMoudle module);

    ServerInfoComponent create(ServerInfoModule module);

}

