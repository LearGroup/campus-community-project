package com.example.chen1.uncom.previewAlbum;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;


public class AlbumEdit extends Fragment implements FragmentBackHandler{

    private int defaultPosition;
    private ArrayList<String> data;
    private RecyclerView bootomRecycler;
    private ThumbnailImageViewAdapter adapter;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private ImagePagerAdapter pagerAdapter;
    private TextView  count;
    private AppCompatButton completeBtn;
    private boolean edit=true;


    public AlbumEdit() {
        // Required empty public constructor
    }
    public static AlbumEdit newInstance(ArrayList<String> param1,int param2) {
        AlbumEdit fragment = new AlbumEdit();
        Bundle args = new Bundle();
        Log.v("defaultPosition", String.valueOf(param2));
        Log.v("album_list",param1.toString());
        args.putInt("defaultPosition",param2);
        args.putStringArrayList("album_list", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            defaultPosition=getArguments().getInt("defaultPosition");
            data = getArguments().getStringArrayList("album_list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_album_edit, container, false);
        bootomRecycler= (RecyclerView) view.findViewById(R.id.recycler);
        appBarLayout= (AppBarLayout) view.findViewById(R.id.appbar_layout);
        count= (TextView) view.findViewById(R.id.count);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        completeBtn= (AppCompatButton) view.findViewById(R.id.complete);
        bootomRecycler.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        bootomRecycler.setHasFixedSize(true);
        pagerAdapter=new ImagePagerAdapter(data,this);
        pagerAdapter.setAppBarLayout(appBarLayout).setBottomLayout(bootomRecycler);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventMessage(data));
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                CountDownTimer timer=new CountDownTimer(250,250) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        adapter.setSelectPosition(position);
                    }
                };
                timer.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter=new ThumbnailImageViewAdapter(data,this,defaultPosition);
        adapter.setEdit(edit);
        adapter.setItemOnClickListener(new ThumbnailImageViewAdapter.ItemOnClickListener() {
            @Override
            public void onClick(View v, int position, String url, int type) {
                if(type==1){
                    if(edit==true){
                        int status=adapter.deleteImage(position);
                        count.setText(adapter.getSelectPosition()+1+"/"+data.size());

                        if(status==0){
                            EventBus.getDefault().post(new EventMessage(data));
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            fragmentManager.popBackStack();
                            pagerAdapter.removeItem(position);
                            pagerAdapter.notifyDataSetChanged();
                        }else{
                            pagerAdapter.removeItem(position);
                            pagerAdapter.notifyDataSetChanged();
                        }
                    }
                }else{
                    viewPager.setCurrentItem(position);
                }
            }


        });
        bootomRecycler.setAdapter(adapter);
        viewPager.setAdapter(pagerAdapter);
        adapter.setSelectPosition(defaultPosition);
        viewPager.setCurrentItem(defaultPosition);
        count.setText(defaultPosition+1+"/"+data.size());
        return view;
    }


    @Override
    public boolean onBackPressed() {
        EventBus.getDefault().post(new EventMessage(data));
        getFragmentManager().popBackStack();
        return true;
    }

    public boolean isEdit() {
        return edit;
    }

    public AlbumEdit setEdit(boolean edit) {
        this.edit = edit;
        return  this;
    }
}
