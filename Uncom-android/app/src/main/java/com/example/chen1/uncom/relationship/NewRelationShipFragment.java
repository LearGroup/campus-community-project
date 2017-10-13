package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NewRelationShipFragment extends Fragment {

    private static NewRelationShipFragment newRelationShipFragment=null;
    private AppCompatImageView back_icon;
    public NewRelationShipFragment() {
        // Required empty public constructor
    }


    /**
     * 获取该Fragment唯一实例
     * @return
     */
    public static NewRelationShipFragment getInstance(){
        if(newRelationShipFragment==null){
            newRelationShipFragment= new NewRelationShipFragment();
        }
        return newRelationShipFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_relation_ship, container, false);
        Toolbar toolbar=(Toolbar) view.findViewById(R.id.new_relationship_toolbar);
        setHasOptionsMenu(true);
        back_icon= (AppCompatImageView) view.findViewById(R.id.new_relationship_back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.popBackStack();
            }
        });
        return view;
    }






}
