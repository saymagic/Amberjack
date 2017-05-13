/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.data;

import android.text.TextUtils;

import com.dockerandroid.R;
import com.dockerandroid.data.dbo.LaunchInfo;
import com.dockerandroid.misc.Constant;
import com.dockerandroid.util.UIUtil;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by saymagic on 16/2/7.
 */
public class OnlineConfig {

    private final static FirebaseRemoteConfig sFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    private static final String FORCE_EMPTY_STRING = "";

    private static String getOnlineOrDefault(String key, String defaultVaule){
        String onlineString = sFirebaseRemoteConfig.getString(key);
        if (TextUtils.isEmpty(onlineString)) {
            return defaultVaule;
        } else if (FORCE_EMPTY_STRING.equals(onlineString)) {
            return "";
        }
        return onlineString;
    }

    public static boolean isSilentUpdate() {
        return Constant.TRUE_CONSTANT.equals(getOnlineOrDefault(Constant.SILENT_UPDATE, Constant.TRUE_CONSTANT).toLowerCase());
    }

    public static boolean isUpdateOnlyWifi() {
        return Constant.TRUE_CONSTANT.equals(getOnlineOrDefault(Constant.UPDATE_ONLY_WIFI, Constant.TRUE_CONSTANT).toLowerCase());
    }

    public static String getCopyrightUrl() {
        return getOnlineOrDefault(Constant.COPYRIGHT_URL, "");
    }

    public static String getShareText() {
        return getOnlineOrDefault(Constant.SHARE_TEXT, UIUtil.getString(R.string.about_share_text));
    }

    public static String getFeedBackEmail(){
        return getOnlineOrDefault(Constant.FEEDBACK_EMAIL, UIUtil.getString(R.string.about_feedback_email));
    }

    public static LaunchInfo getLaunchImageUrl() {
        Gson gson = new Gson();
        try{
            return gson.fromJson(getOnlineOrDefault(Constant.LANUCH_URL, "error"), LaunchInfo.class);
        } catch(JsonSyntaxException e){
            return LaunchInfo.DEFAULT;
        }
    }

    public static boolean hasAdLaunch() {
        return Constant.TRUE_CONSTANT.equals(getOnlineOrDefault(Constant.LAUNCH_HAS_AD, Constant.FALSE_CONSTANT).toLowerCase());
    }

}