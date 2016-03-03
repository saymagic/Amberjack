/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dockerandroid.R;
import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.module.LaunchModule;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.OnlineConfig;
import com.dockerandroid.data.dbo.LaunchInfo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.misc.Constant;
import com.dockerandroid.misc.MiscHolder;
import com.dockerandroid.ui.UIController;
import com.dockerandroid.util.StatisticsUtil;

import java.util.ArrayList;
import java.util.Set;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * Created by saymagic on 2016/1/4.
 */
public class LaunchActivity extends BaseActivity {

    @Bind(R.id.launch_ad_area)
    LinearLayout mLaunchAdArea;
    @Bind(R.id.launch_image_iv)
    ImageView mLaunchAdIv;
    @Bind(R.id.launch_app_name_tv)
    TextView mLaunchAppNameTv;
    @Bind(R.id.launch_app_tip_tv)
    TextView mLaunchAppTipTv;
    @Bind(R.id.launch_icon_iv)
    ImageView mLaunchIconIv;

    @Inject
    DataManager mDataManager;

    private int mShowTime = 2000;

    private String mIntentUrl;

    private long mStart;

    private LaunchInfo mLaunchInfo = LaunchInfo.DEFAULT;

    private boolean mHasAd = true;

    private Runnable mGotoNextActivity = () -> {
        if (mDataManager.sInited && (!mHasAd || (System.currentTimeMillis() - mStart) > mShowTime)) {
            ServerInfo info = mDataManager.getCurrentServerInfo();
            Set<Integer> ids = mDataManager.getAllServerIds();
            Intent intent = new Intent();
            boolean clicked = (!TextUtils.isEmpty(mIntentUrl)) && (!Constant.NULL_CONSTANT.equals(mIntentUrl.toLowerCase()));
            if (clicked) {
                intent.putExtra(Constant.INTENT_OPEN_URL, mIntentUrl);
            }
            if (info == null) {
                if (ids == null || ids.isEmpty() || ids.size() < 1) {
                    intent.setClass(this, LoginActivity.class);
                } else {
                    mDataManager.setCurrentServerId(new ArrayList<>(ids).get(0));
                    intent.setClass(this, MainActivity.class);
                }
            } else {
                intent.setClass(this, MainActivity.class);
            }
            StatisticsUtil.launchImge(mLaunchInfo, clicked);
            UIController.startActivity(LaunchActivity.this, intent);
            UIController.finishActivity(LaunchActivity.this);
        } else {
            next(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mHasAd = OnlineConfig.hasAdLaunch();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        next(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStart = System.currentTimeMillis();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!mHasAd) {
            mLaunchAdArea.setVisibility(View.GONE);
            mLaunchIconIv.setVisibility(View.VISIBLE);
            return;
        }
        LaunchInfo info = OnlineConfig.getLaunchImageUrl();
        if (info == null) {
            return;
        }
        mLaunchInfo = info;
        mShowTime = Math.min(info.getMinShowTime(), mShowTime);
        mLaunchAppTipTv.setText(mLaunchInfo.getDes());
        mLaunchAppTipTv.setOnClickListener((view) -> mIntentUrl = info.getIntentUrl());
        mLaunchAdIv.setOnClickListener((view) -> mIntentUrl = info.getIntentUrl());
        Glide.with(getApplicationContext()).load(info.getUrl()).dontAnimate()
                .placeholder(R.drawable.launch_image_default)
                .error(R.drawable.launch_image_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(mLaunchAdIv);
    }

    @Override
    protected void setupActivityComponent() {
        DaggerManager.INSTANCE.getAppComponent().create(new LaunchModule()).inject(this);
    }

    public void next(boolean delay) {
        int delayMillis = delay ? 300 : 0;
        MiscHolder.getMainThreadHandler().postDelayed(mGotoNextActivity, delayMillis);
    }

}
