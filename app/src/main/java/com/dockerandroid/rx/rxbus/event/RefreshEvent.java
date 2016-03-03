/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.rx.rxbus.event;

import com.dockerandroid.data.dbo.ServerInfo;

/**
 * Created by saymagic on 2016/2/19.
 */
public class RefreshEvent extends BaseRxEvent {

    private ServerInfo mServerInfo;

    public RefreshEvent(ServerInfo serverInfo) {
        mServerInfo = serverInfo;
    }

    public ServerInfo getServerInfo() {
        return mServerInfo;
    }
}
