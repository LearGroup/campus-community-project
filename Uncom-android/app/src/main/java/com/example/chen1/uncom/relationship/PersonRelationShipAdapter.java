package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.LoadLocalImage;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/6/21.
 */

public class PersonRelationShipAdapter extends BaseAdapter {
    private Context context;
    private LoadImageUtils loadImageUtils;
    private ArrayList<RelationShipLevelBean> data=null;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    public PersonRelationShipAdapter(Context context , ArrayList<RelationShipLevelBean> dataList,FragmentManager fragmentManager,Fragment fragment){
        this.data=dataList;
        this.fragment=fragment;
        this.context=context;
        this.fragmentManager=fragmentManager;
        loadImageUtils=new LoadImageUtils();
    }

    private TextView username;
    private ImageView headImage;


    public ArrayList<RelationShipLevelBean> getData() {
        return data;
    }

    public void setData(ArrayList<RelationShipLevelBean> data) {
        this.data = data;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if(data==null){
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        ConstraintLayout constraintLayout=(ConstraintLayout)layoutInflater.inflate(R.layout.person_relation_ship_item_layout,null);
        headImage=(ImageView) constraintLayout.findViewById(R.id.appCompatImageView3);
        username=(TextView) constraintLayout.findViewById(R.id.person_username);
        username.setText(data.get(position).getUsername());
        loadImageUtils.getFirendHeaderImage(data.get(position).getHeader_pic(),headImage,fragment);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDetailedInformationFragment fragment = PersonDetailedInformationFragment.newInstance(data.get(position));
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,fragment).setCustomAnimations(R.anim.default_fragment_switch_leave_translate, R.anim.default_leave_left, R.anim.default_open_right, R.anim.default_fragment_switch_translate_open).commit();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left));
            }
        });

        return constraintLayout;
    }
}
