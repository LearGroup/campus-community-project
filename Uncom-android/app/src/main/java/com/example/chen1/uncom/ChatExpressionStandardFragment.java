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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class ChatExpressionStandardFragment  extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private ViewPager chat_expression_viewpager;
    private static final String ARG_PARAM2 = "param2";
    private static ChatExpressionStandardFragment chatExpressionStandardFragment=null;
    private Integer ExpressionPageCount;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayMap<String,Integer> Emotion_map;

    private ArrayList<LinearLayout> viewList;

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
        InitPager(getContext());
        View view=inflater.inflate(R.layout.fragment_chat_expression_standard, container, false);
        chat_expression_viewpager=(ViewPager)view.findViewById(R.id.expression_standard_viewpager);

        chat_expression_viewpager.setAdapter(new ExpressionStandardPagedapter(viewList,ExpressionPageCount));
        CircleIndicator cir= (CircleIndicator) getActivity().findViewById(R.id.circleIndicator);
        cir.setViewPager(chat_expression_viewpager);


        return view;
    }
    private void InitPager(Context context) {
        GridView gridView;
        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        EditText editText= (EditText) getActivity().findViewById(R.id.person_chat_editText);
        GlobalOnItemClickManagerUtils globalOnItemClickManager= GlobalOnItemClickManagerUtils.getInstance(getActivity());
        globalOnItemClickManager.attachToEditText((EditText) editText);
        viewList = new ArrayList<LinearLayout>();
        for (int i = 0; i < ExpressionPageCount; i++) {
            LayoutInflater layoutInflater=LayoutInflater.from(getContext());
            LinearLayout linearLayout=(LinearLayout)layoutInflater.inflate(R.layout.chat_expression_view_standard_layout,null);
            gridView = (GridView) linearLayout.findViewById(R.id.chat_expression_standard_girdview);
            gridView.setNumColumns(7);
            int itemWidth=dip2px(context,12);
            Log.v("dp:px", String.valueOf(itemWidth));
            int spacing = (screenWidth-itemWidth*7)/14;
            gridView.setHorizontalSpacing(itemWidth);
            gridView.setPadding(itemWidth,spacing,itemWidth,spacing);
            gridView.setVerticalSpacing(spacing);
            gridView.setAdapter(new ExpessionStandardAdapter(Emotion_map, ExpressionPageCount, i,context,screenWidth));
            Log.v("emotion", "onItemClick: ");
            gridView.setOnItemClickListener(globalOnItemClickManager.getOnItemClickListener(1));
            viewList.add(linearLayout);
        }
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



}
