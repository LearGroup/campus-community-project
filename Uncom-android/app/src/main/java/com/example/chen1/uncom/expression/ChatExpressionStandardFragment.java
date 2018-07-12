package com.example.chen1.uncom.expression;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.utils.GlobalOnItemClickManagerUtils;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.utils.EmotionUtils;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class ChatExpressionStandardFragment  extends Fragment {

    private ViewPager chat_expression_viewpager;
    private Integer ExpressionPageCount;
    // TODO: Rename and change types of parameters
    private GlobalOnItemClickManagerUtils globalOnItemClickManager;
    private ArrayMap<String,Integer> Emotion_map;
    private EditText editText;
    private ArrayList<LinearLayout> viewList;

    public ChatExpressionStandardFragment(EditText editText) {
        this.editText=editText;
        // Required empty public constructor
    }


    public static ChatExpressionStandardFragment newInstance(EditText editText){
        ChatExpressionStandardFragment fragment=new ChatExpressionStandardFragment(editText);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Emotion_map= EmotionUtils.getEmojiMap(1);

        if(Emotion_map.size()/20!=0){
            ExpressionPageCount=Emotion_map.size()/20+1;
        }else{
            ExpressionPageCount=Emotion_map.size()/20;
        }
    }

    public void setEditText(EditText text){
        this.editText=text;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat_expression_standard, container, false);
        chat_expression_viewpager=(ViewPager)view.findViewById(R.id.expression_standard_viewpager);
        InitPager(CoreApplication.newInstance().getApplicationContext());
        chat_expression_viewpager.setAdapter(new ExpressionStandardPagedapter(viewList,ExpressionPageCount));
        CircleIndicator cir= (CircleIndicator) getActivity().findViewById(R.id.circleIndicator);
        cir.setViewPager(chat_expression_viewpager);


        return view;
    }
    private void InitPager(Context context) {
        GridView gridView;
        WindowManager wm = (WindowManager)CoreApplication.newInstance().getApplicationContext()
                .getSystemService(CoreApplication.newInstance().getApplicationContext().WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        EditText editText= this.editText;
        globalOnItemClickManager= new GlobalOnItemClickManagerUtils();
        globalOnItemClickManager.attachToEditText((EditText) editText);
        viewList = new ArrayList<LinearLayout>();
        for (int i = 0; i < ExpressionPageCount; i++) {
            LayoutInflater layoutInflater=LayoutInflater.from(CoreApplication.newInstance().getApplicationContext());
            LinearLayout linearLayout=(LinearLayout)layoutInflater.inflate(R.layout.chat_expression_view_standard_layout,null);
            gridView = (GridView) linearLayout.findViewById(R.id.chat_expression_standard_girdview);
            gridView.setNumColumns(7);
            int itemWidth=dip2px(context,12);
            Log.v("dp:px", String.valueOf(itemWidth));
            int spacing = (screenWidth-itemWidth*7-105)/14;
            gridView.setHorizontalSpacing(0);
            gridView.setPadding(itemWidth,spacing,itemWidth,spacing);
            gridView.setVerticalSpacing(0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        globalOnItemClickManager.removeEditText();
    }
}
