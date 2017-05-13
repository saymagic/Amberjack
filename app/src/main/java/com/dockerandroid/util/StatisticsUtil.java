/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.util;

import android.os.Bundle;

import com.dockerandroid.data.dbo.LaunchInfo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.misc.MiscHolder;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by saymagic on 2016/2/25.
 */
public class StatisticsUtil {

    private final static FirebaseAnalytics sFirebaseAnalytics = FirebaseAnalytics.getInstance(MiscHolder.getApplicationContext());

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
    public static final String SUCCESS = "success";

    public static final String LAUNCH_IMG_SHOW= "LAUNCH_IMG_SHOW";
    public static final String LAUNCH_IMG_SHOW_CLICKED= "clicked";


    public static void launch(long duration, long startTime, int serverNum) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(LAUNCH_DURATION_TIME, String.valueOf(duration));
        map.put(LAUNCH_START_TIME, DateUtil.getSimpleDateString(new Date(startTime)));
        map.put(LAUNCH_SERVER_NUMBER, String.valueOf(serverNum));
    }

    public static void login(ServerInfo info) {
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN_SERVER_INFO, String.valueOf(info.toEndPoint()));
        sFirebaseAnalytics.logEvent(LOGIN_SERVER_INFO, bundle);
    }

    public static void deleteServer() {
        sFirebaseAnalytics.logEvent(DELETE_SERVER, null);
    }

    public static void refreshImage() {
        sFirebaseAnalytics.logEvent(REFRESH_IMAGE, null);
    }

    public static void refreshContainter() {
        sFirebaseAnalytics.logEvent(REFRESH_CONTAINTER, null);
    }

    public static void share() {
        sFirebaseAnalytics.logEvent(SHARE, null);
    }

    public static void crash(Map map) {
        sFirebaseAnalytics.logEvent(CRASH, map2Bundle(map));
    }

    public static void feedback(boolean success) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SUCCESS, success);
        sFirebaseAnalytics.logEvent(FEEDBACK, bundle);
    }

    public static void market(boolean success) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SUCCESS, success);
        sFirebaseAnalytics.logEvent(MARKET, bundle);
    }

    public static void launchImge(LaunchInfo info, boolean clicked) {
        if (info == null) {
            return;
        }
        Map map = info.toMap();
        map.put(LAUNCH_IMG_SHOW_CLICKED, clicked);
        sFirebaseAnalytics.logEvent(LAUNCH_IMG_SHOW, map2Bundle(map));
    }

    private static Bundle map2Bundle(Map map) {
        Bundle bundle = new Bundle();
        if (map != null) {
            Set<Map.Entry> entrys = map.entrySet();
            for (Map.Entry entry : entrys) {
                bundle.putString(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        return bundle;
    }
}


