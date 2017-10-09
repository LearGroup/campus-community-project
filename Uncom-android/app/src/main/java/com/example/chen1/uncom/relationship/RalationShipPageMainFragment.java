package com.example.chen1.uncom.relationship;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;


public class RalationShipPageMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AppCompatImageView headImage;
    private ArrayList<RelationShipLevelBean> personFrendList;
    private ListView group_listView;
    private ListView person_listview;
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
        group_listView=(ListView) view.findViewById(R.id.group_list_view);
        person_listview=(ListView)view.findViewById(R.id.person_list_view);
        BaseAdapter group_baseAdapter=new group_relation_ship_adapter();
        person_relation_ship_adapter person_baseAdapter=new person_relation_ship_adapter(getContext(),personFrendList);
        group_listView.setAdapter(group_baseAdapter);
        person_listview.setAdapter(person_baseAdapter);
        person_listview.setOnItemClickListener(new Person_Item_OnClickListener(personFrendList));
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
}
