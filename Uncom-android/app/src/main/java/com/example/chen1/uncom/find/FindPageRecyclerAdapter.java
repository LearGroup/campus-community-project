package com.example.chen1.uncom.find;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.bean.RoutineBean;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by chen1 on 2017/11/17.
 */

public class FindPageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private ArrayList<RoutineBean> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickLitener(OnItemClickListener onItemClickLitener){
        this.onItemClickListener=onItemClickLitener;

    }
    interface  OnItemClickListener{
        void onClick(View view,int positon,RoutineBean routineBean);
    }
    private final Animation anim =new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private final Animation anim2 =new RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    public FindPageRecyclerAdapter(Context context, ArrayList<RoutineBean> list){
        this.context=context;
        this.list=list;
        anim.setDuration(300);
        anim2.setDuration(300);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.find_page_active_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        String tag[]=list.get(position).getTag().split(",");
        String  data= new  SimpleDateFormat("yyyy-MM-dd").format(list.get(position).getCreateTime());
        ((ViewHolder)holder).activeName.setText(list.get(position).getName());
        //((ViewHolder)holder). quality.setText(list.get(position).getQuality().toString());
        ((ViewHolder)holder).shortReadme.setText(list.get(position).getShortReadme());
        ((ViewHolder)holder).createTime.setText(data);
        if(list.get(position).getOfline()==true){
            ((ViewHolder)holder).ofline.setVisibility(View.VISIBLE);
        }else{
            ((ViewHolder)holder).ofline.setVisibility(View.GONE);
        }
        if(list.get(position).getOnline()==true){
            ((ViewHolder)holder).online.setVisibility(View.VISIBLE);
        }else{
            ((ViewHolder)holder).online.setVisibility(View.GONE);
        }
        if(tag[0].equals("null")){
            ((ViewHolder)holder).tag_1.setText("null");
        }else{
            ((ViewHolder)holder).tag_1.setText(tag[0]);
        }
        if(tag[1].equals("null")){
            ((ViewHolder)holder).tag_2.setVisibility(View.GONE);
        }else{
            ((ViewHolder)holder).tag_2.setText(tag[0]);
        }
        if(tag[2].equals("null")){
            ((ViewHolder)holder).tag_3.setVisibility(View.GONE);
        }else{
            ((ViewHolder)holder).tag_3.setText(tag[0]);
        }
        ((ViewHolder)holder).address.setText(list.get(position).getAddress());
        ((ViewHolder)holder).activePercent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, list.get(position).getActivePercent()));
        ((ViewHolder)holder).activePrecentWhite.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) (1.0-list.get(position).getActivePercent())));
        TabPagerAdapter adapter =new TabPagerAdapter(context);
        ((ViewHolder)holder).viewPager.setAdapter(adapter);
        ((ViewHolder)holder).tabLayout.setupWithViewPager( ((ViewHolder)holder).viewPager);
        ((ViewHolder)holder).tabLayout.getTabAt(0).setText("简述");
        ((ViewHolder)holder).tabLayout.getTabAt(1).setText("详细信息");
        ((ViewHolder)holder).tabLayout.getTabAt(2).setText("活动数据");
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }else{
         return list.size();
        }
    }

    @Override
    public void onClick(View v) {

        Log.v("NewRelationshipAdapter","click ok");
        if(onItemClickListener!=null) {
            onItemClickListener.onClick(v, (int) v.getTag(), list.get((int) v.getTag()));
        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView activeName=null;
        public AppCompatImageView openCardView=null;
        public TextView ofline=null;
        public TextView online=null;
        public TextView shortReadme=null;
        public TextView activePercent=null;
        public TextView tag_1=null;
        public TextView tag_2=null;
        public TextView tag_3=null;
        public TextView address=null;
        public TextView createTime=null;
        public TextView quality=null;
        public TextView activePrecentWhite=null;
        public ViewPager viewPager=null;
        public TabLayout tabLayout=null;
        public ViewHolder(View itemView) {
            super(itemView);
            activeName= (TextView) itemView.findViewById(R.id.textView9);
            ofline=(TextView)itemView.findViewById(R.id.textView6);
            online=(TextView)itemView.findViewById(R.id.textView12);
            shortReadme=(TextView)itemView.findViewById(R.id.textView13);
            activePercent=(TextView)itemView.findViewById(R.id.textView16);
            tag_1=(TextView)itemView.findViewById(R.id.textView7);
            tag_2=(TextView)itemView.findViewById(R.id.textView8);
            tag_3=(TextView)itemView.findViewById(R.id.textView14);
            address=(TextView)itemView.findViewById(R.id.page_address);
            createTime=(TextView)itemView.findViewById(R.id.textView10);
            quality=(TextView)itemView.findViewById(R.id.textView11);
            activePrecentWhite=(TextView)itemView.findViewById(R.id.textView26);
            openCardView=(AppCompatImageView)itemView.findViewById(R.id.more_activity_info);
            viewPager=(ViewPager)itemView.findViewById(R.id.viewpagers);
            tabLayout= (TabLayout) itemView.findViewById(R.id.extend_tabLayout);
        }
    }


    public void  add(RoutineBean item){
        list.add(item);
        notifyItemInserted(list.size());
    }

    public void lazyLoad(ArrayList<RoutineBean> list){
        this.list=list;
        notifyItemInserted(list.size());
    }
}
