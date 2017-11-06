package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SessionStoreGsonRequest;
import com.example.chen1.uncom.utils.SessionStoreJsonRequest;
import com.example.chen1.uncom.utils.UserBeanAndJsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RequestBuildRelationShipFragment extends Fragment implements View.OnTouchListener{

    private AppCompatButton senButton;
    private UserBean userBean;
    private AppCompatImageView back_icon;
    private TextView requestMessgae;
    private NewRelationShipBean data;
    private NewRelationShipBean frendData;
    private static RequestBuildRelationShipFragment requestBuildRelationShipFragmen=null;
    public RequestBuildRelationShipFragment() {
        // Required empty public constructor
    }


    public static RequestBuildRelationShipFragment getInstance(){
        if(requestBuildRelationShipFragmen==null){
            requestBuildRelationShipFragmen=new RequestBuildRelationShipFragment();
        }
        return requestBuildRelationShipFragmen;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_request_build_relation_ship, container, false);
        requestMessgae= (EditText) view.findViewById(R.id.request_meessage);
        requestMessgae.setText("你好 我是"+ CoreApplication.newInstance().getUserBean().getUsername());
        requestMessgae.requestFocus();
        senButton= (AppCompatButton) view.findViewById(R.id.request_send_button);
        userBean=CoreApplication.newInstance().getUserBean();
        back_icon= (AppCompatImageView) view.findViewById(R.id.request_back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_translate_open,R.anim.default_leave_left);
                fragmentManager.popBackStack();
            }
        });


        senButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("发送加好友请求","ok");
                data=new NewRelationShipBean();
                data.setUser_id(userBean.getId());
                data.setView_type(1);
                data.setType(frendData.getType());
                data.setHeader_pic(userBean.getHeader_pic());
                data.setGet_time(new Date());
                data.setShort_message(requestMessgae.getText().toString());
                data.setSex(userBean.getSex());
                data.setSprovince(userBean.getSprovince());
                data.setStown(userBean.getStown());
                data.setUser_name(userBean.getUsername());
                Log.v("RequesBuildRelation", String.valueOf(data));
                JsonObject jsonObject = new Gson().toJsonTree(data).getAsJsonObject();
                jsonObject.addProperty("target_id",frendData.getUser_id());
                Message message=new Message();
                message.what=2;
                message.obj=jsonObject;
                CoreApplication.newInstance().getCoreService().getSendChatHandler().sendMessage(message);
                PopupWindowUtils.popupWindow("请求已发送",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,getContext(),getView());
                 CountDownTimer countDownTimer=new CountDownTimer(1000,10) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_translate_open,R.anim.default_leave_left);
                        fragmentManager.popBackStack();

                    }
                };

                countDownTimer.start();



            }
        });
       view.setOnTouchListener(this);
        return view;
    }


    public NewRelationShipBean getFrendData() {
        return frendData;
    }

    public void setFrendData(NewRelationShipBean frendData) {
        this.frendData = frendData;
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

}
