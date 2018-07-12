package com.example.chen1.uncom.relationDynamics;

import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.GlideCircleTransform;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.SpanStringUtils;
import com.example.chen1.uncom.utils.StateCode;
import com.example.chen1.uncom.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by chen1 on 2018/2/18.
 */

public class DynamicsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnTouchListener{
    private ArrayList<Object> dynamics;
    private Fragment fragment=null;
    private LoadImageUtils loadImageUtils;
    private TimeUtils timeUtils;
    private SpanStringUtils spanStringUtils;
    private ItemOnTouchListener itemOnTouchListener;
    private boolean likeBtnAnimator=false;
    private boolean bigLikeAnimator=false;
    private GlideCircleTransform glideCircleTransform=new GlideCircleTransform();

    public DynamicsAdapter(ArrayList<Object> dynamics, Fragment fragment){
        this.dynamics=dynamics;
        this.fragment=fragment;
        this.spanStringUtils=new SpanStringUtils();
        this.timeUtils=new TimeUtils();
        this.loadImageUtils=new LoadImageUtils();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String type= (String) v.getTag(R.id.typeid);
        if(itemOnTouchListener!=null){
            itemOnTouchListener.onTouch(v,type,dynamics.get((Integer) v.getTag(R.id.beanid)),event);
        }
        return  true;
    }


    public interface  ItemOnTouchListener{
        boolean onTouch(View v,String type,Object bean,MotionEvent event);
    }




    public ItemOnTouchListener getItemOnTouchListener() {
        return itemOnTouchListener;
    }

    public void setItemOnTouchListener(ItemOnTouchListener itemOnTouchListener) {
        this.itemOnTouchListener = itemOnTouchListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(dynamics.get(position) instanceof PersonDynamicsBean){
            if((((PersonDynamicsBean)(dynamics.get(position))).getPhoto_list()!=null && ((PersonDynamicsBean)(dynamics.get(position))).getPhoto_list().length()>15 )||( ((PersonDynamicsBean)(dynamics.get(position))).getPhoto_online_list()!=null &&((PersonDynamicsBean)(dynamics.get(position))).getPhoto_online_list().length()>15)){
                return Integer.parseInt(StateCode.PERSON_DYNAMICS_PICTURE);
            }else{
                return Integer.parseInt(StateCode.PERSON_DYNAMICS_NORMAL);
            }
        }else{
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType==Integer.parseInt(StateCode.PERSON_DYNAMICS_PICTURE)){
            View view=layoutInflater.inflate(R.layout.person_dynamics_item_picture,null);
            return new DynamicsAdapter.PersonDynamicsViewHolder(view);
        }else if(viewType==Integer.parseInt(StateCode.PERSON_DYNAMICS_NORMAL)){
            View view =layoutInflater.inflate(R.layout.person_dynamics_item_normal,null);
            return new DynamicsAdapter.PersonDynamicsViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setTag(R.id.imageid,"itemView");
        holder.itemView.setTag(R.id.beanid,position);
        if(position==0){
            holder.itemView.requestFocus();
        }
        if(dynamics.get(position)!=null && dynamics.get(position) instanceof  PersonDynamicsBean){
            PersonDynamicsBean bean=(PersonDynamicsBean)dynamics.get(position);
            final PersonDynamicsViewHolder viewHolder= (PersonDynamicsViewHolder) holder;
            viewHolder.like_btn.setTag(R.id.typeid,"likeBtn");
            viewHolder.comment_btn.setTag(R.id.typeid,"commentBtn");
            viewHolder.like_btn.setTag(R.id.beanid,position);
            viewHolder.comment_btn.setTag(R.id.beanid,position);
            if(viewHolder.phtoContainer!=null){
                viewHolder.phtoContainer.setVisibility(View.GONE);
            }
            if(bean.getLike().indexOf(CoreApplication.newInstance().getUser_id())!=-1){
                viewHolder.like_btn.setImageResource(R.drawable.ic_like_store_2);
            }else{
                viewHolder.like_btn.setImageResource(R.drawable.ic_vector_like_icon);
            }
            if(likeBtnAnimator==true){
                YoYo.with(Techniques.RubberBand)
                        .duration(300)
                        .repeat(0)
                        .playOn(viewHolder.like_btn);
                viewHolder.like_btn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeBtnAnimator=false;
                    }
                },300);
            }
            if(bigLikeAnimator==true){
                viewHolder.bigLike.setVisibility(View.VISIBLE);
                viewHolder.bigLike.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.Pulse)
                                .duration(500)
                                .repeat(0)
                                .playOn(viewHolder.bigLike);
                        YoYo.with(Techniques.FadeOut)
                                .duration(300)
                                .repeat(0).delay(500)
                                .playOn(viewHolder.bigLike);
                    }
                },20);
                viewHolder.bigLike.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bigLikeAnimator=false;
                        viewHolder.bigLike.setVisibility(View.GONE);
                    }
                },820);
            }
            viewHolder.comment_btn.setOnTouchListener(this);
            viewHolder.like_btn.setOnTouchListener(this);
            if(viewHolder.username_1!=null){
                if(bean.getContent()==null ||bean.getContent().equals("")){
                    viewHolder.username_1.setVisibility(View.GONE);
                }else{
                    viewHolder.username_1.setVisibility(View.VISIBLE);
                    viewHolder.username_1.setText(bean.getUsername()+":");
                }
            }
            SpannableString spannableString= spanStringUtils.getEmotionContent(1,CoreApplication.newInstance().getApplicationContext(), viewHolder.text,bean.getContent());
            viewHolder.text.setText(spannableString);
            viewHolder.username_0.setText(bean.getUsername());
            GlideApp.with(fragment)
                    .load(bean.getUser_photo()).transition(new DrawableTransitionOptions().crossFade())
                    .transform(glideCircleTransform)
                    .into(viewHolder.head_img);
            ((PersonDynamicsViewHolder) holder).time.setText(timeUtils.compareTimeDynamicDisplay(new Date(),bean.getCreate_time()));
            if((bean.getPhoto_list()!=null && bean.getPhoto_list().length()>15) ||(bean.getPhoto_online_list()!=null && bean.getPhoto_online_list().length()>15)){
                viewHolder.phtoContainer.setVisibility(View.VISIBLE);
                viewHolder.phtoContainer.setNestedScrollingEnabled(false);
                DynamicPhotoAdapter photoAdapter=new DynamicPhotoAdapter(CoreApplication.newInstance().getApplicationContext(),parseToDArrayList(getImageUrlList(bean)),fragment,DynamicsAdapter.this);
                photoAdapter.setUseTag(true).setPosition(position);
                viewHolder.phtoContainer.setVisibility(View.VISIBLE);
                viewHolder.phtoContainer.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                viewHolder.phtoContainer.setHasFixedSize(true);
                viewHolder.phtoContainer.setAdapter(photoAdapter);
            }else{
                if(viewHolder.phtoContainer!=null){
                    viewHolder.phtoContainer.setVisibility(View.GONE);
                }
            }
            if(bean.getComment_count()==0){
                viewHolder.comment.setVisibility(View.GONE);
            }else{
                viewHolder.comment.setVisibility(View.VISIBLE);
                viewHolder.comment.setText(bean.getComment_count()+"条评论");
            }
            if(bean.getLike_count()==0){
                viewHolder.like.setVisibility(View.GONE);
            }else{
                viewHolder.like.setVisibility(View.VISIBLE);
                viewHolder.like.setText(bean.getLike_count()+"次赞");
            }
            if(bean.getContent()!=null && bean.getContent().replaceAll(" ","").equals("")==false){
                if(viewHolder.contentLayout!=null){
                    viewHolder.contentLayout.setVisibility(View.VISIBLE);
                }
            }else{
                if(viewHolder.contentLayout!=null){
                    viewHolder.contentLayout.setVisibility(View.GONE);
                }
            }

        }else{
        }
    }


    @Override
    public int getItemCount() {
        if(dynamics==null){
            return 0;
        }else{
            return dynamics.size();
        }
    }



    public void setDynamics(ArrayList<Object> dynamics){
        this.dynamics=dynamics;
        notifyDataSetChanged();
    }


    public void updateDynamics(Object object){
        if(object instanceof PersonDynamicsBean){
            for (int i = 0; i <dynamics.size() ; i++) {
                if(((PersonDynamicsBean) object).getId().equals(((PersonDynamicsBean)dynamics.get(i)).getId())){
                    dynamics.set(i,object);
                    notifyItemChanged(i);
                    return;
                }
            }
            dynamics.add(0,object);
            notifyItemInserted(0);
        }
    }

    public static class PersonDynamicsViewHolder extends  RecyclerView.ViewHolder{
        public RecyclerView phtoContainer;
        public TextView text;
        public TextView comment;
        public TextView like;
        public TextView username_0;
        public TextView time;
        public TextView username_1;
        public ImageView head_img;
        public AppCompatImageView  like_btn;
        public AppCompatImageView comment_btn;
        public AppCompatImageView more_btn;
        public AppCompatImageView bigLike;
        public LinearLayout contentLayout;

        public PersonDynamicsViewHolder(View itemView) {
            super(itemView);
            phtoContainer= (RecyclerView) itemView.findViewById(R.id.photo_container);
            text= (TextView) itemView.findViewById(R.id.text);
            comment= (TextView) itemView.findViewById(R.id.comment);
            like= (TextView) itemView.findViewById(R.id.like);
            username_0= (TextView) itemView.findViewById(R.id.username_0);
            time= (TextView) itemView.findViewById(R.id.time);
            contentLayout= (LinearLayout) itemView.findViewById(R.id.content_layout);
            username_1= (TextView) itemView.findViewById(R.id.username_1);
            head_img= (ImageView) itemView.findViewById(R.id.head_img);
            like_btn= (AppCompatImageView) itemView.findViewById(R.id.like_btn);
            comment_btn= (AppCompatImageView) itemView.findViewById(R.id.comment_btn);
            more_btn= (AppCompatImageView) itemView.findViewById(R.id.more_btn);
            bigLike= (AppCompatImageView) itemView.findViewById(R.id.big_like);
        }
    }

    private ArrayList<ArrayList<String>> parseToDArrayList(ArrayList<String> array){
        ArrayList<ArrayList<String>> arrayLists=new ArrayList<>();
        for (int i=0;i<array.size();i++){
            if(i%2==0){
                ArrayList<String> item=new ArrayList<String>();
                item.add(array.get(i));
                arrayLists.add(item);
            }else{
                arrayLists.get(i/2).add(array.get(i));
            }
        }
        return  arrayLists;
    }


    public void add(Object object){
        for (int i = 0; i < dynamics.size(); i++) {
            if(object instanceof  PersonDynamicsBean){
                if(((PersonDynamicsBean) object).getId().equals(((PersonDynamicsBean)dynamics.get(i)).getId())){
                    dynamics.set(i,object);
                    notifyItemChanged(i);
                    return;
                }
            }
        }
        dynamics.add(0,object);
        notifyItemInserted(0);
        notifyItemRangeChanged(0,dynamics.size()-1);
    }


    public DynamicsAdapter syncItem(Object object,int position){
        dynamics.set(position,object);
        notifyItemChanged(position);
        return  this;
    }
    public ArrayList<Object> getDynamics(){
        return  dynamics;
    }

    public ArrayList<String> getImageUrlList(Object object) {
        PersonDynamicsBean bean = null;
        ArrayList<String> path = new ArrayList<>();
        if(object instanceof  PersonDynamicsBean){
            bean= (PersonDynamicsBean) object;
        }
        String local_path[] = bean.getPhoto_list().split(",");
        String online_path[] = new String[20];
        if (bean.getPhoto_online_list() != null) {
                online_path = bean.getPhoto_online_list().split(",");
        }
        for (int i = 0; i < bean.getPhoto_list().split(",").length; i++) {
                File file = new File(local_path[i]);
                if (file.exists() && file.isFile()) {
                    path.add(local_path[i]);
                } else {
                    if (online_path != null && online_path[i] != null) {
                        path.add(online_path[i]);
                    }
                }
            }
        return  path;
    }


    public boolean isLikeBtnAnimator() {
        return likeBtnAnimator;
    }

    public DynamicsAdapter setLikeBtnAnimator(boolean likeBtnAnimator) {
        this.likeBtnAnimator = likeBtnAnimator;
        return this;
    }

    public boolean isBigLikeAnimator() {
        return bigLikeAnimator;
    }

    public DynamicsAdapter setBigLikeAnimator(boolean bigLikeAnimator) {
        this.bigLikeAnimator = bigLikeAnimator;
        return  this;
    }
}
