package com.example.chen1.uncom.relationDynamics;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.CycleInterpolator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.bean.PersonDynamicsBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.previewAlbum.AlbumEdit;
import com.example.chen1.uncom.utils.SessionStoreJsonRequest;
import com.example.chen1.uncom.utils.StateCode;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by chen1 on 2018/2/26.
 */

public class ItemGestorListener extends GestureDetector.SimpleOnGestureListener {

    private Fragment fragment;
    private View v;
    private String type;
    private Object bean;
    private DynamicsAdapter dynamicsAdapter;
    private FragmentTransaction fragmentTransaction;
    private AppCompatImageView bigLike;
    public ItemGestorListener(){}

    @SuppressLint("ResourceType")
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return  false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if(type.equals("image")){
            if(bean instanceof  PersonDynamicsBean){
                PersonDynamicsBean bean1=null;
                bean1= (PersonDynamicsBean) bean;
                Log.v("json",bean1.getLike());
                JSONArray likeJsonArray= JSONArray.parseArray(bean1.getLike());
                if(bean1.getLike().indexOf(CoreApplication.newInstance().getUser_id())!=-1){
                    for (int i = 0; i < likeJsonArray.size(); i++) {
                        JSONObject jsonObject= (JSONObject) likeJsonArray.get(i);
                        if(jsonObject.containsKey(CoreApplication.newInstance().getUser_id())){
                            likeJsonArray.remove(i);
                            bean1.setLike(likeJsonArray.toJSONString());
                            dynamicsAdapter.syncItem(bean1, (int)v.getTag(R.id.beanid));
                        }
                    }
                }else{
                    Long time=new Date().getTime();
                    String str="{"+"\""+CoreApplication.newInstance().getUser_id()+"\""+":"+"\""+time+"\""+"}";
                    JSONObject jsonObject= (JSONObject) JSONObject.parse(str);
                    likeJsonArray.add(jsonObject);
                    bean1.setLike(bean1.getLike().replace("]",str+"]"));
                    bean1.setLike_count(bean1.getLike_count()+1);
                    dynamicsAdapter.setBigLikeAnimator(true).setLikeBtnAnimator(true).syncItem(bean1, (int)v.getTag(R.id.beanid));
                    org.json.JSONObject object=new org.json.JSONObject();
                    try {
                        Log.v("send","data");
                        object.put("type", StateCode.PERSON_DYNAMICS);
                        object.put("motion",StateCode.PERSON_DYNAMICS_LIKE);
                        object.put("u_id",CoreApplication.newInstance().getUser_id());
                        object.put("date",time);
                        object.put("id",bean1.getId());
                        object.put("username",CoreApplication.newInstance().getUserBean().getUsername());
                        object.put("header_pic",CoreApplication.newInstance().getUserBean().getHeader_pic());
                        object.put("updateList", JSONArray.toJSONString(getPersosnFrendId()));
                        final PersonDynamicsBean finalBean = bean1;
                        SessionStoreJsonRequest sessionStoreJsonRequest =new SessionStoreJsonRequest("http://" + CoreApplication.newInstance().IP_ADDR + ":8081/syncDynamicsMessage",
                                object, (response)-> {
                                String status= null;
                                try {
                                    status = response.getString("status");
                                    if(status.equals("1")) {
                                        Log.v("发表成功","success");
                                        PersonDynamicsBeanDao dao= BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
                                        dao.update(finalBean);
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                        },(error)->{

                        });
                        Log.v("parse","online");
                        sessionStoreJsonRequest.setRetryPolicy(new DefaultRetryPolicy(30*1000,0,0f));
                        CoreApplication.newInstance().getRequestQueue().add(sessionStoreJsonRequest);

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                return  true;
            }
        }
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.v("onclick","onSingleTapConfirmed");
        if(type.equals("likeBtn")){
            PersonDynamicsBean bean1=null;
            if(bean instanceof  PersonDynamicsBean){
                bean1= (PersonDynamicsBean) bean;
                Log.v("json",bean1.getLike());
                JSONArray likeJsonArray= JSONArray.parseArray(bean1.getLike());
                if(bean1.getLike().indexOf(CoreApplication.newInstance().getUser_id())!=-1){
                    for (int i = 0; i < likeJsonArray.size(); i++) {
                        JSONObject jsonObject= (JSONObject) likeJsonArray.get(i);
                        if(jsonObject.containsKey(CoreApplication.newInstance().getUser_id())){
                            likeJsonArray.remove(i);
                            bean1.setLike(likeJsonArray.toJSONString());
                            dynamicsAdapter.syncItem(bean1, (int)v.getTag(R.id.beanid));
                        }
                    }
                }else{
                    String str="{"+"\""+CoreApplication.newInstance().getUser_id()+"\""+":"+"\""+new Date().getTime()+"\""+"}";
                    JSONObject jsonObject= (JSONObject) JSONObject.parse(str);
                    likeJsonArray.add(jsonObject);
                    bean1.setLike(bean1.getLike().replace("]",str+"]"));
                    dynamicsAdapter.setBigLikeAnimator(false).setLikeBtnAnimator(true).syncItem(bean1, (int)v.getTag(R.id.beanid));
                }
            }
        }else if(type.equals("commentBtn")){
            Log.v("click","commentBtn");
            if(bean instanceof  PersonDynamicsBean){

                fragmentTransaction = fragment.getFragmentManager().beginTransaction();
                PersonDynamicComment personDynamicComment;
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_open_up,
                        R.anim.null_translate,
                        R.anim.null_translate,
                        R.anim.default_fragment_leave_down
                );
                personDynamicComment= PersonDynamicComment.newInstance((PersonDynamicsBean) bean);
                fragmentTransaction.add(R.id.rootview, personDynamicComment, "PersonDynamicComment").commitAllowingStateLoss();

            }


        }else if(type.equals("image")){
            fragmentTransaction = fragment.getFragmentManager().beginTransaction();
            AlbumEdit albumEdit;
            fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_open_up,
                    R.anim.null_translate,
                    R.anim.null_translate,
                    R.anim.default_fragment_leave_down
            );
            albumEdit= AlbumEdit.newInstance(dynamicsAdapter.getImageUrlList(bean),(int)v.getTag(R.id.imageid));
            albumEdit.setEdit(false);
            fragmentTransaction.add(R.id.rootview, albumEdit, "AlbumEdit").commitAllowingStateLoss();
        }
        return true;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public ItemGestorListener setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public View getV() {
        return v;
    }

    public ItemGestorListener setV(View v) {
        this.v = v;
        return this;
    }

    public String getType() {
        return type;
    }

    public ItemGestorListener setType(String type) {
        this.type = type;
        return this;
    }

    public Object getBean() {
        return bean;
    }

    public ItemGestorListener setBean(Object bean) {
        this.bean = bean;
        return this;
    }

    public DynamicsAdapter getDynamicsAdapter() {
        return dynamicsAdapter;
    }

    public ItemGestorListener setDynamicsAdapter(DynamicsAdapter dynamicsAdapter) {
        this.dynamicsAdapter = dynamicsAdapter;
        return this;
    }

    public String getPersosnFrendId(){
        String string="";
        ArrayList<RelationShipLevelBean> beans=CoreApplication.newInstance().getPersonFrendList();
        for (RelationShipLevelBean bean : beans){
            string+=bean.getMinor_user()+",";
        }
        return string;
    }
}
