package com.example.chen1.uncom.me;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.thinker.PhotoAdapter;
import com.example.chen1.uncom.utils.DisplayUtils;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.SpanStringUtils;
import com.example.chen1.uncom.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chen1 on 2018/2/23.
 */

public class DynamicAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<PersonDynamicsBean> dynamicsBeans;
    private Fragment fragment;
    private int max_height_one;
    private int max_height_two;
    private TimeUtils timeUtils;
    private SpanStringUtils spanStringUtils;

    public DynamicAlbumAdapter(ArrayList<PersonDynamicsBean> beans , Fragment fragment,int max_height_one,int max_height_two){
        this.dynamicsBeans=beans;
        this.fragment=fragment;
        this.max_height_one=max_height_one;
        this.max_height_two=max_height_two;
        this.timeUtils=new TimeUtils();
        this.spanStringUtils=new SpanStringUtils();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.dynamic_album_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PersonDynamicsBean bean=dynamicsBeans.get(position);
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.time.setVisibility(View.GONE);
        if(bean.getPhoto_list()!=null && bean.getPhoto_list().length()>15 ||bean.getPhoto_online_list()!=null && bean.getPhoto_online_list().length()>15) {
            ArrayList<String> path = new ArrayList<>();
            Log.v("getPhoto_list", bean.getPhoto_list());
            String local_path[] = bean.getPhoto_list().split(",");
            String online_path[] = new String[20];
            if (bean.getPhoto_online_list() != null) {
                online_path = bean.getPhoto_online_list().split(",");
            }
            ;
            for (int i = 0; i < bean.getPhoto_list().split(",").length; i++) {
                File file = new File(local_path[i]);
                if (file.exists() && file.isFile()) {
                    path.add(local_path[i]);
                } else {
                    if (online_path != null && online_path[i] != null) {
                        path.add(online_path[i]);
                    }
                }

            }
            viewHolder.photoContainer.setVisibility(View.VISIBLE);
            PhotoAdapter photoAdapter=new PhotoAdapter(CoreApplication.newInstance().getApplicationContext(),parseToDArrayList(path),fragment);
            viewHolder.photoContainer.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
            viewHolder.photoContainer.setHasFixedSize(true);
            photoAdapter.setMax_height_one((int) (DisplayUtils.getWindowWidth(CoreApplication.newInstance().getApplicationContext())*0.65)).setMax_height_two((int) (DisplayUtils.getWindowWidth(CoreApplication.newInstance().getApplicationContext())*0.35));
            viewHolder.photoContainer.setAdapter(photoAdapter);
        }else{
            viewHolder.photoContainer.setVisibility(View.GONE);
        }
        if(bean.getContent()!=null && bean.getContent().equals("")==false){
            bean.setContent(bean.getContent()+" ");
            SpannableString spannableString= spanStringUtils.getEmotionContent(1,CoreApplication.newInstance().getApplicationContext(), viewHolder.content,bean.getContent());
            viewHolder.content.setText(spannableString);
            viewHolder.contentLayout.setVisibility(View.VISIBLE);
            viewHolder.content.setVisibility(View.VISIBLE);
            viewHolder.username.setVisibility(View.VISIBLE);
            viewHolder.username.setText(bean.getUsername()+":");
        }else{
            viewHolder.contentLayout.setVisibility(View.GONE);
            viewHolder.username.setVisibility(View.GONE);
            viewHolder.content.setVisibility(View.GONE);
        }
        if(position==0){
            viewHolder.time.setVisibility(View.VISIBLE);
            viewHolder.time.setText(timeUtils.getDayAndMonth(dynamicsBeans.get(position).getCreate_time()));
        }
        if(position>0){
            if(timeUtils.checkOutDay(dynamicsBeans.get(position).getCreate_time(),dynamicsBeans.get(position-1).getCreate_time())){
                viewHolder.time.setVisibility(View.VISIBLE);
                viewHolder.time.setText(timeUtils.getDayAndMonth(dynamicsBeans.get(position).getCreate_time()));
            }
        }


    }

    @Override
    public int getItemCount() {
       if(dynamicsBeans!=null){
           return  dynamicsBeans.size();
       }
       return 0;
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public RecyclerView photoContainer;
        public TextView content;
        public TextView time;
        public TextView username;
        public LinearLayout contentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            photoContainer= (RecyclerView) itemView.findViewById(R.id.photo_container);
            content= (TextView) itemView.findViewById(R.id.text);
            username= (TextView) itemView.findViewById(R.id.username_1);
            time= (TextView) itemView.findViewById(R.id.time);
            contentLayout= (LinearLayout) itemView.findViewById(R.id.content_layout);
        }
    }


    private ArrayList<ArrayList<String>> parseToDArrayList(ArrayList<String> array){
        ArrayList<ArrayList<String>> arrayLists=new ArrayList<>();
        for (int i=0;i<array.size();i++){
            if(i%2==0){
                ArrayList<String> item=new ArrayList<String>();
                item.add(array.get(i));
                arrayLists.add(item);
            }else{
                arrayLists.get(i/2).add(array.get(i));
            }
        }
        return  arrayLists;
    }

    public void setDynamicsBeans(ArrayList<PersonDynamicsBean> beans){
        dynamicsBeans=beans;
        Log.v("dynamicsBeans", String.valueOf(beans.size()));
        notifyDataSetChanged();
    }

    public void addDynamic(PersonDynamicsBean bean){
        dynamicsBeans.add(0,bean);
        notifyItemInserted(0);
    }
}
