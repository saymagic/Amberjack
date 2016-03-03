/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.ui.fragment;

import android.os.Bundle;

/**
 * Created by saymagic on 15/10/31.
 */
public class FragmentFactory {

    public enum Type{
        IMAGE, CONTAINER, INFO
    }

    public BaseFragment getFragment(Type type, Bundle bundle) {
        switch (type) {
            case IMAGE:
                return new ImageFragment();
            case CONTAINER:
                return new ContainerFragment();
            case INFO:
                return new ServerInfoFragment();
        }

        throw new IllegalArgumentException("Fragment type must be IMAGE, CONTAINER or INFO\n.");
    }
}
