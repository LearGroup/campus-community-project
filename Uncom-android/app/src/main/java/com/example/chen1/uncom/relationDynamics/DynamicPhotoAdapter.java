package com.example.chen1.uncom.relationDynamics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.utils.DisplayUtils;
import com.example.chen1.uncom.utils.GlideApp;

import java.util.ArrayList;

/**
 * Created by chen1 on 2017/12/15.
 */

public class DynamicPhotoAdapter extends RecyclerView.Adapter<DynamicPhotoAdapter.ViewHolder> implements View.OnTouchListener{
    private  Bitmap bm;
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<ArrayList<String >> imageUrlList;
    private Fragment fragment;
    private int max_height=0;
    private int max_height_one=0;
    private int max_height_two=0;
    private DynamicsAdapter dynamicsAdapter;
    private int beanPosition;
    private boolean useTag;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
         dynamicsAdapter.onTouch(v,event);
         return  true;
    }


    public DynamicPhotoAdapter(Context context, ArrayList<ArrayList<String>>imageUrlList, Fragment fragment ,DynamicsAdapter dynamicsAdapter){
        this.context=context;
        this.imageUrlList=imageUrlList;
        this.fragment=fragment;
        this.dynamicsAdapter=dynamicsAdapter;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      layoutInflater = LayoutInflater.from(context);
      View view=layoutInflater.inflate(R.layout.write_thinker_photo_item,null);
      return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);

        if(imageUrlList.get(position).size()==1 && imageUrlList.get(position).get(0)!=null){
            // 设置参数
             holder.linearLayout.removeAllViews();
             ImageView imageView = new ImageView(context);
             if(useTag==true){
                 imageView.setTag(R.id.beanid,beanPosition);
                 imageView.setTag(R.id.typeid,"image");
                 imageView.setTag(R.id.imagelis,imageUrlList);
             }
             imageView.setTag(R.id.imageid,position*2);
             imageView.setOnTouchListener(this);
             imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
             LinearLayout.LayoutParams lpa;
             if(max_height_one!=0){
                 lpa=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,max_height_one);
             }else{
                 lpa=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (DisplayUtils.getWindowWidth(context)*0.8));
             }
            imageView.setLayoutParams
                    (lpa);
            holder.linearLayout.addView(imageView);
            Log.v("img_0",imageUrlList.get(position).get(0));

            GlideApp.with(fragment)
                    .load(imageUrlList.get(position).get(0)).transition(new DrawableTransitionOptions().crossFade())
                    .into(imageView);

        }else{
            holder.linearLayout.removeAllViews();
            ImageView imageViews = new ImageView(context);
            if(useTag==true){
                imageViews.setTag(R.id.beanid,beanPosition);
                imageViews.setTag(R.id.typeid,"image");
                imageViews.setTag(R.id.imagelis,imageUrlList);
            }
            imageViews.setTag(R.id.imageid,position*2+1);
            imageViews.setOnTouchListener(this);
            imageViews.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams lp;
            LinearLayout.LayoutParams lp_2;

            if(max_height_two!=0){
                lp=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),max_height_two);
                lp_2=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),max_height_two);

            }else{
                lp=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),(int) (DisplayUtils.getWindowWidth(context)*0.5));
                lp_2=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),(int) (DisplayUtils.getWindowWidth(context)*0.5));

            }

            if(holder.linearLayout.getChildCount()!=0){
                holder.linearLayout.getChildAt(0).setLayoutParams
                        (lp_2);
            }else{
                 ImageView imageView_1 = new ImageView(context);
                if(useTag==true){
                    imageView_1.setTag(R.id.beanid,beanPosition);
                    imageView_1.setTag(R.id.typeid,"image");
                    imageView_1.setTag(R.id.imagelis,imageUrlList);
                }
                imageView_1.setTag(R.id.imageid,position*2);
                imageView_1.setOnTouchListener(this);
                 imageView_1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                 imageView_1.setLayoutParams
                        (lp_2);
                 holder.linearLayout.addView(imageView_1);
                 Log.v("img_0_1",imageUrlList.get(position).get(0));

                 GlideApp.with(fragment)
                        .load(imageUrlList.get(position).get(0)).transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView_1);
            }
            lp.leftMargin=10;
            imageViews.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.setLayoutParams
                    (lp);
            holder.linearLayout.addView(imageViews);
            Log.v("speciallyUlr",imageUrlList.get(position).get(1));
            GlideApp.with(fragment)
                    .load(imageUrlList.get(position).get(1)).transition(new DrawableTransitionOptions().crossFade())
                    .into(imageViews);
            LinearLayout.LayoutParams lpsa=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            lpsa.bottomMargin=10;
            holder.linearLayout.setLayoutParams(lpsa);
        }


    }

    @Override
    public int getItemCount() {
        if(imageUrlList==null){
            return 0;
        }
        return imageUrlList.size();
    }


    public int getSize(){
        int j=0;
        for (int i = 0; i <imageUrlList.size() ; i++) {
            if(imageUrlList.size()!=1){
                j+=2;
            }else{
                j+=1;
            }
        }
        return j;
    }

    public void setImgUrl(ArrayList<String> data){
        Log.v("imageUrlList-length", String.valueOf(data.size()));
        if(imageUrlList==null){
            imageUrlList=new ArrayList<ArrayList<String>>();
        }else{
            imageUrlList.clear();
        }
        for (int i=0;i<data.size();i++){
            if(i%2==0){
                ArrayList<String> item=new ArrayList<String>();
                item.add(data.get(i));
                imageUrlList.add(item);
            }else{
                imageUrlList.get(i/2).add(data.get(i));
            }
        }
        notifyDataSetChanged();
    }



    public static  class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.photo_container_item);
        }
    }
    public  void addImageView(String  imageUrl){
        if(imageUrlList!=null){
            if(imageUrlList.size()==0){
                ArrayList<String> item=new ArrayList<String>();
                item.add(imageUrl);
                imageUrlList.add(item);
            }else{
                if(imageUrlList.get(imageUrlList.size()-1).size()==2){
                    ArrayList<String> item=new ArrayList<String>();
                    item.add(imageUrl);
                    imageUrlList.add(item);
                }else{
                    imageUrlList.get(imageUrlList.size()-1).add(imageUrl);
                }
            }
            notifyDataSetChanged();
        }
    }




     private  int[]  scaleParse(String path  ,String path2,float currentHeight){
         int height,width,height2,width2,windowWidth=DisplayUtils.getWindowWidth(context);
         float proportion=0,proportion2=0;
         BitmapFactory.Options options = new BitmapFactory.Options();
         options.inJustDecodeBounds = true;
         Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
         height=options.outHeight;
         width=options.outWidth;
         Bitmap bitmap2 = BitmapFactory.decodeFile(path2, options); // 此时返回的bitmap为null
         height2=options.outHeight;
         width2=options.outWidth;
         proportion2=width2/currentHeight;
         proportion=width/currentHeight;
         if(0.9>proportion/proportion2 || proportion/proportion2<1.1){
             return null;
         }else{
             return new int[]{(int) (windowWidth*(proportion/(proportion+proportion2))), (int) (windowWidth*(proportion2/(proportion+proportion2)))};
         }
     }

    private int parseHeight(String path,float currentWidth){

        int height,width;
        float proportion;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        height=options.outHeight;
        width=options.outWidth;
        Log.v("height", "dda"+String.valueOf(height));
        return (int) (currentWidth*0.8);
//        proportion=currentWidth/width;
//        if(height*proportion>(DisplayUtils.getWindowHeight(context)*0.5)){
//            return (int) (DisplayUtils.getWindowHeight(context)*0.5);
//        }else{
//            return (int) (height*proportion);
//        }
    }


    public int getMax_height_one() {
        return max_height_one;
    }

    public DynamicPhotoAdapter setMax_height_one(int max_height_one) {
        this.max_height_one = max_height_one;
        return this;
    }

    public int getMax_height_two() {
        return max_height_two;
    }

    public DynamicPhotoAdapter setMax_height_two(int max_height_two) {
        this.max_height_two = max_height_two;
        return  this;
    }

    public void setItemMaxHeight(int height){
         this.max_height=height;
     }


    public int getPosition() {
        return beanPosition;
    }

    public DynamicPhotoAdapter setPosition(int position) {
        this.beanPosition = position;
        return  this;
    }

    public boolean isUseTag() {
        return useTag;
    }

    public DynamicPhotoAdapter setUseTag(boolean useTag) {
        this.useTag = useTag;
        return this;
    }
}
