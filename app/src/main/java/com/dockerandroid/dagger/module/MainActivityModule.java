/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.dagger.module;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import com.dockerandroid.R;
import com.dockerandroid.adapter.DrawerListAdapter;
import com.dockerandroid.adapter.MainPageListAdapter;
import com.dockerandroid.dagger.scope.ActivityScope;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.presenters.MainPresenter;
import com.dockerandroid.presenters.impl.MainPresenterImpl;
import com.dockerandroid.rx.rxbus.RxBus;
import com.dockerandroid.ui.activity.MainActivity;
import com.dockerandroid.ui.fragment.BaseFragment;
import com.dockerandroid.ui.fragment.FragmentFactory;
import com.umeng.fb.FeedbackAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import dagger.Module;
import dagger.Provides;


/**
 * Created by saymagic on 2016/1/6.
 */
@Module
public class MainActivityModule {

    private MainActivity mMainActivity;

    private FragmentManager mFragmentManager;

    public MainActivityModule(MainActivity mainActivity, FragmentManager fragmentManager) {
        this.mMainActivity = mainActivity;
        mFragmentManager = fragmentManager;
    }

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManager() {
        return mFragmentManager;
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainActivityPresenter(RxBus bus, DataManager manager){
        return new MainPresenterImpl(bus, manager);
    }

    @Provides
    @ActivityScope
    FragmentFactory provideFragmentFactory() {
        return new FragmentFactory();
    }

    @Provides
    @ActivityScope
    List<String> providePageTitles(Application application){
        return Arrays.asList(application.getResources().getStringArray(R.array.viewpage_titles));
    }

    @Provides
    @ActivityScope
    List<BaseFragment> provideFragments(FragmentFactory fragmentFactory){
        List<BaseFragment> mFragments = new LinkedList<BaseFragment>();
        mFragments.add(fragmentFactory.getFragment(FragmentFactory.Type.IMAGE, null));
        mFragments.add(fragmentFactory.getFragment(FragmentFactory.Type.CONTAINER, null));
        mFragments.add(fragmentFactory.getFragment(FragmentFactory.Type.INFO, null));
        return mFragments;
    }

    @Provides
    @ActivityScope
    MainPageListAdapter providePageAdapter(FragmentManager fragmentManager, List<String> titles, List<BaseFragment> fragments){
        return new MainPageListAdapter(fragmentManager, titles, fragments);
    }

    @Provides
    @ActivityScope
    DrawerListAdapter provideDrawerListAdapter() {
        return new DrawerListAdapter(mMainActivity, new ArrayList<>());
    }

    @Provides
    @ActivityScope
    FeedbackAgent provideFeedbackAgent() {
        return new FeedbackAgent(mMainActivity);
    }
}
