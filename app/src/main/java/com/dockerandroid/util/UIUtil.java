/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.util;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.dockerandroid.R;
import com.dockerandroid.misc.MiscHolder;

/**
 * Created by saymagic on 16/2/4.
 */
public final class UIUtil {

    public static String getString(int id) {
        return MiscHolder.getApplicationContext().getString(id);
    }

    public static String getString(int id, Object... params) {
        return MiscHolder.getApplicationContext().getString(id, params);
    }

    public static void showLongToast(String msg) {
        Toast.makeText(MiscHolder.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(int msgId) {
        Toast.makeText(MiscHolder.getApplicationContext(), UIUtil.getString(msgId), Toast.LENGTH_LONG).show();
    }

    public static boolean copyText(String text, String tip) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) MiscHolder.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) MiscHolder.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = ClipData.newPlainText("dockerandroid", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(MiscHolder.getApplicationContext(), tip == null ? getString(R.string.copy_success) : tip, Toast.LENGTH_SHORT).show();
        return true;
    }
}
