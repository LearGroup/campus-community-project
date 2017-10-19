package com.example.chen1.uncom.set;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.TimeUtils;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/10/7.
 */

public class SetPageMainFragmentAdapter extends RecyclerView.Adapter<SetPageMainFragmentAdapter.ViewHolder> implements  View.OnClickListener{

    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private ArrayList<RelationShipLevelBean> personMessageList;
    public interface  OnItemClickListener{
        void onItemClick(View view,int position,RelationShipLevelBean relationShipLevelBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }


    public SetPageMainFragmentAdapter(Context context,ArrayList<RelationShipLevelBean> list){
        Log.v("setPageAdapterActiveCount", String.valueOf(list.size()));
        this.context=context;
        personMessageList=list;
    }


    @Override
    public void onClick(View v) {
        Log.v("setPageOnCLICK","OK");
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(v,(int)v.getTag(),personMessageList.get((int)v.getTag()));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =null;
        layoutInflater = LayoutInflater.from(context);
        view= layoutInflater.inflate(R.layout.set_page_recyclerview_person_message_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        LoadImageUtils.getFirendHeaderImage(personMessageList.get(position).getHeader_pic(),context,holder.headImage);
        holder.userNameTextView.setText(personMessageList.get(position).getUsername());
        holder.lastMessageTextView.setText(personMessageList.get(position).getLast_message());
        Integer temper=personMessageList.get(position).getUn_look();
        Log.v("SetPageFragmentAdapterTemper", String.valueOf(temper));
        if(temper!=null && temper>0){
            if(!holder.unLookSum.isShown()){
                holder.unLookSum.setVisibility(View.VISIBLE);
            }
            holder.unLookSum.setText(""+temper);
        }else{
            holder.unLookSum.setVisibility(View.GONE);
        }
        if(personMessageList!=null&&personMessageList.size()>0){
            Log.v("teimse","a"+personMessageList.size());
            holder.messageDate.setText(TimeUtils.compareTimeChatDisplay(personMessageList.get(position).getLast_active_time(),personMessageList.get(position).getLast_active_time()));
        }
     }




    @Override
    public int getItemCount() {
        if(personMessageList==null){
            return 0;
        }
        return personMessageList.size();
    }



    public static class  ViewHolder extends  RecyclerView.ViewHolder {
        private TextView lastMessageTextView;
        private TextView userNameTextView;
        private CircleImageView headImage;
        private TextView messageDate;
        private TextView unLookSum;
        public ViewHolder(View itemView) {
            super(itemView);
            messageDate=(TextView)itemView.findViewById(R.id.textView3);
            unLookSum=(TextView)itemView.findViewById(R.id.unlook_sum);
            lastMessageTextView= (TextView) itemView.findViewById(R.id.person_last_message);
            userNameTextView= (TextView) itemView.findViewById(R.id.person_username);
            headImage= (CircleImageView) itemView.findViewById(R.id.circleImageView);
        }
    }




    public   void add(RelationShipLevelBean relationShipLevelBean, int position , RelationShipLevelBeanDao relationShipLevelBeanDao){
        personMessageList.add(relationShipLevelBean);
        notifyItemInserted(personMessageList.size());
    }
}
