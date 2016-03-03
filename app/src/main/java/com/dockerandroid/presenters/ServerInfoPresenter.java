/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters;

import com.dockerandroid.views.impl.ServerInfoView;

/**
 * Created by saymagic on 2016/2/20.
 */
public interface ServerInfoPresenter<R extends ServerInfoView> extends IPresenter<R> {

    void init();

    void deleteServer();
}
