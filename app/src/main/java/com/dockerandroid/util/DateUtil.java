/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.util;

import com.dockerandroid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by saymagic on 2016/2/16.
 */
public class DateUtil {

    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;

    public static String fromToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago <= ONE_HOUR)
            return ago / ONE_MINUTE + UIUtil.getString(R.string.minute);
        else if (ago <= ONE_DAY)
            return ago / ONE_HOUR + UIUtil.getString(R.string.hour) + (ago % ONE_HOUR / ONE_MINUTE)
                    + UIUtil.getString(R.string.minute);
        else if (ago <= ONE_DAY * 2)
            return UIUtil.getString(R.string.yesterday) + calendar.get(Calendar.HOUR_OF_DAY) + UIUtil.getString(R.string.hour)
                    + calendar.get(Calendar.MINUTE) + UIUtil.getString(R.string.minute);
        else {
            return getSimpleDateString(date);
        }
    }

    public static String getSimpleDateString(Date date) {

        if (date == null) {
            return "";
        }
        String pattern = UIUtil.getString(R.string.simple_format);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
}
