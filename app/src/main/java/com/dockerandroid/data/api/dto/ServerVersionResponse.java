/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.api.dto;

/**
 * Created by saymagic on 16/2/6.
 */
public class ServerVersionResponse {

    private String Version;

    private String ApiVersion;

    private String GitCommit;

    private String GoVersion;

    private String Os;

    private String Arch;

    private String KernelVersion;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getApiVersion() {
        return ApiVersion;
    }

    public void setApiVersion(String apiVersion) {
        ApiVersion = apiVersion;
    }

    public String getGitCommit() {
        return GitCommit;
    }

    public void setGitCommit(String gitCommit) {
        GitCommit = gitCommit;
    }

    public String getGoVersion() {
        return GoVersion;
    }

    public void setGoVersion(String goVersion) {
        GoVersion = goVersion;
    }

    public String getOs() {
        return Os;
    }

    public void setOs(String os) {
        Os = os;
    }

    public String getArch() {
        return Arch;
    }

    public void setArch(String arch) {
        Arch = arch;
    }

    public String getKernelVersion() {
        return KernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        KernelVersion = kernelVersion;
    }
}
