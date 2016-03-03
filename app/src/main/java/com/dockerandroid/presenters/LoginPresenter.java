/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters;

import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.views.impl.LoginView;

/**
 * Created by saymagic on 16/2/3.
 */
public interface LoginPresenter<R extends LoginView> extends IPresenter<R> {

    void doLogin(ServerInfo serverInfo);

    boolean validate(String ip, String host);
}
