package com.dockerandroid.data.database.dao;

import java.util.List;

import rx.Observable;

/**
 * Created by saymagic on 16/2/4.
 */
public abstract class BaseDao<T, K> {

    public abstract T add(T t);

    public abstract Observable<Integer> delete(T t);

    public abstract Observable<Integer> deleteById(K id);

    public abstract Observable<List<T>> getAll();

    public abstract Observable<T> getItem(int id);



}
