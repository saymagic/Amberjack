/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.presenters.impl;

import android.view.View;

import com.dockerandroid.R;
import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.api.DockerApiService;
import com.dockerandroid.data.api.ServiceApiFactory;
import com.dockerandroid.data.api.dto.EmptyResponse;
import com.dockerandroid.data.dbo.Container;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.domain.usecase.ContainerOperateUseCase;
import com.dockerandroid.domain.usecase.RefreshContainerUseCase;
import com.dockerandroid.exception.ADException;
import com.dockerandroid.presenters.ContainerPresenter;
import com.dockerandroid.util.StatisticsUtil;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.ContainerView;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by saymagic on 16/2/9.
 */
public class ContainerPresenterImpl implements ContainerPresenter<ContainerView>{

    private ContainerView mView;

    private ServiceApiFactory mApiFactory;

    private DataManager mDataManager;

    @Inject
    public ContainerPresenterImpl(ServiceApiFactory mApiFactory, DataManager dataManager) {
        this.mApiFactory = mApiFactory;
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(ContainerView view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public synchronized void onRefresh(ServerInfo info) {
        if (info == null) {
            info = mDataManager.getCurrentServerInfo();
        }
        mView.startLoading();
        DockerApiService service = mApiFactory.getDockerApiService(info);
        RefreshContainerUseCase useCase = new RefreshContainerUseCase(service);
        useCase.subscribe(info, new Observer<List<Container>>() {
            @Override
            public void onCompleted() {
                mView.stopLoading();
                StatisticsUtil.refreshContainter();
            }

            @Override
            public void onError(Throwable e) {
                mView.stopLoading();
//                if (e instanceof RetrofitError) {
//                    RetrofitError error = (RetrofitError) e;
//                    e = error.getCause();
//                }
                mView.showToast(UIUtil.getString(ADException.getErrorDes(e)));
                Logger.e(e, "onRefresh onError");
            }

            @Override
            public void onNext(List<Container> containers) {
                mView.refreshRecyclerView(containers);
            }
        });
    }

    @Override
    public void showOperateMenu(View v, Container container) {
        mView.showPopMenu(v, container);
    }

    @Override
    public boolean onPopupMenuClick(int id, Container container) {
        mView.startLoading();
        ServerInfo currentServerInfo = mDataManager.getCurrentServerInfo();
        DockerApiService service = mApiFactory.getDockerApiService(currentServerInfo);
        ContainerOperateUseCase useCase = new ContainerOperateUseCase(service, getOperateType(id));
        useCase.subscribe(container.getId(),
                new Observer<EmptyResponse>() {
            @Override
            public void onCompleted() {
                Logger.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                mView.stopLoading();
                mView.showToast(UIUtil.getString(ADException.getErrorDes(e)));
                Logger.e(e, "onRefresh onError");
            }

            @Override
            public void onNext(EmptyResponse emptyResponse) {
                onRefresh(null);
            }
        });
        return true;
    }

    public int getOperateType(int id) {
        switch (id) {
            case R.id.containter_remove:
                return ContainerOperateUseCase.CONTAINER_REMOVE;
            case R.id.containter_kill:
                return ContainerOperateUseCase.CONTAINER_KILL;
            case R.id.containter_restart:
                return ContainerOperateUseCase.CONTAINER_RESTART;
            case R.id.containter_pause:
                return ContainerOperateUseCase.CONTAINER_PAUSE;
            case R.id.containter_unpause:
                return ContainerOperateUseCase.CONTAINER_UNPAUSE;
        }
        return ContainerOperateUseCase.CONTAINER_UNSUPPORT;
    }

}
