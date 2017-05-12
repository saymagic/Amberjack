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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by saymagic on 16/2/7.
 */
public class OnlineConfig {

    private static String getOnlineOrDefault(String key, String defaultVaule){
        return defaultVaule;
    }

    public static boolean isFirBugOnlineEnable(){
        return Constant.TRUE_CONSTANT.equals(getOnlineOrDefault(Constant.FIR_BUGHD, Constant.TRUE_CONSTANT).toLowerCase());
    }

    public static boolean isUmengBugOnlineEnable(){
        return Constant.TRUE_CONSTANT.equals(getOnlineOrDefault(Constant.UMENG_BUG, Constant.TRUE_CONSTANT).toLowerCase());
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

    public static LaunchInfo getLaunchImageUrl() {
        Gson gson = new Gson();
        try{
            return gson.fromJson(getOnlineOrDefault(Constant.LANUCH_URL, "error"), LaunchInfo.class);
        } catch(JsonSyntaxException e){
            return LaunchInfo.DEFAULT;
        }
    }

    public static boolean hasAdLaunch() {
        return Constant.TRUE_CONSTANT.equals(getOnlineOrDefault(Constant.LAUNCH_HAS_AD, Constant.TRUE_CONSTANT).toLowerCase());
    }

}