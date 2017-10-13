package com.example.chen1.uncom.relationship;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.chat.Person_Chat_Fragment;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;

import java.util.ArrayList;

/**
 * Created by chen1 on 2017/6/21.
 */

public class Person_Item_OnClickListener implements AdapterView.OnItemClickListener {
    private ArrayList<RelationShipLevelBean> personFrendList;

    public Person_Item_OnClickListener(ArrayList<RelationShipLevelBean> relationShipLevelBeanArrayList){
        this.personFrendList=relationShipLevelBeanArrayList;
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
        Person_Chat_Fragment person_chat_fragment =Person_Chat_Fragment.getInstance();
        person_chat_fragment.setFrendData(personFrendList.get(position));
        FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,person_chat_fragment).commit();
    }
}
