package com.example.chen1.uncom.chat;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.SpanStringUtils;
import com.example.chen1.uncom.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/9/21.
 */

public class PersonChatRecyclerViewAdapter extends RecyclerView.Adapter<PersonChatRecyclerViewAdapter.ViewHolder> implements  View.OnClickListener{
    private boolean ITEM_TYPE;
    private Context context;
    private RelationShipLevelBean minor_data;
    private RelationShipLevelBean frendData;
    private LayoutInflater layoutInflater;
    private ArrayList<MessageHistoryBean> listItem =new ArrayList<MessageHistoryBean>();
   public PersonChatRecyclerViewAdapter(Context context,RelationShipLevelBean data){
       this.context=context;
       frendData=data;
   }

    @Override
    public int getItemViewType(int position) {
        if(listItem.get(position).getMessageType()){
            return  1;
        }else {
            return 0;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater =LayoutInflater.from(context);
        View view =null;
        if(viewType==1){
            view= layoutInflater.inflate(R.layout.person_chat_item_own,parent,false);
        }else{
            view= layoutInflater.inflate(R.layout.person_chat_item_opposite,parent,false);
        }
        ViewHolder viewholder =new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        LoadImageUtils.getFirendHeaderImage(frendData.getHeader_pic(),context,holder.header_image);
        if(!listItem.get(position).getContent().equals(null)){
            String str =listItem.get(position).getContent();
             SpannableString spannableString= SpanStringUtils.getEmotionContent(1,context, holder.textView,str);
             holder.textView.setText(spannableString);
        }
        //如果该条信息与上一条信息时间差大于3分钟 则显示这条数据发送的时间
        if((position-1)>0&&((listItem.get(position).getTime().getTime()-listItem.get(position-1).getTime().getTime())/1000>180)){
            holder.messageTime.setVisibility(View.VISIBLE);
            holder.messageTime.setText(TimeUtils.compareTimeChatDisplay(listItem.get(position).getTime(),listItem.get(position-1).getTime()));

        }else{
            holder.messageTime.setVisibility(View.GONE);
        }
        //holder.textView.setText(listItem.get(position));

    }
    @Override
    public int getItemCount() {
        return listItem==null?0:listItem.size();
    }

    @Override
    public void onClick(View view) {

    }

    public static class   ViewHolder extends  RecyclerView.ViewHolder {
        private TextView textView=null;
        private CircleImageView header_image=null;
        private  TextView messageTime;
        public ViewHolder(View itemView) {
            super(itemView);
            header_image=(CircleImageView)itemView.findViewById(R.id.person_chat_own_header_circleImageView);
            textView=(TextView)itemView.findViewById(R.id.person_chat_own_content_textview);
            messageTime=(TextView)itemView.findViewById(R.id.message_time);
        }
    }


    public   void   add(boolean isVisible,MessageHistoryBean messageHistoryBean, int position , MessageHistoryBeanDao messageHistoryBeanDao){
        RelationShipLevelBeanDao   relationShipLevelBeanDao = BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
       //如果发送信息者的id为当前fendData的id相同，或者我发送信息时targetid与fendDataid相同
        Log.v("messageId",messageHistoryBean.getOwnId());
        Log.v("frenDataId",frendData.getMinor_user());
        Log.v("MessageType", String.valueOf(messageHistoryBean.getMessageType()));
        if((messageHistoryBean.getMessageType()==false && messageHistoryBean.getOwnId().equals(frendData.getMinor_user())) || (messageHistoryBean.getTargetId().equals(frendData.getMinor_user())&& messageHistoryBean.getMessageType()==true) ){
            Log.v("active1","true");
            listItem.add(messageHistoryBean);
            frendData.setLast_message(messageHistoryBean.getContent());
            frendData.setLast_active_time(messageHistoryBean.getTime());
            if(frendData.getActive()==false){
                frendData.setActive(true);
            }
            //该判断为真，则说明用户目前不在与该消息发送者的聊天视图中
            if(isVisible==false){
                Integer temp=frendData.getUn_look();
                Log.v("sendTmper", String.valueOf(isVisible));
                if(temp!=null){
                    frendData.setUn_look(temp+1);
                }else{
                    frendData.setUn_look(1);
                }
                if(frendData.getUn_look()>1){
                    CoreApplication.newInstance().updateActivePersonMessageList(frendData,2);
                }else{
                    CoreApplication.newInstance().updateActivePersonMessageList(frendData,1);
                }
            }else{
                frendData.setUn_look(0);
                CoreApplication.newInstance().updateActivePersonMessageList(frendData,3);
            }
            relationShipLevelBeanDao.update(frendData);
            notifyItemInserted(listItem.size());
        }else{
            RelationShipLevelBean relationShipLevelBean=CoreApplication.newInstance().getRelationShipBean(messageHistoryBean.getOwnId());
            relationShipLevelBean.setLast_message(messageHistoryBean.getContent());
            Log.v("active2",relationShipLevelBean.getUsername());
            relationShipLevelBean.setLast_active_time(messageHistoryBean.getTime());
            if(relationShipLevelBean.getActive()==false){
                relationShipLevelBean.setActive(true);
            }
            //设置该消息未阅读
            Integer temp=relationShipLevelBean.getUn_look();
            Log.v("sendTmper", String.valueOf(temp));
            if(temp!=null){
                relationShipLevelBean.setUn_look(temp+1);
            }else{
                relationShipLevelBean.setUn_look(1);
            }
            CoreApplication.newInstance().updateActivePersonMessageList(relationShipLevelBean,1);
            relationShipLevelBeanDao.update(relationShipLevelBean);
        }
        messageHistoryBeanDao.insert(messageHistoryBean);
    }

    public ArrayList<MessageHistoryBean> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<MessageHistoryBean> listItem) {
        this.listItem = listItem;
        notifyItemInserted(listItem.size());
    }
}
