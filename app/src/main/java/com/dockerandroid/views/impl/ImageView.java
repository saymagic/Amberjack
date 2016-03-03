/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.views.impl;

import android.view.View;

import com.dockerandroid.data.dbo.Image;
import com.dockerandroid.views.ILoadingView;
import com.dockerandroid.views.IToastView;

import java.util.List;

/**
 * Created by hzcaoyanming on 2016/2/17.
 */
public interface ImageView extends ILoadingView, IToastView {

    public void refreshRecyclerView(List<Image> images);

    public void showPopMenu(View v, Image image);

}
