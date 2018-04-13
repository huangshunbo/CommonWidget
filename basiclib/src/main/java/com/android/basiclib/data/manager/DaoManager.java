package com.android.basiclib.data.manager;

import android.database.sqlite.SQLiteDatabase;

import com.android.basiclib.MApplication;
import com.android.basiclib.data.gen.DaoMaster;
import com.android.basiclib.data.gen.DaoSession;

public class DaoManager {

    private DaoMaster.DevOpenHelper mHelper;
    //private Helper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DaoManager(){
        mHelper = new DaoMaster.DevOpenHelper(MApplication.application, "commonwidget-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    private static DaoManager INSTANCE = null;
    public static DaoManager getInstance(){
        if(INSTANCE ==null){
            synchronized (DaoManager.class){
                if(INSTANCE == null){
                    INSTANCE = new DaoManager();
                }
            }
        }
        return INSTANCE;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
}
