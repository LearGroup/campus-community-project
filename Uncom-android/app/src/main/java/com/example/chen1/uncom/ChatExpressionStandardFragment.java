package com.example.chen1.uncom;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class ChatExpressionStandardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private ViewPager chat_expression_viewpager;
    private static final String ARG_PARAM2 = "param2";
    private static ChatExpressionStandardFragment chatExpressionStandardFragment=null;
    private int ExpressionPageCount;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayMap<String,Integer> Emotion_map;



    public ChatExpressionStandardFragment() {
        // Required empty public constructor
    }

    public static ChatExpressionStandardFragment getInstance(){
        if(chatExpressionStandardFragment==null){
            chatExpressionStandardFragment=new ChatExpressionStandardFragment();
        }
        return chatExpressionStandardFragment;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatExpressionStandardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatExpressionStandardFragment newInstance(String param1, String param2) {
        ChatExpressionStandardFragment fragment = new ChatExpressionStandardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Emotion_map=EmotionUtils.getEmojiMap(1);
        if(Emotion_map.size()/20!=0){
            ExpressionPageCount=Emotion_map.size()/20+1;
        }else{
            ExpressionPageCount=Emotion_map.size()/20;
        }

        Log.v("Emotion_count", String.valueOf(Emotion_map.size()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat_expression_standard, container, false);
        chat_expression_viewpager=(ViewPager)view.findViewById(R.id.expression_standard_viewpager);
        /*for (int i = 0; i <ExpressionPageCount ; i++) {
            GridView gridView=(GridView)View.inflate(this.getContext(),R.layout.chat_expression_view_standard_layout,null);
            gridView.setAdapter(new ExpessionStandardAdapter(Emotion_map,ExpressionPageCount,i,20));
        }*/
        return view;
    }



}
