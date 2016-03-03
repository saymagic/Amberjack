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
import com.dockerandroid.adapter.ImageRecylerAdapter;
import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.module.ImageMoudle;
import com.dockerandroid.data.dbo.Image;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.listeners.OnInternalClickListener;
import com.dockerandroid.presenters.ImagePresenter;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.ImageView;
import com.dockerandroid.widget.RecyclerViewEmptySupport;
import com.dockerandroid.widget.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by saymagic on 2016/1/6.
 */
public class ImageFragment extends BaseFragment implements ImageView {

    private static final String TAG = "ImageFragment";

    @Bind(R.id.image_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.image_refresher)
    SwipeRefreshLayout mRefresher;
    @Bind(R.id.image_recyclerView)
    RecyclerViewEmptySupport mRecyclerView;
    @Bind(R.id.image_no_image_area)
    RelativeLayout mNoImageArea;
    @Bind(R.id.tv_image_no_image_text)
    TextView meNoImageText;

    @Inject
    ImagePresenter mPresenter;

    ImageRecylerAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRefresher.setColorSchemeColors(getColorPrimary());
        mRecyclerView.setEmptyView(mNoImageArea);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mPresenter.attachView(this);
        mAdapter = new ImageRecylerAdapter(getContext(), new ArrayList<>());
        mRecyclerView.setHasFixedSize(true);
        mAdapter.addInternalClickListener(R.id.image_more, new OnInternalClickListener<Image>() {
            @Override
            public void onInternalClick(View view, int position, Image value) {
                mPresenter.showOperateMenu(view, value);
            }
            @Override
            public void onInternalLongClick(View view, int position, Image value) {}
        });
        mRefresher.setOnRefreshListener(() -> mPresenter.onRefresh(null));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void setupFragmentComponent() {
        DaggerManager.INSTANCE.getMainActivityComponent()
                .create(new ImageMoudle())
                .inject(this);
    }

    @Override
    public void onRefresh(Object object) {
        if (mPresenter == null) {
            return;
        }
        if (object != null && object instanceof ServerInfo) {
            mPresenter.onRefresh((ServerInfo) object);
        } else {
            mPresenter.onRefresh(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter == null) {
            return;
        }
        mPresenter.onRefresh(null);
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
    public void showToast(String msg) {
        UIUtil.showLongToast(msg);
    }

    @Override
    public void refreshRecyclerView(List<Image> images) {
        mAdapter.setList(images);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPopMenu(View v, Image image) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater()
                .inflate(R.menu.image_operates, popup.getMenu());
        Menu menu = popup.getMenu();
        popup.setOnMenuItemClickListener((item -> mPresenter.onPopupMenuClick(item.getItemId(), image)));
        popup.show();
    }
}
