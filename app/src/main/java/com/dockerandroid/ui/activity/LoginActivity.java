/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.dockerandroid.R;
import com.dockerandroid.dagger.DaggerManager;
import com.dockerandroid.dagger.module.LoginModule;
import com.dockerandroid.data.api.ServiceApiFactory;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.presenters.impl.LoginPresenterImpl;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.LoginView;
import com.dockerandroid.widget.DeletableEditText;
import com.dockerandroid.widget.LoadingButton;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.login_container)
    ViewGroup mLoginContainer;
    @Bind(R.id.login_layout_host_til)
    TextInputLayout mLoginLayoutHostTil;
    @Bind(R.id.login_layout_host_et)
    DeletableEditText mLoginLayoutHostEt;
    @Bind(R.id.login_layout_port_til)
    TextInputLayout mLoginLayoutPortTil;
    @Bind(R.id.login_layout_port_et)
    DeletableEditText mLoginLayoutPortEt;
    @Bind(R.id.login_layout_btn)
    LoadingButton mBtnLogin;

    @Inject
    LoginPresenterImpl mLoginPresenter;

    @Inject
    ServiceApiFactory mServiceApiFactory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void onCreated(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mLoginPresenter.attachView(this);
        mLoginLayoutHostTil.setHint(getString(R.string.login_host_tip));
        mLoginLayoutPortTil.setHint(getString(R.string.login_port_tip));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @OnClick(R.id.login_layout_btn)
    public void onLoginClick() {
        String host = mLoginLayoutHostEt.getText().toString();
        String port = mLoginLayoutPortEt.getText().toString();
        if (mLoginPresenter.validate(host, port)) {
            mLoginPresenter.doLogin(new ServerInfo(host, Integer.valueOf(port)));
        }
    }

    @Override
    protected void setupActivityComponent() {
        DaggerManager.INSTANCE.getAppComponent()
                .create(new LoginModule())
                .inject(this);
    }

    @Override
    public void startLoading() {
        mBtnLogin.doLoding();
    }

    @Override
    public void stopLoading() {
        mBtnLogin.goText();
    }

    @Override
    public void showError(int msgId) {
        if (mLoginContainer != null) {
            UIUtil.showLongToast(msgId);
        }
    }


    @Override
    public void showDone(ServerInfoDpo dpo) {
        if (mLoginContainer != null) {
            UIUtil.showLongToast(R.string.login_success);
            MainActivity.openMainActivity(LoginActivity.this);
        }
    }

    @Override
    public void shakeHostTextView() {
        if (mLoginLayoutHostEt != null) {
            mLoginLayoutHostEt.setShakeAnimation();
            mLoginLayoutHostEt.setText("");
        }
    }

    @Override
    public void shakePortTextView() {
        if (mLoginLayoutPortEt != null) {
            mLoginLayoutPortEt.setShakeAnimation();
            mLoginLayoutPortEt.setText("");
        }
    }


}
