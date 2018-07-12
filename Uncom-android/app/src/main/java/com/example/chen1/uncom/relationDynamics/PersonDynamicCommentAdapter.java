package com.example.chen1.uncom.relationDynamics;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

/**
 * Created by chen1 on 2018/3/2.
 */

public class PersonDynamicCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnTouchListener {

    private Fragment fragment;
    private JSONArray jsonArray;


   public PersonDynamicCommentAdapter(Fragment fragment, JSONArray jsonArray){

   }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
