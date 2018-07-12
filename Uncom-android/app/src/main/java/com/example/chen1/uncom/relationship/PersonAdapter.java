package com.example.chen1.uncom.relationship;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.utils.ImageUtils;
import com.example.chen1.uncom.utils.LoadImageUtils;

import java.util.ArrayList;

/**
 * Created by chen1 on 2018/2/17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements View.OnClickListener{
    private LoadImageUtils loadImageUtils;
    private Fragment fragment;
    private LayoutInflater layoutInflater;
    private ArrayList<RelationShipLevelBean> data;

    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener){
        this.itemOnClickListener=itemOnClickListener;
    }

   public interface  ItemOnClickListener{
       void onClick(View v,String type,RelationShipLevelBean bean);
   }
   public PersonAdapter(Fragment fragment){
        this.fragment=fragment;
       this.loadImageUtils=new LoadImageUtils();
   }
   public PersonAdapter(ArrayList<RelationShipLevelBean> beans,Fragment fragment){
       this.data=beans;
       this.fragment=fragment;
       this.loadImageUtils=new LoadImageUtils();
   }

    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.person_relation_ship_item_layout,null);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setTag(R.id.imageid,"itemView");
       loadImageUtils.getFirendHeaderImage(data.get(position).getHeader_pic(),holder.header,fragment);
       holder.header.setOnClickListener(this);
       holder.header.setTag(R.id.imageid,"header");
       holder.username.setText(data.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
       if(data==null){
           return  0;
       }else {
           return data.size();
       }
    }

    @Override
    public void onClick(View v){
        if(itemOnClickListener!=null) {
            if(v.getTag(R.id.imageid).equals("itemView")){
                itemOnClickListener.onClick(v, (String) v.getTag(R.id.imageid),data.get((Integer) v.getTag()));
            }else{
                itemOnClickListener.onClick(v, (String) v.getTag(R.id.imageid),data.get((Integer) ((View)v.getParent()).getTag()));
            }
        }
    }

    public void setData(ArrayList<RelationShipLevelBean>beans){
       this.data=beans;
       notifyDataSetChanged();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView header;
        ConstraintLayout layout;
        TextView username;
        public ViewHolder(View itemView) {
            super(itemView);
            header= (ImageView) itemView.findViewById(R.id.appCompatImageView3);
            username= (TextView) itemView.findViewById(R.id.person_username);
            layout= (ConstraintLayout) itemView.findViewById(R.id.container);
        }
    }
}
