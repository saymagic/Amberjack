/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.listeners;

import android.view.View;

/**
 * Created by saymagic on 16/2/12.
 */
public interface OnInternalClickListener<T> {

    public void onInternalClick(View view, int position, T value);

    public void onInternalLongClick(View view, int position, T value);

    public class onInternalClickListenerAdapter<T> implements  OnInternalClickListener<T>{

        @Override
        public void onInternalClick(View view, int position, T value) {

        }

        @Override
        public void onInternalLongClick(View view, int position, T value) {

        }
    }

}
