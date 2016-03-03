/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.dockerandroid.App;
import com.dockerandroid.R;
import com.dockerandroid.ui.UIController;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by saymagic on 2016/1/4.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private final String TAG = BaseActivity.this.getClass().getSimpleName();

    private boolean mHandled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setupActivityComponent();
        initView(savedInstanceState);
        onCreated(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        App.get().getRefWatcher().watch(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivityTag());
        MobclickAgent.onResume(this);
        if (!mHandled) {
            UIController.handleActivityIntent(this);
            mHandled = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivityTag());
        MobclickAgent.onResume(this);
    }

    protected String getActivityTag() {
        return this.getClass().getSimpleName();
    }

    protected abstract int getLayoutId();

    protected abstract void onCreated(Bundle savedInstanceState);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void setupActivityComponent();

}
