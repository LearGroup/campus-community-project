package com.example.chen1.uncom.bean;


import android.app.Application;
import android.content.Context;

import com.example.chen1.uncom.application.CoreApplication;

import java.security.AccessControlContext;

/**
 * Created by chen1 on 2017/10/2.
 */

public class BeanDaoManager {

    private static BeanDaoManager mGreenDaoManager;
    private DaoMaster mMaster;
    private DaoSession mDaosession;

    public BeanDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(CoreApplication.newInstance().getApplicationContext(),"user-db",null);
        mMaster=new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaosession=mMaster.newSession();
    }
    public static BeanDaoManager getInstance(){
        if(mGreenDaoManager==null){
            mGreenDaoManager=new BeanDaoManager();
        }
        return mGreenDaoManager;
    }
    public DaoMaster getMaster(){
        return mMaster;
    }
    public DaoSession getDaoSession(){
        return mDaosession;
    }
    public DaoSession getNewSession(){
        mDaosession=mMaster.newSession();
        return mDaosession;
    }
}
