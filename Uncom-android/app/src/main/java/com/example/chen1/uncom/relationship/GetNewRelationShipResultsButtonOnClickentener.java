package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.utils.KeybordUtil;

/**
 * Created by chen1 on 2017/10/17.
 */

public class GetNewRelationShipResultsButtonOnClickentener implements  NewRelationshipAdapter.OnItemClickListener {

    private NewRelationShipFragment fragment;
    private Context context;
    private NewRelationshipSearchResultsFragment newRelationshipSearchResultsFragment;
    private SearchResultPersonDetailFragment searchResultPersonDetailFragment;

    public GetNewRelationShipResultsButtonOnClickentener(Context context,NewRelationShipFragment fragment){
        this.fragment=fragment;
        this.context=context;
    }


    @Override
    public void onClick(View view, int positon, final NewRelationShipBean newRelationShipBean) {
        Log.v("GetNewRelationShipResultsButtonOnClickentener", String.valueOf(newRelationShipBean.getView_type()));
        if(positon==0&& newRelationShipBean.getView_type()==0){
            CoreApplication.newInstance().setDisPlayType(true);
            KeybordUtil.closeKeybordSearch(fragment.getSearchView(),fragment.getContext());
            CountDownTimer timer=new CountDownTimer(400,10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    FragmentManager fragmentManager= fragment.getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    newRelationshipSearchResultsFragment= (NewRelationshipSearchResultsFragment) fragmentManager.findFragmentByTag("newRelationshipSearchResultsFragment");
                    if(newRelationshipSearchResultsFragment!=null){
                        newRelationshipSearchResultsFragment.setSearchResult(newRelationShipBean.getResults());
                        fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                                R.anim.default_fragment_switch_translate_open,
                                R.anim.default_fragment_switch_translate_open,
                                R.anim.default_fragment_switch_leave_translate).show(newRelationshipSearchResultsFragment);
                    }else{
                        newRelationshipSearchResultsFragment =NewRelationshipSearchResultsFragment.getInstance();
                        newRelationshipSearchResultsFragment.setSearchResult(newRelationShipBean.getResults());
                        fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                                R.anim.default_fragment_switch_translate_open,
                                R.anim.default_fragment_switch_translate_open,
                                R.anim.default_fragment_switch_leave_translate).add(R.id.drawer_layout,newRelationshipSearchResultsFragment,"newRelationshipSearchResultsFragment");

                    }
                     fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,R.anim.default_leave_left,
                            R.anim.default_open_right,R.anim.default_open_right).hide(fragment)
                            .commit();
                }
            };
            timer.start();
        }else{
            FragmentManager fragmentManager= fragment.getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            searchResultPersonDetailFragment= (SearchResultPersonDetailFragment) fragmentManager.findFragmentByTag("newRelationshipSearchResultsFragment");
            if(searchResultPersonDetailFragment!=null){
                searchResultPersonDetailFragment.setFrendData(newRelationShipBean);
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate).show(searchResultPersonDetailFragment);
            }else{
                searchResultPersonDetailFragment =SearchResultPersonDetailFragment.getInstance();
                searchResultPersonDetailFragment.setFrendData(newRelationShipBean);
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate).add(R.id.drawer_layout,searchResultPersonDetailFragment,"newRelationshipSearchResultsFragment");

            }
            fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,R.anim.default_leave_left,
                    R.anim.default_open_right,R.anim.default_open_right).hide(fragment)
                    .commit();

        }

    }
}
