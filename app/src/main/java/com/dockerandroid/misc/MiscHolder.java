/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.misc;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by saymagic on 2016/1/4.
 */
public class MiscHolder {

    private static Context sApplicationContext;

    private static Handler mMainThreadHandler;

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static void setApplicationContext(Context applicationContext) {
        sApplicationContext = applicationContext;
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }
}
