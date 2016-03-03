/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.rx.rxbus;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by saymagic on 2016/2/19.
 * We don't use it now.
 */
public class RxBus<T, R> {

    private SerializedSubject<T, R> rxBus;

    private SerializedSubject<T, R> rxStickBus;

    public RxBus(){
        rxBus = new SerializedSubject(PublishSubject.<T>create());
        rxStickBus = new SerializedSubject(BehaviorSubject.<T>create());
    }

    public void postEvent(T event) {
        rxBus.onNext(event);
    }

    public void postStickEvent(T event) {
        rxStickBus.onNext(event);
    }

    public Observable<R> toObservable() {
        return rxBus.asObservable().onBackpressureBuffer();
    }

    public boolean hasObservers() {
        return rxBus.hasObservers();
    }

    public Observable<R> toStickObservable() {
        return rxStickBus.asObservable().share().onBackpressureBuffer();
    }

    public boolean hasStickObservers() {
        return rxStickBus.hasObservers();
    }


}
