package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.NewRelationShipBean;
import com.example.chen1.uncom.chat.ChatUserDataUtil;
import com.example.chen1.uncom.set.OnItemClickListener;
import com.example.chen1.uncom.utils.Anim;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SpanStringUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class NewRelationshipSearchResultsFragment extends Fragment implements FragmentBackHandler {

    private NewRelationShipBean search_layout_view;
    private String searchResult;
    private String use;
    private View rootView;
    private JSONObject params;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView resultsNone;
    private AppCompatImageView back_icon;
    private PopupWindow popupWindow;
    private boolean search_display_type;//搜索按钮显示状态true 显示 false 隐藏
    private NewRelationShipSearchResultsAdapter newRelationShipSearchResultsAdapter;
    private static  NewRelationshipSearchResultsFragment newRelationshipSearchResultsFragment=null;

    public NewRelationshipSearchResultsFragment() {
        // Required empty public constructor
    }



    public static NewRelationshipSearchResultsFragment getInstance(){
        if(newRelationshipSearchResultsFragment==null){
            newRelationshipSearchResultsFragment=new NewRelationshipSearchResultsFragment();
        }
        return newRelationshipSearchResultsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        search_layout_view=new NewRelationShipBean();
        search_layout_view.setView_type(0);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_new_relationship_search_results, container, false);
        searchView= (SearchView) view.findViewById(R.id.search_column);
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
        searchView.setQueryHint(searchResult);
        rootView=view;
        searchView.clearFocus();
        recyclerView= (RecyclerView) view.findViewById(R.id.search_page_recyclerview);
        resultsNone= (TextView) view.findViewById(R.id.results_none);
        newRelationShipSearchResultsAdapter=new NewRelationShipSearchResultsAdapter(getContext()) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newRelationShipSearchResultsAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                newRelationShipSearchResultsAdapter.notifyItemRemoved(0);
                search_display_type=false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchResult=newText;
                if(resultsNone.getVisibility()==View.VISIBLE){
                    resultsNone.setVisibility(View.GONE);
                }
                search_layout_view.setResults(newText);
                if(search_display_type ==false){
                    newRelationShipSearchResultsAdapter.add(search_layout_view,1);
                    search_display_type=true;
                }else{
                    newRelationShipSearchResultsAdapter.add(search_layout_view);
                }
                return false;
            }
        });
        back_icon= (AppCompatImageView) view.findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoreApplication.newInstance().setDisPlayType(false);
                if(popupWindow.isShowing()==true){
                    popupWindow.dismiss();
                }
                FragmentManager fragmentManager= RalationShipPageMainFragment.getInstance().getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_translate_open,R.anim.default_leave_left);
                fragmentManager.popBackStack();
            }
        });
        RequestData();

        return view;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }




    public void RequestData(){
        params=SpanStringUtils.checkSearchMode(searchResult);
        Log.v("NewRelationshipSearchResultsFragment", String.valueOf(params));
        try {
            if(params.getString("use").equals("null")){
                //为空 不执行
            }else{
                popupWindow= PopupWindowUtils.popupWindowNormal(null,R.layout.access_popupwindow_loginwaiting_layout, LinearLayout.LayoutParams.MATCH_PARENT,1000,-1,getContext(),rootView);
                Log.v("开始访问网络", String.valueOf(params));
                ChatUserDataUtil.searchUser(CoreApplication.newInstance().getRequestQueue(),getContext(),rootView,params,popupWindow,newRelationShipSearchResultsAdapter,resultsNone,this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onBackPressed() {
        CoreApplication.newInstance().setDisPlayType(false);
        if(popupWindow.isShowing()==true){
            popupWindow.dismiss();
            return true;
        }
        return BackHandlerHelper.handleBackPress(this);
    }

/*  @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(!enter){
            CoreApplication.newInstance().setDisPlayType(false);
        }
        return Anim.defaultFragmentAnim(getActivity(),transit,enter,nextAnim);
    }*/
}
