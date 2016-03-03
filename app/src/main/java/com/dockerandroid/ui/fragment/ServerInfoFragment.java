/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dockerandroid.R;
import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.module.ServerInfoModule;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.presenters.ServerInfoPresenter;
import com.dockerandroid.ui.UIController;
import com.dockerandroid.ui.activity.BaseActivity;
import com.dockerandroid.ui.activity.LoginActivity;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.ServerInfoView;
import com.dockerandroid.widget.CopiedTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by saymagic on 2016/1/4.
 */
public class ServerInfoFragment extends BaseFragment implements ServerInfoView {

    private static final String TAG = "ImageFragment";

    @Bind(R.id.server_info_containter)
    LinearLayout mServerInfoContainter;
    @Bind(R.id.server_info_os)
    TextView mServerInfoOs;
    @Bind(R.id.server_info_host)
    TextView mServerInfoHost;
    @Bind(R.id.server_info_port)
    TextView mServerInfoPort;
    @Bind(R.id.server_info_mem)
    TextView mServerInfoMem;
    @Bind(R.id.server_info_kernel_version)
    TextView mServerInfoKernelVersion;
    @Bind(R.id.server_info_api_version)
    TextView mServerInfoApiVersion;
    @Bind(R.id.server_info_arch)
    TextView mServerInfoArch;
    @Bind(R.id.server_info_docker_version)
    CopiedTextView mServerInfoDockerVersion;
    @Bind(R.id.server_info_name)
    CopiedTextView mServerInfoName;

    @Inject
    ServerInfoPresenter mPresenter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_server_info;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    protected void setupFragmentComponent() {
        DaggerManager.INSTANCE.getMainActivityComponent()
                .create(new ServerInfoModule())
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onRefresh(Object object) {
        if (mPresenter == null) {
            return;
        }
        mPresenter.init();
    }

    @Override
    public void setInfoDpo(ServerInfoDpo serverInfoDpo) {
        mServerInfoKernelVersion.setText(serverInfoDpo.getKernelVersion());
        mServerInfoOs.setText(serverInfoDpo.getOperatingSystem());
        mServerInfoMem.setText(serverInfoDpo.getMemTotal() / 1024 / 1024 + "Mib");
        mServerInfoArch.setText(serverInfoDpo.getArch());
        mServerInfoApiVersion.setText(serverInfoDpo.getApiVersion());
        mServerInfoHost.setText(serverInfoDpo.getServerHost());
        mServerInfoPort.setText(serverInfoDpo.getServerPort() + "");
        mServerInfoName.setText(serverInfoDpo.getName());
        mServerInfoDockerVersion.setText(serverInfoDpo.getVersion());
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void gotoLoginActivity() {
        mServerInfoContainter.postDelayed(() -> {
            UIController.startActivity(getActivity(), LoginActivity.class);
            getActivity().finish();
        }, 300);
    }

    @OnClick(R.id.server_delete)
    public void deleteServer(View view) {
        mPresenter.deleteServer();
    }

    @Override
    public void showToast(String msg) {
        UIUtil.showLongToast(msg);
    }
}
