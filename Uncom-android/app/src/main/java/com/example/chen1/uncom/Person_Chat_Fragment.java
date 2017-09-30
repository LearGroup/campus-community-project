package com.example.chen1.uncom;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomNavigationView;
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
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Person_Chat_Fragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, FragmentBackHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatImageView back_icon;
    private int IniteFragment = 0;
    private AppCompatButton send_btn;
    private SharedPreferences sp;
    private static final String SHARE_PREFERENCE_NAME = "EmotionKeyboard";
    private static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";
    private int ContentViewHeight;
    private ViewPager ExpressionViewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewGroup viewGroup;
    private EditText input_text;
    private int KeyBoardHeight;
    private RecyclerView ContentView;
    private PersonChatRecyclerViewAdapter personChatRecyclerViewAdapter;
    private int ExpressionTypeCount;
    private InputMethodManager mInputManager;
    private List<Integer> list = new ArrayList<>();
    private RecyclerView ExpressionMenuType;
    private AppCompatImageView chat_more_icon;
    private AppCompatImageView ExpressionBtn;
    private int ExpressionBtnStatus = 0;
    private LinearLayout ExpressionLinearLayout;

    public Person_Chat_Fragment() {
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


    private static Person_Chat_Fragment person_chat_fragment = null;

    public static Person_Chat_Fragment getInstance() {
        if (person_chat_fragment == null) {
            person_chat_fragment = new Person_Chat_Fragment();
        }
        return person_chat_fragment;
    }


    public static Person_Chat_Fragment newInstance(String param1, String param2) {
        Person_Chat_Fragment fragment = new Person_Chat_Fragment();
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

        // Inflate the layout for this fragment
        final TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(150);
        final TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(150);
        quitFullScreen();
        final View view = inflater.inflate(R.layout.fragment_person__chat_, container, false);
        personChatRecyclerViewAdapter = new PersonChatRecyclerViewAdapter(getContext());
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

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.person_chat_toolbar);
        setHasOptionsMenu(true);
        send_btn = (AppCompatButton) view.findViewById(R.id.person_chat_send_button);
        chat_more_icon = (AppCompatImageView) view.findViewById(R.id.appCompatImageView6);
        input_text = (EditText) view.findViewById(R.id.person_chat_editText);
        toolbar.inflateMenu(R.menu.person_chat_menu_layout);
        ExpressionLinearLayout = (LinearLayout) view.findViewById(R.id.Expression_LinearLayout);
        ExpressionBtn = (AppCompatImageView) view.findViewById(R.id.appCompatImageView5);
        back_icon = (AppCompatImageView) view.findViewById(R.id.person_chat_back_icon);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=input_text.getText().toString();
                ChatMessgaeContent item2= new ChatMessgaeContent(str,new Date(),R.drawable.head_img,true);
                personChatRecyclerViewAdapter.add(item2,1);
                ContentView.smoothScrollToPosition(personChatRecyclerViewAdapter.getItemCount()-1);
                input_text.setText(null);
            }
        });
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = RalationShipPageMainFragment.getInstance().getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentManager.popBackStack();
            }
        });
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

                KeyBoardHeight = height;
                if (ExpressionLinearLayout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    ExpressionLinearLayout.setVisibility(View.GONE);
                    unlockContentHeightDelayed();

                }
            }

            @Override
            public void keyBoardHide(int height) {
            /*    KeyBoardHeight=height;*/


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
                }else{
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
        ExpressionMenuType.setAdapter(grallyAdapter);
        ExpressionMenuType.setItemAnimator(new DefaultItemAnimator());
        grallyAdapter.setOnItemClickListener(new GrallyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ExpressionViewPager.setCurrentItem(position);

            }


        });

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


    interface onBackListener {
        public void backListener();
    }
}
