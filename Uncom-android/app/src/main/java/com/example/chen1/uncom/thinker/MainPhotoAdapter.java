package com.example.chen1.uncom.thinker;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.utils.AsynLoadImageUtils;
import com.example.chen1.uncom.utils.DisplayUtils;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.ImageUtils;

import java.util.ArrayList;

/**
 * Created by chen1 on 2017/12/24.
 */

public class MainPhotoAdapter extends RecyclerView.Adapter<MainPhotoAdapter.ViewHolder> implements View.OnClickListener{
    private Bitmap bm;
    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<ArrayList<String >> imageUrlList;
    private ThinkerAdapter.ItemOnClickListener itemOnClickListener;
    private AsynLoadImageUtils asynLoadImageUtils;
    private ThinkerBean bean;
    private int position;
    private Fragment fragment;
    private MotionEvent event;

//    @Override
//    public void onClick(View v) {
//        if(itemOnclickListener!=null){
//            itemOnclickListener.Onclick(v,position,bean);
//        }
//    }

    @Override
    public void onClick(View v) {
        if(itemOnClickListener!=null){
            itemOnClickListener.onClick(v,position,bean);
        }
    }

    public MainPhotoAdapter(ArrayList<ArrayList<String>>imageUrlList,AsynLoadImageUtils asynLoadImageUtils,ThinkerBean bean,int position,Fragment fragment){
        this.bean=bean;
        this.position=position;
        this.fragment=fragment;
        this.imageUrlList=imageUrlList;
        this.asynLoadImageUtils=asynLoadImageUtils;
    }
    public void  setItemOnClickListener(ThinkerAdapter.ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener=itemOnClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.write_thinker_photo_item,null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int lastImageHieght;
        int currentImageHeight;
        holder.itemView.setTag(position);
        if(imageUrlList.get(position).size()==1 && imageUrlList.get(position).get(0)!=null){
            // 设置参数
            holder.linearLayout.removeAllViews();
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams
                    (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,parseHeight(imageUrlList.get(position).get(0),DisplayUtils.getWindowWidth(context))));
            holder.linearLayout.addView(imageView);
            GlideApp.with(fragment)
                    .load(imageUrlList.get(position).get(0)).transition(new DrawableTransitionOptions().crossFade())
                    .into(imageView);

        }else{
            holder.linearLayout.removeAllViews();
            lastImageHieght= ImageUtils.getImageHeight(imageUrlList.get(position).get(0));
            currentImageHeight=ImageUtils.getImageHeight(imageUrlList.get(position).get(1));
            ImageView imageViews = new ImageView(context);
            imageViews.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams lp;
            LinearLayout.LayoutParams lp_2;
            if((lastImageHieght>currentImageHeight && ((lastImageHieght-currentImageHeight)<600))||(currentImageHeight>lastImageHieght && ((currentImageHeight-lastImageHieght)<600))){
                int[] result=scaleParse(imageUrlList.get(position).get(0),imageUrlList.get(position).get(1), (float) 640);
                if(result==null){
                    lp=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),640);
                    lp_2=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),640);
                }else{
                    lp=new LinearLayout.LayoutParams(result[0],640);
                    lp_2=new LinearLayout.LayoutParams(result[0],640);
                }

            }else{
                int[] result=scaleParse(imageUrlList.get(position).get(0),imageUrlList.get(position).get(1), (float) 640);
                if(result==null){
                    lp=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),540);
                    lp_2=new LinearLayout.LayoutParams((int) (DisplayUtils.getWindowWidth(context)*0.5),540);

                }else{
                    lp=new LinearLayout.LayoutParams(result[0],540);
                    lp_2=new LinearLayout.LayoutParams(result[1],540);
                }
            }

            if(holder.linearLayout.getChildCount()!=0){
                holder.linearLayout.getChildAt(0).setLayoutParams
                        (lp_2);
            }else{
                ImageView imageView_1 = new ImageView(context);
                imageView_1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView_1.setLayoutParams
                        (lp_2);
                holder.linearLayout.addView(imageView_1);
                GlideApp.with(fragment)
                        .load(imageUrlList.get(position).get(0)).transition(new DrawableTransitionOptions().crossFade())
                        .into(imageView_1);
            }
            lp.leftMargin=10;
            imageViews.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.setLayoutParams
                    (lp);
            holder.linearLayout.addView(imageViews);
            GlideApp.with(fragment)
                    .load(imageUrlList.get(position).get(1)).transition(new DrawableTransitionOptions().crossFade())
                    .into(imageViews);

            LinearLayout.LayoutParams lpsa=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            lpsa.bottomMargin=10;
            holder.linearLayout.setLayoutParams(lpsa);
//            if(position==1 && imageUrlList.get(position).size()==1){
//                LinearLayout.LayoutParams lps=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//                lps.topMargin=15;
//                holder.linearLayout.setLayoutParams(lps);
//            }
        }

    }




    @Override
    public int getItemCount() {
        if(imageUrlList==null){
            return 0;
        }
        return imageUrlList.size();
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

    public void addImageUrlList(String[] url){
        if(imageUrlList==null){
            imageUrlList=new ArrayList<ArrayList<String>>();
        }
        for (int i=0;i<url.length;i++){
            if(i%2==0){
                ArrayList<String> item=new ArrayList<String>();
                item.add(url[i]);
                imageUrlList.add(item);
            }else{
                imageUrlList.get(i/2).add(url[i]);
            }
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
        proportion=currentWidth/width;
        if(height*proportion>(DisplayUtils.getWindowHeight(context)*0.5)){
            return (int) (DisplayUtils.getWindowHeight(context)*0.5);
        }else{
            return (int) (height*proportion);
        }
    }

}
