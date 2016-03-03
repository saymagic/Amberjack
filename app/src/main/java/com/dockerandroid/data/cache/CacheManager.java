/*
* Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
*
* This file is a part of project DockerAndroid
*
* Licensed under the Apache License, Version 2.0 (the "License”);
*/
package com.dockerandroid.data.cache;

import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.data.sp.SPManager;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by saymagic on 16/2/12.
 */
public class CacheManager {

    private SPManager mSpManager;

    private Map<Integer, ServerInfo> mServerInfos = new ConcurrentHashMap<Integer, ServerInfo>();

    @Singleton @Inject
    public CacheManager(SPManager spManager) {
        mSpManager = spManager;
    }

    public synchronized int getCurrentServerId() {
        return mSpManager.getIntParam(SPManager.CURRENT_SERVER_ID);
    }

    public synchronized void setCurrentServerId(int id) {
        mSpManager.saveParam(SPManager.CURRENT_SERVER_ID, id);
    }

    public synchronized ServerInfo getCurrentServerInfo() {
        return mServerInfos.get(getCurrentServerId());
    }

    public synchronized void addServerInfo(int id, ServerInfo info) {
        if(info == null) return;
        mServerInfos.put(id, info);
    }

    public synchronized void addServerInfos(List<ServerInfoDpo> dpos) {
        if (dpos == null || dpos.size() == 0) {
            return;
        }
        for (ServerInfoDpo dpo : dpos) {
            mServerInfos.put(dpo.getId(), dpo.transformToDbo());
        }
    }

    public Map<Integer, ServerInfo> getServerInfos() {
        return mServerInfos;
    }

    public int getCurrentServerSize() {
        return mServerInfos.size();
    }

    public void removeServer(int id) {
        mServerInfos.remove(id);
    }

    public Set<Integer> getAllServerIds() {
        return mServerInfos.keySet();
    }
}
