/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters.impl;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;
import android.view.MenuItem;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.dockerandroid.BuildConfig;
import com.dockerandroid.R;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.OnlineConfig;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.misc.MiscHolder;
import com.dockerandroid.presenters.MainPresenter;
import com.dockerandroid.rx.rxbus.RxBus;
import com.dockerandroid.ui.UIController;
import com.dockerandroid.ui.activity.AboutActivity;
import com.dockerandroid.ui.activity.BaseActivity;
import com.dockerandroid.ui.activity.LoginActivity;
import com.dockerandroid.ui.fragment.BaseFragment;
import com.dockerandroid.util.Objects;
import com.dockerandroid.util.StatisticsUtil;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.MainView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by saymagic on 2016/1/4.
 */
public class MainPresenterImpl implements MainPresenter<MainView> {

    private MainView mMainView;

    private RxBus mBus;

    private DataManager mDataManager;

    public MainPresenterImpl(RxBus bus, DataManager dataManager) {
        this.mBus = bus;
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(MainView view) {
        Objects.requireNonNull(view);
        this.mMainView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void onDrawerOpened() {
//        mMainView.openDrawer();
    }

    @Override
    public void onDrawerClosed() {
//        mMainView.closeDrawer();
    }

    @Override
    public void onNavigationOnClick() {
        if (mMainView.isDrawerOpening()) {
            mMainView.closeDrawer();
        } else {
            mMainView.openDrawer();
        }
    }

    @Override
    public void onAddServerClick() {
        UIController.startActivity(mMainView.getActivity(), LoginActivity.class);
        mMainView.closeDrawer();
    }

    @Override
    public void onServerItemClick(Pair<Integer, ServerInfo> infoPair, int position) {
        if (infoPair == null || infoPair.first == null || infoPair.second == null || mMainView == null) {
            return;
        }
        for (BaseFragment fragment : mMainView.getFragments()) {
            fragment.onRefresh(infoPair.second);
        }
        mDataManager.setCurrentServerId(infoPair.first);
        mMainView.closeDrawer();
        mMainView.selectDrawerListItem(position);
    }

    @Override
    public void refreshFragments() {
        if (mMainView == null) {
            return;
        }
        for (BaseFragment fragment : mMainView.getFragments()) {
            fragment.onRefresh(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                if (mMainView != null) {
                    UIController.startActivity(mMainView.getActivity(), AboutActivity.class);
                    return true;
                }
                break;
            case R.id.action_market:
                boolean done = true;
                try {
                    BaseActivity activity = mMainView.getActivity();
                    Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    done = false;
                    mMainView.showToast(UIUtil.getString(R.string.menu_no_activity_found));
                }
                StatisticsUtil.market(done);
                return true;
            case R.id.action_feedback:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String[] to = new String[1];
                to[0] = OnlineConfig.getFeedBackEmail();
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, UIUtil.getString(R.string.about_feedback_subject,
                        BuildConfig.VERSION_NAME, String.valueOf(BuildConfig.VERSION_CODE)));
                intent.putExtra(Intent.EXTRA_TEXT, "");
                BaseActivity activity = mMainView.getActivity();
                try {
                    activity.startActivity(Intent.createChooser(intent, UIUtil.getString(R.string.about_feedback_title)));
                } catch (ActivityNotFoundException e) {
                    StatisticsUtil.feedback(false);
                    mMainView.showToast(UIUtil.getString(R.string.menu_no_activity_found));
                }
                StatisticsUtil.feedback(false);

        }
        return true;
    }

    @Override
    public void initDrawerData() {
        Map<Integer, ServerInfo> infos = mDataManager.getAllServerInfos();
        mMainView.resetAdapter(generatePairListFromMap(infos));
    }

    @Override
    public void checkUpdate() {

    }

    @Override
    public void deleteServer(Pair<Integer, ServerInfo> value) {
        if (value == null || value.first == null) {
            return;
        }
        mDataManager.deleteServerInfo(value.first)
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((integer) -> {
                            mMainView.showToast(UIUtil.getString(R.string.drawer_delete_success));
                            if (mDataManager.getCurrentServerSize() <= 0) {
                                UIController.startActivity(mMainView.getActivity(), LoginActivity.class);
                                mMainView.getActivity().finish();
                            }
                        },
                        (error) -> mMainView.showToast(UIUtil.getString(R.string.drawer_delete_fail)));
    }


    public List<Pair<Integer, ServerInfo>> generatePairListFromMap(Map<Integer, ServerInfo> map) {
        if (map == null || map.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Stream.of(map.entrySet()).map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
