/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dockerandroid.R;
import com.dockerandroid.data.dbo.Container;
import com.dockerandroid.util.Objects;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saymagic on 16/2/12.
 */
public class ContainerRecylerAdapter extends BaseRecylerViewAdapter<Container> {

    public ContainerRecylerAdapter(Context context, List<Container> values) {
        super(context, values);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_containter, parent, false);
        return new ContainerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        ContainerItemViewHolder containerItemViewHolder = (ContainerItemViewHolder) holder;
        containerItemViewHolder.bind(mList.get(position));
    }

    class ContainerItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.containter_item_root)
        CardView mContainterItemRoot;
        @Bind(R.id.container_status)
        ImageView mContainterStatus;
        @Bind(R.id.container_id_and_name)
        TextView mContainerIdAndName;
        @Bind(R.id.container_command_and_port)
        TextView mContainerCommand;
        @Bind(R.id.container_desc)
        TextView mContainerDesc;
        @Bind(R.id.containter_more)
        ImageButton mMore;

        public ContainerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Container container) {
            Objects.requireNonNull(container);
            if (container.isUp()) {
                if (container.isPaused()) {
                    mContainterStatus.setBackgroundColor(ContextCompat.getColor(mContainterStatus.getContext(),R.color.yellow));
                } else {
                    mContainterStatus.setBackgroundColor(ContextCompat.getColor(mContainterStatus.getContext(),R.color.green));
                }
            } else {
                mContainterStatus.setBackgroundColor(ContextCompat.getColor(mContainterStatus.getContext(),R.color.red));
            }
            mContainerIdAndName.setText(container.getIdAndName());
            mContainerCommand.setText("command: " + container.getCommand());
            mContainerDesc.setText(container.getDescString());
        }
    }

    private static class ContainterFilter extends Filter {

        private ContainerRecylerAdapter mAdapter;

        private List<Container> mOriginData;

        private List<Container> mFilteredData;

        public ContainterFilter(ContainerRecylerAdapter mAdapter, List<Container> mOriginData) {
            this.mAdapter = mAdapter;
            this.mOriginData = mOriginData;
            mFilteredData = new LinkedList<Container>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            mFilteredData.clear();
            FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                mFilteredData.addAll(mOriginData);
            } else {
                for (Container container : mOriginData) {
                    if (container.containsKey(charSequence)) {
                        mOriginData.add(container);
                    }
                }
            }
            results.count = mFilteredData.size();
            results.values = mFilteredData;
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mAdapter.setList((List<Container>) filterResults.values);
            mAdapter.notifyDataSetChanged();
        }
    }
}
