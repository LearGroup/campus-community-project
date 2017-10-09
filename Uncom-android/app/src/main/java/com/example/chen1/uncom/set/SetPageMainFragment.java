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
import android.widget.LinearLayout;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.chat.Person_Chat_Fragment;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;


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
                Person_Chat_Fragment person_chat_fragment =Person_Chat_Fragment.getInstance();
                person_chat_fragment.setFrendData(relationShipLevelBean);
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null).replace(R.id.drawer_layout,person_chat_fragment).commit();
            }
        });
        return view;
    }

}
