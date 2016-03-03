/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project AndroidDocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dockerandroid.R;

/**
 * Created by saymagic on 2016/2/26.
 */
public class LoadingButton extends FrameLayout {

    private Context mContext;

    private TextView mTextView;

    private ProgressBar mProgressBar;

    public LoadingButton(Context context) {
        this(context, null);
    }

    public LoadingButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_loading_button, this);
        mTextView = (TextView) view.findViewById(R.id.view_loading_button_text);
        mProgressBar = (ProgressBar) view.findViewById(R.id.view_loading_button_progress_bar);
    }

    public void doLoding() {
        mTextView.setVisibility(GONE);
        mProgressBar.setVisibility(VISIBLE);
    }

    public void goText() {
        postDelayed(() -> {
            if (mTextView != null && mProgressBar != null) {
                mTextView.setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
            }
        }, 300);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }
}
