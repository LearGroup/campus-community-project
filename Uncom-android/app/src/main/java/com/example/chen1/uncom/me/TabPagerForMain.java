package com.example.chen1.uncom.me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;


public class TabPagerForMain extends Fragment {


    public TabPagerForMain() {
        // Required empty public constructor
    }


    public static TabPagerForMain newInstance() {
        TabPagerForMain fragment = new TabPagerForMain();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_pager_for_main, container, false);
    }




}
