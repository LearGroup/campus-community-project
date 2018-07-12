package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;

/**
 * Created by chen1 on 2017/10/20.
 */

public class ToRequestBuildRelationShipButtonOnClickListener implements View.OnClickListener {
    private Context context;
    private SearchResultPersonDetailFragment fragment;
    private NewRelationShipBean frendData;
    public ToRequestBuildRelationShipButtonOnClickListener(Context context,SearchResultPersonDetailFragment fragment,NewRelationShipBean frendData){
        this.context=context;
        this.fragment=fragment;
        this.frendData=frendData;
    }

    @Override
    public void onClick(View v) {
        RequestBuildRelationShipFragment requestBuildRelationShipFragment =RequestBuildRelationShipFragment.getInstance();
        CoreApplication.newInstance().setDisPlayType(false);
        requestBuildRelationShipFragment.setFrendData(frendData);
        FragmentManager fragmentManager= NewRelationShipFragment.getInstance().getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null).setCustomAnimations(
                R.anim.default_fragment_switch_translate_open, R.anim.default_fragment_switch_translate_open,
                R.anim.default_fragment_switch_leave_translate, R.anim.default_fragment_switch_leave_translate  )
                .add(R.id.drawer_layout,requestBuildRelationShipFragment,"newRelationShipFragment");
        fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,R.anim.default_leave_left,
                R.anim.default_open_right,R.anim.default_open_right).hide(fragment)
                .commit();

    }
}
