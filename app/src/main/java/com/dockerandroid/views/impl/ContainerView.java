/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.views.impl;

import android.view.View;

import com.dockerandroid.data.dbo.Container;
import com.dockerandroid.views.ILoadingView;
import com.dockerandroid.views.IToastView;

import java.util.List;

/**
 * Created by saymagic on 16/2/9.
 */
public interface ContainerView extends ILoadingView, IToastView{

    public void refreshRecyclerView(List<Container> containers);

    public void showPopMenu(View v, Container container);

}
