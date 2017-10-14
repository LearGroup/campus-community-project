package com.example.chen1.uncom.relationship;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.chen1.uncom.R;

/**
 * Created by chen1 on 2017/10/13.
 */

public class NewRelationShipButtonOnclickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        NewRelationShipFragment newRelationShipFragment =NewRelationShipFragment.getInstance();
        FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,newRelationShipFragment).commit();

    }
}
