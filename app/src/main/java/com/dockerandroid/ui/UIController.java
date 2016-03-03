/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.dockerandroid.misc.Constant;
import com.dockerandroid.util.Objects;

/**
 * Created by saymagic on 16/2/4.
 */
public final class UIController {

    public static void startActivity(Activity activity, Intent intent) {
        Objects.requireNonNull(activity, intent);
        activity.startActivity(intent);
    }

    public static void startActivity(Activity activity, Class z) {
        Objects.requireNonNull(activity, z);
        activity.startActivity(new Intent(activity, z));
    }

    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
        }
    }

    public static void handleActivityIntent(Activity activity) {
        if (activity == null) {
            return;
        }
        Intent intent = activity.getIntent();
        if (intent.hasExtra(Constant.INTENT_OPEN_URL)) {
            openBrower(activity, intent.getStringExtra(Constant.INTENT_OPEN_URL));
        }
    }

    public static void openBrower(Activity activity, String url) {
        if (activity == null || TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.getApplicationContext().startActivity(intent);
    }
}
