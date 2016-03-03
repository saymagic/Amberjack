/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.util;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dockerandroid.R;
import com.dockerandroid.ui.activity.BaseActivity;


/**
 * Created by saymagic on 2016/1/6.
 */
public class MiscUtil {

    public static void initToolbar(Toolbar toolbar, AppCompatActivity activity){
        if (toolbar == null || activity == null)
            return;
        if (activity instanceof BaseActivity){
            toolbar.setBackgroundColor(((BaseActivity) activity).getColorPrimary());
        }
        toolbar.setTitle(activity.getTitle());
        toolbar.setTitleTextColor(ContextCompat.getColor(activity,R.color.white));
        toolbar.collapseActionView();
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
