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
import android.widget.TextView;

import com.dockerandroid.util.UIUtil;

/**
 * Created by saymagic on 2016/2/20.
 */
public class CopiedTextView extends TextView {
    public CopiedTextView(Context context) {
        super(context);
        init();
    }

    public CopiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CopiedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnLongClickListener((v) -> UIUtil.copyText(getText().toString(), null));
    }

}
