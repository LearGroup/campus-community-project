package com.example.chen1.uncom;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by chen1 on 2017/6/28.
 */

public class GrallyAdapter extends RecyclerView.Adapter<GrallyAdapter.ViewHolder> implements View.OnClickListener {

    private List<Integer> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private List<Integer> datalist=new ArrayList<>();
    private Context conext;
    private LayoutInflater layoutInflater;

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(v,(int)v.getTag());
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public GrallyAdapter(Context context){
        this.conext=context;
        datalist.clear();
        datalist.add(R.drawable.ic_expression_2_icon);
        datalist.add(R.drawable.ic_vector_expression_heart_icon);
        datalist.add(R.drawable.ic_vector_group_travel);
    }

    @Override
    public GrallyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater= LayoutInflater.from(conext);
        View view=  layoutInflater.inflate(R.layout.chat_type_menu_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
         return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GrallyAdapter.ViewHolder holder, final int position) {
         holder.imageView.setImageResource(datalist.get(position));
         holder.ExpressionType=0x0001;
         holder.itemView.setTag(position);
    }
    @Override
    public int getItemCount() {
        return datalist==null?0:datalist.size();
    }
    public static class  ViewHolder extends  RecyclerView.ViewHolder  {
        public   AppCompatImageView imageView;
        public   Integer  ExpressionType;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(AppCompatImageView)itemView.findViewById(R.id.chat_expression_type_icon);
        }
    }





}
