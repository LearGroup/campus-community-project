package com.example.chen1.uncom.relationship;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.utils.BadgeMessageUtil;

/**
 * Created by chen1 on 2017/10/13.
 */

public class ToNewRelationShipButtonOnclickListener implements View.OnClickListener {

    private FragmentManager fragmentManager;
    public ToNewRelationShipButtonOnclickListener(FragmentManager fragmentManager){
    this.fragmentManager=fragmentManager;
    }

    @Override
    public void onClick(View v) {
        BadgeMessageUtil.setSetPageIsVisible(false);
        NewRelationShipFragment newRelationShipFragment =NewRelationShipFragment.getInstance();
        CoreApplication.newInstance().setDisPlayType(false);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null).setCustomAnimations(
                R.anim.default_fragment_switch_translate_open, R.anim.default_fragment_switch_translate_open,
                R.anim.default_fragment_switch_leave_translate, R.anim.default_fragment_switch_leave_translate  )
                .add(R.id.drawer_layout,newRelationShipFragment,"newRelationShipFragment").commit();
        CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left));
    }
}
