/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dockerandroid.R;
import com.dockerandroid.adapter.ContainerRecylerAdapter;
import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.module.ContainerFragmentModule;
import com.dockerandroid.data.dbo.Container;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.listeners.OnInternalClickListener;
import com.dockerandroid.presenters.ContainerPresenter;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.ContainerView;
import com.dockerandroid.widget.RecyclerViewEmptySupport;
import com.dockerandroid.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by saymagic on 2016/1/6.
 */
public class ContainerFragment extends BaseFragment implements ContainerView{

    public static final String TAG = "ContainerFragment";

    @Bind(R.id.container_coordinator_layout)
    CoordinatorLayout mContainerCoordinatorLayout;
    @Bind(R.id.container_refresher)
    SwipeRefreshLayout mRefresher;
    @Bind(R.id.container_recyclerView)
    RecyclerViewEmptySupport mRecyclerView;
    @Bind(R.id.container_no_contaniner_area)
    RelativeLayout mNoContaninerArea;
    @Bind(R.id.tv_container_no_contaniner_text)
    TextView mTvContainerNoContaninerText;

    @Inject
    ContainerPresenter mPresenter;

    ContainerRecylerAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRefresher.setColorSchemeColors(getColorPrimary());
        mRecyclerView.setEmptyView(mNoContaninerArea);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mPresenter.attachView(this);
        mAdapter = new ContainerRecylerAdapter(getContext(), new ArrayList<>());
        mRecyclerView.setHasFixedSize(true);
        mAdapter.addInternalClickListener(R.id.containter_more, new OnInternalClickListener<Container>() {
            @Override
            public void onInternalClick(View view, int position, Container value) {
                mPresenter.showOperateMenu(view, value);
            }
            @Override
            public void onInternalLongClick(View view, int position, Container value) {}
        });
        mRefresher.setOnRefreshListener(() -> mPresenter.onRefresh(null));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onRefresh(null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void setupFragmentComponent() {
        DaggerManager.INSTANCE.getMainActivityComponent()
                .create(new ContainerFragmentModule())
                .inject(this);
    }

    @Override
    public void onRefresh(Object object) {
        if (mPresenter == null) {
            return;
        }
        if (object instanceof ServerInfo) {
            ServerInfo info = (ServerInfo) object;
            mPresenter.onRefresh(info);
        } else {
            mPresenter.onRefresh(null);
        }
    }

    @Override
    public void startLoading() {
        if (mRefresher != null && !mRefresher.isRefreshing()) {
            mRefresher.setRefreshing(true);
        }
    }

    @Override
    public void stopLoading() {
        if (mRefresher != null && mRefresher.isRefreshing()) {
            mRefresher.setRefreshing(false);
        }
    }

    @Override
    public void refreshRecyclerView(List<Container> containers) {
        mAdapter.setList(containers);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPopMenu(View v, Container container) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater()
                .inflate(R.menu.containter_operates, popup.getMenu());
        Menu menu = popup.getMenu();
        if (container.isUp()) {
            menu.removeItem(R.id.containter_remove);
            if (!container.isPaused()) {
                menu.removeItem(R.id.containter_unpause);
            } else {
                menu.removeItem(R.id.containter_kill);
                menu.removeItem(R.id.containter_pause);
            }
        } else {
            menu.removeItem(R.id.containter_unpause);
            menu.removeItem(R.id.containter_pause);
            menu.removeItem(R.id.containter_kill);
        }
        popup.setOnMenuItemClickListener((item -> mPresenter.onPopupMenuClick(item.getItemId(), container)));
        popup.show();
    }

    @Override
    public void showToast(String msg) {
        UIUtil.showLongToast(msg);
    }
}
