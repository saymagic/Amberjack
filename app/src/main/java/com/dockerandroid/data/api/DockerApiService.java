/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.api;

import com.dockerandroid.data.api.dto.ContainerResponse;
import com.dockerandroid.data.api.dto.EmptyResponse;
import com.dockerandroid.data.api.dto.ImageResponse;
import com.dockerandroid.data.api.dto.ServerInfoResponse;
import com.dockerandroid.data.api.dto.ServerVersionResponse;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by saymagic on 2016/1/6.
 */
public interface DockerApiService {

    @GET("/info")
    Observable<ServerInfoResponse> getInfo();

    @GET("/version")
    Observable<ServerVersionResponse> getVersion();

    @GET("/containers/json?all=1")
    Observable<List<ContainerResponse>> getContainers();

    @DELETE("/containers/{id}")
    Observable<EmptyResponse> removeContainter(@Path("id") String id);

    @POST("/containers/{id}/kill")
    Observable<EmptyResponse> killContainter(@Path("id") String id);

    @POST("/containers/{id}/restart")
    Observable<EmptyResponse> restartContainter(@Path("id") String id);

    @POST("/containers/{id}/pause")
    Observable<EmptyResponse> pauseContainter(@Path("id") String id);

    @POST("/containers/{id}/unpause")
    Observable<EmptyResponse> unPauseContainter(@Path("id") String id);

    @GET("/images/json")
    Observable<List<ImageResponse>> getImages();

    @DELETE("/images/{name}")
    Observable<EmptyResponse> removeImage(@Path("name") String name, @Query("force")boolean force);
}
