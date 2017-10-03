package com.example.chen1.uncom.relationship;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chen1.uncom.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link person_detailed_information_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link person_detailed_information_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class person_detailed_information_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static  person_detailed_information_fragment fragment=null;
    // TODO: Rename and change types of parameters
    private CollapsingToolbarLayoutState state;
    private AppBarLayout appBarLayout;
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }
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
    public person_detailed_information_fragment() {
        // Required empty public constructor
    }

    public static  person_detailed_information_fragment getInstance(){
        if(fragment==null){
            fragment=new person_detailed_information_fragment();
        }
        return fragment;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment person_detailed_information_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static person_detailed_information_fragment newInstance(String param1, String param2) {
        person_detailed_information_fragment fragment = new person_detailed_information_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void setFullScreen(){
        Window window = getActivity().getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        ViewGroup rootView=(ViewGroup) ((ViewGroup) getActivity().findViewById(R.id.drawer_layout)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);


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
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.popBackStack();

            }
        });
        person_iamgeview=(ImageView)view.findViewById(R.id.person_detaild_information_circleImageView);
        constraintLayout=(ConstraintLayout)view.findViewById(R.id.person_detaild_information_constraintlayout);
        person_name=(TextView)view.findViewById(R.id.person_detaild_information_name);
        person_back_icon=(AppCompatImageView)view.findViewById(R.id.person_detaild_information_back_icon);
        imageView=(ImageView)view.findViewById(R.id.person_detailed_information_background_img);
        imageView2=(ImageView)view.findViewById(R.id.person_detailed_information_background_img_two);
        buttonBarLayout=(ButtonBarLayout)view.findViewById(R.id.btn_Play);
        appBarLayout=(AppBarLayout) view.findViewById(R.id.peron_dtailed_information_appbar_layout);
        state = CollapsingToolbarLayoutState.EXPANDED;
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(verticalOffset==0)
                {
                    //全展开
                    person_name.setVisibility(View.GONE);
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        toolbar.setTitle("心灵鸡汤");//设置title为EXPANDED
                    }
                } else if(Math.abs(verticalOffset)>= appBarLayout.getTotalScrollRange())
                {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        toolbar.getMenu().findItem(R.id.person_detaild_information_menu_set).setIcon(R.drawable.ic_vector_person_detaild_information_option_icon);
                        person_back_icon.setImageResource(R.drawable.ic_vector_back_black_icon);
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                        if(imageView.getVisibility()==View.VISIBLE) {
                            imageView2.setVisibility(View.VISIBLE);
                            imageView.setVisibility(View.GONE);
                            constraintLayout.setVisibility(View.GONE);
                            person_name.setVisibility(View.VISIBLE);

                        }
                    }

                }else{
                    imageView.setVisibility(View.VISIBLE);
                        if(imageView.getVisibility()==View.VISIBLE&&state==CollapsingToolbarLayoutState.INTERNEDIATE){
                            imageView2.setVisibility(View.GONE);
                            constraintLayout.setVisibility(View.VISIBLE);
                            person_name.setVisibility(View.GONE);
                    }

                    //constraintLayout.setVisibility(View.VISIBLE);
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {

                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                        }
                        toolbar.getMenu().findItem(R.id.person_detaild_information_menu_set).setIcon(R.drawable.ic_vector_person_detail_information_option_white_icon);
                        person_back_icon.setImageResource(R.drawable.ic_vector_back_icon);
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
        return view;
    }


    /*@Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (imageView.getVisibility()==View.VISIBLE) {
            menu.findItem(R.id.person_detaild_information_menu_set).setIcon(R.drawable.ic_vector_person_detaild_information_option_icon);
        }
        if(imageView.getVisibility()==View.VISIBLE&&state==CollapsingToolbarLayoutState.INTERNEDIATE) {
            menu.findItem(R.id.person_detaild_information_menu_set).setIcon(R.drawable.ic_vector_person_detail_information_option_white_icon);
        }
    }*/
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
}
