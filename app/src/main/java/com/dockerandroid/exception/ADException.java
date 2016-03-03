/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.exception;

import com.dockerandroid.R;
import com.dockerandroid.util.UIUtil;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by saymagic on 16/1/27.
 */
public class ADException extends Throwable {

    private int mErrorReason = NO_ERROR;

    private Throwable mOriThrowable;

    public static final int UNKNOW_ERROR = 0;

    public static final int NO_ERROR = 1;

    public static final int NET_ERROR = 2;

    public static final int UNSUPPORT_OPERATION = 3;

    public static final int NO_SDCARD = 4;

    public ADException(int errorReason, Throwable oriThrowable) {
        mErrorReason = errorReason;
        mOriThrowable = oriThrowable;
    }

    public Throwable getOriThrowable() {
        return mOriThrowable;
    }

    public void setOriThrowable(Throwable oriThrowable) {
        mOriThrowable = oriThrowable;
    }

    public int getErrorReason() {
        return mErrorReason;
    }

    public void setErrorReason(int errorReason) {
        mErrorReason = errorReason;
    }

    public static ADException creapteException(Throwable throwable) {
        if (throwable == null) {
            return new ADException(NO_ERROR, null);
        }
        if (throwable instanceof Exception) {
            if (throwable instanceof UnsupportedOperationException) {
                return new ADException(UNSUPPORT_OPERATION, throwable);
            }
        }else if (throwable instanceof Error) {

        }
        return new ADException(UNKNOW_ERROR, throwable);
    }

    public static ADException createException(int errorReason) {
        return new ADException(errorReason, new Exception());
    }

    public static void throwRuntimeException(){
        throw new RuntimeException();
    }

    public static RuntimeException generateRuntimeException() {
        return new RuntimeException();
    }

    public static void throwRuntimeException(String msg){
        throw generateRuntimeException();
    }

   public String getDescribe(){
       switch (mErrorReason){
           case NO_ERROR:
               return "";
           case UNSUPPORT_OPERATION:
               return UIUtil.getString(R.string.error_unsupport);
           case NET_ERROR:
               return UIUtil.getString(R.string.error_net);
           case NO_SDCARD:
               return UIUtil.getString(R.string.error_nosdcard);
       }
       return "";
   }

    public static int getErrorDes(Throwable e) {
        if(e instanceof UnknownHostException){
            return R.string.error_host_unknow;
        }else if(e instanceof ConnectException){
            return R.string.error_net_connect;
        }else if (e instanceof SocketException || e instanceof SocketTimeoutException) {
            return R.string.error_net_unknow;
        } else {
            return R.string.error_unknow;
        }
    }
}
