package com.example.chen1.uncom.find;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;


public class TabPageForAbs extends Fragment {

    private static TabPageForAbs tabPageForAbs;

    public static TabPageForAbs getInstance(){
        return new TabPageForAbs();


    }



    public TabPageForAbs() {
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
        Log.v("TabPageForAbs","init");
        return inflater.inflate(R.layout.fragment_tab_page_for_abs, container, false);
    }


}
