/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.presenters;

import android.view.View;

import com.dockerandroid.data.dbo.Container;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.views.impl.ContainerView;

/**
 * Created by saymagic on 16/2/9.
 */
public interface ContainerPresenter<R extends ContainerView> extends IPresenter<R> {

    public void onRefresh(ServerInfo info);

    public void showOperateMenu(View v, Container container);

    public boolean onPopupMenuClick(int id, Container container);

}
