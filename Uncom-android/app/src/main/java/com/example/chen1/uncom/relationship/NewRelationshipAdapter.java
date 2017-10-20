package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.set.SetPageMainFragmentAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/10/14.
 */

public class NewRelationshipAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnClickListener {

    private ArrayList<NewRelationShipBean> datalist=new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public  interface  OnItemClickListener{
          void onClick(View view,int positon,NewRelationShipBean newRelationShipBean);
    }

    public NewRelationshipAdapter(Context context,ArrayList<NewRelationShipBean> list){
        this.context=context;
        this.datalist=list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =null;
        layoutInflater = LayoutInflater.from(context);
        if(viewType==0){
            view= layoutInflater.inflate(R.layout.search_new_relationship_item_layout,parent,false);
            view.setOnClickListener(this);
            return new NewRelationshipAdapter.SearchViewHoler(view);
        }else if(viewType==1){
            view= layoutInflater.inflate(R.layout.search_results_item_layout,parent,false);
            return new NewRelationshipAdapter.SearchViewHoler(view);
        }else{

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(holder instanceof SearchViewHoler){
            ((SearchViewHoler) holder).input_results.setText(datalist.get(0).getResults());
        }else if(holder instanceof  HistoryViewHolder){

        }
    }

    @Override
    public int getItemViewType(int position) {
        return   datalist.get(position).getView_type();
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public void onClick(View v) {
        Log.v("NewRelationshipAdapter","click ok");
        if(onItemClickListener!=null){
            onItemClickListener.onClick(v,(int)v.getTag(),datalist.get((int)v.getTag()));
        }
    }


    public void add(NewRelationShipBean item){
        if(item.getView_type()==0 ){
            if(item.getResults().length()<=0){
                datalist.remove(0);
                notifyItemRemoved(0);
                return ;
            }
            if(datalist.size()==0){
                datalist.add(item);
                notifyItemInserted(0);
                return ;
            }
            datalist.set(0,item);
        }else{
            datalist.add(item);
        }
        notifyDataSetChanged();
    }

    /**
     * 只对应第一次搜索框加入
     * @param item
     * @param status
     */
    public void add(NewRelationShipBean item ,int status){
        if(item.getView_type()==0 ) {
            datalist.add(0, item);
            notifyItemInserted(0);
        }
    }

    public void remove(int position){
        notifyItemRemoved(position);
    }


    public static class  SearchViewHoler extends  RecyclerView.ViewHolder{
        TextView input_results;
        public SearchViewHoler(View itemView) {
            super(itemView);
            input_results= (TextView) itemView.findViewById(R.id.results);
        }
    }

    public static class HistoryViewHolder extends  RecyclerView.ViewHolder{

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }
    }

}
