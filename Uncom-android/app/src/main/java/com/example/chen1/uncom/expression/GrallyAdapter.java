package com.example.chen1.uncom.expression;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.example.chen1.uncom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen1 on 2017/6/28.
 */

public class GrallyAdapter extends RecyclerView.Adapter<GrallyAdapter.ViewHolder> implements View.OnClickListener{

    private List<Integer> list=new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private List<Integer> datalist=new ArrayList<>();
    private Context conext;
    private LayoutInflater layoutInflater;
    private ArrayList<Boolean> isFocus =new ArrayList<Boolean>();
    private View lastView=null;
    ScaleAnimation animation = new ScaleAnimation(
            1.0f, 1.4f, 1.0f, 1.4f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    );
    ScaleAnimation recoveryAnimation =new ScaleAnimation( 1.4f,1.0f, 1.4f,  1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(v,(int)v.getTag());
            if(lastView!=null) {
                recoveryAnimation.setDuration(150);
                recoveryAnimation.setFillBefore(true);
                lastView.startAnimation(recoveryAnimation);
            }
            animation.setDuration(150);
            animation.setFillAfter(true);
            v.startAnimation(animation);
            lastView=v;

        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return  1;
        }else {
            return 0;
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
        isFocus.add(false);
        isFocus.add(false);
        isFocus.add(false);


    }

    @Override
    public GrallyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater= LayoutInflater.from(conext);
        View view=  layoutInflater.inflate(R.layout.chat_type_menu_layout,parent,false);
        if(viewType==1){
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(dip2px(conext,23),dip2px(conext,23));
            lp.topMargin=dip2px(conext,8);
            lp.rightMargin=dip2px(conext,15);
            lp.leftMargin=dip2px(conext,25);
            AppCompatImageView imageView =(AppCompatImageView) view.findViewById(R.id.chat_expression_type_icon);
            imageView.setLayoutParams(lp);
            animation.setDuration(150);
            animation.setFillAfter(true);
            view.startAnimation(animation);
            lastView=view;
        }
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
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
