/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.database;

import android.content.Context;

import com.dockerandroid.data.database.dao.ServerInfoDao;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by saymagic on 2016/1/6.
 */
@Singleton
public class DBManager {

    private Context mContext;

    private DBHelper mDBHelper;

    private ServerInfoDao mServerInfoDao;

    @Inject
    public DBManager(Context context, DBHelper dbHelper, ServerInfoDao serverInfoDao) {
        this.mContext = context;
        this.mDBHelper = dbHelper;
        this.mServerInfoDao = serverInfoDao;
    }

    public Observable<List<ServerInfoDpo>> getAllServerInfo() {
        return mServerInfoDao.getAll();
    }

    public Observable<Integer> deleteServerInfo(int id) {
        return mServerInfoDao.deleteById(id);
    }

    public ServerInfoDpo addServerInfo(ServerInfoDpo serverInfoDpo) {
        return mServerInfoDao.add(serverInfoDpo);
    }

    public Observable<ServerInfoDpo> getServerInfoDpo(int id) {
        return mServerInfoDao.getItem(id);
    }

}
