/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.api;

/**
 * Created by saymagic on 2016/1/7.
 */
public interface ServiceProvider {

    public DockerApiService getDockerApiService(String endPoint);

}
