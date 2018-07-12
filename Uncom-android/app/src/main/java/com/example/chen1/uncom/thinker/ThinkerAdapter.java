package com.example.chen1.uncom.thinker;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.bean.ThinkerBeanDao;
import com.example.chen1.uncom.relationship.NewRelationshipAdapter;
import com.example.chen1.uncom.utils.AsynLoadImageUtils;
import com.example.chen1.uncom.utils.LoadImageUtils;

import org.greenrobot.greendao.DbUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import lecho.lib.hellocharts.model.Line;

/**
 * Created by chen1 on 2017/12/3.
 */

public class ThinkerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private ArrayList<ThinkerBean> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ArrayList<String >> imageUrlList;
    private View view;
    private AsynLoadImageUtils asynLoadImageUtils;
    private ItemOnClickListener itemOnClickListener;
    private Fragment fragment;
    public ThinkerAdapter(ArrayList<ThinkerBean> list,AsynLoadImageUtils asynLoadImageUtils,Fragment fragment){
        this.context=context;
        this.list=list;
        this.asynLoadImageUtils=asynLoadImageUtils;
        this.fragment=fragment;
    }


    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener=itemOnClickListener;
    }



    @Override
    public void onClick(View v) {
        if(itemOnClickListener!=null){
            itemOnClickListener.onClick(v,(int)v.getTag(),list.get((int)v.getTag()));
        }
    }

    public interface  ItemOnClickListener{
        void onClick(View view,int position,ThinkerBean bean);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        layoutInflater = LayoutInflater.from(parent.getContext());
        view= layoutInflater.inflate(R.layout.thinker_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        ((ViewHolder) holder).cardView.setCardBackgroundColor(Color.parseColor("#ffffffff"));
        ((ViewHolder) holder).content.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).title.setVisibility(View.VISIBLE);
        ((ViewHolder) holder).titleLinearLayout.setVisibility(View.GONE);
        ((ViewHolder) holder).contentLinearLayout.setVisibility(View.GONE);
        ((ViewHolder) holder).content.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        ((ViewHolder) holder).title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        ((ViewHolder) holder).content.setText(null);
        ((ViewHolder) holder).title.setText(null);
        ((ViewHolder) holder).content.setGravity(Gravity.LEFT);
        ((ViewHolder) holder).title.setGravity(Gravity.LEFT);
        ((ViewHolder) holder).photoContainer.removeAllViews();
        TextPaint textPaint=((ViewHolder) holder).title.getPaint();
        textPaint.setFakeBoldText(true);
        if(list.get(position).getBackColor()!=null && list.get(position).getBackColor().length()!=0){
            ((ViewHolder) holder).cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getBackColor()));
        }

        if(list.get(position).getContent()!=null && list.get(position).getContent().length()!=0){
            ((ViewHolder) holder).contentLinearLayout.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).content.setText(list.get(position).getContent());
        }
        if(list.get(position).getTitle()!=null && list.get(position).getTitle().length()!=0){
            ((ViewHolder) holder).titleLinearLayout.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).title.setText(list.get(position).getTitle());

        }

        if(list.get(position).getContent()==null || list.get(position).getContent().length()==0 && list.get(position).getImgUrl()==null){
            ((ViewHolder) holder).title.setGravity(Gravity.CENTER);
            ((ViewHolder) holder).title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
            TextPaint textPaints=((ViewHolder) holder).title.getPaint();
             textPaints.setFakeBoldText(false);
            ((ViewHolder) holder).content.setVisibility(View.GONE);
        }

        if(list.get(position).getTitle()==null || list.get(position).getTitle().length()==0 && list.get(position).getImgUrl()==null){
            ((ViewHolder) holder).content.setGravity(Gravity.CENTER);
            ((ViewHolder) holder).content.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
            ((ViewHolder) holder).title.setVisibility(View.GONE);
        }

        if(list.get(position).getImgUrl()!=null){
            addImageUrlList(list,position);
            MainPhotoAdapter photoAdatper=new MainPhotoAdapter(imageUrlList,asynLoadImageUtils,list.get(position),position,fragment);
            photoAdatper.setItemOnClickListener(itemOnClickListener);
            ((ViewHolder) holder).photoContainer.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).photoContainer.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
            ((ViewHolder) holder).photoContainer.setHasFixedSize(true);
            ((ViewHolder) holder).photoContainer.setAdapter(photoAdatper);
        }else{
            ((ViewHolder) holder).photoContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    @Override
//    public void onClick(View v) {
//        if(itemOnclickListener!=null){
//            Log.v("OnclickListener","success");
//            itemOnclickListener.Onclick(v,(int)v.getTag(),list.get((int)v.getTag()));
//        }
//    }


    public static  class  ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView photoContainer;
        CardView  cardView;
        TextView title;
        TextView content;
        LinearLayout titleLinearLayout;
        LinearLayout contentLinearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            titleLinearLayout= (LinearLayout) itemView.findViewById(R.id.titleLinearLayout);
            contentLinearLayout= (LinearLayout) itemView.findViewById(R.id.contentLinearLayout);
            cardView=(CardView)itemView.findViewById(R.id.item_view);
            photoContainer= (RecyclerView) itemView.findViewById(R.id.photo_container);
            content= (TextView) itemView.findViewById(R.id.content);
            title= (TextView) itemView.findViewById(R.id.title);
        }
    }

    public void addImageUrlList(ArrayList<ThinkerBean> list,int position){
        ThinkerBean thinkerBean=list.get(position);
        boolean isSync=false;
        ArrayList<String> origin_url=new ArrayList<String>(Arrays.asList(thinkerBean.getImgUrl().split(",")));
        ArrayList<String> cache_url=null;
        ArrayList<String> online_url=null;
        if(thinkerBean.getImgCacheUrl()!=null&&thinkerBean.getImgCacheUrl().length()>0){
            cache_url=new ArrayList<>(Arrays.asList(thinkerBean.getImgCacheUrl().split(",")));

        }
        if(thinkerBean.getImgOnlineUrl()!=null && thinkerBean.getImgOnlineUrl().length()>0){
            online_url=new ArrayList<>(Arrays.asList(thinkerBean.getImgOnlineUrl().split(",")));
        }
        imageUrlList=new ArrayList<ArrayList<String>>();
        for (int i=0;i<origin_url.size();i++){
            if(i%2==0){
                ArrayList<String> item=new ArrayList<String>();
                if( cache_url!=null && cache_url.size()>i){
                    File file=new File(cache_url.get(i));
                    if(file.exists()){
                        item.add(cache_url.get(i));
                    }else{
                        cache_url.remove(i);
                        thinkerBean.setImgCacheUrl(cache_url.toString().replace("[","").replace("]",""));
                        list.set(position,thinkerBean);

                        isSync=true;
                        if(online_url!=null && online_url.size()>i && cache_url.size()<i){
                            item.add(online_url.get(i));
                        }else{
                            item.add(origin_url.get(i));
                        }
                    }
                }else if(online_url!=null && online_url.size()>i && cache_url.size()<i){
                    item.add(online_url.get(i));
                }else{
                    item.add(origin_url.get(i));
                }
                imageUrlList.add(item);
            }else{
                if(cache_url!=null && cache_url.size()>i){
                    Log.v("cache_url", String.valueOf(cache_url));
                    File file=new File(cache_url.get(i));
                    if(file.exists()){
                        imageUrlList.get(i/2).add(cache_url.get(i));
                    }else{
                        isSync=true;
                        Log.v("deleteCache_url","success");
                        cache_url.remove(i);
                        thinkerBean.setImgCacheUrl(cache_url.toString().replace("[","").replace("]",""));
                        list.set(position,thinkerBean);

                        if(online_url!=null &&  online_url.size()>i && cache_url.size()<i){
                            imageUrlList.get(i/2).add(online_url.get(i));
                        }else{
                            imageUrlList.get(i/2).add(origin_url.get(i));
                        }
                    }
                }else if(online_url!=null &&  online_url.size()>i && cache_url.size()<i){
                    imageUrlList.get(i/2).add(online_url.get(i));
                }else{
                    imageUrlList.get(i/2).add(origin_url.get(i));
                }
            }
        }
        if(isSync){
            ThinkerBeanDao thinkerBeanDao= BeanDaoManager.getInstance().getDaoSession().getThinkerBeanDao();
            thinkerBeanDao.update(list.get(position));
        }
    }

    public void setDataList(ArrayList<ThinkerBean> list){
        if(list!=null){
                this.list=list;
                notifyDataSetChanged();
        }
    }




}
