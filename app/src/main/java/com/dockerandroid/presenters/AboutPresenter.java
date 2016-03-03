/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters;

import com.dockerandroid.views.impl.AboutView;

/**
 * Created by saymagic on 2016/2/25.
 */
public interface AboutPresenter extends  IPresenter<AboutView> {

    void initVersion();

    void initCopyRight();

    void checkUpdate();

    void doShare();
}
