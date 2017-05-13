/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.dbo;

import com.dockerandroid.R;
import com.dockerandroid.util.UIUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by saymagic on 2016/3/1.
 */
public class LaunchInfo {

    private int id;

    private String imgUrl;

    private int minShowTime;

    private String intentUrl;

    private String des;

    public static LaunchInfo DEFAULT = new LaunchInfo();

    static {
        DEFAULT.setDes(UIUtil.getString(R.string.default_launch_info_des));
        DEFAULT.setId(-1);
        DEFAULT.setIntentUrl(UIUtil.getString(R.string.default_launch_info_url));
        DEFAULT.setMinShowTime(2000);
        DEFAULT.setUrl(UIUtil.getString(R.string.default_launch_info_img));
    }

    public String getIntentUrl() {
        return intentUrl;
    }

    public void setIntentUrl(String intentUrl) {
        this.intentUrl = intentUrl;
    }

    public int getMinShowTime() {
        return minShowTime;
    }

    public void setMinShowTime(int minShowTime) {
        this.minShowTime = minShowTime;
    }

    public String getUrl() {
        return imgUrl;
    }

    public void setUrl(String url) {
        this.imgUrl = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("imgUrl", imgUrl);
        map.put("id", id+"");
        map.put("minShowTime", minShowTime + "");
        map.put("intentUrl", intentUrl);
        map.put("des", des);
        return map;
    }

    @Override
    public String toString() {
        return "LaunchInfo{" +
                "des='" + des + '\'' +
                ", id=" + id +
                ", imaUrl='" + imgUrl + '\'' +
                ", minShowTime=" + minShowTime +
                ", intentUrl='" + intentUrl + '\'' +
                '}';
    }
}
