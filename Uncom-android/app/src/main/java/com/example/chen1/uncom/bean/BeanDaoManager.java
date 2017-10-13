package com.example.chen1.uncom.bean;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

import java.security.AccessControlContext;

/**
 * Created by chen1 on 2017/10/2.
 */

public class BeanDaoManager {

    private static BeanDaoManager mGreenDaoManager=null;
    private DaoMaster mMaster;
    private DaoSession mDaosession;

    public BeanDaoManager() {
        String id = SharedPreferencesUtil.getUserId(CoreApplication.newInstance().getApplicationContext());
        Log.v("BeanManagerId:",id);
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(CoreApplication.newInstance().getApplicationContext(),"user"+id,null);
        mMaster=new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaosession=mMaster.newSession();
    }
    public  static   BeanDaoManager getInstance(){
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
