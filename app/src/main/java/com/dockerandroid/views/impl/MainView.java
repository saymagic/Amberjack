/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.views.impl;


import android.util.Pair;

import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.ui.activity.BaseActivity;
import com.dockerandroid.ui.fragment.BaseFragment;
import com.dockerandroid.views.IToastView;
import com.umeng.fb.FeedbackAgent;

import java.util.List;

/**
 * Created by saymagic on 2016/1/4.
 */
public interface MainView extends IToastView{

    BaseActivity getActivity();

    boolean isDrawerOpening();

    void openDrawer();

    void closeDrawer();

    List<BaseFragment> getFragments();

    void resetAdapter(List<Pair<Integer, ServerInfo>> infos);

    void selectDrawerListItem(int position);

    FeedbackAgent getFeedBackAgent();
}
