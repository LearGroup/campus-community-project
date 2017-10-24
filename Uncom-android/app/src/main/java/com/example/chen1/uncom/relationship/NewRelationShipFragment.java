package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.set.OnItemClickListener;
import com.example.chen1.uncom.utils.Anim;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NewRelationShipFragment extends Fragment  implements View.OnTouchListener{

    private static NewRelationShipFragment newRelationShipFragment=null;
    private AppCompatImageView back_icon;
    private SearchView searchView;
    private RecyclerView search_result_recycler_view;
    private NewRelationShipBean search_layout_view;
    private NewRelationshipAdapter newRelationshipAdapter;
    private boolean search_display_type;
    private ArrayList<NewRelationShipBean> newRelationShipList;
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
        search_display_type=false;
        CoreApplication.newInstance().setNewRelationActive(0);
        View view=inflater.inflate(R.layout.fragment_new_relation_ship, container, false);
        Toolbar toolbar=(Toolbar) view.findViewById(R.id.new_relationship_toolbar);
        setHasOptionsMenu(true);
        search_layout_view=new NewRelationShipBean();
        search_layout_view.setView_type(0);
        search_result_recycler_view= (RecyclerView) view.findViewById(R.id.search_page_recyclerview);
        ArrayList<NewRelationShipBean> data=new ArrayList<>();
        newRelationshipAdapter=new NewRelationshipAdapter(getContext(),data) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        search_result_recycler_view.setLayoutManager(linearLayoutManager);
        search_result_recycler_view.setHasFixedSize(true);
        search_result_recycler_view.setItemAnimator(new DefaultItemAnimator());
        newRelationshipAdapter.setOnItemClickListener(new GetNewRelationShipResultsButtonOnClickentener(getContext(),this));
        if(newRelationShipList==null){
            newRelationShipList=CoreApplication.newInstance().getNewRelationShipList();
        }
        if(newRelationShipList!=null &&newRelationShipList.size()>0){
            for (NewRelationShipBean item :newRelationShipList){
                newRelationshipAdapter.add(item);
            }
        }
        search_result_recycler_view.setAdapter(newRelationshipAdapter);
        searchView= (SearchView) view.findViewById(R.id.search_column);
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                newRelationshipAdapter.notifyItemRemoved(0);
                search_display_type=false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.v("NewRelationFragment","onQueryTextChange");
                search_layout_view.setResults(newText);
                if(search_display_type ==false){
                    newRelationshipAdapter.add(search_layout_view,1);
                    search_display_type=true;
                }else{
                    newRelationshipAdapter.add(search_layout_view);
                }
                return false;
            }
        });
        back_icon= (AppCompatImageView) view.findViewById(R.id.new_relationship_back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_translate_open,R.anim.default_leave_left);
                fragmentManager.popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(CoreApplication.newInstance().isDisPlayType()==false){
            CoreApplication.newInstance().setDisPlayType(true);
            CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.default_open_right));
        }
    }


    /*    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(CoreApplication.newInstance().isDisPlayType()==true){
            CoreApplication.newInstance().setDisPlayType(false);
            if(!enter){
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.default_open_right));
            }
            Log.v("第一种显示方式","ok");
            return Anim.defaultFragmentAnim(getActivity(),transit,enter,nextAnim);
        }
        return null;
}*/




    public SearchView getSearchView() {
        return searchView;
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
