/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.data.database.dpo;

/**
 * Created by saymagic on 2016/1/7.
 */
public interface DpoTransform<P> {

    public P transformToDpo();

}
