/*
 * Copyright (c) 2016 saymagic <saymagic.dev@gmail.com>
 *
 * This file is a part of project DockerAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 */
package com.dockerandroid.domain.usecase;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * Created by saymagic on 2016/1/15.
 */
public abstract class UseCase<T, R> {

    private Subscription mSubscription = Subscriptions.empty();

    public void subscribe(R params, Observer<T> useCaseObserver) {
        mSubscription = execute(params)
                .onBackpressureBuffer()
                .take(1)
                .filter(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return !mSubscription.isUnsubscribed();
                    }
                }).subscribe(useCaseObserver);
    }

    public void unSubscribe(){
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    protected abstract Observable<T> execute(@NonNull R params);
}
