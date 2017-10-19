package com.example.chen1.uncom.set;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.chat.PersonChatFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetPageMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetPageMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int CONNECTION_ERROR =-1;
    private int RECONNECTION =0;
    private  View rootView;
    private int CONNECTION_STATUS=1;
    private LinearLayout popupWindow;
    private  SetPageMainFragmentAdapter setPageMainFragmentAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static SetPageMainFragment setPageMainFragment=null;

    public static  SetPageMainFragment getInstance(){
        if(setPageMainFragment==null){
            setPageMainFragment=new SetPageMainFragment();
        }
        return setPageMainFragment;
    }

    public int getCONNECTION_STATUS() {
        return CONNECTION_STATUS;
    }

    public void setCONNECTION_STATUS(int CONNECTION_STATUS) {
        if(popupWindow!=null){
            this.CONNECTION_STATUS = CONNECTION_STATUS;
            popupWindow=(LinearLayout) rootView.findViewById(R.id.popupwindow);
            if(CONNECTION_STATUS==CONNECTION_ERROR && rootView!=null){
                if(popupWindow.getVisibility()!=View.VISIBLE){
                    popupWindow.setVisibility(View.VISIBLE);
                }
            }else if(CONNECTION_STATUS==RECONNECTION && rootView!=null){
                if(popupWindow.getVisibility()!=View.GONE){
                    popupWindow.setVisibility(View.GONE);
                }
            }
        }
    }

    public SetPageMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetPageMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetPageMainFragment newInstance(String param1, String param2) {
        SetPageMainFragment fragment = new SetPageMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("SetPageMainFramgent:", "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("SetPageMainFramgent:", "onCreateView: ");
        View view=inflater.inflate(R.layout.fragment_set_page_main, container, false);
        rootView=view;
        popupWindow=(LinearLayout) view.findViewById(R.id.popupwindow);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.set_page_main_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        setPageMainFragmentAdapter =new SetPageMainFragmentAdapter(getContext(), CoreApplication.newInstance().getActivePersonMessageList());
        Log.v("actieCount", String.valueOf(CoreApplication.newInstance().getActivePersonMessageList().size()));
        CoreApplication.newInstance().setSetPageMainFragmentAdapter(setPageMainFragmentAdapter);
        recyclerView.setAdapter(setPageMainFragmentAdapter);
        setPageMainFragmentAdapter.setOnItemClickListener(new SetPageMainFragmentAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, RelationShipLevelBean relationShipLevelBean) {
                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                PersonChatFragment person_chat_fragment= (PersonChatFragment) CoreApplication.newInstance().getTemperFragment();
                if(person_chat_fragment!=null){
                    Log.v("第一种","...............ok");
                    person_chat_fragment.setFrendData(relationShipLevelBean);
                    fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,person_chat_fragment,"chatPage").setCustomAnimations(R.anim.default_fragment_switch_leave_translate, R.anim.default_leave_left, R.anim.default_open_right, R.anim.default_fragment_switch_translate_open).commit();

                }else{
                    Log.v("第二种","...............ok");
                    person_chat_fragment = PersonChatFragment.getInstance();
                    CoreApplication.newInstance().setTemperFragment(person_chat_fragment);
                    fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,person_chat_fragment,"chatPage").setCustomAnimations(R.anim.default_fragment_switch_leave_translate, R.anim.default_leave_left, R.anim.default_open_right, R.anim.default_fragment_switch_translate_open).commit();

                }
                person_chat_fragment.setFrendData(relationShipLevelBean);
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(view.getContext(),R.anim.default_leave_left));

             }
        });
        return view;
    }

}
