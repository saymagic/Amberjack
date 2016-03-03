/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dockerandroid.R;

/**
 * Created by saymagic on 2016/1/6.
 */
public class NavifationFragment extends BaseFragment {

    public static final String TAG = "NavifationFragment";

    public NavifationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navifation, container, false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navifation;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void setupFragmentComponent() {

    }

    @Override
    public void onRefresh(Object object) {

    }

}
