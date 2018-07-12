package com.example.chen1.uncom.find;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;

public class TabPageForData extends Fragment {

    private View contentView;
    private static TabPageForData tabPageForData;

    public static TabPageForData getInstance(){
         return new TabPageForData();


    }

    public TabPageForData() {
        // Required empty public constructor
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
        contentView=inflater.inflate(R.layout.fragment_tab_page_for_data, container, false);
        return contentView;
    }


}
