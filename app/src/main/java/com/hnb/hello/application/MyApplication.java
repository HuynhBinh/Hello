package com.hnb.hello.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import greendao.DaoMaster;
import greendao.DaoSession;


// 1st run application
// init db local storage with green dao
public class MyApplication extends Application
{

    public DaoSession daoSession;

    @Override
    public void onCreate()
    {
        super.onCreate();
        setupDatabase();

    }


    private void setupDatabase()
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "hellodb", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }

    public DaoSession getDaoSession()
    {
        if (daoSession != null)
        {
            return daoSession;
        }
        else
        {
            setupDatabase();
            return daoSession;
        }
    }

    public void clearDaoSession()
    {
        daoSession = null;
    }

}
