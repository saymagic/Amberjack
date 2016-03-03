/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dockerandroid.listeners.OnInternalClickListener;

import java.util.List;


/**
 * Created by saymagic on 16/2/12.
 */
public abstract class BaseListAdapter<V> extends BaseAdapter {

    public List<V> list;

    public Context mContext;

    public LayoutInflater mInflater;

    private SparseArray<OnInternalClickListener<V>> mSpareArray;

    public List<V> getList() {
        return list;
    }

    public void setList(List<V> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void add(V e) {
        this.list.add(e);
        notifyDataSetChanged();
    }

    public void addAll(List<V> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.list.remove(position);
        notifyDataSetChanged();
    }

    public BaseListAdapter(Context context, List<V> list) {
        super();
        this.mContext = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public V getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = bindView(position, convertView, parent);
        addInternalClickListener(convertView, position, list.get(position));
        return convertView;
    }

    public abstract View bindView(int position, View convertView,
                                  ViewGroup parent);


    private void addInternalClickListener(final View itemView, final Integer position, final V v) {
        if (mSpareArray == null) {
            return;
        }
        int size = mSpareArray.size();
        for (int i = 0; i < size; i++) {
            int id = mSpareArray.keyAt(i);
            View inView = itemView.findViewById(id);
            if (inView != null) {
                OnInternalClickListener listener = mSpareArray.get(id);
                if (listener != null) {
                    inView.setOnClickListener((view) -> listener.onInternalClick(inView, position, v));
                    inView.setOnLongClickListener((view) -> {
                        listener.onInternalLongClick(inView, position, v);
                        return true;
                    });
                }
            }
        }
    }

    public void addOnInViewClickListener(Integer key, OnInternalClickListener<V> onClickListener) {
        if (mSpareArray == null)
            mSpareArray = new SparseArray<>();
        mSpareArray.put(key, onClickListener);
    }

}
