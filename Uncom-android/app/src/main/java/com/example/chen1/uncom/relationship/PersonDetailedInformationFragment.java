package com.example.chen1.uncom.relationship;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.utils.Anim;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.GlideCircleTransform;
import com.example.chen1.uncom.utils.LoadImageUtils;



public class PersonDetailedInformationFragment extends Fragment implements FragmentBackHandler{


    private AppBarLayout appBarLayout;
    private TextView username;
    private RelationShipLevelBean frendData;
    private Menu menus=null;
    private AppCompatImageView person_back_icon;
    private TextView person_name;
    private ButtonBarLayout buttonBarLayout;
    private Toolbar toolbar;
    private ImageView headImage;
    private AppCompatImageView editBtn;
    private ImageView backImage;
    private AppCompatImageView backBtn;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LoadImageUtils loadImageUtils;
    public PersonDetailedInformationFragment() {
    }


    public static PersonDetailedInformationFragment newInstance(RelationShipLevelBean param1){
        PersonDetailedInformationFragment fragment=new PersonDetailedInformationFragment();
        Bundle args = new Bundle();
        args.putParcelable("bean",param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImageUtils=new LoadImageUtils();
        frendData=getArguments().getParcelable("bean");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_person_detailed_information_fragment, container, false);
        setHasOptionsMenu(true);
        //toolbar.inflateMenu(R.menu.person_detail_menu_layout);
        toolbar = (Toolbar)view. findViewById(R.id.toolbar);
        toolbar.setTitleMarginTop(85);
        toolbar.setTitleMarginStart(25);
        backBtn= (AppCompatImageView) view.findViewById(R.id.back_btn);
        appBarLayout=(AppBarLayout) view.findViewById(R.id.appbar_layout);
        editBtn= (AppCompatImageView) view.findViewById(R.id.edit_btn);
        collapsingToolbarLayout= (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        headImage= (ImageView) view.findViewById(R.id.head_img);
        GlideApp.with(this)
                .load(frendData.getHeader_pic()).transition(new DrawableTransitionOptions().crossFade())
                .into(headImage);
        AppCompatImageView appCompatImageView=(AppCompatImageView) view.findViewById(R.id.back_btn);
        collapsingToolbarLayout.setTitle(frendData.getUsername());
        appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                getFragmentManager().popBackStack();
            }
        });
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

    @Override
    public void onStart() {
        super.onStart();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                    if(isAdded()){
                        backBtn.setImageResource(R.drawable.ic_vector_back_icon);
                        editBtn.setImageResource(R.drawable.ic_vector_person_detaild_information_option_icon);
                    }
                }else{
                    if(isAdded()){
                        backBtn.setImageResource(R.drawable.ic_vector_back_transparent);
                        editBtn.setImageResource(R.drawable.ic_vector_person_detail_information_option_white_icon);
                    }
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        frendData=null;
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(!enter){
            CoreApplication.newInstance().getRoot().setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.default_open_right));
        }
        return new Anim().defaultFragmentAnim(getActivity(),transit,enter,nextAnim);
    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();
        return  true;
    }
}
