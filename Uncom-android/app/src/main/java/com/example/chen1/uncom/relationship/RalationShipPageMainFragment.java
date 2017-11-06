package com.example.chen1.uncom.relationship;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;

import java.util.ArrayList;


public class RalationShipPageMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private TextView newRelationShipActive;
    private String mParam2;
    private AppCompatImageView headImage;
    private ArrayList<RelationShipLevelBean> personFrendList;
    private ListView group_listView;
    private ListView person_listview;
    private ConstraintLayout new_relation_ship_button;
    private OnFragmentInteractionListener mListener;
    private static RalationShipPageMainFragment ralationShipPageMainFragment=null;



    public static RalationShipPageMainFragment getInstance(){
        if(ralationShipPageMainFragment==null){
            ralationShipPageMainFragment=new RalationShipPageMainFragment();
        }
        return ralationShipPageMainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("RalationShipPageMainFragmentOncreate",".............ok");
        personFrendList= CoreApplication.newInstance().getPersonFrendList();
        Log.v("freendlist", String.valueOf(personFrendList));
    }

    public RalationShipPageMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ralation_ship_page_main, container, false);
        ScrollView sv = (ScrollView) view.findViewById(R.id.relation_ship_scroll_view);
        sv.smoothScrollTo(0,0);
        if(personFrendList==null){
            personFrendList= CoreApplication.newInstance().getPersonFrendList();
        }
        newRelationShipActive= (TextView) view.findViewById(R.id.unlook_newrelationship_sum);
        if(CoreApplication.newInstance().getNewRelationActive()!=0){
            if(newRelationShipActive.getVisibility()==View.GONE){
                newRelationShipActive.setVisibility(View.VISIBLE);
            }
            newRelationShipActive.setText(CoreApplication.newInstance().getNewRelationActive().toString());
        }
        new_relation_ship_button=(ConstraintLayout)view.findViewById(R.id.new_relationship_button);
        group_listView=(ListView) view.findViewById(R.id.group_list_view);
        person_listview=(ListView)view.findViewById(R.id.person_list_view);
        BaseAdapter group_baseAdapter=new GroupRelationShipAdapter();
        PersonRelationShipAdapter person_baseAdapter=new PersonRelationShipAdapter(getContext(),personFrendList);
        group_listView.setAdapter(group_baseAdapter);
        CoreApplication.newInstance().setPersonRelationShipAdapter(person_baseAdapter);
        CoreApplication.newInstance().setRalationShipPageMainFragment(this);
        person_listview.setAdapter(person_baseAdapter);
        person_listview.setOnItemClickListener(new PersonItemOnClickListener(this,personFrendList));
        new_relation_ship_button.setOnClickListener(new ToNewRelationShipButtonOnclickListener(this));
        return view;
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


    public void updateNewRelaionShipActive(){
        Log.v("更新fragment","ok" );
        if(CoreApplication.newInstance().getNewRelationActive()!=0){
            if(newRelationShipActive.getVisibility()==View.GONE){
                newRelationShipActive.setVisibility(View.VISIBLE);
            }
            newRelationShipActive.setText(CoreApplication.newInstance().getNewRelationActive().toString());
        }else if(CoreApplication.newInstance().getNewRelationActive()==0){
            if(newRelationShipActive.getVisibility()==View.VISIBLE){
                newRelationShipActive.setVisibility(View.GONE);
            }
        }
    }

}
