/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.views.impl;

import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.views.ILoadingView;

/**
 * Created by saymagic on 16/2/3.
 */
public interface LoginView extends ILoadingView {

    void showError(int msgId);

    void showDone(ServerInfoDpo dpo);

    void shakeHostTextView();

    void shakePortTextView();

}
