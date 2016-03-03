package com.dockerandroid.data.dbo;

import com.dockerandroid.R;
import com.dockerandroid.util.DateUtil;
import com.dockerandroid.util.UIUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by saymagic on 2016/2/17.
 */
public class Image {

    private String mName;

    private String mId;

    private int mCreated;

    private int mSize;

    private int mVirtualSize;

    private List<String> mRepoTags;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getCreated() {
        return mCreated;
    }

    public void setCreated(int created) {
        mCreated = created;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<String> getRepoTags() {
        return mRepoTags;
    }

    public void setRepoTags(List<String> repoTags) {
        mRepoTags = repoTags;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public int getVirtualSize() {
        return mVirtualSize;
    }

    public void setVirtualSize(int virtualSize) {
        mVirtualSize = virtualSize;
    }

    public String getDes() {
        BigDecimal bd   =   new   BigDecimal(getVirtualSize()/1000000.0);
        return  DateUtil.fromToday(new Date(getCreated() * 1L * 1000))
                + UIUtil.getString(R.string.image_create)
                + " [" + bd.setScale(2,BigDecimal.ROUND_HALF_UP) + "MB]";
    }
}
