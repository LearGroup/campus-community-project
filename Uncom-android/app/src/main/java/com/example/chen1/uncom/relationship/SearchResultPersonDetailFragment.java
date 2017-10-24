package com.example.chen1.uncom.relationship;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.utils.LoadImageUtils;


public class SearchResultPersonDetailFragment extends Fragment  implements View.OnTouchListener{

    private NewRelationShipBean frendData;
    private AppBarLayout appBarLayout;
    private TextView username;
    private Menu menus=null;
    private AppCompatImageView person_back_icon;
    private TextView person_name;
    private TextView customText;
    private ButtonBarLayout buttonBarLayout;
    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private Button buildRelationship;
    private ImageView imageView2;
    private ConstraintLayout constraintLayout;
    private ImageView  person_iamgeview;
    private static SearchResultPersonDetailFragment searchResultPersonDetailFragment =null;



    public static SearchResultPersonDetailFragment getInstance(){
        if(searchResultPersonDetailFragment ==null){
            searchResultPersonDetailFragment =new SearchResultPersonDetailFragment();
        }
        return searchResultPersonDetailFragment;
    }

    public SearchResultPersonDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view=inflater.inflate(R.layout.fragment_search_result_person_detail, container, false);
        final Toolbar toolbar=(Toolbar)view.findViewById(R.id.person_detailed_information_toolbar);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.person_detail_menu_layout);
        AppCompatImageView appCompatImageView=(AppCompatImageView) view.findViewById(R.id.person_detaild_information_back_icon);
        appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.popBackStack();
            }
        });
        customText=(TextView)view.findViewById(R.id.custom_short_text);
        username=(TextView)view.findViewById(R.id.person_username);
        username.setText(frendData.getUser_name());
        if(frendData.getShort_message()!=null){
            customText.setText(frendData.getShort_message());
        }
        buildRelationship=(Button)view.findViewById(R.id.build_relationship);
        person_iamgeview=(ImageView)view.findViewById(R.id.person_detaild_information_circleImageView);
        constraintLayout=(ConstraintLayout)view.findViewById(R.id.person_detaild_information_constraintlayout);
        LoadImageUtils.getFirendHeaderImage(frendData.getHeader_pic(),getContext(),person_iamgeview);
        person_name=(TextView)view.findViewById(R.id.person_detaild_information_name);
        person_back_icon=(AppCompatImageView)view.findViewById(R.id.person_detaild_information_back_icon);
        imageView=(ImageView)view.findViewById(R.id.person_detailed_information_background_img);
        imageView2=(ImageView)view.findViewById(R.id.person_detailed_information_background_img_two);
        buttonBarLayout=(ButtonBarLayout)view.findViewById(R.id.btn_Play);
        appBarLayout=(AppBarLayout) view.findViewById(R.id.peron_dtailed_information_appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                final CollapsingToolbarLayout collapsing_toolbar_layout = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar_layout);
                collapsing_toolbar_layout.setTitle("");
                collapsing_toolbar_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
                collapsing_toolbar_layout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
                collapsing_toolbar_layout.setExpandedTitleColor(Color.TRANSPARENT);
                Log.v("Tag", "appBarLayoutHeight:" + appBarLayout.getHeight() + " getTotalScrollRange:" + appBarLayout.getTotalScrollRange() + " offSet:" + verticalOffset);
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
                    collapsing_toolbar_layout.setTitle("keep");
                    person_name.setVisibility(View.VISIBLE);
                }else{
                    person_name.setVisibility(View.GONE);
                    collapsing_toolbar_layout.setTitle("");
                }
            }
        });
        buildRelationship.setOnClickListener(new ToRequestBuildRelationShipButtonOnClickListener(getContext(),this,frendData));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.person_detail_menu_layout,menu);
        if(menus==null){
            menus=menu;
        }
    }

    public NewRelationShipBean getFrendData() {
        return frendData;
    }

    public void setFrendData(NewRelationShipBean frendData) {
        this.frendData = frendData;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
