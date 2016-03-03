/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.views.impl;

import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.ui.activity.BaseActivity;
import com.dockerandroid.views.IToastView;

/**
 * Created by saymagic on 2016/2/20.
 */
public interface ServerInfoView extends IToastView{

    void setInfoDpo(ServerInfoDpo serverInfoDpo);

    BaseActivity getBaseActivity();

    void gotoLoginActivity();
}
