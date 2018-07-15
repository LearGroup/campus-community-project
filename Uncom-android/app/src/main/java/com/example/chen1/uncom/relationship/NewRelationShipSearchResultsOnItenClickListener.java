package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;

import java.net.ConnectException;

/**
 * Created by chen1 on 2017/10/19.
 */

public class NewRelationShipSearchResultsOnItenClickListener implements  NewRelationShipSearchResultsAdapter.OnItemOnClickListener {

    private Context context;
    private NewRelationshipSearchResultsFragment fragment;
    public NewRelationShipSearchResultsOnItenClickListener(Context context,NewRelationshipSearchResultsFragment fragment){
        this.context=context;
        this.fragment=fragment;
    }


    @Override
    public void OnClick(View view, int positon, NewRelationShipBean newRelationShipBean) {
        if(newRelationShipBean.getView_type()==0){
            fragment.RequestData();
        }else{
            //跳转详情fragment
            if(newRelationShipBean.getView_type()==2){
                SearchResultPersonDetailFragment searchResultPersonDetailFragment =SearchResultPersonDetailFragment.getInstance();
                CoreApplication.newInstance().setDisPlayType(false);
                searchResultPersonDetailFragment.setFrendData(newRelationShipBean);
                FragmentManager fragmentManager=fragment.getFragmentManager() ;
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null).setCustomAnimations(
                        R.anim.default_fragment_switch_translate_open, R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate, R.anim.default_fragment_switch_leave_translate  )
                        .add(R.id.drawer_layout,searchResultPersonDetailFragment,"newRelationShipFragment");
                fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,R.anim.default_leave_left,
                        R.anim.default_open_right,R.anim.default_open_right).hide(fragment)
                        .commit();

            }
        }
    }
}
