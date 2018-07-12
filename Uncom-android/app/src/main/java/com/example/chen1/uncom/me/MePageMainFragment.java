package com.example.chen1.uncom.me;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.find.FindPageRecyclerAdapter;
import com.example.chen1.uncom.thinker.WriteThinkFragment;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.LoadImageUtils;

import de.hdodenhof.circleimageview.CircleImageView;


public class MePageMainFragment extends Fragment implements FragmentBackHandler{

    private FragmentTransaction fragmentTransaction;
    private ConstraintLayout detail_btn;
    private static MePageMainFragment mePageMainFragment=null;
    private LoadImageUtils loadImageUtils;
    private ImageView head_img;
    private TextView username;
    private AppCompatImageView cameraBtn;
    private View view;
    private LinearLayout dynamicAlbum;
    public static MePageMainFragment newInstance(){
        return new MePageMainFragment();
    }

    public static MePageMainFragment getInstance(){
        if(mePageMainFragment==null){
            mePageMainFragment=new MePageMainFragment();
        }
        return mePageMainFragment;
    }

    public MePageMainFragment() {
        loadImageUtils=new LoadImageUtils();
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
        if(view==null){
            view = inflater.inflate(R.layout.fragment_me_page_main, container, false);
        }
        dynamicAlbum= (LinearLayout) view.findViewById(R.id.photo_list);
        detail_btn= (ConstraintLayout) view.findViewById(R.id.to_me_detail_page);
        head_img= (ImageView) view.findViewById(R.id.head_img);
        username= (TextView) view.findViewById(R.id.username);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username.setText(CoreApplication.newInstance().getUserBean().getUsername());
        loadImageUtils.getFirendHeaderImage(CoreApplication.newInstance().getUserBean().getHeader_pic(),head_img,this);
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                MeDetailPage meDetailPage= (MeDetailPage) fragmentManager.findFragmentByTag("meDetailPage");
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate,
                        R.anim.default_fragment_switch_leave_translate
                );
                if(meDetailPage!=null){
                    meDetailPage.setTargetFragment(MePageMainFragment.this,2000);
                    fragmentTransaction.show(meDetailPage).commitAllowingStateLoss();
                }else{
                    meDetailPage=MeDetailPage.newInstance();
                    meDetailPage.setTargetFragment(MePageMainFragment.this,2000);
                    fragmentTransaction.add(R.id.drawer_layout, meDetailPage, "meDetailPage").commitAllowingStateLoss();
                }
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left));
            }
        });
        dynamicAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentTransaction=fragmentManager.beginTransaction();
                DynamicAlbum dynamicAlbum= (DynamicAlbum) fragmentManager.findFragmentByTag("dynmaicAlbum");
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_translate_open,
                        R.anim.default_fragment_switch_leave_translate,
                        R.anim.default_fragment_switch_leave_translate
                );
                if(dynamicAlbum!=null){
                    dynamicAlbum.setTargetFragment(MePageMainFragment.this,2000);
                    fragmentTransaction.show(dynamicAlbum).commitAllowingStateLoss();
                }else{
                    dynamicAlbum=DynamicAlbum.newInstance();
                    dynamicAlbum.setTargetFragment(MePageMainFragment.this,2000);
                    fragmentTransaction.add(R.id.drawer_layout, dynamicAlbum, "dynamicAlbum").commitAllowingStateLoss();
                }
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left));

            }
        });

    }

    public void updateInfo(){
        username.setText(CoreApplication.newInstance().getUserBean().getUsername());
        loadImageUtils.getFirendHeaderImage(CoreApplication.newInstance().getUserBean().getHeader_pic(),head_img,this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.v("mePage", String.valueOf(hidden));
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackAllImmediate(this);
    }
}
