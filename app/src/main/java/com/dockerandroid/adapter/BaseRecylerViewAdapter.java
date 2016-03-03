/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.dockerandroid.listeners.OnInternalClickListener;

import java.util.List;

/**
 * Created by saymagic on 16/2/12.
 */
public abstract class BaseRecylerViewAdapter<V> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;

    protected List<V> mList;

    private SparseArray<OnInternalClickListener<V>> mSpareArray;

    public BaseRecylerViewAdapter(Context context, List<V> values) {
        mContext = context;
        mList = values;
    }

    public void addValue(V v) {
        mList.add(0, v);
        notifyItemInserted(0);
    }

    public void updateValue(V e, int fromPosition, int toPosition){
        mList.remove(fromPosition);
        mList.add(toPosition, e);
        if (fromPosition == toPosition){
            notifyItemChanged(fromPosition);
        }else {
            notifyItemRemoved(fromPosition);
            notifyItemInserted(toPosition);
        }
    }

    public void updateValue(V v, int fromPosition){
        updateValue(v, fromPosition, 0);
    }

    public void updateValue(V v){
        int fromPosition = mList.indexOf(v);
        updateValue(v, fromPosition);
    }

    public void removeValue(V v) {
        int position = mList.indexOf(v);
        removeValue(position);
    }

    public void removeValue(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void setList(List<V> list) {
        mList.clear();
        mList.addAll(list);
    }

    public List<V> getList() {
        return mList;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            addInternalClickListener(holder.itemView, position, mList.get(position));
        }
    }

    private void addInternalClickListener(View itemView, int position, V v) {
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

    public void addInternalClickListener(int id, OnInternalClickListener<V> listener) {
        if (mSpareArray == null) {
            mSpareArray = new SparseArray<OnInternalClickListener<V>>();
        }
        mSpareArray.put(id, listener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
