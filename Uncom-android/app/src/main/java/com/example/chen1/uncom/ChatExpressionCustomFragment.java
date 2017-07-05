package com.example.chen1.uncom;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ChatExpressionCustomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int  ExpressionPosition;
    private static  ChatExpressionCustomFragment chatExpressionCustomFragment=null;
    public ChatExpressionCustomFragment() {
        // Required empty public constructor
    }

   public  ChatExpressionCustomFragment(int position){
       ExpressionPosition=position;
   }


    public static ChatExpressionCustomFragment getInstance(int position){
        if(chatExpressionCustomFragment==null){
            chatExpressionCustomFragment=new ChatExpressionCustomFragment(position);
        }
        return chatExpressionCustomFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_expression_custom, container, false);
    }

}
