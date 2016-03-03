/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.api.dto;

import com.dockerandroid.data.database.dpo.DpoTransform;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;

/**
 * Created by saymagic on 2016/1/7.
 */
public class ServerInfoResponse implements DpoTransform<ServerInfoDpo>{

    private String ID;
    private String Name;
    private String Driver;
    private int Containers;
    private int Images;
    private int MemTotal;
    private String OperatingSystem;
    private String IndexServerAddress;
    private String SystemTime;
    private String InitSha1;
    private String InitPath;
    private int NCPU;
    private String KernelVersion;
    private String DockerRootDir;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public int getContainers() {
        return Containers;
    }

    public void setContainers(int containers) {
        Containers = containers;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    public int getMemTotal() {
        return MemTotal;
    }

    public void setMemTotal(int memTotal) {
        MemTotal = memTotal;
    }

    public String getOperatingSystem() {
        return OperatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        OperatingSystem = operatingSystem;
    }

    public String getIndexServerAddress() {
        return IndexServerAddress;
    }

    public void setIndexServerAddress(String indexServerAddress) {
        IndexServerAddress = indexServerAddress;
    }

    public String getSystemTime() {
        return SystemTime;
    }

    public void setSystemTime(String systemTime) {
        SystemTime = systemTime;
    }

    public String getInitSha1() {
        return InitSha1;
    }

    public void setInitSha1(String initSha1) {
        InitSha1 = initSha1;
    }

    public String getInitPath() {
        return InitPath;
    }

    public void setInitPath(String initPath) {
        InitPath = initPath;
    }

    public int getNCPU() {
        return NCPU;
    }

    public void setNCPU(int NCPU) {
        this.NCPU = NCPU;
    }

    public String getKernelVersion() {
        return KernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        KernelVersion = kernelVersion;
    }

    public String getDockerRootDir() {
        return DockerRootDir;
    }

    public void setDockerRootDir(String dockerRootDir) {
        DockerRootDir = dockerRootDir;
    }

    @Override
    public String toString() {
        return "InfoResponse{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Driver='" + Driver + '\'' +
                ", Containers=" + Containers +
                ", Images=" + Images +
                ", MemTotal=" + MemTotal +
                ", OperatingSystem='" + OperatingSystem + '\'' +
                ", IndexServerAddress='" + IndexServerAddress + '\'' +
                ", SystemTime='" + SystemTime + '\'' +
                ", InitSha1='" + InitSha1 + '\'' +
                ", InitPath='" + InitPath + '\'' +
                ", NCPU=" + NCPU +
                ", KernelVersion='" + KernelVersion + '\'' +
                ", DockerRootDir='" + DockerRootDir + '\'' +
                '}';
    }

    @Override
    public ServerInfoDpo transformToDpo() {
        ServerInfoDpo dpo = new ServerInfoDpo();
        dpo.setServerId(getID());
        dpo.setName(getName());
        dpo.setContainers(getContainers());
        dpo.setDockerRootDir(getDockerRootDir());
        dpo.setDriver(getDriver());
        dpo.setImages(getImages());
        dpo.setIndexServerAddress(getIndexServerAddress());
        dpo.setInitPath(getInitPath());
        dpo.setInitSha1(getInitSha1());
        dpo.setKernelVersion(getKernelVersion());
        dpo.setOperatingSystem(getOperatingSystem());
        dpo.setNCPU(getNCPU());
        dpo.setMemTotal(getMemTotal());
        dpo.setSystemTime(getSystemTime());
        return dpo;
    }
}