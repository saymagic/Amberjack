/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.api.dto;

import com.dockerandroid.data.dbo.Container;
import com.dockerandroid.data.dbo.DboTransform;

import java.util.List;

/**
 * Created by saymagic on 16/2/9.
 */
public class ContainerResponse implements DboTransform<Container>{


    /**
     * Id : 5d5b000c71b95b4b65eb453982143bf5f0c9bade38c2353bad09429d69c575fc
     * Names : ["/sleepy_ritchie"]
     * Image : ubuntu:14.04
     * Command : /bin/bash
     * Created : 1454755357
     * Labels : {}
     * Status : Up 3 days
     */

    private String Id;
    private String Image;
    private String Command;
    private int Created;
    private LabelsEntity Labels;
    private String Status;
    private List<String> Names;

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public void setCommand(String Command) {
        this.Command = Command;
    }

    public void setCreated(int Created) {
        this.Created = Created;
    }

    public void setLabels(LabelsEntity Labels) {
        this.Labels = Labels;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setNames(List<String> Names) {
        this.Names = Names;
    }

    public String getId() {
        return Id;
    }

    public String getImage() {
        return Image;
    }

    public String getCommand() {
        return Command;
    }

    public int getCreated() {
        return Created;
    }

    public LabelsEntity getLabels() {
        return Labels;
    }

    public String getStatus() {
        return Status;
    }

    public List<String> getNames() {
        return Names;
    }


    @Override
    public Container transformToDbo() {
        Container container = new Container();
        container.setId(getId());
        container.setCreated(getCreated());
        container.setCommand(getCommand());
        container.setImage(getImage());
        container.setNames(getNames());
        container.setStatus(getStatus());
        return container;
    }

    public static class LabelsEntity {
    }
}
