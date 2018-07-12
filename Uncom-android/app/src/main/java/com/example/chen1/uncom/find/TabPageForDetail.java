package com.example.chen1.uncom.find;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.RoutineBean;


public class TabPageForDetail extends Fragment {

    private View contentView;
    private GridView gridView;
    private static TabPageForDetail tabPageForDetail;

    public static TabPageForDetail getInstance(){
        return new TabPageForDetail();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(contentView!=null){
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if(parent!=null){
                parent.removeView(contentView);
            }
            return contentView;
        }
        View view=inflater.inflate(R.layout.fragment_tab_page_for_detail, container, false);
        contentView=view;
        gridView= (GridView) view.findViewById(R.id.data_grid);
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        gridView.measure(w, h); Log.v("creating。。。。。。.......................。 ICON","SUCCESS");
        int height =gridView.getMeasuredHeight();
        int width =gridView.getMeasuredWidth();
        gridView.setNumColumns(7);
        gridView.setHorizontalSpacing(10);
        gridView.setPadding(0,0,0,0);
        gridView.setAdapter(new ActiveTimeAdapterForWeek());
        return view;
    }


}
