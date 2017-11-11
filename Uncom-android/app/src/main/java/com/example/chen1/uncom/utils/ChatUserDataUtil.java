package com.example.chen1.uncom.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.relationship.NewRelationShipSearchResultsAdapter;
import com.example.chen1.uncom.relationship.NewRelationShipSearchResultsOnItenClickListener;
import com.example.chen1.uncom.relationship.NewRelationshipAdapter;
import com.example.chen1.uncom.relationship.NewRelationshipSearchResultsFragment;
import com.example.chen1.uncom.utils.UserBeanAndJsonUtils;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SessionStoreJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen1 on 2017/10/3.
 */

public class ChatUserDataUtil {


    /**
     *获取用户好友列表
     * @param requestQueue  volley 请求队列
     * @param context
     * @param rootView
     */
        public static void getFriendList(final RequestQueue requestQueue, final Context context, final View rootView){
            Log.v("getFriendlist","start");
            Map<String, String> map = new HashMap<String, String>();
            map.put("use", "phone");
            JSONObject params = new JSONObject(map);
            final JSONArray jsonArray;
             /*http://10.0.2.2:8081 47.95.0.73*/
            SessionStoreJsonRequest sessionStoreJsonRequest =new SessionStoreJsonRequest("http://"+CoreApplication.newInstance().IP_ADDR+":8081/getFrendList",
                    params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getString("status").equals("1")){
                            RelationShipLevelBeanDao relationShipLevelBeanDao=BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                            try {
                                ArrayList<RelationShipLevelBean> Array= UserBeanAndJsonUtils.getRelationShipLevelBean(response.getJSONArray("results"));
                                Log.v("ArraySize", String.valueOf(Array.size()));
                                if(Array.size()>0){
                                    for (int i = 0; i <Array.size() ; i++) {
                                        Log.v("好友name:",Array.get(i).getUsername());
                                        RelationShipLevelBean bean=relationShipLevelBeanDao.queryBuilder().where(RelationShipLevelBeanDao.Properties.Id.eq(Array.get(i).getId())).build().unique();
                                        if(bean==null){
                                            relationShipLevelBeanDao.insert(Array.get(i));
                                        }else{
                                            relationShipLevelBeanDao.update(Array.get(i));
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    PopupWindowUtils.popupWindow("网络错误", R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,context,rootView);
                }
            });
            requestQueue.add(sessionStoreJsonRequest);
        }




    public static void searchUser(final RequestQueue requestQueue, final Context context, final View rootView, JSONObject params, final PopupWindow popupWindow, final NewRelationShipSearchResultsAdapter newRelationShipSearchResultsAdapter, final TextView resultsNone, final NewRelationshipSearchResultsFragment fragment) throws JSONException {


        ArrayList<NewRelationShipBean> array=new ArrayList<>();
        for(RelationShipLevelBean item :CoreApplication.newInstance().getPersonFrendList()){
            Log.v("寻找中",".......");
            if((params.getString("use").equals("phone") && params.getString("result").equals(item.getPhone()))||(params.getString("use").equals("email") && params.getString("result").equals(item.getEmail()))){
                resultsNone.setVisibility(View.VISIBLE);
                if(popupWindow.isShowing()==true){
                    popupWindow.dismiss();
                }

                newRelationShipSearchResultsAdapter.setData(array);
                newRelationShipSearchResultsAdapter.notifyDataSetChanged();
                newRelationShipSearchResultsAdapter.setOnItenClickListener(new NewRelationShipSearchResultsOnItenClickListener(context,fragment));

                Log.v("已找到","ok");
                return ;
            }
        }


        final SessionStoreJsonRequest sessionStoreJsonRequest=new SessionStoreJsonRequest("http://"+CoreApplication.newInstance().IP_ADDR+":8081/searchUser", params,
                new Response.Listener<JSONObject>() {
                    @Override
                            public void onResponse(JSONObject response) {
                            ArrayList<NewRelationShipBean> array=new ArrayList<>();
                                Log.v("searchUserResponse", String.valueOf(response));
                                try {

                                    if(response.getString("status").equals("1")){
                                         array=UserBeanAndJsonUtils.getNewRelationShipBean(response.getJSONArray("results"));
                                        if(array.size()==1 && array.get(0).getResults().equals(CoreApplication.newInstance().getUserBean().getEmail())){
                                            resultsNone.setVisibility(View.VISIBLE);
                                            array.clear();
                                        }else if(array.size()==1 && array.get(0).getResults().equals(CoreApplication.newInstance().getUserBean().getPhone())){

                                            resultsNone.setVisibility(View.VISIBLE);
                                            array.clear();
                                        }else if(array.size()>0){
                                            for (int i = 0; i <array.size() ; i++) {
                                                array.get(i).setView_type(2);
                                            }
                                        }
                                    }else if(response.getString("status").equals("0") ||response.getString("status").equals("-1") ){
                                        Log.v("显示为未搜索到","ok");
                                        resultsNone.setVisibility(View.VISIBLE);
                                    }
                                    newRelationShipSearchResultsAdapter.setData(array);
                                    newRelationShipSearchResultsAdapter.notifyDataSetChanged();
                                    newRelationShipSearchResultsAdapter.setOnItenClickListener(new NewRelationShipSearchResultsOnItenClickListener(context,fragment));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(popupWindow.isShowing()==true){
                            popupWindow.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(popupWindow.isShowing()==true){
                    popupWindow.dismiss();
                }
                newRelationShipSearchResultsAdapter.setOnItenClickListener(new NewRelationShipSearchResultsOnItenClickListener(context,fragment));
                PopupWindowUtils.popupWindowNormal("网络错误", R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,context,rootView);
                Log.e("LOGIN-ERROR", error.getMessage(), error);
            }
        });
        CountDownTimer timer=new CountDownTimer(1000,10) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                requestQueue.add(sessionStoreJsonRequest);
            }
        };
        timer.start();
    }


    /**
     *注册新关系
     * @param min_id 同意者id
     * @param req_id 请求者id
     * @param context
     * @param waitBar 等待barView
     * @param acceptOk 接受textView
     */
    public static void registerPersonRelationShip(final RequestQueue requestQueue, Context context, String min_id, String req_id, final ProgressBar waitBar, final TextView acceptOk, final Button acceptButton){
        HashMap<String ,String> map=new HashMap<>();
        map.put("min_id",min_id);
        map.put("req_id",req_id);
        JSONObject params = new JSONObject(map);
        final SessionStoreJsonRequest sessionStoreJsonRequest=new SessionStoreJsonRequest("http://"+CoreApplication.newInstance().IP_ADDR+":8081/registerPersonRelationShip", params,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RelationShipLevelBeanDao relationShipLevelBeanDao=BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();

                Log.v("searchUserResponse", String.valueOf(response));
                try {
                    if(response.getString("status").equals("1")){

                        ArrayList<RelationShipLevelBean> Array= UserBeanAndJsonUtils.getRelationShipLevelBean(response.getJSONArray("results"));
                        Log.v("ArraySize", String.valueOf(Array.size()));
                        if(Array.size()>0){
                            for (int i = 0; i <Array.size() ; i++) {
                                RelationShipLevelBean bean=relationShipLevelBeanDao.queryBuilder().where(RelationShipLevelBeanDao.Properties.Id.eq(Array.get(i).getId())).build().unique();
                                if(bean==null){
                                    //若关系不存在  更新好友请求界面 好友界面
                                    for (int j = 0; j <CoreApplication.newInstance().getNewRelationShipList().size() ; j++) {
                                      if(CoreApplication.newInstance().getNewRelationShipList().get(i).getUser_id().equals(Array.get(i).getMinor_user())){
                                          CoreApplication.newInstance().getNewRelationShipList().get(i).setResult_type(1);
                                          CoreApplication.newInstance().addRelationShipLevelBean(Array.get(i));
                                          JSONArray jsonArray1=new JSONArray();
                                          JSONObject json=new JSONObject();
                                          json.put("ownId",Array.get(i).getMinor_user());
                                          json.put("targetId",CoreApplication.newInstance().getUser_id());
                                          json.put("content", CoreApplication.newInstance().getNewRelationShipList().get(i).getShort_message());
                                          Long time=new Date().getTime();
                                          json.put("time", String.valueOf(time));
                                          jsonArray1.put(json);
                                          Message messages=new Message();
                                          messages.what=0;
                                          messages.obj=jsonArray1;
                                          //Log.v("接受好友Message", String.valueOf(jsonArray1 ));
                                          CoreApplication.newInstance().getCoreAppGetChatDataHandler().sendMessage(messages);
                                          BeanDaoManager.getInstance().getDaoSession().getNewRelationShipBeanDao().update(CoreApplication.newInstance().getNewRelationShipList().get(i));
                                      }
                                    }
                                }else{
                                    relationShipLevelBeanDao.update(Array.get(i));
                                }
                               // Log.v("listitem", String.valueOf(Array.get(i).getId()));

                            }
                            //Log.v("registerPersonRelationShip", String.valueOf(response));
                            waitBar.setVisibility(View.GONE);
                            acceptOk.setVisibility(View.VISIBLE);
                        }else{
                            acceptButton.setVisibility(View.VISIBLE);
                            waitBar.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                        e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        sessionStoreJsonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, 1.0f));
        requestQueue.add(sessionStoreJsonRequest);

    }

}
