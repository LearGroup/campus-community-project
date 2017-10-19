package com.example.chen1.uncom.set;

import android.view.View;

import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.relationship.NewRelationshipAdapter;

/**
 * Created by chen1 on 2017/10/17.
 */

public class OnItemClickListener implements NewRelationshipAdapter.OnItemClickListener {
    @Override
    public void onClick(View view, int positon, NewRelationShipBean newRelationShipBean) {
        //发送网络请求
        if(positon==0 && newRelationShipBean.getView_type()==0){

        }else{

        }
    }
}
