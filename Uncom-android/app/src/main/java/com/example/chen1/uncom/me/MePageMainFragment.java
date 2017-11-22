package com.example.chen1.uncom.me;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.find.FindPageRecyclerAdapter;


public class MePageMainFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static MePageMainFragment mePageMainFragment=null;


    public static MePageMainFragment getInstance(){
        if(mePageMainFragment==null){
            mePageMainFragment=new MePageMainFragment();
        }
        return mePageMainFragment;
    }

    public MePageMainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_me_page_main, container, false);
       viewPager= (ViewPager) view.findViewById(R.id.mypage_viewpager);
       tabLayout=(TabLayout)view.findViewById(R.id.mypage_tablayout);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new TabPagerAdapter(getFragmentManager()));
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("个人主页");
        tabLayout.getTabAt(1).setText("详细资料");
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
}
