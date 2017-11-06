package com.example.chen1.uncom.relationship;

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

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.utils.Anim;
import com.example.chen1.uncom.utils.LoadImageUtils;



public class PersonDetailedInformationFragment extends Fragment {


    private static PersonDetailedInformationFragment fragment=null;
    private AppBarLayout appBarLayout;
    private TextView username;
    private RelationShipLevelBean frendData;

    private Menu menus=null;
    private AppCompatImageView person_back_icon;
    private TextView person_name;
    private ButtonBarLayout buttonBarLayout;
    private String mParam1;
    private String mParam2;
    private ImageView imageView;
    private ImageView imageView2;
    private ConstraintLayout constraintLayout;
    private OnFragmentInteractionListener mListener;
    private ImageView  person_iamgeview;
    public PersonDetailedInformationFragment() {
        // Required empty public constructor
    }

    public static PersonDetailedInformationFragment getInstance(){
        if(fragment==null){
            fragment=new PersonDetailedInformationFragment();
        }
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view=inflater.inflate(R.layout.fragment_person_detailed_information_fragment, container, false);
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
        username=(TextView)view.findViewById(R.id.person_username);
        username.setText(frendData.getUsername());
        person_iamgeview=(ImageView)view.findViewById(R.id.person_detaild_information_circleImageView);
        LoadImageUtils.getFirendHeaderImage(frendData.getHeader_pic(),getContext(),person_iamgeview);
        constraintLayout=(ConstraintLayout)view.findViewById(R.id.person_detaild_information_constraintlayout);
        person_name=(TextView)view.findViewById(R.id.person_detaild_information_name);
        person_name.setText(frendData.getUsername());
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
                    person_name.setVisibility(View.VISIBLE);
                }else{
                    person_name.setVisibility(View.GONE);
                    collapsing_toolbar_layout.setTitle("");
                }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public RelationShipLevelBean getFrendData() {
        return frendData;
    }

    public void setFrendData(RelationShipLevelBean frendData) {
        this.frendData = frendData;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(!enter){
            CoreApplication.newInstance().getRoot().setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.default_open_right));
        }
        return Anim.defaultFragmentAnim(getActivity(),transit,enter,nextAnim);
    }
}
