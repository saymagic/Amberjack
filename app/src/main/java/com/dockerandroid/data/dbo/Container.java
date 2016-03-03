package com.dockerandroid.data.dbo;

import android.text.TextUtils;

import com.dockerandroid.R;
import com.dockerandroid.util.DateUtil;
import com.dockerandroid.util.UIUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by saymagic on 16/2/9.
 */
public class Container {

    private String id = "";
    private String image = "";
    private String command = "";
    private int created;
    private String status = "";
    private List<String> names;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public boolean isUp() {
        return TextUtils.isEmpty(getStatus()) ? false : getStatus().toLowerCase().startsWith("up");
    }

    public boolean isPaused() {
        return TextUtils.isEmpty(getStatus()) ? false : getStatus().toLowerCase().endsWith("(paused)");
    }

    public String getIdAndName() {
        String name = names == null && names.size() > 0 && names.get(0).length()> 0 ? "" : names.get(0).substring(1);
        return id.substring(0, 12) + "("+name+")";
    }

    public boolean containsKey(CharSequence cs) {
        if (names != null && names.size() > 0) {
            for (String name : names) {
                if (name.contains(cs)) {
                    return true;
                }
            }
        }
        return id.contains(cs) || String.valueOf(created).contains(cs) || image.contains(cs) || command.contains(cs) || status.contains(cs);
    }

    public String getDescString() {
        return DateUtil.fromToday(new Date(getCreated() * 1L * 1000))
                + UIUtil.getString(R.string.ago)
                + UIUtil.getString(R.string.container_base_on)
                + "\"" + getImage() + "\""
                + UIUtil.getString(R.string.container_create);
    }

    @Override
    public String toString() {
        return "Container{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", command='" + command + '\'' +
                ", created=" + created +
                ", status='" + status + '\'' +
                ", names=" + names +
                '}';
    }
}
