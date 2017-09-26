package com.example.chen1.uncom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by chen1 on 2017/9/21.
 */

public class PersonChatRecyclerViewAdapter extends RecyclerView.Adapter<PersonChatRecyclerViewAdapter.ViewHolder> implements  View.OnClickListener{
    private boolean ITEM_TYPE;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ChatMessgaeContent> listItem =new ArrayList<ChatMessgaeContent>();
   public PersonChatRecyclerViewAdapter(Context context){
       this.context=context;
       ChatMessgaeContent item= new ChatMessgaeContent("Hello World!",new Date(),R.drawable.head_img,false);

       ChatMessgaeContent item2= new ChatMessgaeContent("Hello World!",new Date(),R.drawable.head_img,true);

   }

    @Override
    public int getItemViewType(int position) {
        if(listItem.get(position).isMessageType()){
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
        if(!listItem.get(position).getText().equals(null)){
            String str =listItem.get(position).getText();
            SpannableString spannableString= SpanStringUtils.getEmotionContent(1,context, holder.textView,str);
             holder.textView.setText(spannableString);
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
        public TextView textView=null;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.person_chat_own_content_textview);
        }
    }


    public   void add(ChatMessgaeContent cmc,int position){
        listItem.add(cmc);
        notifyItemInserted(listItem.size());
    }

}
