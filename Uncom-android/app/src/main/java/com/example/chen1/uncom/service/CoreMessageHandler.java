package com.example.chen1.uncom.service;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.chen1.uncom.access.AccessActivity;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.relationship.PersonRelationShipAdapter;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.set.SetPageMainFragmentAdapter;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

/**
 *
 * Created by chen1 on 2018/2/21.
 */

public class CoreMessageHandler extends Handler {

    public CoreMessageHandler(){

    }
    @Override
    public void handleMessage(Message msg) {
        NewRelationShipBean newRelationShipBean;
        RelationShipLevelBean shipLevelBean;
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                Log.v("BadgeCount:", String.valueOf(BadgeMessageUtil.getItem_1()+1));
                if(CoreApplication.newInstance().basePagerPosition!=0) {
                    BadgeMessageUtil.setItem_1(BadgeMessageUtil.getItem_1()+1);
                }
                break;
            case 2:
                //退出登陆逻辑
                Log.v("coreApplication:","退出登陆");
                SharedPreferencesUtil.delUsetId(CoreApplication.newInstance().getApplicationContext());
                RalationShipPageMainFragment.getInstance().InitTag=true;
                CoreApplication.newInstance().setPersonFrendList(null);
                CoreApplication.newInstance().getApplicationContext().unbindService(CoreApplication.newInstance().getServiceConnection());
                CoreApplication.newInstance().getApplicationContext().stopService(CoreApplication.newInstance().startIntent);
                Intent intent=new Intent(CoreApplication.newInstance().getApplicationContext(), AccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CoreApplication.newInstance().startActivity(intent);
                CoreApplication.newInstance().getActivity().finish();
                break;
            //System.exit(0);

            case 1:
                //执行Set页面UI更新
                if(CoreApplication.newInstance().getSetPageMainFragmentAdapter()!=null){
                    CoreApplication.newInstance().getSetPageMainFragmentAdapter().notifyDataSetChanged();
                }
                break;
            case 4:
                //执行新关系请求页面更新
                Log.v("执行新关系请求页面更新","ok");
                newRelationShipBean=(NewRelationShipBean) msg.obj;
                CoreApplication.newInstance().setNewRelationActive(CoreApplication.newInstance().getNewRelationActive()+1);
                CoreApplication.newInstance().addNewRelationShip(newRelationShipBean,1);

                break;
            case 5:
                //同步信息时之行新关系请求页面更新
                newRelationShipBean=(NewRelationShipBean) msg.obj;
                CoreApplication.newInstance().addNewRelationShip(newRelationShipBean,0);
                break;
            case 6:
                //好友请求发送后 同意时，本地添加新好友
                shipLevelBean=(RelationShipLevelBean)msg.obj;
                CoreApplication.newInstance().addRelationShipLevelBean(shipLevelBean);
                break;


        }
    }
}

//new Handler(){
//@Override
//public void handleMessage(Message msg) {
//        NewRelationShipBean newRelationShipBean;
//        RelationShipLevelBean shipLevelBean;
//        super.handleMessage(msg);
//        switch (msg.what){
//        case 0:
//        Log.v("BadgeCount:", String.valueOf(BadgeMessageUtil.getItem_1()+1));
//        if(basePagerPosition!=0) {
//        BadgeMessageUtil.setItem_1(BadgeMessageUtil.getItem_1()+1);
//        }
//        break;
//        case 2:
//        //退出登陆逻辑
//        Log.v("coreApplication:","退出登陆");
//        SharedPreferencesUtil.delUsetId(getApplicationContext());
//        RalationShipPageMainFragment.getInstance().InitTag=true;
//        personFrendList=null;
//        getApplicationContext().unbindService(serviceConnection);
//        getApplicationContext().stopService(startIntent);
//        Intent intent=new Intent(getApplicationContext(), AccessActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        activity.finish();
//        break;
//        //System.exit(0);
//
//        case 1:
//        //执行Set页面UI更新
//        if(setPageMainFragmentAdapter!=null){
//        setPageMainFragmentAdapter.notifyDataSetChanged();
//        }
//        break;
//        case 3:
//        //执行关系页面个人关系UI更新
//        if(PersonRelationShipAdapter !=null){
//        PersonRelationShipAdapter.notifyDataSetChanged();
//        }
//        break;
//        case 4:
//        //执行新关系请求页面更新
//        Log.v("执行新关系请求页面更新","ok");
//        newRelationShipBean=(NewRelationShipBean) msg.obj;
//        setNewRelationActive(getNewRelationActive()+1);
//        addNewRelationShip(newRelationShipBean,1);
//
//        break;
//        case 5:
//        //同步信息时之行新关系请求页面更新
//        newRelationShipBean=(NewRelationShipBean) msg.obj;
//        addNewRelationShip(newRelationShipBean,0);
//        break;
//        case 6:
//        //好友请求发送后 同意时，本地添加新好友
//        shipLevelBean=(RelationShipLevelBean)msg.obj;
//        addRelationShipLevelBean(shipLevelBean);
//        break;
//
//
//        }
//        }
//        };