package com.dockerandroid.data.database.dao;

import com.dockerandroid.data.database.DBHelper;
import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.dockerandroid.exception.ADException;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.j256.ormlite.dao.Dao;
import com.orhanobut.logger.Logger;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by saymagic on 16/2/4.
 */
public class ServerInfoDao extends BaseDao<ServerInfoDpo, Integer> {

    DBHelper mDBHelper;

    private Dao<ServerInfoDpo, Integer> mServerInfoDao;

    @Inject
    @Singleton
    public ServerInfoDao(DBHelper dbHelper) {
        Logger.d("ServerInfoDao init");
        try {
            this.mDBHelper = dbHelper;
            mServerInfoDao = mDBHelper.getDao(ServerInfoDpo.class);
        } catch (SQLException e) {
            Logger.e(e, "get ServerInfoDao error");
            ADException.throwRuntimeException("get ServerInfoDao error");
        }
    }

    @Override
    public ServerInfoDpo add(ServerInfoDpo serverInfoDpo) {
        if (serverInfoDpo == null) {
            return null;
        }
        try {
            mServerInfoDao.create(serverInfoDpo);
            return serverInfoDpo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @RxLogObservable
    public Observable<Integer> delete(ServerInfoDpo serverInfoDpo) {
        if (serverInfoDpo == null) {
            return Observable.error(new NullPointerException("serverInfoDpo is null"));
        }
        return Observable.create((subscriber -> {
            try {
                subscriber.onNext(mServerInfoDao.delete(serverInfoDpo));
                subscriber.onCompleted();
            } catch (Exception e) {
                Logger.e(e, "delete serverInfoDpo exception , serverInfoDpo is " + serverInfoDpo.toString());
                subscriber.onError(e);
            }
        }));
    }

    @Override
    @RxLogObservable
    public Observable<Integer> deleteById(Integer id) {
        return Observable.create((subscriber) -> {
                    try {
                        subscriber.onNext(mServerInfoDao.deleteById(id));
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        Logger.e(e, "delete serverInfoDpo by id error. ");
                        subscriber.onError(e);
                    }
                }
        );
    }

    @Override
    @RxLogObservable
    public Observable<List<ServerInfoDpo>> getAll() {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(mServerInfoDao.queryForAll());
                subscriber.onCompleted();
            } catch (Exception e) {
                Logger.e(e, "get all ServerInfoDpo error!");
                subscriber.onError(e);
            }
        });
    }

    @Override
    @RxLogObservable
    public Observable<ServerInfoDpo> getItem(int id) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(mServerInfoDao.queryForId(id));
                subscriber.onCompleted();
            } catch (Exception e) {
                Logger.e(e, "get ServerInfoDpo item error!");
                subscriber.onError(e);
            }
        });
    }
}
