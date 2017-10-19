package com.example.chen1.uncom.relationship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.alibaba.fastjson.JSON;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.chat.ChatUserDataUtil;
import com.example.chen1.uncom.set.OnItemClickListener;
import com.example.chen1.uncom.utils.Anim;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SpanStringUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class NewRelationshipSearchResultsFragment extends Fragment {

    private SearchView searchView;
    private String searchResult;
    private String use;
    private JSONObject params;
    private RecyclerView recyclerView;
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
        searchView.clearFocus();
        recyclerView= (RecyclerView) view.findViewById(R.id.search_page_recyclerview);
        newRelationShipSearchResultsAdapter=new NewRelationShipSearchResultsAdapter(getContext()) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newRelationShipSearchResultsAdapter);
        params=SpanStringUtils.checkSearchMode(searchResult);
        Log.v("NewRelationshipSearchResultsFragment", String.valueOf(params));
        try {
            if(params.getString("use").equals("null")){
                //为空 不执行
            }else{
                final PopupWindow popwin= PopupWindowUtils.popupWindow(null,R.layout.access_popupwindow_loginwaiting_layout, LinearLayout.LayoutParams.MATCH_PARENT,1000,-1,getContext(),view);
                Log.v("开始访问网络", String.valueOf(params));
                ChatUserDataUtil.searchUser(CoreApplication.newInstance().getRequestQueue(),getContext(),view,params,popwin,newRelationShipSearchResultsAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }



/*  @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(!enter){
            CoreApplication.newInstance().setDisPlayType(false);
        }
        return Anim.defaultFragmentAnim(getActivity(),transit,enter,nextAnim);
    }*/
}
