package com.example.chen1.uncom.find;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.RoutineBean;


public class TabPageForDetail extends Fragment {

    private GridLayout gridView;
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
        View view=inflater.inflate(R.layout.fragment_tab_page_for_detail, container, false);
        gridView= (GridLayout) view.findViewById(R.id.data_grid);
        return view;
    }


}
