package com.example.chen1.uncom.chat;

import android.content.Context;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
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
    private SpanStringUtils spanStringUtils;
    private Context context;
    private TimeUtils timeUtils;
    private RelationShipLevelBean minor_data;
    private RelationShipLevelBean frendData;
    private LayoutInflater layoutInflater;
    private LoadImageUtils loadImageUtils;
    private Fragment fragment;
    private ArrayList<MessageHistoryBean> listItem =new ArrayList<MessageHistoryBean>();
   public PersonChatRecyclerViewAdapter(Context context, RelationShipLevelBean data, Fragment fragment){
       this.context=context;
       this.fragment=fragment;
       frendData=data;
       this.spanStringUtils=new SpanStringUtils();
       timeUtils=new TimeUtils();
       loadImageUtils=new LoadImageUtils();
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
        layoutInflater =LayoutInflater.from(parent.getContext());
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
       holder.itemview.setTag(position);
        RecyclerView.LayoutParams lps = (RecyclerView.LayoutParams) holder.itemview.getLayoutParams();
        lps.topMargin = 0;
        holder.itemview.setLayoutParams(lps);
       if(position==0||(listItem.get(position-1).getTargetId().equals(listItem.get(position).getTargetId())==false)){
           RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemview.getLayoutParams();
           lp.topMargin = 25;
           holder.itemview.setLayoutParams(lp);
       }
        if(listItem.get(position).getOwnId().equals(frendData.getMinor_user())){
             holder.header_image.setVisibility(View.VISIBLE);
            loadImageUtils.getFirendHeaderImage(frendData.getHeader_pic(),holder.header_image,fragment);
        }else{
            holder.header_image.setVisibility(View.GONE);
        }
        if(!listItem.get(position).getContent().equals(null)){
            String str =listItem.get(position).getContent();
             SpannableString spannableString= spanStringUtils.getEmotionContent(1,context, holder.textView,str);
             holder.textView.setText(spannableString);
        }
        //如果该条信息与上一条信息时间差大于3分钟 则显示这条数据发送的时间
        if((position-1)>=0&&((listItem.get(position).getTime().getTime()-listItem.get(position-1).getTime().getTime())/1000>180)){
            holder.messageTime.setVisibility(View.VISIBLE);
            holder.messageTime.setText(timeUtils.compareTimeChatDisplay(new Date(),listItem.get(position).getTime()));

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
       private LinearLayout itemview;
        private TextView textView=null;
        private ImageView header_image=null;
        private  TextView messageTime;
        public ViewHolder(View itemView) {
            super(itemView);
            header_image=(ImageView)itemView.findViewById(R.id.person_chat_own_header_circleImageView);
            textView=(TextView)itemView.findViewById(R.id.person_chat_own_content_textview);
            messageTime=(TextView)itemView.findViewById(R.id.message_time);
            itemview= (LinearLayout) itemView;
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
                Log.v("BadgeCount:", String.valueOf(BadgeMessageUtil.getItem_1()+1));
                BadgeMessageUtil.setItem_1(BadgeMessageUtil.getItem_1()+1);
                Integer temp=frendData.getUn_look();
                Log.v("sendTmper", String.valueOf(isVisible));
                if(temp!=null){
                    frendData.setUn_look(temp+1);
                }else{
                    frendData.setUn_look(1);
                }
                if(frendData.getUn_look()>1){
                    CoreApplication.newInstance().getSetPageMainFragmentAdapter().updateActivePersonMessageList(frendData,2);
                }else{
                    CoreApplication.newInstance().getSetPageMainFragmentAdapter().updateActivePersonMessageList(frendData,1);
                }
            }else{
                frendData.setUn_look(0);
                CoreApplication.newInstance().getSetPageMainFragmentAdapter().updateActivePersonMessageList(frendData,3);
            }
            relationShipLevelBeanDao.update(frendData);
            notifyItemInserted(listItem.size());

        }else{
            Log.v("send User id: ",messageHistoryBean.getOwnId());
            RelationShipLevelBean relationShipLevelBean=CoreApplication.newInstance().getRelationShipBean(messageHistoryBean.getOwnId(),relationShipLevelBeanDao);
            relationShipLevelBean.setLast_message(messageHistoryBean.getContent());
            Log.v("active2",relationShipLevelBean.getUsername());
            relationShipLevelBean.setLast_active_time(messageHistoryBean.getTime());
            if(relationShipLevelBean.getActive()==false){
                relationShipLevelBean.setActive(true);
            }
            //设置该消息未阅读
            Log.v("BadgeCount:", String.valueOf(BadgeMessageUtil.getItem_1()+1));
            BadgeMessageUtil.setItem_1(BadgeMessageUtil.getItem_1()+1);
            Integer temp=relationShipLevelBean.getUn_look();
            Log.v("sendTmper", String.valueOf(temp));
            if(temp!=null){
                relationShipLevelBean.setUn_look(temp+1);
            }else{
                relationShipLevelBean.setUn_look(1);
            }
            CoreApplication.newInstance().getSetPageMainFragmentAdapter().updateActivePersonMessageList(relationShipLevelBean,1);
            relationShipLevelBeanDao.update(relationShipLevelBean);
        }
        if(messageHistoryBean.getId()!=null){
            //对应发送消息
            messageHistoryBeanDao.update(messageHistoryBean);
        }else{
            //对应接受聊天消息
            messageHistoryBeanDao.insert(messageHistoryBean);
        }
    }

    public ArrayList<MessageHistoryBean> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<MessageHistoryBean> listItem) {
        this.listItem = listItem;
        notifyDataSetChanged();
    }


    public RelationShipLevelBean getFrendData() {
        return frendData;
    }

    public void setFrendData(RelationShipLevelBean frendData) {
        this.frendData = frendData;
    }
}
