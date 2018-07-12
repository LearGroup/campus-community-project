package com.example.chen1.uncom.relationship;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.utils.BadgeMessageUtil;

import java.util.ArrayList;

/**
 * Created by chen1 on 2017/6/21.
 */

public class PersonItemOnClickListener implements AdapterView.OnItemClickListener {
    private ArrayList<RelationShipLevelBean> personFrendList;
    private FragmentManager fragmentManager ;
    public PersonItemOnClickListener(FragmentManager fragmentManager ,ArrayList<RelationShipLevelBean> relationShipLevelBeanArrayList){
        this.personFrendList=relationShipLevelBeanArrayList;
        this.fragmentManager=fragmentManager;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BadgeMessageUtil.setSetPageIsVisible(false);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PersonChatFragment person_chat_fragment=PersonChatFragment.newInstance(personFrendList.get(position));
        if(person_chat_fragment!=null){
            Log.v("第一种","...............ok");
            person_chat_fragment.setFrendData(personFrendList.get(position));
            fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,person_chat_fragment,"chatPage").setCustomAnimations(R.anim.default_fragment_switch_leave_translate, R.anim.default_leave_left, R.anim.default_open_right, R.anim.default_fragment_switch_translate_open).commit();
            CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left));
        }
    }
}
