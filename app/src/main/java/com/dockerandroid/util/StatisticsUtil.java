/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.util;

import com.dockerandroid.data.dbo.LaunchInfo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.misc.MiscHolder;
import com.umeng.analytics.MobclickAgent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saymagic on 2016/2/25.
 */
public class StatisticsUtil {

    public static final String LAUNCH = "LAUNCH";
    public static final String LAUNCH_DURATION_TIME = "duration";
    public static final String LAUNCH_START_TIME = "startTime";
    public static final String LAUNCH_SERVER_NUMBER = "server_number";

    public static final String LOGIN = "LOGIN";
    public static final String LOGIN_SERVER_INFO = "server_info";

    public static final String DELETE_SERVER = "DELETE_SERVER";

    public static final String REFRESH_IMAGE = "REFRESH_IMAGE";

    public static final String REFRESH_CONTAINTER= "REFRESH_CONTAINTER";

    public static final String SHARE= "SHARE";

    public static final String CRASH= "CRASH";

    public static final String FEEDBACK= "FEEDBACK";

    public static final String MARKET= "MARKET";
    public static final String MARKET_SUCCESS= "success";

    public static final String LAUNCH_IMG_SHOW= "LAUNCH_IMG_SHOW";
    public static final String LAUNCH_IMG_SHOW_CLICKED= "clicked";


    public static void launch(long duration, long startTime, int serverNum) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(LAUNCH_DURATION_TIME, String.valueOf(duration));
        map.put(LAUNCH_START_TIME, DateUtil.getSimpleDateString(new Date(startTime)));
        map.put(LAUNCH_SERVER_NUMBER, String.valueOf(serverNum));
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), LAUNCH, map);
    }

    public static void login(ServerInfo info) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(LOGIN_SERVER_INFO, String.valueOf(info.toEndPoint()));
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), LOGIN, map);
    }

    public static void deleteServer() {
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), DELETE_SERVER);
    }

    public static void refreshImage() {
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), REFRESH_IMAGE);
    }

    public static void refreshContainter() {
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), REFRESH_CONTAINTER);
    }

    public static void share() {
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), SHARE);
    }

    public static void crash(Map map) {
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), CRASH, map);
    }

    public static void feedback() {
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), FEEDBACK);
    }

    public static void market(boolean has) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(MARKET_SUCCESS, has + "");
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), MARKET_SUCCESS, map);
    }

    public static void launchImge(LaunchInfo info, boolean clicked) {
        if (info == null) {
            return;
        }
        Map map = info.toMap();
        map.put(LAUNCH_IMG_SHOW_CLICKED, clicked);
        MobclickAgent.onEvent(MiscHolder.getApplicationContext(), LAUNCH_IMG_SHOW, map);
    }
}
