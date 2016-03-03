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
import com.dockerandroid.data.dbo.Image;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.domain.usecase.ImageOperateUseCase;
import com.dockerandroid.domain.usecase.RefreshImageUseCase;
import com.dockerandroid.exception.ADException;
import com.dockerandroid.presenters.ImagePresenter;
import com.dockerandroid.util.StatisticsUtil;
import com.dockerandroid.util.UIUtil;
import com.dockerandroid.views.impl.ImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observer;

/**
 * Created by saymagic on 2016/2/17.
 */
public class ImagePresenterImpl implements ImagePresenter<ImageView> {

    private ImageView mView;

    private ServiceApiFactory mApiFactory;

    private DataManager mDataManager;

    public ImagePresenterImpl(ServiceApiFactory apiFactory, DataManager dataManager) {
        mApiFactory = apiFactory;
        mDataManager = dataManager;
    }

    @Override
    public void onRefresh(ServerInfo info) {
        if (info == null) {
            info = mDataManager.getCurrentServerInfo();
        }
        mView.startLoading();
        DockerApiService service = mApiFactory.getDockerApiService(info);
        RefreshImageUseCase useCase = new RefreshImageUseCase(service);
        useCase.subscribe(info, new Observer<List<Image>>() {
            @Override
            public void onCompleted() {
                mView.stopLoading();
                StatisticsUtil.refreshImage();
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
            public void onNext(List<Image> images) {
                mView.refreshRecyclerView(images);
            }
        });
    }

    @Override
    public void showOperateMenu(View v, Image image) {
        mView.showPopMenu(v, image);
    }

    @Override
    public boolean onPopupMenuClick(int id, Image image) {
        mView.startLoading();
        ServerInfo currentServerInfo = mDataManager.getCurrentServerInfo();
        DockerApiService service = mApiFactory.getDockerApiService(currentServerInfo);
        ImageOperateUseCase useCase = new ImageOperateUseCase(service, getOperateType(id));
        useCase.subscribe(image.getName(), new Observer<EmptyResponse>() {
            @Override
            public void onCompleted() {

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

    @Override
    public void attachView(ImageView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    public int getOperateType(int id) {
        switch (id) {
            case R.id.image_remove:
                return ImageOperateUseCase.IMAGE_REMOVE;
        }
        return ImageOperateUseCase.IMAGE_UNSUPPORT;
    }
}
