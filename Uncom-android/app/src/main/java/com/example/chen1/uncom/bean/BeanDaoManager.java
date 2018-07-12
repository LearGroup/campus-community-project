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

public class  BeanDaoManager {

    private static BeanDaoManager mGreenDaoManager=null;
    private DaoMaster mMaster;
    private DaoSession mDaosession;

    public BeanDaoManager() {
        String id = SharedPreferencesUtil.getUserId(CoreApplication.newInstance().getApplicationContext());
        Log.v("BeanManagerId:",id);
        OpenHelper  devOpenHelper=new OpenHelper(CoreApplication.newInstance().getApplicationContext(),"user"+id,null);
        mMaster=new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaosession=mMaster.newSession();
    }

    /**
     * 获取数据库操作句柄
     * 0   获取默认数据库操作句柄 1获取新的数据库操作
     * @return
     */
    public  static   BeanDaoManager getInstance(int status){
        if(mGreenDaoManager==null || status==1){
            mGreenDaoManager=new BeanDaoManager();
        }
        return mGreenDaoManager;
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
        if(mDaosession==null){
            mDaosession=mMaster.newSession();
        }
        return mDaosession;
    }
    public DaoSession getNewSession(){
        mDaosession=mMaster.newSession();
        return mDaosession;
    }
}
