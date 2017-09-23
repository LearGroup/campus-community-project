package com.example.chen1.uncom;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.io.Console;
import java.util.ArrayList;

/**
 * Created by chen1 on 2017/7/2.
 */

public class ExpessionStandardAdapter extends BaseAdapter {

    private  ArrayMap<String,Integer> EmotionSet;
    private ArrayList<String> EmotionList;
    private Integer CurrentPage;
    private Integer PageCount;
    private Context context;
    private int screenWdith;
    public ExpessionStandardAdapter(ArrayMap<String,Integer> Emotion,Integer PageCount,Integer currentPage,Context context,int screenWidth){
        EmotionSet=Emotion;

        EmotionList=new ArrayList<String>(EmotionSet.keySet());
        this.PageCount=PageCount;
        this.CurrentPage=currentPage;
        this.context=context;
    }

    @Override
    public int getCount() {

        if(CurrentPage.equals(PageCount-1)){
            return   EmotionList.size()-(CurrentPage)*20+1;
        }
            return 21;


    }

    @Override
    public Object getItem(int position) {
            return EmotionList.get(CurrentPage*20+position);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            LinearLayout view=(LinearLayout)layoutInflater.inflate(R.layout.chat_expression_standard_icon,null);

            AppCompatImageView imageView=(AppCompatImageView) view.findViewById(R.id.expression_icon_standard);
        if(position==20||(PageCount.equals(CurrentPage+1)&&position==(EmotionList.size()-(CurrentPage)*20))){
                imageView.setImageResource(R.drawable.ic_expression_delete_icon);
                 LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,84);
                 lp.topMargin=10;
                 lp.rightMargin=20;
                 imageView.setLayoutParams(lp);

        }else{
            imageView.setImageResource(EmotionSet.get(EmotionList.get(CurrentPage*20+position)));
        }

      /*  Log.v("Standard Expression current width:", String.valueOf(itemWidth));*/

     /*   imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,90));*/

        return view;
    }
}
