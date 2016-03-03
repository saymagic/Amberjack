package com.dockerandroid.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dockerandroid.data.database.dpo.ServerInfoDpo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.orhanobut.logger.Logger;

import java.sql.SQLException;

import javax.inject.Inject;

/**
 * Created by saymagic on 16/2/4.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "android_docker";

    public static final int DB_VERSION = 1;

    @Inject
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ServerInfoDpo.class);
        } catch (SQLException e) {
            Logger.e(e, "db create error!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,int newVersion) {
        if (newVersion != DB_VERSION) {
            return;
        }
        try {
            db.beginTransaction();
            if (oldVersion <= 1) {
                updateFromV1ToV2(db, connectionSource);
            }
            db.endTransaction();
        } catch (Throwable e) {
            Logger.e(e, "db update error!");
        }
    }

    private void updateFromV1ToV2(SQLiteDatabase db, ConnectionSource connectionSource) {
    }

    @Override
    public void close() {
        super.close();
    }
}
