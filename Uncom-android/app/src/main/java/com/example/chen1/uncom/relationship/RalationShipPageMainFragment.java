package com.example.chen1.uncom.relationship;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.BadgeMessageUtil;
import com.example.chen1.uncom.utils.StateCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


public class RalationShipPageMainFragment extends Fragment implements FragmentBackHandler{

    public boolean InitTag=true;
    private TextView newRelationShipActive;
    private AppCompatImageView headImage;
    public  ArrayList<RelationShipLevelBean> personFrendList;
    private ListView group_listView;
    private RecyclerView person_listview;
    private ConstraintLayout new_relation_ship_button;
    private   BaseAdapter group_baseAdapter;
    private PersonAdapter personAdapter;
    private  PersonRelationShipAdapter person_baseAdapter;
    private static RalationShipPageMainFragment ralationShipPageMainFragment=null;
    private View view;



    public static RalationShipPageMainFragment getInstance(){
        if(ralationShipPageMainFragment==null){
            ralationShipPageMainFragment=new RalationShipPageMainFragment();
        }
        return ralationShipPageMainFragment;
    }

    public  static RalationShipPageMainFragment newInstance(){
        return  new RalationShipPageMainFragment();

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        personFrendList=CoreApplication.newInstance().getPersonFrendList();
        if(personFrendList==null){
            personAdapter=new PersonAdapter(RalationShipPageMainFragment.this);
            CoreApplication.newInstance().asynPersonFrendList(new Runnable() {
                @Override
                public void run() {
                    Log.v("relationShip:","Run");
                    personFrendList=CoreApplication.newInstance().getPersonFrendList();
                    personAdapter.setData(personFrendList);
                    personAdapter.notifyDataSetChanged();
                }
            });
        }else{
            personAdapter=new PersonAdapter(personFrendList,RalationShipPageMainFragment.this);
            Log.v("frendList size", String.valueOf(personFrendList.size()));
        }
    }

    public RalationShipPageMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null){
            view=inflater.inflate(R.layout.fragment_ralation_ship_page_main, container, false);
        }
        new_relation_ship_button=(ConstraintLayout)view.findViewById(R.id.new_relationship_button);
        group_listView=(ListView) view.findViewById(R.id.group_list_view);
        person_listview=(RecyclerView) view.findViewById(R.id.person_list_view);
        person_listview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        person_listview.setHasFixedSize(true);
        newRelationShipActive= (TextView) view.findViewById(R.id.unlook_newrelationship_sum);
         group_baseAdapter=new GroupRelationShipAdapter();
         return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(CoreApplication.newInstance().getNewRelationActive()!=0){
            if(newRelationShipActive.getVisibility()==View.GONE){
                newRelationShipActive.setVisibility(View.VISIBLE);
            }
        }
        group_listView.setAdapter(group_baseAdapter);
        person_listview.setAdapter(personAdapter);
        personAdapter.setItemOnClickListener(new PersonAdapter.ItemOnClickListener() {

            @Override
            public void onClick(View v, String type, RelationShipLevelBean bean) {
                if(type.equals("itemView")){
                    BadgeMessageUtil.setSetPageIsVisible(false);
                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null)
                    .setCustomAnimations(R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_translate_open,
                            R.anim.default_fragment_switch_leave_translate,
                            R.anim.default_fragment_switch_leave_translate
                    );
                    PersonChatFragment person_chat_fragment= (PersonChatFragment) getFragmentManager().findFragmentByTag("chatPage");
                    if(person_chat_fragment!=null){
                        person_chat_fragment.setFrendData(bean);
                        person_chat_fragment.setTargetFragment(RalationShipPageMainFragment.this,11123);
                        fragmentTransaction.show(person_chat_fragment).commitAllowingStateLoss();
                    }else{
                        person_chat_fragment=PersonChatFragment.newInstance(bean);
                        person_chat_fragment.setTargetFragment(RalationShipPageMainFragment.this,11123);
                        fragmentTransaction.add(R.id.drawer_layout,person_chat_fragment,"chatPage").commitAllowingStateLoss();
                    }
                    CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left));

                }else{
                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(null)
                            .setCustomAnimations(R.anim.default_fragment_switch_leave_translate,
                                    R.anim.default_leave_left,
                                    R.anim.default_open_right,
                                    R.anim.default_fragment_switch_translate_open);
                    PersonDetailedInformationFragment fragment= (PersonDetailedInformationFragment) getFragmentManager().findFragmentByTag("PersonDetailedInformationFragment");
                    if(fragment!=null){
                        fragmentTransaction.show(fragment).commitAllowingStateLoss();
                    }else{
                        fragment = PersonDetailedInformationFragment.newInstance(bean);
                         fragmentTransaction.add(R.id.drawer_layout,fragment,"PersonDetailedInformationFragment").commitAllowingStateLoss();
                    }
                    CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_leave_left));

                }
            }
        });
        newRelationShipActive.setText(CoreApplication.newInstance().getNewRelationActive().toString());
        new_relation_ship_button.setOnClickListener(new ToNewRelationShipButtonOnclickListener(getFragmentManager()));

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void syncMessage(RelationMessage message){
        if(message.getSTATE_CODE().equals(StateCode.PERSON_INFO_MESSAGE)){
            RelationShipLevelBean bean= (RelationShipLevelBean) message.getMessage();
            for (int i = 0; i <personFrendList.size() ; i++) {
                if(personFrendList.get(i).getMinor_user().equals(bean.getMinor_user())){
                        personFrendList.set(i,bean);
                        personAdapter.notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        CoreApplication.newInstance().setPersonRelationShipAdapter(null);
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackAllImmediate(this);
    }
}
