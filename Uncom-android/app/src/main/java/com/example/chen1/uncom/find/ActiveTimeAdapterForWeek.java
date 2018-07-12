package com.example.chen1.uncom.find;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.R;

/**
 * Created by chen1 on 2017/11/25.
 */

public class ActiveTimeAdapterForWeek extends BaseAdapter {
    private LinearLayout view;
    @Override
    public int getCount() {
        return 12*7;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("create ICON","SUCCESS");
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        if(position==1||position==7||position==18||position==30||position==12||position==13||position==14||position==24||position==110||position==24||position==32){
            view=(LinearLayout)layoutInflater.inflate(R.layout.time_active_item,null);
        }else{
            view=(LinearLayout)layoutInflater.inflate(R.layout.time_normal_item,null);
        }
        TextView item= (TextView) view.findViewById(R.id.time_item);

        if((position==1||position%8==0||position==0 ||position==41)&& position<42){
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(48,12);
            //  lp.bottomMargin=10;
            view.setPadding(0,0,0,25);
            item.setLayoutParams(lp);
        }

  /*      if((position==42||position==43||position==44||position==45||position==46||position==47||position==58)&& position>=42){
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(50,12);
         //   lp.topMargin=10;
            view.setPadding(0,15,0,0);
            item.setLayoutParams(lp);
        }*/

        return view;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
