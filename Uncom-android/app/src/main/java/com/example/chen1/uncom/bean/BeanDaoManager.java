package com.example.chen1.uncom.bean;


import android.app.Application;
import android.content.Context;

import java.security.AccessControlContext;

/**
 * Created by chen1 on 2017/10/2.
 */

public class BeanDaoManager {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private static volatile BeanDaoManager instance =null;
    public BeanDaoManager(Context context){
        if(instance==null){
            DaoMaster.DevOpenHelper devOpenHelper =new
                    DaoMaster.DevOpenHelper(context,"user.db");
            daoMaster= new DaoMaster(devOpenHelper.getWritableDatabase());
            daoSession=daoMaster.newSession();
        }
    }

    public static BeanDaoManager getInstance(Context context){
        if(instance==null){
            synchronized (BeanDaoManager.class){
                instance=new BeanDaoManager(context);
            }
        }
        return instance;
    }


    public DaoMaster getDaoMaster() {
        return daoMaster;
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoSession getNewSession(){
        daoSession=daoMaster.newSession();
        return daoSession;
    }
}
