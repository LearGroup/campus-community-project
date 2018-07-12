package com.example.chen1.uncom.previewAlbum;

import android.animation.ObjectAnimator;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.utils.GlideApp;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by chen1 on 2018/1/28.
 */

public class ImagePagerAdapter extends PagerAdapter {
    private ArrayList<String> imgUrl;
    private ArrayList<View> views;
    private LayoutInflater layoutInflater;
    private Fragment fragment;
    private AppBarLayout appBarLayout;
    private RecyclerView bottomLayout;
    private boolean hideTool=false;
    public ImagePagerAdapter(ArrayList<String> list,Fragment fragment){

         this.fragment=fragment;
         if(views!=null){
             views.clear();
         }else{
             views=new ArrayList<>();
         }
         layoutInflater = LayoutInflater.from(CoreApplication.newInstance().getApplicationContext());
         this.imgUrl=list;
         for (int i = 0; i <list.size() ; i++) {
             View view=layoutInflater.inflate(R.layout.preview_image_item,null);
             PhotoView photoView= (PhotoView) view.findViewById(R.id.zoom_image_view);
             photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
                 @Override
                 public void onPhotoTap(ImageView view, float x, float y) {
                     ObjectAnimator animator,animator2;
                     if(hideTool==false){
                         animator=ObjectAnimator.ofFloat(appBarLayout,"translationY",0,-appBarLayout.getHeight());
                         animator2=ObjectAnimator.ofFloat(bottomLayout,"translationY",0,bottomLayout.getHeight());
                         hideTool=true;
                     }else{
                         animator=ObjectAnimator.ofFloat(appBarLayout,"translationY",-appBarLayout.getHeight(),0);
                         animator2=ObjectAnimator.ofFloat(bottomLayout,"translationY",bottomLayout.getHeight(),0);
                         hideTool=false;
                     }
                     animator2.setDuration(180);
                     animator.setDuration(180);
                     animator2.setInterpolator(new AccelerateDecelerateInterpolator());
                     animator.setInterpolator(new AccelerateDecelerateInterpolator());
                     animator.start();
                     animator2.start();
                 }
             });

             views.add(view);
         }
     }

     public ImagePagerAdapter setAppBarLayout(AppBarLayout appBarLayout){
        this.appBarLayout=appBarLayout;
        return this;
     }

     public ImagePagerAdapter setBottomLayout(RecyclerView recyclerView){
         this.bottomLayout=recyclerView;
         return this;
     }

     public void removeItem(int position){
         views.remove(position);
     }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
         if(position<views.size()){
             container.removeView(views.get(position));
         }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        GlideApp.with(fragment)
                .load(imgUrl.get(position)).transition(new DrawableTransitionOptions().crossFade())
                .into((ImageView) view.findViewById(R.id.zoom_image_view));
        if(container.getChildAt(position)==views.get(position)){
            container.removeView(views.get(position));
        }

        container.addView(views.get(position));
        return views.get(position);
    }
}
