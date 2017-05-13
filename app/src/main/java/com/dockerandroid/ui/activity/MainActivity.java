/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dockerandroid.R;
import com.dockerandroid.adapter.DrawerListAdapter;
import com.dockerandroid.adapter.MainPageListAdapter;
import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.component.MainActivityComponent;
import com.dockerandroid.dagger.module.MainActivityModule;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.presenters.MainPresenter;
import com.dockerandroid.ui.UIController;
import com.dockerandroid.ui.fragment.BaseFragment;
import com.dockerandroid.util.MiscUtil;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.util.reflect.Reflect;
import com.dockerandroid.views.impl.MainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * Created by saymagic on 2016/1/4.
 */
public class MainActivity extends BaseActivity implements MainView {

    public static final String TAG = "MainActivity";

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;

    @Bind(R.id.left_drawer)
    LinearLayout mLeftDrawer;
    @Bind(R.id.left_drawer_listview)
    ListView mLeftDrawerListview;

    FrameLayout mAddServerContainter;

    @Inject
    MainPageListAdapter mPageAdapter;
    @Inject
    List<BaseFragment> mFragments;
    @Inject
    MainPresenter mMainPresenter;
    @Inject
    DataManager mDataManager;
    @Inject
    DrawerListAdapter mDrawerAdapter;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        mMainPresenter.initDrawerData();;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mMainPresenter.initDrawerData();
        mMainPresenter.refreshFragments();
        scrollToTabPosition(0);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        MiscUtil.initToolbar(mToolbar, this);
        mViewpager.setAdapter(mPageAdapter);
        mViewpager.setOffscreenPageLimit(2);
        mTabs.setupWithViewPager(mViewpager);
        initDrawerView();
        mMainPresenter.attachView(this);
        mMainPresenter.checkUpdate();
        mViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabs));
        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewpager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void initDrawerView() {
        View footView = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.drawer_listview_footer, null, false);
        mLeftDrawerListview.addFooterView(footView);
        mAddServerContainter = (FrameLayout) footView.findViewById(R.id.left_drawer_add_server);
        mAddServerContainter.setOnClickListener((view) -> mMainPresenter.onAddServerClick());
        mLeftDrawerListview.setAdapter(mDrawerAdapter);
        mLeftDrawerListview.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) ->
                        mMainPresenter.onServerItemClick(mDrawerAdapter.getItem(position), position));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                mMainPresenter.onDrawerOpened();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                mMainPresenter.onDrawerClosed();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener((view) -> mMainPresenter.onNavigationOnClick());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setupActivityComponent() {
        MainActivityComponent component = DaggerManager.INSTANCE.getAppComponent()
                .create(new MainActivityModule(this, getSupportFragmentManager()));
        component.inject(this);
        DaggerManager.INSTANCE.setMainActivityComponent(component);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (mMainPresenter.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void openMainActivity(Activity activity) {
        UIController.startActivity(activity, MainActivity.class);
        activity.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
        DaggerManager.INSTANCE.setMainActivityComponent(null);
    }

    @Override
    public BaseActivity getActivity() {
        return this;
    }

    @Override
    public boolean isDrawerOpening() {
        return mDrawerLayout.isDrawerOpen(mLeftDrawer);
    }

    @Override
    public void openDrawer() {
        mDrawerLayout.openDrawer(mLeftDrawer);
    }

    @Override
    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mLeftDrawer);
    }

    @Override
    public List<BaseFragment> getFragments() {
        return mFragments;
    }

    @Override
    public void resetAdapter(List<Pair<Integer, ServerInfo>> infos) {
        mDrawerAdapter.setList(infos);
        int size = infos.size(), currentId = mDataManager.getCurrentServerId();
        for (int i = 0; i < size; i++) {
            if (currentId == infos.get(i).first) {
                selectDrawerListItem(i);
                return;
            }
        }
    }

    @Override
    public void selectDrawerListItem(int position) {
        if (mLeftDrawerListview == null) {
            return;
        }
        mLeftDrawerListview.setItemChecked(position, true);
    }

    @Override
    protected String getActivityTag() {
        return TAG;
    }

    @Override
    public void showToast(String msg) {
        UIUtil.showLongToast(msg);
    }

    public void scrollToTabPosition(int position) {
        if (mTabs == null || mViewpager == null || position < 0 || position > mTabs.getTabCount()) {
            return;
        }
        Reflect.on(mTabs).call("animateToTab", position);
        mViewpager.setCurrentItem(position, true);
    }
}
