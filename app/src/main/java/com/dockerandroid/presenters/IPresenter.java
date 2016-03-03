/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters;


import com.dockerandroid.views.IView;

/**
* Created by saymagic on 2016/1/4.
 */
public interface IPresenter<R extends IView> {
    void attachView(R view);

    void detachView();
}
