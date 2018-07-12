package com.example.chen1.uncom.thinker;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.bean.ThinkerBeanDao;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.expression.SoftKeyBoardListener;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.previewAlbum.AlbumEdit;
import com.example.chen1.uncom.previewAlbum.AlbumEditCallBack;
import com.example.chen1.uncom.previewAlbum.EventMessage;
import com.example.chen1.uncom.set.SetMessage;
import com.example.chen1.uncom.set.SetPageMainFragment;
import com.example.chen1.uncom.utils.AsynLoadImageUtils;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.DisplayUtils;
import com.example.chen1.uncom.utils.GestureListener;
import com.example.chen1.uncom.utils.GlideEngine;
import com.example.chen1.uncom.utils.KeyboardChangeListener;
import com.example.chen1.uncom.utils.MessageEvent;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;
import com.example.chen1.uncom.utils.StateCode;
import com.example.chen1.uncom.utils.SwipLayout;
import com.example.chen1.uncom.utils.TimeUtils;
import com.huantansheng.easyphotos.EasyPhotos;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;


public class WriteThinkFragment extends Fragment implements FragmentBackHandler,View.OnTouchListener,AlbumEditCallBack {
    @android.support.annotation.IdRes
    int TAG01 = 1000;
    int TAG02 = 1001;
    int TAG03 = 1002;
    int TAG04 = 1003;
    int TAG05 = 1004;
    int TAG06 = 1005;
    int TAG07 = 1006;
    private boolean changePhoto=false;
    private boolean changeColor=false;
    public  static final   int TAKE_PHOTO = 1;
    public  static final   int CHOOSE_PHOTO = 2;
    private LinearLayout  photographBtn;
    private LinearLayout leftBtn;
    private LinearLayout containerView;
    private LinearLayout rightBtn;
    private EditText title;
    private FrameLayout rootview;
    private ImageView rightImageView;
    private LinearLayout photoListBtn;
    private ImageView backImageView;
    private ImageView bottomImageView;
    private InputMethodManager imm;
    private int toMain=0;
    private boolean unTop=false;
    private CoordinatorLayout contentView;
    private LayoutInflater layoutInflater;
    private EditText content;
    private LinearLayout bottomLinearLayout;
    private BottomNavigationView bottomNavigationBar;
    private Animation hideAnimation;
    private ScrollView scrollView;
    private Animation showAnimation;
    private  CoordinatorLayout.LayoutParams lp;
    private int softHeight;
    private LinearLayout toolsContainer;
    private View leftView;
    private View rightView;
    private LinearLayout camerBtn;
    private RecyclerView coloSelector;
    private View view;
    private View rootView;
    private ColorSelectorAdapter colorSelectorAdapter;
    private AppBarLayout appBarLayout;
    private boolean leftViewIsShown=false;
    private  boolean rightViewIsShown=false;
    private boolean touchTitle;
    private boolean touchContent;
    private int titleY;
    private int RawY;
    private int  Y;
    private FragmentTransaction fragmentTransaction;
    private int contentY;
    public Object fragment;
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    private SoftKeyBoardListener softKeyBoardListener;
    private int titleRawY;
    private int slop;
    private long pressStartTime;
    private float pressedX;
    private float pressedY;
    private int contentRawY;
    private RecyclerView photoContainer;
    private ValueAnimator scrollViewAnimator;
    private int scrollViewBottom;
    private LinearLayout contentLinearLayout;
    private LinearLayout titleLinearLayout;
    private  ThinkerBean thinkerBean;
    private TextView changeTime;
    private AsynLoadImageUtils asynLoadImageUtils;
    private PhotoAdapter photoAdatper;
    private AppCompatImageView remind_btn;
    private AppCompatImageView nail_btn;
    private AppCompatImageView backBtn;
    private SwipLayout swipLayout;
    public WriteThinkFragment() {
        // Required empty public constructor

    }



public static WriteThinkFragment newInstance() {
        WriteThinkFragment fragment = new WriteThinkFragment();
        return fragment;
        }

@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        fragment=getTargetFragment();
        if(asynLoadImageUtils==null){
            asynLoadImageUtils=new AsynLoadImageUtils();
        }
        if(thinkerBean==null){
            photoAdatper=new PhotoAdapter(CoreApplication.newInstance().getBaseContext(),new ArrayList<ArrayList<String>>(),this);
        }else{
            photoAdatper=new PhotoAdapter(CoreApplication.newInstance().getBaseContext(),asynLoadImageUtils.addImageUrlList(thinkerBean),this);
        }
        leftView = View.inflate(CoreApplication.newInstance().getBaseContext(),R.layout.thinker_left_layout,null);
        rightView = View.inflate(CoreApplication.newInstance().getBaseContext(),R.layout.thinker_right_layout,null);
        rightImageView= (ImageView) rightView.findViewById(R.id.right_img_view);
        coloSelector= (RecyclerView) rightView.findViewById(R.id.color_selector);
        softHeight=SharedPreferencesUtil.getSoftInputHeight(CoreApplication.newInstance().getBaseContext());
        Log.v("softHieght", String.valueOf(softHeight));
        hideAnimation= AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_fragment_leave_down);
        showAnimation=AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_fragment_open_up);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentg

        Log.v("onCreateView","success");
        changeColor=false;
        view = inflater.inflate(R.layout.fragment_write_think, container, false);
        rootView=view;
        ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipLayout=new SwipLayout(getContext());
        swipLayout.setLayoutParams(params);
        swipLayout.setBackgroundColor(Color.TRANSPARENT);
        swipLayout.removeAllViews();
        swipLayout.addView(view);
        if(getTargetFragment() instanceof  ThinkerMainFragment){
            swipLayout.setParentView(getTargetFragment().getView());
        }else{
            swipLayout.setParentView(CoreApplication.newInstance().getRoot());
        }
        swipLayout.setFragment(WriteThinkFragment.this);
        return swipLayout;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backBtn= (AppCompatImageView) view.findViewById(R.id.back_btn);
        bottomImageView= (ImageView) view.findViewById(R.id.bottom_img_view);
        backImageView= (ImageView) view.findViewById(R.id.back_image_view);
        containerView= (LinearLayout) view.findViewById(R.id.container_view);
        rootview= (FrameLayout) view.findViewById(R.id.rootview);
        nail_btn= (AppCompatImageView) view.findViewById(R.id.nail_btn);
        scrollView= (ScrollView) view.findViewById(R.id.scrollView);

        view.setClickable(true);
        changeTime= (TextView) view.findViewById(R.id.changeTime);
        changeTime.setText("修改时间 :  "+ new TimeUtils().getTime(new Date()));
        titleLinearLayout= (LinearLayout) view.findViewById(R.id.titleLinearLayout);
        contentLinearLayout= (LinearLayout) view.findViewById(R.id.contentLinearLayout);
        photoContainer= (RecyclerView) view.findViewById(R.id.photo_container);
        if(thinkerBean!=null){
            Log.v("photoContainer","visible");
            photoContainer.setVisibility(View.VISIBLE);
        }else{
            photoContainer.setVisibility(View.GONE);
            thinkerBean=new ThinkerBean();
        }
        appBarLayout= (AppBarLayout) view.findViewById(R.id.appbar_layout);
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        layoutInflater=LayoutInflater.from(CoreApplication.newInstance().getBaseContext());
        title= (EditText) view.findViewById(R.id.title);
        content=(EditText)view.findViewById(R.id.content);
        if(thinkerBean!=null && thinkerBean.getTitle()!=null){
            Log.v("title",thinkerBean.getTitle());
            title.setText(thinkerBean.getTitle());
        }else{
            title.setText(null);
        }
        if(thinkerBean!=null && thinkerBean.getContent()!=null){
            content.setText(thinkerBean.getContent());
        }else{
            content.setText(null);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBean();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                getFragmentManager().popBackStack();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));
            }
        });
        toolsContainer= (LinearLayout) view.findViewById(R.id.tools_container);
        bottomNavigationBar= (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        contentView= (CoordinatorLayout) view.findViewById(R.id.contentView);
        bottomLinearLayout=(LinearLayout)view.findViewById(R.id.bottom_linearlayout);
        photoContainer.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        photoContainer.setHasFixedSize(true);
        photoAdatper.setItemOnClickListener(new PhotoAdapter.ItemOnClickListener() {
            @Override
            public void onClick(View v, int position, ArrayList<ArrayList<String>> imageUrlList) {
                fragmentTransaction = getFragmentManager().beginTransaction();
                AlbumEdit albumEdit= (AlbumEdit) getFragmentManager().findFragmentByTag("AlbumEdit");
                fragmentTransaction.addToBackStack(null).setCustomAnimations(R.anim.default_fragment_open_up,
                        R.anim.null_translate,
                        R.anim.null_translate,
                        R.anim.default_fragment_leave_down
                );
                if(albumEdit!=null){
                    fragmentTransaction.show(albumEdit).commitAllowingStateLoss();
                }else{
                    albumEdit= AlbumEdit.newInstance(parseToArrayList(imageUrlList),position);
                    fragmentTransaction.add(R.id.rootview, albumEdit, "AlbumEdit").commitAllowingStateLoss();
                }
                rootview.startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_leave_left));

            }
        });

        photoContainer.setAdapter(photoAdatper);
        photoContainer.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoreApplication.newInstance().getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        coloSelector.setLayoutManager(linearLayoutManager);
        coloSelector.setHasFixedSize(true);
        colorSelectorAdapter=new ColorSelectorAdapter(CoreApplication.newInstance().getBaseContext());
        colorSelectorAdapter.setItemOnClickListener(new ColorSelectorAdapter.ItemOnClickListener() {
            @Override
            public void onClick(View view, int positon, final String color) {
                final float finalRadius = (float) Math.hypot(DisplayUtils.getWindowWidth(CoreApplication.newInstance().getBaseContext()), DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext()));
                // 定义揭露动画
                final int centerX =0;
                final int centerY = DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())+toolsContainer.getHeight();
                final int centerX2 = 0;
                final int centerY2 = toolsContainer.getHeight()+bottomLinearLayout.getHeight();
                final int centerY3 = bottomLinearLayout.getHeight();
                backImageView.setBackgroundColor(Color.parseColor(color));
                rightImageView.setBackgroundColor(Color.parseColor(color));
                bottomImageView.setBackgroundColor(Color.parseColor(color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                            backImageView, centerX, centerY, 0, finalRadius);
                    // 设置动画持续时间，并开始动画
                    mCircularReveal.setDuration(300).start();
                    Animator mCircularRevea2 = ViewAnimationUtils.createCircularReveal(
                            rightImageView, centerX2, centerY2, 0, finalRadius);
                    // 设置动画持续时间，并开始动画
                    mCircularRevea2.setDuration(300).start();
                    Animator mCircularRevea3 = ViewAnimationUtils.createCircularReveal(
                            bottomImageView, centerX2, centerY3, 0, finalRadius);
                    // 设置动画持续时间，并开始动画
                    mCircularRevea3.setDuration(300).start();
                }
                leftView.setBackgroundColor(Color.parseColor(color));
                colorSelectorAdapter.setSelectedPosition(positon);
                thinkerBean.setBackColor(color);
                changeColor=true;
                CountDownTimer timer=new CountDownTimer(300,50) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        rootview.setBackgroundColor(Color.parseColor(color));
                        rightView.setBackgroundColor(Color.parseColor(color));
                        bottomLinearLayout.setBackgroundColor(Color.parseColor(color));
                    }
                };
                timer.start();
            }
        });
        coloSelector.setAdapter(colorSelectorAdapter);
        if(thinkerBean!=null && thinkerBean.getBackColor()!=null && thinkerBean.getBackColor().length()>0){
            backImageView.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            rootview.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            toolsContainer.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            leftView.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            bottomImageView.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            bottomLinearLayout.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            rightView.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            rightImageView.setBackgroundColor(Color.parseColor(thinkerBean.getBackColor()));
            colorSelectorAdapter.setSelectedPosition(thinkerBean.getBackColor());
        }
        if(thinkerBean!=null && thinkerBean.getToMain()==true){
            nail_btn.setImageResource(R.drawable.ic_vector_nail_icon);
        }
        nail_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if(thinkerBean!=null){
                    if( thinkerBean.getToTop()!=true){
                        nail_btn.setImageResource(R.drawable.ic_vector_nail_icon);
                        Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                                "主页已置顶", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 100);
                        toast.setDuration(500);
                        thinkerBean.setToMain(true);
                        thinkerBean.setToMainTime(new Date());
                        thinkerBean.setToTop(true);
                        toast.show();
                        toMain=1;
                    }else{
                        nail_btn.setImageResource(R.drawable.ic_vector_nail_null_icon);
                        Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                                "取消置顶", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 100);
                        toast.setDuration(500);
                        thinkerBean.setToMain(false);
                        thinkerBean.setToMainTime(null);
                        thinkerBean.setToTop(false);
                        toast.show();
                        toMain=-1;
                    }
                }

            }
        });



        leftBtn= (LinearLayout) view.findViewById(R.id.left_more);
        rightBtn=(LinearLayout)view.findViewById(R.id.rightBtn);
        lp = (CoordinatorLayout.LayoutParams) bottomLinearLayout.getLayoutParams();
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftViewIsShown){
                    toolsContainer.setVisibility(View.VISIBLE);
                    toolsContainer.startAnimation(hideAnimation);
                    CountDownTimer timer = new CountDownTimer(100, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            toolsContainer.removeAllViews();
                            toolsContainer.addView(rightView);
                            toolsContainer.startAnimation(showAnimation);

                            if (toolsContainer.getVisibility() == View.GONE) {
                                toolsContainer.setVisibility(View.VISIBLE);
                            }
                            rightViewIsShown=true;
                            leftViewIsShown=false;
                        }
                    };
                    timer.start();
                }else{
                    if (toolsContainer.getChildCount() != 0) {
                        toolsContainer.setVisibility(View.VISIBLE);
                        toolsContainer.startAnimation(hideAnimation);
                        CountDownTimer timer = new CountDownTimer(100, 10) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }
                            @Override
                            public void onFinish() {
                                toolsContainer.removeAllViews();
                            }
                        };
                        timer.start();
                    } else {
                        if (isSoftShowing()) {
                            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                        }
                        if (isSoftShowing()) {
                            CountDownTimer countDownTimer = new CountDownTimer(100, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {}
                                @Override
                                public void onFinish() {
                                    toolsContainer.addView(rightView);
                                    toolsContainer.startAnimation(showAnimation);
                                    if (toolsContainer.getVisibility() == View.GONE) {
                                        toolsContainer.setVisibility(View.VISIBLE);
                                    }
                                    rightViewIsShown=true;
                                    leftViewIsShown=false;
                                }
                            };
                            countDownTimer.start();
                        } else {
                            toolsContainer.addView(rightView);
                            toolsContainer.startAnimation(showAnimation);
                            if (toolsContainer.getVisibility() == View.GONE) {
                                toolsContainer.setVisibility(View.VISIBLE);
                            }
                            rightViewIsShown=true;
                            leftViewIsShown=false;

                        }
                    }
                }

            }
        });


        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rightViewIsShown){
                    toolsContainer.setVisibility(View.VISIBLE);
                    toolsContainer.startAnimation(hideAnimation);
                    CountDownTimer timer = new CountDownTimer(100, 10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            toolsContainer.removeAllViews();
                            toolsContainer.addView(leftView);
                            toolsContainer.startAnimation(showAnimation);
                            if (toolsContainer.getVisibility() == View.GONE) {
                                toolsContainer.setVisibility(View.VISIBLE);
                            }
                            rightViewIsShown=false;
                            leftViewIsShown=true;
                        }
                    };
                    timer.start();
                }else{
                    if(toolsContainer.getChildCount()!=0){
                        toolsContainer.setVisibility(View.VISIBLE);
                        toolsContainer.startAnimation(hideAnimation);
                        CountDownTimer timer=new CountDownTimer(100,10) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                toolsContainer.removeAllViews();
                            }
                        };
                        timer.start();
                    }else{
                        if(isSoftShowing()){
                            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                        }
                        if(isSoftShowing()){
                            CountDownTimer countDownTimer=new CountDownTimer(100,10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    toolsContainer.addView(leftView);
                                    toolsContainer.startAnimation(showAnimation);
                                    if(toolsContainer.getVisibility()==View.GONE){
                                        toolsContainer.setVisibility(View.VISIBLE);
                                        rightViewIsShown=false;
                                        leftViewIsShown=true;
                                    };
                                }
                            };
                            countDownTimer.start();
                        }else{
                            toolsContainer.addView(leftView);
                            toolsContainer.startAnimation(showAnimation);
                            if(toolsContainer.getVisibility()==View.GONE){
                                toolsContainer.setVisibility(View.VISIBLE);
                                rightViewIsShown=false;
                                leftViewIsShown=true;
                            }
                        }

                    }
                }


            }
        });


        title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RawY= (int) event.getY()+v.getTop();
                Y= (int) event.getRawY();
                touchTitle=true;
                touchContent=false;
                titleY= v.getBottom();
                titleRawY= (int) event.getRawY();
                return false;
            }
        });

        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchContent=true;
                RawY= (int) event.getY()+v.getTop();
                Y= (int) event.getRawY();
                touchTitle=false;
                contentRawY= (int) event.getRawY();
                contentY=v.getBottom();
                return false;
            }
        });


        onSoftKeyBoardChangeListener= new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(final int height) {
                leftViewIsShown=false;
                rightViewIsShown=false;
                if(softHeight!=height){
                    softHeight=height;
                    SharedPreferencesUtil.setSoftInputHeight(height,CoreApplication.newInstance().getBaseContext());
                }
                ObjectAnimator animator=ObjectAnimator.ofFloat(bottomLinearLayout,"translationY",-height+100,-height);
                animator.setDuration(180);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
                if(touchTitle==true && (DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-titleRawY)<(height+bottomLinearLayout.getHeight())){
                    scrollViewBottom=height+bottomLinearLayout.getHeight();
                    scrollViewAnimator=ValueAnimator.ofInt(bottomLinearLayout.getHeight(),scrollViewBottom);
                    if(scrollViewBottom>(height+bottomLinearLayout.getHeight())){
                        scrollViewBottom=height+bottomLinearLayout.getHeight();
                    }

                    scrollViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            // 3.为目标对象的属性设置计算好的属性值
                            int animatorValue = (int)animation.getAnimatedValue();
                            LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) scrollView.getLayoutParams();
                            marginLayoutParams.bottomMargin = animatorValue;
                            scrollView.setLayoutParams(marginLayoutParams);
                            if(animatorValue==scrollViewBottom){
                                if((DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-Y)<(height+bottomLinearLayout.getHeight()) &&RawY>(DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-bottomLinearLayout.getHeight())){
                                    scrollView.smoothScrollBy(0,   height+bottomLinearLayout.getHeight()-(DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-Y));
                                    title.setFocusable(true);
                                    title.setFocusableInTouchMode(true);
                                    title.requestFocus();
                                }
                            }
                        }
                    });
                    scrollViewAnimator.setDuration(230);
                    scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
                    scrollViewAnimator.setTarget(scrollView);
                    scrollViewAnimator.start();

                }
                if(touchContent ==true && (DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-contentRawY)<(height+bottomLinearLayout.getHeight())){
                    scrollViewBottom=height+bottomLinearLayout.getHeight();

                    if((DisplayUtils.getWindowHeight(CoreApplication.newInstance().getApplicationContext())-Y)>(height+bottomLinearLayout.getHeight())){
                        scrollViewBottom=bottomLinearLayout.getHeight();
                    }
                    scrollViewAnimator=ValueAnimator.ofInt(bottomLinearLayout.getHeight(),scrollViewBottom);
                    scrollViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            // 3.为目标对象的属性设置计算好的属性值
                            int animatorValue = (int)animation.getAnimatedValue();
                            LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) scrollView.getLayoutParams();
                            marginLayoutParams.bottomMargin = animatorValue;
                            scrollView.setLayoutParams(marginLayoutParams);
                            if(animatorValue==scrollViewBottom){
                                if((DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-Y)<(height+bottomLinearLayout.getHeight()) &&RawY>(DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-bottomLinearLayout.getHeight())){
                                    scrollView.smoothScrollBy(0,   height+bottomLinearLayout.getHeight()-(DisplayUtils.getWindowHeight(CoreApplication.newInstance().getBaseContext())-Y));
                                    content.setFocusable(true);
                                    content.setFocusableInTouchMode(true);
                                    content.requestFocus();
                                }
                            }
                        }
                    });
                    scrollViewAnimator.setDuration(230);
                    scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
                    scrollViewAnimator.setTarget(scrollView);
                    scrollViewAnimator.start();
                }

                CountDownTimer timer=new CountDownTimer(150,10) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        hideToolsContainer(toolsContainer);
                    }
                };
                timer.start();
            }

            @Override
            public void keyBoardHide(int height) {
                ObjectAnimator animator=ObjectAnimator.ofFloat(bottomLinearLayout,"translationY",-height,0);
                animator.setDuration(180);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();

                scrollViewAnimator=ValueAnimator.ofInt(scrollViewBottom,bottomLinearLayout.getHeight());
                scrollViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 3.为目标对象的属性设置计算好的属性值
                        int animatorValue = (int)animation.getAnimatedValue();
                        LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) scrollView.getLayoutParams();
                        marginLayoutParams.bottomMargin = animatorValue;
                        scrollView.setLayoutParams(marginLayoutParams);
                    }
                });
                scrollViewAnimator.setDuration(230);
                scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
                scrollViewAnimator.setTarget(scrollView);
                scrollViewAnimator.start();
            }
        };
        softKeyBoardListener= SoftKeyBoardListener.setListener(getActivity().getWindow(),onSoftKeyBoardChangeListener);

        setLViewOnclickListener(leftView);
        setRViewOnclickListener(rightView);
    }

    private void setLViewOnclickListener(View view){
        photographBtn= (LinearLayout) view.findViewById(R.id.photograph);
        photoListBtn= (LinearLayout) view.findViewById(R.id.photo_list);
        photographBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                File vFile = new File( CoreApplication.newInstance().getDir());
                if(thinkerBean.getImgUrl()==null){
                    thinkerBean.setImgUrl(CoreApplication.newInstance().getDir()+"/"+new Date().getTime()+".jpg");
                }else{
                    thinkerBean.setImgUrl(thinkerBean.getImgUrl()+","+CoreApplication.newInstance().getDir()+"/"+new Date().getTime()+".jpg");
                }
               String imgUrl=thinkerBean.getImgUrl().split(",")[thinkerBean.getImgUrl().split(",").length-1];
               Log.v("imgUrl",imgUrl);
                Log.v("imgUrlBean",thinkerBean.getImgUrl());
                //如果状态不是mounted，无法读写
                if (!state.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                if(!vFile.exists()) {
                    Log.v("文件夹不存在","创建文件夹");
                    Log.v("addr",CoreApplication.newInstance().getDir());
                    boolean tag=vFile.mkdirs();
                    Log.v("results", String.valueOf(tag));
                }
               //必须确保文件夹路径存在，否则拍照后无法完成回调
                File imgFile=new File(imgUrl);
                Uri uri = FileProvider.getUriForFile(getActivity(),"com.example.chen1.uncom.fileprovider",imgFile);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, TAKE_PHOTO);
                if(toolsContainer.getChildCount()!=0){
                    toolsContainer.setVisibility(View.VISIBLE);
                    toolsContainer.startAnimation(hideAnimation);
                    CountDownTimer timer=new CountDownTimer(100,10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            toolsContainer.removeAllViews();
                        }
                    };
                    timer.start();
                }
            }
        });

        photoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPhotos.createAlbum(getActivity(), true, GlideEngine.getInstance())//参数说明：最大可选数，默认1//参数说明：上下文，是否显示相机按钮，图片加载引擎实现(ImageEngine说明)
                        .setCount(9) .setFileProviderAuthority("com.example.chen1.uncom.fileprovider")
                        .start(CoreApplication.WRITE_THINKE_FRAGMENT);
                if(toolsContainer.getChildCount()!=0){
                    toolsContainer.setVisibility(View.VISIBLE);
                    toolsContainer.startAnimation(hideAnimation);
                    CountDownTimer timer=new CountDownTimer(100,10) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            toolsContainer.removeAllViews();
                        }
                    };
                    timer.start();
                }
            }
        });
    }


    private void setRViewOnclickListener(View view){

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden==false){
            ((MainActivity)getActivity()).unBindDrawer();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imgUrl=thinkerBean.getImgUrl().split(",")[thinkerBean.getImgUrl().split(",").length-1];
        Log.v("获取照片_1",resultCode+TAKE_PHOTO+""+RESULT_OK);
        Log.v("获取照片_0",imgUrl);
        Log.v("resultCode", String.valueOf(resultCode));
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Log.v("有图像返回","success");
                    Flowable.just(imgUrl)
                            .map(new Function<String,File >() {
                                @Override
                                public File apply(@NonNull String list) throws Exception {
                                    // 同步方法直接返回压缩后的文件
                                    return  Luban.with(CoreApplication.newInstance().getBaseContext()).load(list).get(list);
                                }
                            })
                            .subscribe(new Consumer<File>() {
                                @Override
                                public void accept(final File file) throws Exception {
                                    if(thinkerBean.getImgCacheUrl()==null){
                                        thinkerBean.setImgCacheUrl(file.getAbsolutePath());
                                    }else{
                                        thinkerBean.setImgCacheUrl(thinkerBean.getImgCacheUrl()+","+file.getAbsolutePath());
                                    }
                                    photoAdatper.addImageView(file.getAbsolutePath());
                                    changePhoto=true;
                                    if(photoContainer.getVisibility()==View.GONE){
                                        photoContainer.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                    hideToolsContainer(toolsContainer);
                }else{
                    Log.v("无图像返回","successs");
                    File file = new File(imgUrl);
                    if(file.exists() && file.isFile()){
                        file.delete();
                    }
                    changePhoto=true;
                    thinkerBean.setImgUrl(thinkerBean.getImgUrl().replace(","+imgUrl,""));
                    Log.v("removeUrl",thinkerBean.getImgUrl().toString());
                    Log.v("removeCacheUrl",thinkerBean.getImgCacheUrl().toString());
                }
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        fragment=null;
        thinkerBean=null;
        softKeyBoardListener.unLink(1);
        asynLoadImageUtils=null;
        EventBus.getDefault().unregister(this);
        RefWatcher refWatcher = CoreApplication.getRefWatcher(CoreApplication.newInstance().getBaseContext());
        refWatcher.watch(this);
    }

    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getActivity().getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return screenHeight - rect.bottom-getSoftButtonsBarHeight()!=0 ;
    }


    /**
     * 底部虚拟按键栏的高度
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


    private void showSoftInput(EditText mEditText){
        InputMethodManager  inputManager =
                (InputMethodManager)CoreApplication.newInstance().getBaseContext().getSystemService(CoreApplication.newInstance().getBaseContext().INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mEditText, 0);
    }


    public void hideToolsContainer(final LinearLayout toolsContainer){
        if(toolsContainer.getVisibility()==View.VISIBLE){
            toolsContainer.setVisibility(View.VISIBLE);
            toolsContainer.startAnimation(hideAnimation);
            CountDownTimer timer=new CountDownTimer(180,10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    toolsContainer.removeAllViews();
                }
            };
            timer.start();
        }
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        params.height = contentView.getHeight();
        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed() {
        ((LinearLayout.LayoutParams) contentView.getLayoutParams()).weight = 1.0F;
    }

    @Override
    public boolean onBackPressed() {
        saveBean();
        getFragmentManager().popBackStack();
        if(getTargetFragment() instanceof SetPageMainFragment){
            CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));
        }else{
            getTargetFragment().getView().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));

        }
        return true;
    }


    public void saveBean(){
        ThinkerBeanDao thinkerBeanDao= BeanDaoManager.getInstance().getDaoSession().getThinkerBeanDao();
        CoreApplication.newInstance().setDisPlayType(false);
         //param type 1 插入了新数据 0 更新数据 2 删除数据
        if (thinkerBean!=null && (photoContainer.getVisibility()!=View.GONE &&photoContainer.getChildCount()>0 )|| title.getText().length()!=0 || content.getText().length()!=0) {
            if(thinkerBean!=null && null==thinkerBean.getCreateTime()){
                thinkerBean.setAuthorId(CoreApplication.newInstance().getUser_id());
                thinkerBean.setTitle(title.getText().toString());
                thinkerBean.setContent(content.getText().toString());
                thinkerBean.setChangeTime(new Date());
                thinkerBean.setCreateTime(new Date());
                Log.v("insert thinkBean","success");
                thinkerBeanDao.insert(thinkerBean);
                EventBus.getDefault().post(new ThinkMessage(thinkerBean,StateCode.THINKER_BEAN_INSERT));
                if(toMain==1){
                    Log.v("update thinkBean","main add");
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,1));
                }else{
                    Log.v("update thinkBean","main update");
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,3));
                }
            }else{
                if((thinkerBean.getTitle()==null && title.getText().length()!=0)||(thinkerBean.getTitle()!=null && title.getText().length()==0)||(thinkerBean.getTitle()!=null && title.getText().length()!=0 && thinkerBean.getTitle().equals(title.getText().toString())==false)){
                    Log.v("update thinkBean","title");
                    thinkerBean.setTitle(title.getText().toString());
                    thinkerBean.setChangeTime(new Date());
                    thinkerBeanDao.update(thinkerBean);
                    EventBus.getDefault().post(new ThinkMessage(thinkerBean,StateCode.THINKER_BEAN_UPDATE));
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,0));
                }

                if((thinkerBean.getContent()==null && content.getText().length()!=0)||(thinkerBean.getContent()!=null && thinkerBean.getContent().length()>0 && content.getText().length()==0)||(thinkerBean.getContent()!=null && content.getText().length()!=0 && thinkerBean.getContent().equals(content.getText().toString())==false)){
                    Log.v("update thinkBean","content");
                    Log.v("bean content", String.valueOf(thinkerBean.getContent().length()));
                    Log.v("content", String.valueOf(content.getText().length()));
                    thinkerBean.setContent(content.getText().toString());
                    thinkerBean.setChangeTime(new Date());
                    thinkerBeanDao.update(thinkerBean);
                    EventBus.getDefault().post(new ThinkMessage(thinkerBean,StateCode.THINKER_BEAN_UPDATE));
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,0));
                }
                if(changePhoto==true){
                    Log.v("update thinkBean","photo");
                    thinkerBean.setChangeTime(new Date());
                    thinkerBeanDao.update(thinkerBean);
                    EventBus.getDefault().post(new ThinkMessage(thinkerBean,StateCode.THINKER_BEAN_UPDATE));
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,0));
                }

                if(changeColor==true){
                    Log.v("update thinkBean","color");
                    thinkerBean.setChangeTime(new Date());
                    thinkerBeanDao.update(thinkerBean);
                    EventBus.getDefault().post(new ThinkMessage(thinkerBean,StateCode.THINKER_BEAN_UPDATE));
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,0));
                }
                if(toMain==1){
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,1));
                }else if(toMain==-1){
                    EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,-1));
                }
                if(toMain!=0){
                    Log.v("toMin","true");
                    thinkerBeanDao.update(thinkerBean);
                }

            }
        }
        if((thinkerBean!=null && (photoContainer.getVisibility()==View.GONE || photoContainer.getChildCount()==0 ) && (title.getText().length()==0) && content.getText().length()==0)){
            Log.v("delete","thinkBean");
            try {
                thinkerBeanDao.delete(thinkerBean);
            }catch (Exception e){

            }
            EventBus.getDefault().post(new ThinkMessage(thinkerBean,StateCode.THINKER_BEAN_DELETE));
            EventBus.getDefault().post(new SetMessage(thinkerBean, StateCode.THINKER_BEAN,-1));
        }
    }


    private double distance(float x1,float y1,float x2,float y2){
        float x=0,y=0;
        if(x1 < x2){
            x=(x2-x1)*(x2-x1);
        }else{
            x=(x1-x2)*(x1-x2);
        }
        if (y1 < y2) {
            y=(y2-y1)*(y2-y1);
        }else{
            y=(y1-y2)*(y1-y2);
        }
        Log.v("length:", String.valueOf(Math.sqrt(x+y)));
        return Math.sqrt(x+y);
    }



    private ArrayList<String> parseToArrayList(ArrayList<ArrayList<String>> list){
        ArrayList<String> data=new ArrayList<>();
        for (ArrayList<String> parm: list) {
            for (String str :parm){
                data.add(str);
            }
        }
        return data;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    public AsynLoadImageUtils getAsynLoadImageUtils() {
        return asynLoadImageUtils;
    }

    public void setAsynLoadImageUtils(AsynLoadImageUtils asynLoadImageUtils) {
        this.asynLoadImageUtils = asynLoadImageUtils;
    }

    public EditText getTitle() {
        return title;
    }

    public void setTitle(EditText title) {
        this.title = title;
    }

    public EditText getContent() {
        return content;
    }

    public void setContent(EditText content) {
        this.content = content;
    }

    public ThinkerBean getThinkerBean() {
        return thinkerBean;
    }

    public void setThinkerBean(ThinkerBean thinkerBean) {
        this.thinkerBean = thinkerBean;
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void albumResult(EventMessage eventMessage) {
        Log.v("results", String.valueOf(eventMessage.getList()));
        updateImage(eventMessage.getList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncEventBus(MessageEvent messageEvent){
      changePhoto=true;
      if(thinkerBean.getImgUrl()!=null){
          thinkerBean.setImgUrl(thinkerBean.getImgUrl()+","+parseToString(messageEvent.getList()));
      }else{
          thinkerBean.setImgUrl(parseToString(messageEvent.getList()));
        }
        changePhoto=true;
        if(photoContainer.getVisibility()==View.GONE){
            photoContainer.setVisibility(View.VISIBLE);
        }
        for(String url :messageEvent.getList()){
            photoAdatper.addImageView(url);
        }
      Log.v("thinkerBeanLength",thinkerBean.getImgUrl());
    }
    private void updateImage(ArrayList<String> data){
            Log.v("update","success");
            if(data.size()==0){
                Log.v("update","null");
                thinkerBean.setImgCacheUrl(null);
                thinkerBean.setImgOnlineUrl(null);
            }
            photoAdatper.setImgUrl(data);
            thinkerBean.setImgUrl(parseToString(data));
            changePhoto=true;


    }

    private String parseToString(List<String> list){
        if(list.size()==0){
            return null;
        }
        String result="";
        for (int i=0;i<list.size();i++){
            result+=list.get(i);
            if(i<(list.size()-1)){
                result+=",";
            }
        }
        return  result;
    }
}
