package com.example.chen1.uncom.thinker;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.RoutineBean;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/12/9.
 */

public class ColorSelectorAdapter extends RecyclerView.Adapter<ColorSelectorAdapter.ViewHolder> implements View.OnClickListener{
    private int selectePosition=0;
    private ArrayList<String>colorList;
    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    private ItemOnClickListener itemOnClickListener;
    public interface  ItemOnClickListener{
        void  onClick(View view,int positon,String color);
    }

    public void  setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener=itemOnClickListener;
    }

    public void setSelectedPosition(int position){
        selectePosition=position;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(String color){
        for (int i = 0; i <colorList.size() ; i++) {
            if(color.equals(colorList.get(i))){
                selectePosition=i;
                notifyDataSetChanged();
            }
        }
    }


    public ColorSelectorAdapter(Context context){
        layoutInflater =LayoutInflater.from(context);
        colorList=new ArrayList<String>();
        colorList.add("#FFFFFF");
        colorList.add("#FF8A80");
        colorList.add("#FFD180");
        colorList.add("#FFFF8D");
        colorList.add("#CCFF90");
        colorList.add("#A7FFEB");
        colorList.add("#80D8FF");
        colorList.add("#82B1FF");
        colorList.add("#B388FF");
        colorList.add("#F8BBD0");
        colorList.add("#D7CCC8");
        colorList.add("#CFD8DC");


    }
    @Override
    public ColorSelectorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= layoutInflater.inflate(R.layout.color_select_item_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        AppCompatImageView imageView= (AppCompatImageView) view.findViewById(R.id.color_selector_img);
        viewHolder.cir.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ColorSelectorAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.cir.setTag(position);
        if(position==selectePosition){
            holder.selected.setVisibility(View.VISIBLE);
        }else{
            holder.selected.setVisibility(View.GONE);
        }
        holder.cir.setColorFilter(Color.parseColor(colorList.get(position)));
    }

    @Override
    public int getItemCount() {
        if(colorList!=null){
            return colorList.size();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if(itemOnClickListener!=null) {
            itemOnClickListener.onClick(v, (int)v.getTag(), colorList.get((int) v.getTag()));
        }

    }


    public class ViewHolder extends  RecyclerView.ViewHolder{
        public CircleImageView cir;
        public AppCompatImageView selected;
        public ViewHolder(View itemView) {
            super(itemView);
            cir= (CircleImageView) itemView.findViewById(R.id.color_selector_cir);
            selected= (AppCompatImageView) itemView.findViewById(R.id.color_selector_img);
        }
    }
}
