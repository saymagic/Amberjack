/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dockerandroid.R;
import com.dockerandroid.data.dbo.ServerInfo;

import java.util.List;

/**
 * Created by saymagic on 2016/2/22.
 */
public class DrawerListAdapter extends BaseListAdapter<Pair<Integer, ServerInfo>> {

    public DrawerListAdapter(Context context, List<Pair<Integer, ServerInfo>> infos) {
        super(context, infos);
    }

    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_drawer, null);
            holder = new Holder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).second.toEndPoint());
        return convertView;
    }

    static class Holder {
        TextView textView;
    }
}
