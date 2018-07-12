package com.example.chen1.uncom.relationDynamics;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.expression.SoftKeyBoardListener;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.GlideCircleTransform;
import com.example.chen1.uncom.utils.SpanStringUtils;
import com.example.chen1.uncom.utils.SwipLayout;

import org.json.JSONObject;


public class PersonDynamicComment extends Fragment implements FragmentBackHandler{

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private PersonDynamicsBean bean;
    private SwipLayout layout;
    private View view;
    private ImageView userImg;
    private RecyclerView commentRecycler;
    private AppCompatImageView sendIcon;
    private EditText editText;
    private boolean iconType=false;//false 灰色 true  白色
    private LinearLayout editLayout;
    private InputMethodManager mInputManager;
    private FrameLayout dynamic;
    private ObjectAnimator objectAnimator;
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    private SoftKeyBoardListener softKeyBoardListener;
    private ColorMatrixColorFilter colorFilter;
    private ImageView dynamicHead;
    private TextView username;
    private TextView content;
    private SpanStringUtils spanStringUtils;
    private FragmentTransaction fragmentTransaction;

    public PersonDynamicComment() {
        spanStringUtils=new SpanStringUtils();
        // Required empty public constructor
    }

    public static PersonDynamicComment newInstance(PersonDynamicsBean bean) {
        PersonDynamicComment fragment = new PersonDynamicComment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bean = getArguments().getParcelable(ARG_PARAM1);
        }
        mInputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        JSONObject jsonObject=new JSONObject();
        // Inflate the layout for this fragment      ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout=new SwipLayout(getContext());
        layout.setLayoutParams(params);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        colorFilter = new ColorMatrixColorFilter(matrix);
        layout.setBackgroundColor(Color.TRANSPARENT);
        if(view==null){
            view= inflater.inflate(R.layout.fragment_person_dynamic_comment, container, false);
        }
        layout.removeAllViews();
        layout.addView(view);
        layout.setRunnable(new Runnable() {
            @Override
            public void run() {
                backParse(true);
            }
        }).setParentView(CoreApplication.newInstance().getRoot()).setFragment(PersonDynamicComment.this).setPop(true).setRunnable(new Runnable() {
            @Override
            public void run() {
                if(isSoftInputShown()){
                    Log.v("hide softInput","start");
                    hideSoftInput();
                }
            }
        });
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userImg= (ImageView) view.findViewById(R.id.user_img);
        dynamic= (FrameLayout) view.findViewById(R.id.dynamic);
        content= (TextView) view.findViewById(R.id.text);
        commentRecycler= (RecyclerView) view.findViewById(R.id.commentRecycler);
        editText= (EditText) view.findViewById(R.id.edit);
        editLayout= (LinearLayout) view.findViewById(R.id.edit_layout);
        sendIcon= (AppCompatImageView) view.findViewById(R.id.send_icon);
        showSoftInputFromWindow(getActivity(),editText);
        if(bean.getContent()!=null && bean.getContent().replaceAll(" ","").equals("")==false){
            content.setVisibility(View.VISIBLE);
            dynamic.setVisibility(View.VISIBLE);
            dynamicHead= (ImageView) view.findViewById(R.id.head_img);
            username= (TextView) view.findViewById(R.id.username_0);
            SpannableString spannableString= spanStringUtils.getEmotionContent(1,CoreApplication.newInstance().getApplicationContext(),content,bean.getContent());
            content.setText(spannableString);
            username.setText(bean.getUsername());
            GlideApp.with(this)
                    .load(bean.getUser_photo()).transition(new DrawableTransitionOptions().crossFade())
                    .transform(new GlideCircleTransform())
                    .into(dynamicHead);
        }else{
            content.setVisibility(View.GONE);
            dynamic.setVisibility(View.GONE);
        }
        GlideApp.with(this)
                .load(CoreApplication.newInstance().getUserBean().getHeader_pic()).transition(new DrawableTransitionOptions().crossFade())
                .transform(new GlideCircleTransform())
                .into(userImg);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp =s.toString();
                temp=temp.replaceAll(" ","");
                if(temp.equals("") && iconType==true){
                    sendIcon.setColorFilter(colorFilter);
                    iconType=false;
                }else if(iconType==false){
                    sendIcon.setColorFilter(Color.WHITE);
                    iconType=true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        onSoftKeyBoardChangeListener=new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                if(editLayout.getTranslationY()==0){
                    ObjectAnimator animator=ObjectAnimator.ofFloat(editLayout,"translationY",-height+100,-height);
                    animator.setDuration(180);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                }
            }

            @Override
            public void keyBoardHide(int height) {
                if(editLayout.getTranslationY()!=0){
                    ObjectAnimator animator=ObjectAnimator.ofFloat(editLayout,"translationY",-height,0);
                    animator.setDuration(180);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                }
            }
        };
        softKeyBoardListener=SoftKeyBoardListener.setListener(getActivity().getWindow(), onSoftKeyBoardChangeListener);

        sendIcon.setColorFilter(colorFilter);
        iconType=false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        softKeyBoardListener.unLink(1);
    }

    private void backParse(boolean swip){
        if(swip==false){
            getFragmentManager().popBackStack();
        }

    }
    @Override
    public boolean onBackPressed() {
        backParse(false);
        return  true;
    }
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm=(InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
    }
    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        Log.v("hideSoftInput","true");
        mInputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
}
