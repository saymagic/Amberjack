/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.util;

import java.util.regex.Pattern;

/**
 * Created by saymagic on 2016/2/26.
 */
public class RegularUtil {

    public static final Pattern IP = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

    public static final Pattern INT = Pattern.compile("[0-9]*");

    public static boolean isIpAddress(String ip) {
        return IP.matcher(ip).matches();
    }

    public static boolean isInt(String s) {
        return INT.matcher(s).matches();
    }

}
