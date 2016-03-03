/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.exception;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.dockerandroid.data.DataManager;
import com.dockerandroid.data.file.FileManager;
import com.dockerandroid.ui.activity.LaunchActivity;
import com.dockerandroid.util.StatisticsUtil;
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saymagic on 16/2/9.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler handler;

    private DataManager mDataManager;

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Context mContext;

    public static CrashHandler INSTANCE = new CrashHandler();

    public void init(Context context, DataManager manager) {
        this.mContext = context;
        this.mDataManager = manager;
        handler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        handleException(thread, throwable);
        handler.uncaughtException(thread, throwable);
    }

    private boolean handleException(Thread thread, Throwable ex) {
        if (ex == null) {
            return false;
        }
        Map<String, String> infos = new HashMap<String, String>();
        infos.put("THREAD_NAME", thread.getName());
        infos.put("THREAD_RRIORITY", thread.getPriority() + "");
        collectDeviceInfo(mContext, infos);
        writeCrashInfoToFile(ex, infos);
        StatisticsUtil.crash(infos);
        restart();
        return true;
    }

    public void collectDeviceInfo(Context ctx, Map<String, String> infos) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                if (mDataManager != null) {
                    infos.put("FLAVOR", mDataManager.getFlavor());
                    infos.put("BUILD_TIME", mDataManager.getBuildTime() + "");
                }
                infos.put("VERSION_NAME", versionName);
                infos.put("VERSION_CODE", versionCode);
                infos.put("CRASH_TIME", formatter.format(new Date()));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e, "an error occured when collect package info");
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Logger.e(e, "an error occured when collect crash info");
            }
        }
    }

    private void writeCrashInfoToFile(Throwable ex, Map<String, String> infos) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        writeLog(sb.toString());
    }

    private String writeLog(String log) {
        try {
            String filename = FileManager.getCrashPath() + File.separator + "crash";

            File file = new File(filename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(log);
            bw.newLine();
            bw.close();
            fw.close();
            return filename;
        } catch (Throwable throwable) {
            Logger.e(throwable, "error occured while writing exception to file");
            return null;
        }
    }

    private void restart() {
        if (mContext == null) {
            return;
        }
        Intent intent = new Intent(mContext.getApplicationContext(), LaunchActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(
                mContext.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                restartIntent);
    }

}