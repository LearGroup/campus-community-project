package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.utils.LoadImageUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/10/18.
 */

public class NewRelationShipSearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnClickListener {
    private LoadImageUtils loadImageUtils;
    private ArrayList<NewRelationShipBean>data;
    private Context context;
    private LayoutInflater layoutInflater;
    private Fragment fragment;
    private OnItemOnClickListener onItemOnClickListener;

    public   interface OnItemOnClickListener{
            void OnClick(View view,int positon,NewRelationShipBean newRelationShipBean);
    }


    public void setOnItenClickListener(OnItemOnClickListener onItenClickListener){
        this.onItemOnClickListener=onItenClickListener;
    }

    public NewRelationShipSearchResultsAdapter(Context context, Fragment fragment){
        this.fragment=fragment;
        this.context=context;
        data=new ArrayList<>();
        loadImageUtils=new LoadImageUtils();
    }


    @Override
    public int getItemViewType(int position) {
        return data.get(position).getView_type();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =null;
        layoutInflater = LayoutInflater.from(context);
        if(viewType==0){
            view= layoutInflater.inflate(R.layout.search_new_relationship_item_layout,parent,false);
            view.setOnClickListener(this);
            return new NewRelationshipAdapter.SearchViewHoler(view);
        }else if(viewType==2){
            view= layoutInflater.inflate(R.layout.new_relationship_search_results_item_layout,parent,false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(holder instanceof NewRelationshipAdapter.SearchViewHoler){
            ((NewRelationshipAdapter.SearchViewHoler) holder).input_results.setText(data.get(0).getResults());
        }else if(holder instanceof ViewHolder){
            loadImageUtils.getFirendHeaderImage(data.get(position).getHeader_pic(), (ImageView)((ViewHolder) holder).header_pic,fragment);
            ((ViewHolder) holder).username.setText(data.get(position).getUser_name());
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(data!=null){
            count=data.size();
        }
        return count;
    }

    @Override
    public void onClick(View v) {
        if(onItemOnClickListener!=null){
            onItemOnClickListener.OnClick(v,(int)v.getTag(),data.get((int)v.getTag()));
        }
    }



    public void add(NewRelationShipBean item){
        if(item.getView_type()==0 ){
            if(item.getResults().length()<=0){
                data.remove(0);
                notifyItemRemoved(0);
                return ;
            }
            if(data.size()==0){
                data.add(item);
                notifyItemInserted(0);
                return ;
            }
            data.set(0,item);
        }else{
            data.add(item);
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
            data.add(0, item);
            notifyItemInserted(0);
        }
    }



    public static class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView username;
        ImageView header_pic;
        public ViewHolder(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.person_username);
            header_pic=(ImageView) itemView.findViewById(R.id.circleImageView2);
        }
    }

    public ArrayList<NewRelationShipBean> getData() {
        return data;
    }

    public void setData(ArrayList<NewRelationShipBean> data) {
        this.data = data;
    }
}
