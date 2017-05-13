/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dockerandroid.R;
import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.module.AboutModule;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.OnlineConfig;
import com.dockerandroid.misc.Constant;
import com.dockerandroid.presenters.AboutPresenter;
import com.dockerandroid.ui.UIController;
import com.dockerandroid.util.MiscUtil;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.AboutView;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by saymagic on 2016/2/4.
 */
public class AboutActivity extends BaseActivity implements AboutView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.scroll_view)
    ScrollView mScrollView;
    @Bind(R.id.app_name_tv)
    TextView mAppNameTv;
    @Bind(R.id.version_text_tv)
    TextView mVersionTextTv;
    @Bind(R.id.copyright_tv)
    TextView mCopyrightTv;
    @Bind(R.id.app_ic_img)
    ImageView mIconView;

    @Inject
    AboutPresenter mPresenter;
    @Inject
    DataManager mDataManger;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        mIconView.setColorFilter(Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                mPresenter.doShare();
                break;
            case android.R.id.home:
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showShareDialog() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_share_title));
        intent.putExtra(Intent.EXTRA_TEXT, OnlineConfig.getShareText());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getString(R.string.about_share_title)));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        MiscUtil.initToolbar(mToolbar, this);
        mPresenter.attachView(this);
        mPresenter.initVersion();
        mPresenter.initCopyRight();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void setupActivityComponent() {
        DaggerManager.INSTANCE.getAppComponent().create(new AboutModule()).inject(this);
    }

    @Override
    public void setVersionText(String text) {
        if (mVersionTextTv == null) {
            return;
        }
        mVersionTextTv.setText(text);
    }

    @Override
    public void setCopyrightText(String text) {
        if (mCopyrightTv == null) {
            return;
        }
        mCopyrightTv.setText(text);
    }

    @Override
    public void setCopyrightClickable(String url) {
        if (mCopyrightTv == null) {
            return;
        }
        if ((!TextUtils.isEmpty(url)) && (!Constant.NULL_CONSTANT.equals(url.toLowerCase()))) {
            mCopyrightTv.setClickable(true);
            mCopyrightTv.setOnClickListener((view) -> UIController.openBrower(this, url));
        } else {
            mCopyrightTv.setClickable(false);
            mCopyrightTv.setOnClickListener(null);
        }
    }

    @Override
    public void showToast(String msg) {
        UIUtil.showLongToast(msg);
    }
}
