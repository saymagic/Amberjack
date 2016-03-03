/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.api.dto;

import com.dockerandroid.data.dbo.DboTransform;
import com.dockerandroid.data.dbo.Image;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by saymagic on 16/2/9.
 */
public class ImageResponse implements DboTransform<Image> {


    /**
     * Id : 8693db7e8a0084b8aacba184cfc4ff9891924ed2270c6dec6a9d99bdcff0d1aa
     * ParentId : a4c5be5b6e596241b4530ade26294afa8d8a4a2b9163c30e36c87f879b0f5073
     * RepoTags : ["ubuntu:14.04","ubuntu:latest"]
     * RepoDigests : []
     * Created : 1453246284
     * Size : 0
     * VirtualSize : 187925225
     * Labels : null
     */

    private String Id;
    private String ParentId;
    private int Created;
    private int Size;
    private int VirtualSize;
    private List<String> RepoTags;

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public void setCreated(int Created) {
        this.Created = Created;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public void setVirtualSize(int VirtualSize) {
        this.VirtualSize = VirtualSize;
    }

    public void setRepoTags(List<String> RepoTags) {
        this.RepoTags = RepoTags;
    }

    public String getId() {
        return Id;
    }

    public String getParentId() {
        return ParentId;
    }

    public int getCreated() {
        return Created;
    }

    public int getSize() {
        return Size;
    }

    public int getVirtualSize() {
        return VirtualSize;
    }

    public List<String> getRepoTags() {
        return RepoTags;
    }

    @Override
    public Image transformToDbo() {
        Image image = new Image();
        List<String> tags = getRepoTags();
        if (tags != null && tags.size() > 0) {
            image.setName(tags.get(0).split(":")[0]);
            List<String> newTags = new LinkedList<String>();
            for (String s : tags) {
                String[] splits = s.split(":");
                if (splits.length > 1) {
                    newTags.add(s.split(":")[1]);
                } else {
                    newTags.add(s);
                }
            }
            image.setRepoTags(newTags);
        } else {
            image.setName("");
            image.setRepoTags(Collections.EMPTY_LIST);
        }
        image.setId(getId());
        image.setCreated(getCreated());
        image.setSize(getSize());
        image.setVirtualSize(getVirtualSize());
        return image;
    }
}
