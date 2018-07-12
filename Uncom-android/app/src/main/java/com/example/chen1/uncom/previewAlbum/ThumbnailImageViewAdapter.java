package com.example.chen1.uncom.previewAlbum;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.thinker.ThinkerAdapter;
import com.example.chen1.uncom.utils.GlideApp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Created by chen1 on 2018/1/28.
 */

    public class ThumbnailImageViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
        private LayoutInflater layoutInflater;
        private View view;
        private ArrayList<String> status=new ArrayList<>();
        private ArrayList<String> list;
        private Fragment fragment;
    private int selectPosition=-1;
    private ItemOnClickListener itemOnClickListener;
    private ImageView selectedView;
    private boolean edit=true;
    public ThumbnailImageViewAdapter(ArrayList<String> data,Fragment fragment,int selected){
        this.selectPosition=selected;
        this.list=data;
        for (int i = 0; i < data.size() ; i++) {
            String str;
            if(selectPosition==i){
                str="true";
            }else{
                str="false";
            }
            status.add(str);
        }
        this.fragment=fragment;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener=itemOnClickListener;
    }

    public interface  ItemOnClickListener{
        /**
         *
         * @param v  ad
         * @param position a
         * @param url a
         * @param type 0 select 1 delete
         */
         void onClick(View v,int position,String url,int type);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        view= layoutInflater.inflate(R.layout.priview_image_thumbnail_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        ((ViewHolder)holder).deleteBtn.setOnClickListener(this);
        ((ViewHolder)holder).selected.setOnClickListener(this);

        if(selectPosition==position){
            Log.v("设置选中状态", String.valueOf(position));
            ((ViewHolder)holder).selected.setAlpha((float) 0.5);
        }else{
            Log.v("设置非选中状态", String.valueOf(position));
            ((ViewHolder)holder).selected.setAlpha((float) 1.0);
        }
        ((ViewHolder)holder).selected.setTag(R.id.appimageid,-1);
        ((ViewHolder)holder).selected.setTag(R.id.imageid,position);
        ((ViewHolder)holder).deleteBtn.setTag(R.id.appimageid,position);
        ((ViewHolder)holder).deleteBtn.setTag(R.id.imageid,position);
        GlideApp.with(fragment)
                .load(list.get(position)).transition(new DrawableTransitionOptions().crossFade())
                .into(((ViewHolder)holder).selected);
        if(edit==false){
            ((ViewHolder)holder).deleteBtn.setVisibility(View.GONE);
        }else{
            ((ViewHolder)holder).deleteBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return  0;
        }else{
            return list.size();
        }
    }



    @Override
    public void onClick(View v) {
        if(itemOnClickListener!=null){
            if((int)v.getTag(R.id.appimageid)!=-1){
                itemOnClickListener.onClick(v,(int)v.getTag(R.id.imageid),list.get((int)v.getTag(R.id.imageid)),1);
            }else{
                itemOnClickListener.onClick(v,(int)v.getTag(R.id.imageid),list.get((int)v.getTag(R.id.imageid)),0);
            }

        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView deleteBtn;
        ImageView selected;
        public ViewHolder(View itemView) {
            super(itemView);
            deleteBtn= (AppCompatImageView) itemView.findViewById(R.id.delete);
            selected= (ImageView) itemView.findViewById(R.id.select);
        }
    }

    public void setSelectPosition(int position){
        this.selectPosition=position;
        Log.v("execute","success");
        notifyDataSetChanged();
    }


    public int getSelectPosition(){
        return selectPosition;
    }

    public void setSelectPositionNotNotify(int position){
        this.selectPosition=position;
        Log.v("execute","success");
    }



    /**
     *
     * @param position 1 0 -1
     * @return   1代表完成  0代表完成后相册已为空
     */
    public int  deleteImage(int position){
        Log.v("deleteImage", String.valueOf(position+selectPosition));
        if(position==selectPosition){
            if(selectPosition==0){
                if(list.size()==0){
                    return  0;
                }else{
                    selectPosition=0;
                }
            }else{
                if(selectPosition==(list.size()-1)){
                    selectPosition-=1;
                }else{
                    selectPosition+=1;
                }
            }
        }

        list.remove(position);
        notifyDataSetChanged();
        if(list.size()==0){
            return  0;
        }
        return 1;
    }

    public boolean isEdit() {
        return edit;
    }

    public ThumbnailImageViewAdapter setEdit(boolean edit) {
        this.edit = edit;
        return this;
    }
}
