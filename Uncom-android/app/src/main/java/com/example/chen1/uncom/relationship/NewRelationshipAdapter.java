package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.set.SetPageMainFragmentAdapter;
import com.example.chen1.uncom.utils.ChatUserDataUtil;
import com.example.chen1.uncom.utils.LoadImageUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chen1 on 2017/10/14.
 */

public class NewRelationshipAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> implements  View.OnClickListener {

        private ArrayList<NewRelationShipBean> datalist=new ArrayList<>();
    private Context context;
    private Button acceptButton;
    private LayoutInflater layoutInflater;
    private TextView acceptOk;
    private LoadImageUtils loadImageUtils;
    private ProgressBar waitBar;
    private Fragment fragment;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public  interface  OnItemClickListener{
          void onClick(View view,int positon,NewRelationShipBean newRelationShipBean);
    }

    public NewRelationshipAdapter(Context context,ArrayList<NewRelationShipBean> list,Fragment fragment){
        this.fragment=fragment;
        this.context=context;
        this.datalist=list;
        loadImageUtils=new LoadImageUtils();
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
            view.setOnClickListener(this);
            acceptButton=(Button)view.findViewById(R.id.accept_relation);
            waitBar=(ProgressBar)view.findViewById(R.id.accept_progressBar);
            acceptOk=(TextView)view.findViewById(R.id.accept_ok);
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View suc= (View) v.getParent();
                    waitBar.setVisibility(View.VISIBLE);
                    acceptButton.setVisibility(View.GONE);
                   // Log.v("当前对象名称；",datalist.get((Integer) suc.getTag()).getUser_name());
                    new ChatUserDataUtil().registerPersonRelationShip(CoreApplication.newInstance().getRequestQueue(),context,  CoreApplication.newInstance().getUser_id(), datalist.get((Integer) suc.getTag()).getUser_id(),waitBar,acceptOk,acceptButton);
                }
            });
            return new NewRelationshipAdapter.HistoryViewHolder(view);
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
            if(datalist.get(position).getSelf_abstract()!=null){
                ((HistoryViewHolder) holder).short_message.setText(datalist.get(position).getSelf_abstract());
                if(datalist.get(position).getResult_type()==1){
                    ((HistoryViewHolder) holder).acceptOk.setVisibility(View.VISIBLE);
                    ((HistoryViewHolder) holder).acceptButton.setVisibility(View.GONE);
                }else{
                    ((HistoryViewHolder) holder).acceptOk.setVisibility(View.GONE);
                    ((HistoryViewHolder) holder).acceptButton.setVisibility(View.VISIBLE);
                }
            }else{
                ((HistoryViewHolder) holder).short_message.setText("");
            }
            ((HistoryViewHolder) holder).username.setText(datalist.get(position).getUser_name());
            loadImageUtils.getFirendHeaderImage(datalist.get(position).getHeader_pic(), ((HistoryViewHolder) holder).header_pic,fragment);

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
        notifyDataSetChanged();
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
        private TextView username;
        private TextView short_message;
        private ImageView header_pic;
        private Button acceptButton;
        private TextView acceptOk;
        private ProgressBar waitBar;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.person_username);
            short_message= (TextView) itemView.findViewById(R.id.short_message);
            header_pic= (ImageView) itemView.findViewById(R.id.appCompatImageView);
            acceptButton=(Button)itemView.findViewById(R.id.accept_relation);
            waitBar=(ProgressBar)itemView.findViewById(R.id.accept_progressBar);
            acceptOk=(TextView)itemView.findViewById(R.id.accept_ok);
        }
    }

}
