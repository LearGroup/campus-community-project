package com.example.chen1.uncom.find;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;


public class TabPageForDetail extends Fragment {


    private static TabPageForDetail tabPageForDetail;

    public static TabPageForDetail getInstance(){
        if(tabPageForDetail==null){
            tabPageForDetail=new TabPageForDetail();
        }
        return tabPageForDetail;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_page_for_detail, container, false);
    }


}
