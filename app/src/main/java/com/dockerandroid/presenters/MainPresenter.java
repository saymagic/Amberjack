/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.presenters;


import android.util.Pair;
import android.view.MenuItem;

import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.views.impl.MainView;

/**
 * Created by saymagic on 2016/1/4.
 */
public interface MainPresenter<R extends MainView>  extends IPresenter<R> {

    void onDrawerOpened();

    void onDrawerClosed();

    void onNavigationOnClick();

    void onAddServerClick();

    void onServerItemClick(Pair<Integer, ServerInfo> infoPair, int position);

    void initDrawerData();

    void checkUpdate();

    void deleteServer(Pair<Integer, ServerInfo> value);

    void refreshFragments();

    boolean onOptionsItemSelected(MenuItem item);
}
