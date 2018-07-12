package com.example.chen1.uncom.set;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.bean.ThinkerBeanDao;
import com.example.chen1.uncom.thinker.SetPagePhotoAdapter;
import com.example.chen1.uncom.thinker.ThinkerAdapter;
import com.example.chen1.uncom.utils.AsynLoadImageUtils;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/10/7.
 */

public class SetPageMainFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnClickListener{
    private LoadImageUtils loadImageUtils;
    private TimeUtils timeUtils;
    private Context context;
    private ArrayList<ArrayList<String >> imageUrlList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private ArrayList<Object> dataList;
    private ArrayList<RelationShipLevelBean> personMessageList;
    private Fragment fragment;
    public interface  OnItemClickListener{
        void onItemClick(View view,int position,Object object);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }


    public SetPageMainFragmentAdapter(Context context,ArrayList<Object> list,Fragment fragment){
        this.fragment=fragment;
        this.context=context;
        dataList=list;
        timeUtils=new TimeUtils();
        loadImageUtils=new LoadImageUtils();
    }


    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(v,(int)v.getTag(),dataList.get((int)v.getTag()));
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =null;
        layoutInflater = LayoutInflater.from(context);
        if(viewType==1){
            view= layoutInflater.inflate(R.layout.set_page_recyclerview_person_message_item,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }else if(viewType==2){
            view= layoutInflater.inflate(R.layout.set_thinker_item,parent,false);
            ThinkerViewHolder ThinkerviewHolder=new ThinkerViewHolder(view);
            view.setOnClickListener(this);
            return ThinkerviewHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(dataList.get(position) instanceof RelationShipLevelBean){
            if(position==0){
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
                lp.topMargin = 40;
                holder.itemView.setLayoutParams(lp);
            }
            loadImageUtils.getFirendHeaderImage(((RelationShipLevelBean)dataList.get(position)).getHeader_pic(),((ViewHolder)holder).headImage,fragment);
            ((ViewHolder)holder).userNameTextView.setText(((RelationShipLevelBean)dataList.get(position)).getUsername());
            ((ViewHolder)holder).lastMessageTextView.setText(((RelationShipLevelBean)dataList.get(position)).getLast_message());
            Integer temper=((RelationShipLevelBean)dataList.get(position)).getUn_look();
            if(temper!=null && temper>0){
                if(!  ((ViewHolder)holder).unLookSum.isShown()){
                    ((ViewHolder)holder).unLookSum.setVisibility(View.VISIBLE);
                }
                ((ViewHolder)holder).unLookSum.setText(""+temper);
            }else{
                ((ViewHolder)holder).unLookSum.setVisibility(View.GONE);
            }
            if(dataList!=null&&dataList.size()>0){
                ((ViewHolder)holder).messageDate.setText(timeUtils.compareTimeChatDisplay(((RelationShipLevelBean)dataList.get(position)).getLast_active_time(),((RelationShipLevelBean)dataList.get(position)).getLast_active_time()));
            }
        }else if(dataList.get(position) instanceof  ThinkerBean){
            ((ThinkerViewHolder)holder).cardView.setCardBackgroundColor(Color.parseColor("#ffffffff"));
            ((ThinkerViewHolder)holder).content.setVisibility(View.VISIBLE);
            ((ThinkerViewHolder)holder).title.setVisibility(View.VISIBLE);
            ((ThinkerViewHolder)holder).content.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
            ((ThinkerViewHolder)holder).title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            ((ThinkerViewHolder)holder).content.setText(null);
            ((ThinkerViewHolder)holder).titleLinearLayout.setVisibility(View.GONE);
            ((ThinkerViewHolder)holder).contentLinearLayout.setVisibility(View.GONE);
            ((ThinkerViewHolder)holder).title.setText(null);
            ((ThinkerViewHolder)holder).content.setGravity(Gravity.LEFT);
            ((ThinkerViewHolder)holder).title.setGravity(Gravity.LEFT);
            ((ThinkerViewHolder)holder).photoContainer.removeAllViews();
            TextPaint textPaint=((ThinkerViewHolder)holder).title.getPaint();
            textPaint.setFakeBoldText(true);
            if(((ThinkerBean)dataList.get(position)).getBackColor()!=null && ((ThinkerBean)dataList.get(position)).getBackColor().length()!=0){
              ((ThinkerViewHolder)holder).cardView.setCardBackgroundColor(Color.parseColor(((ThinkerBean)dataList.get(position)).getBackColor()));
            }


            if(((ThinkerBean)dataList.get(position)).getContent()!=null && ((ThinkerBean)dataList.get(position)).getContent().length()!=0){;
                ((ThinkerViewHolder)holder).contentLinearLayout.setVisibility(View.VISIBLE);
              ((ThinkerViewHolder)holder).content.setText(((ThinkerBean)dataList.get(position)).getContent());

            }
            if(((ThinkerBean)dataList.get(position)).getTitle()!=null && ((ThinkerBean)dataList.get(position)).getTitle().length()!=0){
                ((ThinkerViewHolder)holder).titleLinearLayout.setVisibility(View.VISIBLE);
                ((ThinkerViewHolder)holder).title.setText(((ThinkerBean)dataList.get(position)).getTitle());

            }

            if(((ThinkerBean)dataList.get(position)).getContent()==null || ((ThinkerBean)dataList.get(position)).getContent().length()==0 &&  ((ThinkerBean)dataList.get(position)).getImgUrl()==null){
              ((ThinkerViewHolder)holder).title.setGravity(Gravity.CENTER);
              ((ThinkerViewHolder)holder).title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
                TextPaint textPaints=   ((ThinkerViewHolder)holder).title.getPaint();
                textPaints.setFakeBoldText(false);
              ((ThinkerViewHolder)holder).content.setVisibility(View.GONE);
            }

            if(((ThinkerBean)dataList.get(position)).getTitle()==null ||((ThinkerBean)dataList.get(position)).getTitle().length()==0 &&  ((ThinkerBean)dataList.get(position)).getImgUrl()==null){
              ((ThinkerViewHolder)holder).content.setGravity(Gravity.CENTER);
              ((ThinkerViewHolder)holder).content.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
              ((ThinkerViewHolder)holder).title.setVisibility(View.GONE);
            }

            if(((ThinkerBean)dataList.get(position)).getImgUrl()!=null){
                addImageUrlList(dataList,position);
                SetPagePhotoAdapter photoAdatper=new SetPagePhotoAdapter(imageUrlList,((ThinkerBean)dataList.get(position)),position,fragment);
                photoAdatper.setOnItemClickListener(onItemClickListener);
              ((ThinkerViewHolder)holder).photoContainer.setVisibility(View.VISIBLE);
              ((ThinkerViewHolder)holder).photoContainer.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
              ((ThinkerViewHolder)holder).photoContainer.setHasFixedSize(true);
              ((ThinkerViewHolder)holder).photoContainer.setAdapter(photoAdatper);
            }else{
              ((ThinkerViewHolder)holder).photoContainer.setVisibility(View.GONE);
            }
        }

    }



    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position) instanceof  RelationShipLevelBean){
            return 1;
        }else if(dataList.get(position) instanceof  ThinkerBean){
            return 2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(dataList==null){
            return 0;
        }
        return dataList.size();
    }

    public static class  ThinkerViewHolder extends RecyclerView.ViewHolder{
        RecyclerView photoContainer;
        CardView cardView;
        TextView title;
        TextView content;
        LinearLayout titleLinearLayout;
        LinearLayout contentLinearLayout;
        public ThinkerViewHolder(View itemView) {
            super(itemView);
            titleLinearLayout= (LinearLayout) itemView.findViewById(R.id.titleLinearLayout);
            contentLinearLayout= (LinearLayout) itemView.findViewById(R.id.contentLinearLayout);
            cardView=(CardView)itemView.findViewById(R.id.item_view);
            photoContainer= (RecyclerView) itemView.findViewById(R.id.photo_container);
            content= (TextView) itemView.findViewById(R.id.content);
            title= (TextView) itemView.findViewById(R.id.title);
        }
    }

    public static  class  ViewHolder extends  RecyclerView.ViewHolder {
        private TextView lastMessageTextView;
        private TextView userNameTextView;
        private ImageView headImage;
        private TextView messageDate;
        private TextView unLookSum;
        public ViewHolder(View itemView) {
            super(itemView);
            messageDate=(TextView)itemView.findViewById(R.id.textView3);
            unLookSum=(TextView)itemView.findViewById(R.id.unlook_sum);
            lastMessageTextView= (TextView) itemView.findViewById(R.id.person_last_message);
            userNameTextView= (TextView) itemView.findViewById(R.id.person_username);
            headImage= (ImageView) itemView.findViewById(R.id.circleImageView);
        }
    }




    public   void add(ArrayList<Object> list){
        dataList=list;
        Log.v("SetPageAdatperListCount", "..."+String.valueOf(dataList.size()));
        notifyDataSetChanged();
    }
    public void addThinker(ThinkerBean thinkerBean){
        dataList.add(0,thinkerBean);
        notifyDataSetChanged();
    }

    /**
     *
     * @param thinkerBean
     * @param type 1强制置顶 2非置顶 3 删除
     */
    public void updateThinker(ThinkerBean thinkerBean,int type){
        Log.v("updateThinker","success");
        for (int i = 0; i <dataList.size() ; i++) {
            if (dataList.get(i) instanceof  ThinkerBean && (((ThinkerBean) dataList.get(i)).getCreateTime().equals(thinkerBean.getCreateTime()))){
                Log.v("updateThinker","targeted");
                if(type==1){
                    Log.v("type1","success");
                    if(i==0){
                        dataList.set(0,thinkerBean);
                    }else{
                        dataList.remove(i);
                        dataList.add(0,thinkerBean);
                    }
                    notifyDataSetChanged();
                    return ;
                }else if(type==0){
                    Log.v("type0","success");
                    dataList.set(i,thinkerBean);
                    notifyDataSetChanged();
                    return ;
                }else if(type==3){
                    Log.v("type3","success");
                    dataList.remove(i);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public void delThinker(ThinkerBean thinkerBean){
        if(dataList!=null){
            for (int i = 0; i <dataList.size() ; i++) {
                if(dataList.get(i) instanceof ThinkerBean && (((ThinkerBean) dataList.get(i)).getCreateTime().equals(thinkerBean.getCreateTime()))){
                    dataList.remove(i);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    /**
     * 更新界面好友信息数据(Set)
     * @param relationShipLevelBean  消息Bean(当前指定为好友信息Bean)
     * @param isRealChange  1表示除了更新未读提示外，还会对该信息在聚合(Set)页面的位置进行更新(example:某人给我发了信息，
     *                      而之前也有人给我发了信息，我认为这时，不能直接放到头部)
     *                      2表示只是更新未读提示(example:他只是看了这个人发来的的消息，而并没有给这个人回复)
     *                      3除了更新未读提示 还会更新其位置，并更新到最顶端(example:该用户不仅看了这个人发来的的消息，
     *                       并且也回复了他)
     */
    public String updateActivePersonMessageList(RelationShipLevelBean relationShipLevelBean,int isRealChange) {
        Log.v("进入Set消息排序界面","ok");
        for (int i = 0; i < dataList.size(); i++) {
            if( (dataList.get(i) instanceof  RelationShipLevelBean )&& ((RelationShipLevelBean)dataList.get(i)).getMinor_user().equals(relationShipLevelBean.getMinor_user())) {
                Log.v("updateMessage", String.valueOf(isRealChange));
                if (isRealChange == 1) {
                    //在未读信息之后加入该信息
                    //如果该用户会话状态在Set界面头部，则只需将其加到头部
                    if (i == 0) {
                        dataList.set(0, relationShipLevelBean);
                    } else {//如不在头部则，从未读信息后加入
                        dataList.remove(i);
                        for (int j = 0; j < dataList.size(); j++) {

                            if(dataList.get(j) instanceof RelationShipLevelBean){
                                if( ((RelationShipLevelBean)dataList.get(j)).getUn_look() == null|| ((RelationShipLevelBean)dataList.get(j)).getUn_look() == 0 ) {
                                    dataList.add(j, relationShipLevelBean);
                                    Log.v("searchResuls", "s" + ((RelationShipLevelBean)dataList.get(j)).getUn_look());
                                    break;
                                } else if (j == (dataList.size() - 1)) {
                                    dataList.add(relationShipLevelBean);
                                    break;
                                }
                            }
                        }
                    }

                } else if (isRealChange == 2) {
                    dataList.set(i, relationShipLevelBean);
                } else if (isRealChange == 3) {
                    //在未读信息之后加入该信息
                    //如果该用户会话状态在Set界面头部，则只需将其加到头部
                    Log.v("currentI   ", String.valueOf(i));
                    if (i == 0) {
                        dataList.set(0, relationShipLevelBean);
                    } else {//如不在头部则将其加入到头部 如之前在Set界面，则删掉之前的位置
                        dataList.add(0, relationShipLevelBean);
                        dataList.remove(i + 1);
                    }
                }
                notifyDataSetChanged();
                return ";";
            }
            if ((i == dataList.size() - 1) && (isRealChange == 1 || isRealChange == 3)) {
                dataList.add(0, relationShipLevelBean);
                notifyDataSetChanged();
                return "";
            }
        }
        //对应一个特殊情况：处理该条消息时，聚合(Set)界面为空
        if(isRealChange == 1 || isRealChange==2 || isRealChange == 3){
            Log.v("执行该语句","ok");
            dataList.add(0, relationShipLevelBean);
            notifyDataSetChanged();
        }
        return "";
    }


    public void addImageUrlList(ArrayList<Object> list,int position){
        ThinkerBean thinkerBean=(ThinkerBean) list.get(position);
        boolean isSync=false;
        ArrayList<String> origin_url=new ArrayList<String>(Arrays.asList(thinkerBean.getImgUrl().split(",")));
        ArrayList<String> cache_url=null;
        ArrayList<String> online_url=null;
        if(thinkerBean.getImgCacheUrl()!=null&&thinkerBean.getImgCacheUrl().length()>0){
            cache_url=new ArrayList<>(Arrays.asList(thinkerBean.getImgCacheUrl().split(",")));

        }
        if(thinkerBean.getImgOnlineUrl()!=null && thinkerBean.getImgOnlineUrl().length()>0){
            online_url=new ArrayList<>(Arrays.asList(thinkerBean.getImgOnlineUrl().split(",")));
        }
        imageUrlList=new ArrayList<ArrayList<String>>();
        for (int i=0;i<origin_url.size();i++){
            if(i%2==0){
                ArrayList<String> item=new ArrayList<String>();
                if( cache_url!=null && cache_url.size()>i){
                    File file=new File(cache_url.get(i));
                    if(file.exists()){
                        item.add(cache_url.get(i));
                    }else{
                        cache_url.remove(i);
                        thinkerBean.setImgCacheUrl(cache_url.toString().replace("[","").replace("]",""));
                        list.set(position,thinkerBean);
                        isSync=true;
                        if(online_url!=null && online_url.size()>i && cache_url.size()<i){
                            item.add(online_url.get(i));
                        }else{
                            item.add(origin_url.get(i));
                        }
                    }
                }else if(online_url!=null && online_url.size()>i && cache_url.size()<i){
                    item.add(online_url.get(i));
                }else{
                    item.add(origin_url.get(i));
                }
                imageUrlList.add(item);
            }else{
                if(cache_url!=null && cache_url.size()>i){
                    Log.v("cache_url", String.valueOf(cache_url));
                    File file=new File(cache_url.get(i));
                    if(file.exists()){
                        imageUrlList.get(i/2).add(cache_url.get(i));
                    }else{
                        isSync=true;
                        Log.v("deleteCache_url","success");
                        cache_url.remove(i);
                        thinkerBean.setImgCacheUrl(cache_url.toString().replace("[","").replace("]",""));
                        list.set(position,thinkerBean);

                        if(online_url!=null &&  online_url.size()>i && cache_url.size()<i){
                            imageUrlList.get(i/2).add(online_url.get(i));
                        }else{
                            imageUrlList.get(i/2).add(origin_url.get(i));
                        }
                    }
                }else if(online_url!=null &&  online_url.size()>i && cache_url.size()<i){
                    imageUrlList.get(i/2).add(online_url.get(i));
                }else{
                    imageUrlList.get(i/2).add(origin_url.get(i));
                }
            }
        }
        if(isSync){
            ThinkerBeanDao thinkerBeanDao= BeanDaoManager.getInstance().getDaoSession().getThinkerBeanDao();
            thinkerBeanDao.update((ThinkerBean) list.get(position));
        }
    }



}
