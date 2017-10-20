package com.example.chen1.uncom.chat;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
import com.example.chen1.uncom.relationship.NewRelationshipSearchResultsFragment;
import com.example.chen1.uncom.utils.UserBeanAndJsonUtils;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SessionStoreJsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
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
             /*http://10.0.2.2:8081*/
            SessionStoreJsonRequest sessionStoreJsonRequest =new SessionStoreJsonRequest("http://10.0.2.2:8081/getFrendList",
                    params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.v("getFrendListResponse0", String.valueOf(response));
                    try {
                        if(response.getString("status").equals("1")){
                            RelationShipLevelBeanDao relationShipLevelBeanDao=BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                            try {
                                ArrayList<RelationShipLevelBean> Array= UserBeanAndJsonUtils.getRelationShipLevelBean(response.getJSONArray("results"));
                                Log.v("ArraySize", String.valueOf(Array.size()));
                                if(Array.size()>0){
                                    for (int i = 0; i <Array.size() ; i++) {
                                        RelationShipLevelBean bean=relationShipLevelBeanDao.queryBuilder().where(RelationShipLevelBeanDao.Properties.Id.eq(Array.get(i).getId())).build().unique();
                                        if(bean==null){
                                            relationShipLevelBeanDao.insert(Array.get(i));
                                        }else{
                                            relationShipLevelBeanDao.update(Array.get(i));
                                        }
                                        Log.v("listitem", String.valueOf(Array.get(i).getId()));

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




    public static void searchUser(final RequestQueue requestQueue, final Context context, final View rootView, JSONObject params, final PopupWindow popupWindow, final NewRelationShipSearchResultsAdapter newRelationShipSearchResultsAdapter, final TextView resultsNone, final NewRelationshipSearchResultsFragment fragment){

        final SessionStoreJsonRequest sessionStoreJsonRequest=new SessionStoreJsonRequest("http://10.0.2.2:8081/searchUser", params,
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
                                    }else if(response.getString("status").equals("0") ){
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
}
