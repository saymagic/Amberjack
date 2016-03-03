/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dockerandroid.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by saymagic on 2016/1/4.
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    BaseFragment(){initNullParamConstructor();}

    BaseFragment(Bundle bundle) {
        if(bundle == null) initNullParamConstructor();
        else initParamsConstructor(bundle);
    }

    protected void initNullParamConstructor(){}
    protected void initParamsConstructor(Bundle bundle) {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mRootView);
        setupFragmentComponent();
        initView(savedInstanceState);
        initData(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getFragmentTag());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
    protected void initData(Bundle savedInstanceState) {}

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract String getFragmentTag();

    protected abstract void setupFragmentComponent();

    public abstract void onRefresh(Object object);

}
