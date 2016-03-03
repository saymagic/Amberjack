/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.presenters;

import android.view.View;

import com.dockerandroid.data.dbo.Image;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.views.impl.ImageView;

/**
 * Created by hzcaoyanming on 2016/2/17.
 */
public interface ImagePresenter<R extends ImageView> extends IPresenter<R>{

    public void onRefresh(ServerInfo info);

    public void showOperateMenu(View v, Image image);

    public boolean onPopupMenuClick(int id, Image image);

}
