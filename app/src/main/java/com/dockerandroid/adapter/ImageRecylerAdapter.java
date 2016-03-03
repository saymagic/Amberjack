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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dockerandroid.R;
import com.dockerandroid.data.dbo.Image;
import com.dockerandroid.widget.FlowLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by saymagic on 2016/2/17.
 */
public class ImageRecylerAdapter extends BaseRecylerViewAdapter<Image> {

    public ImageRecylerAdapter(Context context, List<Image> values) {
        super(context, values);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_image, parent, false);
        return new ImageItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        ImageItemViewHolder containerItemViewHolder = (ImageItemViewHolder) holder;
        containerItemViewHolder.bind(mList.get(position));
    }

    class ImageItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image_item_root)
        CardView mImageItemRoot;
        @Bind(R.id.image_id)
        TextView mImageId;
        @Bind(R.id.image_des)
        TextView mImageDes;
        @Bind(R.id.image_more)
        ImageButton mImageMore;
        @Bind((R.id.image_tag_layout))
        FlowLayout mFlowLayout;

        public ImageItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Image image) {
            mImageId.setText(image.getId().substring(0, 12) + "(" + image.getName()+ ")");
            mImageDes.setText(image.getDes());
            List<String> tags = image.getRepoTags();
            if (tags == null || tags.size() == 0) {
                mFlowLayout.setVisibility(View.GONE);
                return;
            }
            mFlowLayout.setVisibility(View.VISIBLE);
            int tagSize = tags.size();
            int childCount = mFlowLayout.getChildCount();
            int diff = tagSize - childCount;
            if (diff < 0) {
                for (int i = 0; i < childCount; i++) {
                    if (i < tagSize) {
                        mFlowLayout.getChildAt(i).setVisibility(View.VISIBLE);
                        ((TextView) mFlowLayout.getChildAt(i)).setText(tags.get(i));
                    } else if (i > 1) {
                        mFlowLayout.removeViewAt(i);
                    } else {
                        mFlowLayout.getChildAt(i).setVisibility(View.GONE);
                    }
                }
            } else {
                for (int i = 0; i < childCount; i++) {
                    ((TextView) mFlowLayout.getChildAt(i)).setVisibility(View.VISIBLE);
                    ((TextView) mFlowLayout.getChildAt(i)).setText(tags.get(i));
                }
                for (int j = 0; j < diff; j++) {
                    TextView tv = new TextView(mFlowLayout.getContext());
                    tv.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tv.setBackgroundResource(R.drawable.image_tag_bg);
                    tv.setText(tags.get(childCount + j));
                    tv.setTextColor(ContextCompat.getColor(mFlowLayout.getContext(), R.color.textview_shallow_color));
                    mFlowLayout.addView(tv);
                }
            }
        }
    }
}
