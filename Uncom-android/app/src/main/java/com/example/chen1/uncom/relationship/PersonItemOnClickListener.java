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
    private Fragment fragment ;
    public PersonItemOnClickListener(Fragment fragment ,ArrayList<RelationShipLevelBean> relationShipLevelBeanArrayList){
        this.personFrendList=relationShipLevelBeanArrayList;
        this.fragment=fragment;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BadgeMessageUtil.setSetPageIsVisible(false);
        FragmentManager fragmentManager= fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        PersonChatFragment person_chat_fragment= (PersonChatFragment) CoreApplication.newInstance().getTemperFragment();
        if(person_chat_fragment!=null){
            Log.v("第一种","...............ok");
            person_chat_fragment.setFrendData(personFrendList.get(position));
            fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,person_chat_fragment,"chatPage").setCustomAnimations(R.anim.default_fragment_switch_leave_translate, R.anim.default_leave_left, R.anim.default_open_right, R.anim.default_fragment_switch_translate_open).commit();

        }else{
            Log.v("第二种","...............ok");
            person_chat_fragment = PersonChatFragment.getInstance();
            CoreApplication.newInstance().setTemperFragment(person_chat_fragment);
            fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,person_chat_fragment,"chatPage").setCustomAnimations(R.anim.default_fragment_switch_leave_translate, R.anim.default_leave_left, R.anim.default_open_right, R.anim.default_fragment_switch_translate_open).commit();

        }
        person_chat_fragment.setFrendData(personFrendList.get(position));
         CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(view.getContext(),R.anim.default_leave_left));
    }
}
