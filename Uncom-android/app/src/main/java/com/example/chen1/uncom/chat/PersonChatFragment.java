package com.example.chen1.uncom.chat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.animation.content.Content;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.expression.GrallyAdapter;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.previewAlbum.EventMessage;
import com.example.chen1.uncom.relationDynamics.RelationDynamics;
import com.example.chen1.uncom.relationship.NewRelationshipAdapter;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.expression.ChatExpressionTypePageSwitchAdapter;
import com.example.chen1.uncom.expression.SoftKeyBoardListener;
import com.example.chen1.uncom.set.SetMessage;
import com.example.chen1.uncom.utils.Anim;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.GestureListener;
import com.example.chen1.uncom.utils.KeybordUtil;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;
import com.example.chen1.uncom.utils.StateCode;
import com.example.chen1.uncom.utils.SwipLayout;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class PersonChatFragment extends Fragment implements FragmentBackHandler {
    private KeybordUtil keybordUtil;
    private AppCompatImageView back_icon;
    private AppCompatButton send_btn;
    private String user_id;
    private RelationShipLevelBeanDao  relationShipLevelBeanDao;
    private  boolean isVisible;//判断当前fragment是否可见
    private int ContentViewHeight;
    private ViewPager ExpressionViewPager;
    private Handler getChatDataHandler;
    private RelationShipLevelBean frendData;
    private EditText input_text;
    private int KeyBoardHeight;
    private TextView username;
    private RecyclerView ContentView;
    private PersonChatRecyclerViewAdapter personChatRecyclerViewAdapter;
    private int ExpressionTypeCount;
    private InputMethodManager mInputManager;
    private MessageHistoryBeanDao messageHistoryBeanDao;
    private List<Integer> list = new ArrayList<>();
    private RecyclerView ExpressionMenuType;
    private AppCompatImageView chat_more_icon;
    private AppCompatImageView ExpressionBtn;
    private  ArrayList<MessageHistoryBean> messgaeContents=null;
    private int ExpressionBtnStatus = 0;
    private LinearLayout ExpressionLinearLayout;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private LinearLayout softinputLinearLayout;
    private LinearLayoutManager linearLayoutManager;
    private ImageView toolbarHeader;
    private Query query;
    private LoadImageUtils loadImageUtils;
    private int query_state;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipLayout swipLayout;
    private  QueryBuilder queryBuilder;
    private View view;
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    private SoftKeyBoardListener softKeyBoardListener;
    private Flowable flowable= Flowable.create(new FlowableOnSubscribe<ArrayList<MessageHistoryBean>>() {
        @Override
        public void subscribe(FlowableEmitter<ArrayList<MessageHistoryBean>> e) throws Exception {
            messageHistoryBeanDao = BeanDaoManager.getInstance().getDaoSession().getMessageHistoryBeanDao();
            if(frendData==null){
                frendData=getArguments().getParcelable("bean");
            }
            if(frendData!=null){
                Log.v("flowable","username"+frendData.getUsername());
            }else{
                Log.v("flowable","null");
            }
            queryBuilder=messageHistoryBeanDao.queryBuilder();
            query=queryBuilder.where(
                    queryBuilder.or(queryBuilder.and(MessageHistoryBeanDao.Properties.OwnId.eq(user_id),
                            MessageHistoryBeanDao.Properties.TargetId.eq(frendData.getMinor_user())),queryBuilder.and(MessageHistoryBeanDao.Properties.OwnId.eq(frendData.getMinor_user()),
                            MessageHistoryBeanDao.Properties.TargetId.eq(user_id)))).
                    orderDesc(MessageHistoryBeanDao.Properties.Time).limit(15).offset(0).build();
            if(query!=null){

                ArrayList<MessageHistoryBean> temp= (ArrayList<MessageHistoryBean>) query.list();
                e.onNext(temp);
            }
            e.onComplete();
        }
    }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io());


    public RelationShipLevelBean getFrendData() {
        return frendData;
    }

    public void setFrendData(RelationShipLevelBean frendData) {
        if(this.frendData.getMinor_user().equals(frendData.getMinor_user())==false){
            this.frendData = frendData;
            personChatRecyclerViewAdapter.setFrendData(frendData);
            loadImageUtils.getFirendHeaderImage(frendData.getHeader_pic(),toolbarHeader,this);
        }
        if(frendData.getUn_look()!=null &&  frendData.getUn_look()!=0){
            EventBus.getDefault().post(new SetMessage(frendData,StateCode.RELATION_LEVEL_BEAN));
            frendData.setUn_look(0);
            relationShipLevelBeanDao.update(frendData);
        }
        syncData();
    }

    public PersonChatFragment( ) {
        // Required empty public constructor
    }





    public static PersonChatFragment newInstance(RelationShipLevelBean bean){
        PersonChatFragment fragment=new PersonChatFragment();
        Bundle args = new Bundle();
        args.putParcelable("bean",bean);
        fragment.setArguments(args);
        return fragment;
    }



    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        Log.v("hideSoftInput","true");
        mInputManager.hideSoftInputFromWindow(input_text.getWindowToken(), 0);
    }

    private void showEmotionLayout() {
        Log.v("showEmotionLayout","true");
        int softInputHeight = KeyBoardHeight;
        if(softInputHeight>0){
            ExpressionLinearLayout.getLayoutParams().height = KeyBoardHeight;
            ExpressionLinearLayout.setVisibility(View.VISIBLE);
            hideSoftInput();
        }

    }


    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        Log.v("showSoftInput","true");
        input_text.requestFocus();
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin=KeyBoardHeight;
        softinputLinearLayout.setLayoutParams(lp);
        input_text.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(input_text, 0);
            }
        });
    }

    /**
     * 隐藏表情布局
     *
     * @param showSoftInput 是否显示软件盘
     */
    private void hideEmotionLayout(boolean showSoftInput) {
        Log.v("hideEmotionLayout","true");
        if (ExpressionLinearLayout.isShown()) {
            ExpressionLinearLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 是否显示软件盘
     *
     * @return
     */
    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = getActivity().getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;
        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }

        return softInputHeight;
    }


    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) swipeRefreshLayout.getLayoutParams();
        params.height = swipeRefreshLayout.getHeight();
        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed() {
        input_text.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) swipeRefreshLayout.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }

    private void syncData(){
        Log.v("syncData","begin");
        query_state=1;
       flowable.observeOn(AndroidSchedulers.mainThread()).
        subscribe(new Subscriber<ArrayList<MessageHistoryBean>>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ArrayList<MessageHistoryBean> objects) {
                    ArrayList<MessageHistoryBean>temp=objects;
                    messgaeContents=new ArrayList<>();
                    Log.v("query", String.valueOf(query));
                    if(temp!=null){
                        for (int i = 0; i < temp.size(); i++) {
                            Log.v("temp",temp.get(temp.size()-i-1).getContent());
                            messgaeContents.add(i,temp.get(temp.size()-i-1));
                        }
                    }
                personChatRecyclerViewAdapter.setListItem(messgaeContents);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        quitFullScreen();
        if(CoreApplication.newInstance().getCoreService()==null){
            CoreApplication.newInstance().startServices();
        }
        keybordUtil=new KeybordUtil();
        loadImageUtils=new LoadImageUtils();
        if(frendData==null){
            Log.v("frendData","null");
            frendData=getArguments().getParcelable("bean");
        }
        Log.v("frendData",frendData.getUsername());
        this.relationShipLevelBeanDao = BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
        personChatRecyclerViewAdapter = new PersonChatRecyclerViewAdapter(CoreApplication.newInstance().getBaseContext(),frendData,getTargetFragment());
        flowable.observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<ArrayList<MessageHistoryBean>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ArrayList<MessageHistoryBean> objects) {
                        ArrayList<MessageHistoryBean>temp=objects;
                        messgaeContents=new ArrayList<>();
                        Log.v("query", String.valueOf(query));
                        if(temp!=null){
                            for (int i = 0; i < temp.size(); i++) {
                                Log.v("temp",temp.get(temp.size()-i-1).getContent());
                                messgaeContents.add(i,temp.get(temp.size()-i-1));
                            }
                        }
                        CountDownTimer timer=new CountDownTimer(230,230) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                personChatRecyclerViewAdapter.setListItem(messgaeContents);
                            }
                        };
                        timer.start();
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        user_id=SharedPreferencesUtil.getUserId(CoreApplication.newInstance().getBaseContext());
        KeyBoardHeight= SharedPreferencesUtil.getSoftInputHeight(CoreApplication.newInstance().getBaseContext());
        // Inflate the layout for this fragment
       mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(150);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(150);
        query_state=1;
    }

    private void quitFullScreen() {

        mInputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        final WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().setAttributes(attrs);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        Log.v("PersonChatFramgent:", "onCreateview: ");
        if(view==null){
            view = inflater.inflate(R.layout.fragment_person__chat_, container, false);
        }
        ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipLayout=new SwipLayout(getContext());
        swipLayout.setLayoutParams(params);
        swipLayout.setBackgroundColor(Color.TRANSPARENT);
        swipLayout.removeAllViews();
        swipLayout.addView(view);
        swipLayout.setParentView(CoreApplication.newInstance().getRoot()).setFragment(PersonChatFragment.this);
        return swipLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KeyBoardHeight= SharedPreferencesUtil.getSoftInputHeight(CoreApplication.newInstance().getBaseContext());
        isVisible = true;
        username=(TextView) view.findViewById(R.id.person_username);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(messgaeContents.size()>=15){
                    query=query.forCurrentThread();
                    if(query_state==1){
                        Log.v("query setOffset", String.valueOf(10));
                        query.setOffset(15);
                    }else{
                        Log.v("query setOffset", String.valueOf(15+(query_state-1)*15));
                        query.setOffset(15+(query_state-1)*15);
                    }
                    query_state+=1;
                    ArrayList<MessageHistoryBean>temp=(ArrayList<MessageHistoryBean>) query.list();
                    Log.v("query length", String.valueOf(temp.size()));
                    if(temp!=null){
                        for (int i = 0; i < temp.size(); i++) {
                            Log.v("temp",temp.get(temp.size()-i-1).getContent());
                            messgaeContents.add(0,temp.get(i));
                        }
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    swipeRefreshLayout.setEnabled(false);
                    personChatRecyclerViewAdapter.notifyItemRangeInserted(0,temp.size());
                    if(temp.size()>=3){
                        ContentView.smoothScrollToPosition(temp.size()-3);
                    }else {
                        ContentView.smoothScrollToPosition(0);
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(false);
            }
        });

        username.setText(frendData.getUsername());
        toolbarHeader= (ImageView) view.findViewById(R.id.toolbar_heade);
        ExpressionViewPager = (ViewPager) view.findViewById(R.id.chat_expression_viewpager);
        ExpressionMenuType = (RecyclerView) view.findViewById(R.id.chat_listmenuitem_view);
        linearLayoutManager = new LinearLayoutManager(CoreApplication.newInstance().getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ExpressionMenuType.setLayoutManager(linearLayoutManager);
        ExpressionMenuType.setHasFixedSize(true);
        LinearLayoutManager contentViewLinearLayoutManager = new LinearLayoutManager(CoreApplication.newInstance().getBaseContext());
        contentViewLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contentViewLinearLayoutManager.setStackFromEnd(true);
        ContentView = (RecyclerView) view.findViewById(R.id.person_chat_recyclerview);
        ContentView.setLayoutManager(contentViewLinearLayoutManager);
        ContentView.setHasFixedSize(true);
        ContentView.setAdapter(personChatRecyclerViewAdapter);
        ContentView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int aa =linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                Log.v("findFirstCompletelyVisibleItemPosition", String.valueOf(aa));
                if(recyclerView.canScrollVertically(0)==true){
                    swipeRefreshLayout.setEnabled(true);
                }else{
                    swipeRefreshLayout.setEnabled(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        softinputLinearLayout= (LinearLayout) view.findViewById(R.id.person_chat_bottomNavigationView);
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.person_chat_toolbar);
        setHasOptionsMenu(true);
        send_btn = (AppCompatButton) view.findViewById(R.id.person_chat_send_button);
        chat_more_icon = (AppCompatImageView) view.findViewById(R.id.appCompatImageView6);
        input_text = (EditText) view.findViewById(R.id.person_chat_editText);
        //  toolbar.inflateMenu(R.menu.person_chat_menu_layout);
        ExpressionLinearLayout = (LinearLayout) view.findViewById(R.id.Expression_LinearLayout);
        ExpressionBtn = (AppCompatImageView) view.findViewById(R.id.appCompatImageView5);
        back_icon = (AppCompatImageView) view.findViewById(R.id.person_chat_back_icon);
        if(messgaeContents!=null && messgaeContents.size()>0){
            personChatRecyclerViewAdapter.setListItem(messgaeContents);
            ContentView.scrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
        }
        loadImageUtils.getFirendHeaderImage(frendData.getHeader_pic(),toolbarHeader,this);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=input_text.getText().toString();
                /*str,new Date().toString(),R.drawable.head_img,true*/
                MessageHistoryBean item2= new MessageHistoryBean(null,user_id,frendData.getMinor_user(),str,new Date(),false,true);
                personChatRecyclerViewAdapter.add(isVisible,item2,1,messageHistoryBeanDao);
                ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                final Message message=new Message();
                message.what=0;
                message.obj=item2;
                input_text.setText(null);
                if(CoreApplication.newInstance().getCoreService()==null){
                    CoreApplication.newInstance().startServices();
                    CountDownTimer timer=new CountDownTimer(200,200) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            CoreApplication.newInstance().getCoreService().getSendChatHandler().sendMessage(message);
                        }
                    };
                    timer.start();
                }else{
                    CoreApplication.newInstance().getCoreService().getSendChatHandler().sendMessage(message);
                }
            }
        });
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("setFragment ","show");
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                //得到InputMethodManager的实例
                keybordUtil.closeKeybord(input_text,CoreApplication.newInstance().getBaseContext());
                FragmentManager fragmentManager =getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.popBackStack();
            }
        });


        onSoftKeyBoardChangeListener=new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                if(KeyBoardHeight !=height ){
                    KeyBoardHeight = height;
                    SharedPreferencesUtil.setSoftInputHeight(KeyBoardHeight,CoreApplication.newInstance().getBaseContext());
                }
                if (ExpressionLinearLayout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    ExpressionLinearLayout.setVisibility(View.GONE);
                    unlockContentHeightDelayed();
                }else{

                }
            }

            @Override
            public void keyBoardHide(int height) {
            /*    KeyBoardHeight=height;*/
                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) softinputLinearLayout.getLayoutParams();
                layoutParams.setMargins(0,0,0,0);//设置rlContent的marginBottom的值为软键盘占有的高度即可
                softinputLinearLayout.requestLayout();
            }
        };


        softKeyBoardListener=SoftKeyBoardListener.setListener(getActivity().getWindow(), onSoftKeyBoardChangeListener);
        input_text.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.v("input_text","onTouchListener");
                if (event.getAction() == MotionEvent.ACTION_UP && ExpressionLinearLayout.isShown()) {
                    if(personChatRecyclerViewAdapter.getItemCount()>=2){
                        ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                    }
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    ExpressionLinearLayout.setVisibility(View.GONE);//隐藏表情布局，显示软件盘
                    //软件盘显示后，释放内容高度
                    input_text.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 200L);
                }else if(event.getAction() == MotionEvent.ACTION_UP && ExpressionLinearLayout.isShown()==false){
                    Log.v("软键盘弹出","表情布局影藏");
                    Log.v("软键盘高度", String.valueOf(KeyBoardHeight));
                    LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) softinputLinearLayout.getLayoutParams();
                    if(KeyBoardHeight>0){
                        layoutParams.setMargins(0,0,0,KeyBoardHeight);//设置rlContent的marginBottom的值为软键盘占有的高度即可
                        softinputLinearLayout.requestLayout();
                    }

                }
                else{
                    input_text.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(personChatRecyclerViewAdapter.getItemCount()>=2){
                                ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                            }

                        }
                    }, 200L);
                }
                return false;
            }
        });
        input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("input_text changed", String.valueOf(KeyBoardHeight));
                if (input_text.getText().length() != 0 && send_btn.getVisibility() == View.GONE) {
                    chat_more_icon.startAnimation(mHiddenAction);
                    chat_more_icon.setVisibility(View.GONE);
                    send_btn.startAnimation(mShowAction);
                    send_btn.setVisibility(View.VISIBLE);

                } else if (input_text.getText().length() == 0 && chat_more_icon.getVisibility() == View.GONE) {
                    send_btn.startAnimation(mHiddenAction);
                    send_btn.setVisibility(View.GONE);
                    chat_more_icon.startAnimation(mShowAction);
                    chat_more_icon.setVisibility(View.VISIBLE);
                }
            }
        });
        ExpressionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personChatRecyclerViewAdapter.getItemCount()>=2){
                    ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                }
                if (ExpressionLinearLayout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                    if(personChatRecyclerViewAdapter.getItemCount()>=2){
                        ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                    }
                } else {
                    if (isSoftInputShown()) {//同上
                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeightDelayed();

                    } else {
                        showEmotionLayout();//两者都没显示，直接显示表情布局
                    }
                }
            }
        });
        GrallyAdapter grallyAdapter = new GrallyAdapter();
        ExpressionViewPager.setAdapter(new ChatExpressionTypePageSwitchAdapter(getChildFragmentManager(), list,input_text));
        ExpressionViewPager.setCurrentItem(0);
        ExpressionMenuType.setItemAnimator(new DefaultItemAnimator());
        grallyAdapter.setOnItemClickListener(new GrallyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ExpressionViewPager.setCurrentItem(position);
            }
        });
        //当进入聊天页面 则可判断该用户已浏览该好友的未读消息
        ExpressionMenuType.setAdapter(grallyAdapter);
        if(frendData.getUn_look()!=null &&  frendData.getUn_look()!=0){
            EventBus.getDefault().post(new SetMessage(frendData,StateCode.RELATION_LEVEL_BEAN));
            frendData.setUn_look(0);
            relationShipLevelBeanDao.update(frendData);
        }

    }


    private void backParse(boolean swip) {
        if (swip == false) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.default_fragment_switch_leave_translate,
                    R.anim.default_fragment_switch_leave_translate,
                    R.anim.default_fragment_switch_translate_open,
                    R.anim.default_fragment_switch_translate_open
            ).hide(this).commitAllowingStateLoss();
            CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));

        }
    }

    @Override
    public boolean onBackPressed() {
        if (ExpressionLinearLayout.isShown()) {
            ExpressionLinearLayout.setVisibility(View.GONE);
            return true;
        }

        backParse(false);
    return  true;
       // return BackHandlerHelper.handleBackPress(this);
    }



    /**
     * 判断当前fragment是否可见
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.v("setUserVisibleHint", "gaga:"+String.valueOf(getUserVisibleHint()));
        if(isVisibleToUser) {
            isVisible = true;
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncMessage(ChatMessage chatMessage) {
        if(chatMessage.getType().equals(StateCode.PERSON_CHAT_MESSAGE)){
                    personChatRecyclerViewAdapter.add(isVisible,
                            (MessageHistoryBean) chatMessage.getMessageHistoryBean(),
                            1,messageHistoryBeanDao);
                    ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        isVisible = false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden==true){
            EventBus.getDefault().unregister(this);
            ((MainActivity)getActivity()).bindDrawer();
            Log.v("chatFragment","true");
        }else{
            EventBus.getDefault().register(this);
            ((MainActivity)getActivity()).unBindDrawer();
            Log.v("chatFragment","false");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        frendData=null;
        softKeyBoardListener.unLink(1);
        keybordUtil=null;
        EventBus.getDefault().unregister(this);
    }



}
