package com.example.chen1.uncom.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;


/**
 * 滑动Layout
 * Created by chen1 on 2018/2/24.
 */

public class SwipLayout extends FrameLayout {
    private int  targetWidth=300;
    private Runnable runnable=null;
    private View parentView;
    private boolean parseBackAnimator=false;
    private  ValueAnimator   animator;
    private ValueAnimator    selfAnimator;
    private boolean canSwip=true;
    private Fragment fragment;
    private int invalidateType;
    private  int animatorValue;
    private boolean  pop=false;
    private int startX;

    private ViewDragHelper mDragHelper=ViewDragHelper.create(this,new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            boolean dragEnable = mDragHelper.isEdgeTouched(ViewDragHelper.EDGE_LEFT);
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if(parentView!=null ){
                parentView.scrollTo(parentView.getWidth()-left-280,0);
            }

            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            canSwip=false;
            parseBackAnimator=true;
            if(xvel<=targetWidth){
                invalidateType=1;
                mDragHelper.settleCapturedViewAt(0,0);
                animator.setIntValues(parentView.getScrollX(),parentView.getWidth()-280);
                startX=parentView.getScrollX();
                animatorValue=parentView.getWidth()-280;
                animator.setTarget(parentView);
                animator.start();
                invalidate();
            }else{
                invalidateType=2;
                mDragHelper.smoothSlideViewTo(getChildAt(0),getWidth(),0);
                animator.setIntValues(parentView.getScrollX(),0);
                startX=parentView.getScrollX();
                animatorValue=0;
                animator.setTarget(parentView);
                animator.start();
                if(runnable!=null){
                    runnable.run();
                }
                invalidate();
            }
        }
    });

    final int deltax=100;
    private void parseAnimator(){
        animator= ValueAnimator.ofInt(0,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int)animation.getAnimatedValue();
                parentView.scrollTo(animatorValue,0);
                if(animatorValue==SwipLayout.this.animatorValue){
                    canSwip=true;
                    parentView.setScrollX(0);
                }
            }
        });
        animator.setDuration(280);
        animator.setInterpolator(new DecelerateInterpolator());

        selfAnimator=ValueAnimator.ofInt(0,0);

    }

    public SwipLayout(@NonNull Context context) {
        super(context);
        parseAnimator();
    }

    public SwipLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parseAnimator();
    }

    public SwipLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAnimator();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwipLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAnimator();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x=ev.getX();
        if(x<30){
            mDragHelper.processTouchEvent(ev);
            return super.onInterceptTouchEvent(ev);
        }
        return  mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mDragHelper.getViewDragState()==0 && parseBackAnimator==true && invalidateType==2){
            //说明已完成滑动
            invalidateType=3;
            if(fragment!=null){
                SwipLayout.this.getChildAt(0).setVisibility(View.GONE);
                SwipLayout.this.getChildAt(0).setX(0);
                SwipLayout.this.getChildAt(0).setTranslationX(0);
                if(pop==true){
                    fragment.getFragmentManager().popBackStack();
                }else{
                    fragment.getFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
                }
            }

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    SwipLayout.this.getChildAt(0).setVisibility(View.VISIBLE);
                }
            },1);
            parseBackAnimator=false;

        }
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public SwipLayout setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
        return this;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public SwipLayout setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return  this;
    }



    public View getParentView() {
        return parentView;
    }

    public SwipLayout setParentView(View view) {
        this.parentView = view;
        return this;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public SwipLayout setFragment(Fragment fragment) {
        this.fragment = fragment;
        return  this;
    }

    public boolean isPop() {
        return pop;
    }

    public SwipLayout setPop(boolean pop) {
        this.pop = pop;
        return  this;
    }
}
