package com.example.chen1.uncom.chat;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBean;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.expression.GrallyAdapter;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.relationship.RalationShipPageMainFragment;
import com.example.chen1.uncom.expression.ChatExpressionTypePageSwitchAdapter;
import com.example.chen1.uncom.expression.SoftKeyBoardListener;
import com.example.chen1.uncom.utils.Anim;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.KeybordUtil;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PersonChatFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, FragmentBackHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatImageView back_icon;
    private int IniteFragment = 0;
    private AppCompatButton send_btn;
    private SharedPreferences sp;
    private String user_id;
    private RelationShipLevelBeanDao  relationShipLevelBeanDao;
    private  boolean isVisible;//判断当前fragment是否可见
    private static final String SHARE_PREFERENCE_NAME = "EmotionKeyboard";
    private static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";
    private int ContentViewHeight;
    private ViewPager ExpressionViewPager;
    private Handler getChatDataHandler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private RelationShipLevelBean frendData;
    private String mParam2;
    private ViewGroup viewGroup;
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

    public RelationShipLevelBean getFrendData() {
        return frendData;
    }

    public void setFrendData(RelationShipLevelBean frendData) {
        this.frendData = frendData;
        relationShipLevelBeanDao = BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
    }

    public PersonChatFragment() {
        // Required empty public constructor
    }

    private void InitExpression() {
        ExpressionMenuType.removeAllViews();
        ExpressionTypeCount = 2;
        list.clear();
        list.add(R.drawable.ic_expression_2_icon);
        list.add(R.drawable.ic_vector_expression_heart_icon);
        list.add(R.drawable.ic_vector_group_travel);

    }


    private static PersonChatFragment person_chat_fragment = null;

    public static PersonChatFragment getInstance() {
        if (person_chat_fragment == null) {
            person_chat_fragment = new PersonChatFragment();
        }
        return person_chat_fragment;
    }


    public static PersonChatFragment newInstance(String param1, String param2) {
        PersonChatFragment fragment = new PersonChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(input_text.getWindowToken(), 0);
    }

    private void showEmotionLayout() {
        int softInputHeight = KeyBoardHeight;
        ExpressionLinearLayout.getLayoutParams().height = KeyBoardHeight;
        ExpressionLinearLayout.setVisibility(View.VISIBLE);
        hideSoftInput();
    }


    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        input_text.requestFocus();
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
        if (softInputHeight < 0) {

        }
        //存一份到本地
        if (softInputHeight > 0) {
        }
        return softInputHeight;
    }


    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ContentView.getLayoutParams();
        params.height = ContentView.getHeight();
        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed() {
        input_text.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) ContentView.getLayoutParams()).weight = 1.0F;
            }
        }, 500L);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quitFullScreen();
        user_id=SharedPreferencesUtil.getUserId(getContext());
        KeyBoardHeight= SharedPreferencesUtil.getSoftInputHeight(getContext());
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
        messageHistoryBeanDao = BeanDaoManager.getInstance().getDaoSession().getMessageHistoryBeanDao();
        QueryBuilder queryBuilder=messageHistoryBeanDao.queryBuilder();
/*  queryBuilder.or(MessageHistoryBeanDao.Properties.OwnId.eq(frendData.getMinor_user()),
                        queryBuilder.and( MessageHistoryBeanDao.Properties.OwnId.eq(user_id),
                                MessageHistoryBeanDao.Properties.TargetId.eq(user_id))))*/
//查询聊天对象对我发送的历史记录
/* queryBuilder.and(MessageHistoryBeanDao.Properties.OwnId.eq(frendData.getMinor_user()),
                        MessageHistoryBeanDao.Properties.TargetId.eq(user_id))*/
        Query query=queryBuilder.where(
queryBuilder.or(queryBuilder.and(MessageHistoryBeanDao.Properties.OwnId.eq(user_id),
        MessageHistoryBeanDao.Properties.TargetId.eq(frendData.getMinor_user())),queryBuilder.and(MessageHistoryBeanDao.Properties.OwnId.eq(frendData.getMinor_user()),
        MessageHistoryBeanDao.Properties.TargetId.eq(user_id)))).
                orderDesc(MessageHistoryBeanDao.Properties.Time).limit(10).offset(0).build();

              /*  queryBuilder.and(MessageHistoryBeanDao.Properties.OwnId.eq(user_id),
                        MessageHistoryBeanDao.Properties.TargetId.eq(frendData.getMinor_user()))).
                orderDesc(MessageHistoryBeanDao.Properties.Time).limit(10).offset(0).build();*/

        /*
        Query query=messageHistoryBeanDao.queryBuilder().where(MessageHistoryBeanDao.Properties.OwnId.eq(frendData.getId())).orderDesc(MessageHistoryBeanDao.Properties.Time).limit(10).offset(0).build();
   */   if(query!=null){

            ArrayList<MessageHistoryBean>temp=(ArrayList<MessageHistoryBean>) query.list();
            messgaeContents=new ArrayList<>();
            Log.v("query", String.valueOf(query));
            if(temp!=null){
                for (int i = 0; i < temp.size(); i++) {
                    Log.v("temp",temp.get(temp.size()-i-1).getContent());
                    messgaeContents.add(i,temp.get(temp.size()-i-1));
                }
            }
            }

        getChatDataHandler=new Handler(){
            //接收消息
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        JSONArray jsonArray=(JSONArray) msg.obj;
                        try {
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject object=jsonArray.getJSONObject(i);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String str=  object.getString("time");
                                str=str.replaceAll( "\\\\",  "");
                                str=str.replaceAll("\"","");
                                String d = format.format(Long.parseLong(str));
                                Date date=format.parse(d);
                                Log.v("time", String.valueOf(date));

                                personChatRecyclerViewAdapter.add(isVisible,new MessageHistoryBean(null,object.getString("ownId"),user_id,object.getString("content"),date,false,false),1,messageHistoryBeanDao);
                                ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);

                            }
                            //MessageHistoryBean item2= new MessageHistoryBean(frendData.getId(),str,new Date().toString(),true);
                                             } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        CoreApplication.newInstance().setGetChatDataHandler(getChatDataHandler);
        CoreApplication.newInstance().getCoreService().setGetChatDataHandler(getChatDataHandler);
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
                             Bundle savedInstanceState) {
        Log.v("PersonChatFramgent:", "onCreateview: ");
        KeyBoardHeight= SharedPreferencesUtil.getSoftInputHeight(getContext());
        isVisible = true;
        final View view = inflater.inflate(R.layout.fragment_person__chat_, container, false);
        username=(TextView) view.findViewById(R.id.person_username);
        username.setText(frendData.getUsername());
        personChatRecyclerViewAdapter = new PersonChatRecyclerViewAdapter(getContext(),frendData);
        ExpressionViewPager = (ViewPager) view.findViewById(R.id.chat_expression_viewpager);
        ExpressionMenuType = (RecyclerView) view.findViewById(R.id.chat_listmenuitem_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ExpressionMenuType.setLayoutManager(linearLayoutManager);
        ExpressionMenuType.setHasFixedSize(true);
        LinearLayoutManager contentViewLinearLayoutManager = new LinearLayoutManager(view.getContext());
        contentViewLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ContentView = (RecyclerView) view.findViewById(R.id.person_chat_recyclerview);
        ContentView.setLayoutManager(contentViewLinearLayoutManager);
        ContentView.setHasFixedSize(true);
        ContentView.setAdapter(personChatRecyclerViewAdapter);
        softinputLinearLayout= (LinearLayout) view.findViewById(R.id.person_chat_bottomNavigationView);
        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.person_chat_toolbar);
        setHasOptionsMenu(true);
        send_btn = (AppCompatButton) view.findViewById(R.id.person_chat_send_button);
        chat_more_icon = (AppCompatImageView) view.findViewById(R.id.appCompatImageView6);
        input_text = (EditText) view.findViewById(R.id.person_chat_editText);
        toolbar.inflateMenu(R.menu.person_chat_menu_layout);
        ExpressionLinearLayout = (LinearLayout) view.findViewById(R.id.Expression_LinearLayout);
        ExpressionBtn = (AppCompatImageView) view.findViewById(R.id.appCompatImageView5);
        back_icon = (AppCompatImageView) view.findViewById(R.id.person_chat_back_icon);
        if(messgaeContents!=null && messgaeContents.size()>0){
            personChatRecyclerViewAdapter.setListItem(messgaeContents);
            ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
        }
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=input_text.getText().toString();
                /*str,new Date().toString(),R.drawable.head_img,true*/
               MessageHistoryBean item2= new MessageHistoryBean(null,user_id,frendData.getMinor_user(),str,new Date(),false,true);
               personChatRecyclerViewAdapter.add(isVisible,item2,1,messageHistoryBeanDao);
                ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                Message message=new Message();
                message.what=0;
                message.obj=item2;
                CoreApplication.newInstance().getCoreService().getSendChatHandler().sendMessage(message);
                input_text.setText(null);
            }
        });
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                //得到InputMethodManager的实例
                KeybordUtil.closeKeybord(input_text,getContext());
                FragmentManager fragmentManager = RalationShipPageMainFragment.getInstance().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.popBackStack();
            }
        });
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                if(KeyBoardHeight !=height){
                    KeyBoardHeight = height;
                    SharedPreferencesUtil.setSoftInputHeight(KeyBoardHeight,getContext());
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
        });
        input_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP && ExpressionLinearLayout.isShown()) {
                    if(personChatRecyclerViewAdapter.getItemCount()>=2){
                        ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                    }
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
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
                    layoutParams.setMargins(0,0,0,KeyBoardHeight);//设置rlContent的marginBottom的值为软键盘占有的高度即可
                    softinputLinearLayout.requestLayout();
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
                    Log.v("softInput", "true ");
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
        GrallyAdapter grallyAdapter = new GrallyAdapter(view.getContext());
        ExpressionViewPager.setAdapter(new ChatExpressionTypePageSwitchAdapter(getChildFragmentManager(), list));
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
            frendData.setUn_look(0);
            CoreApplication.newInstance().updateActivePersonMessageList(frendData,2);
            relationShipLevelBeanDao.update(frendData);
        }
        return view;
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onBackPressed() {

        if (ExpressionLinearLayout.isShown()) {
            ExpressionLinearLayout.setVisibility(View.GONE);
            return true;
        }
        return BackHandlerHelper.handleBackPress(this);
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


    @Override
    public void onStop() {
        super.onStop();
        isVisible = false;
        Log.v("PersonChatFragment OnStop","ok");
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(!enter){
            CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.default_open_right));

            //     CoreApplication.newInstance().getBottomNavigationView().setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.default_open_right));
        }
        return Anim.defaultFragmentAnim(getActivity(),transit,enter,nextAnim);
    }

    interface onBackListener {
        public void backListener();
    }
}
