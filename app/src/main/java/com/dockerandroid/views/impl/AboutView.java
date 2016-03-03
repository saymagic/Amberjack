/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.views.impl;

import com.dockerandroid.views.IToastView;

/**
 * Created by saymagic on 2016/2/25.
 */
public interface AboutView extends IToastView {

    void setVersionText(String text);

    void setCopyrightText(String text);

    void setCopyrightClickable(String url);

    void showShareDialog();
}
