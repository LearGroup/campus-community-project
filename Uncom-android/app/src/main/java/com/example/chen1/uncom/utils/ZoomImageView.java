package com.example.chen1.uncom.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;


/**
 * Created by caobotao on 15/12/10.
 */
@SuppressLint("AppCompatCustomView")
public class ZoomImageView extends ImageView implements OnGlobalLayoutListener, OnScaleGestureListener, OnTouchListener {
    private boolean mOnce;
    //初始化时所发的比例
    private float mInitScale;
    //双击后放大的比例
    private float mMidScale;
    //可以放大的最大比例
    private float mMaxScale;

    private Matrix mMatrix;

    //通过ScaleGestureDetector可以获取到多点触控的缩放比例
    private ScaleGestureDetector mScaleGestureDetector;

    //--------------自由移动-------------

    //记录上一次触控点的数量
    private int mLastPointerCount;

    //上次多点触控的中心点位置
    private float mLastX;
    private float mLastY;

    private int MTouchSlop;
    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    //-------------双击放大与缩小----------
    private GestureDetector mGestureDetector;

    //是否正在进行缓慢缩放
    private boolean isAutoScale;


    public ZoomImageView(Context context) {
        this(context,null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("ZoomImageView构造方法","ZoomImageView构造方法");
        mMatrix = new Matrix();
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context,this);
        setOnTouchListener(this);
        MTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //如果此刻正在进行自动的缓慢缩放,则禁止用户双击缩放
                if (isAutoScale){
                    return true;
                }

                float x = e.getX();
                float y = e.getY();

                if (getScale() < mMidScale) {
//                    mMatrix.postScale(mMidScale / getScale(),mMidScale / getScale(),x,y);
//                    setImageMatrix(mMatrix);
                    postDelayed(new AutoScaleRunnable(mMidScale,x,y),16);
                }
                else {
//                    mMatrix.postScale(mInitScale / getScale(),mInitScale / getScale(),x,y);
//                    setImageMatrix(mMatrix);
                    postDelayed(new AutoScaleRunnable(mInitScale,getWidth()/2,getHeight()/2),16);

                }
                isAutoScale = true;
                return true;
            }
        });
    }


    //当此view附加到窗体上时调用该方法
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //添加全局布局监听
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    //当此view从窗体上消除时调用该方法
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除全局布局监听
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     *  获取ImageView加载完成的图片
     */
    @Override
    public void onGlobalLayout() {
        //由于onGlobalLayout可能会被调用多次,我们使用一个标志mOnce来判断是否已经调用
        if (!mOnce) {
            //获得此View的宽和高
            float width = getWidth();
            float height = getHeight();
            Log.i("onGlobalLayout Width",width+"");
            Log.i("onGlobalLayout height",height+"");
            //得到图片及其宽高
            Drawable d = getDrawable();
            if (d == null) {
                return;
            }
            float dw = d.getIntrinsicWidth();
            float dh = d.getIntrinsicHeight();
            Log.i("onGlobalLayout dw",dw+"");
            Log.i("onGlobalLayout dh",dh+"");
            /**
             *  比较图片的尺寸与此View的尺寸,如果图片的尺寸比此View的尺寸大,
             *  则缩放,反之,则放大,以达到与此View尺寸一致
             */

            //缩放比例
            float scale = 1.0f;

            //如果图片宽度比此View宽度大,且高度比此View小,则以宽度的比例缩小
            if (dw > width && dh < height) {
                scale = width / dw;
            }

            //如果图片宽度比此View宽度小,且高度比此View大,则以高度的比例缩小
            if (dw < width && dh > height) {
                scale = height / dh;
            }

            //如果图片宽度比此View宽度大,且高度比此View大,则以高度的比例与宽度的比例中大的一者缩小
            if ( (dw > width && dh > height) || (dw < width && dh < height) ) {
                scale = Math.max(width / dw,height/ dh);
            }

            //如果图片宽度比此View宽度小,且高度比此View小,则以高度的比例与宽度的比例中小的一者放大
            if (dw < width && dh < height) {
                scale = Math.min(width / dw,height / dh);
            }

            //分别设置初始化时的比例,双击后的比例,可以放大的最大比例
            mInitScale = scale;
            mMidScale = scale * 2;
            mMaxScale = scale * 4;
            //将图片移动到此View的中心
            float dx = width / 2 - dw / 2;//需要移动的x方向的距离
            float dy = height / 2 - dh / 2;//需要移动的y方向的距离

            //设置平移
            mMatrix.postTranslate(dx,dy);
            //设置缩放
            mMatrix.postScale(mInitScale,mInitScale,width / 2,height / 2 );
            setImageMatrix(mMatrix);

            mOnce = true;
        }
    }

    //获取当前图片的缩放比例
    public float getScale(){
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 缩放区间:[initScale,maxScale]
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //获取当前图片的缩放比例
        float scale = getScale();
        //多点触控缩放比例
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null){
            return true;
        }

        //进行缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
            //缩放
            mMatrix.postScale(scaleFactor,scaleFactor,detector.getFocusX(),detector.getFocusY());
            //在缩放的时候进行边界以及位置的控制
            checkBorderAndCenterWhenScale();

            setImageMatrix(mMatrix);
        }

        return true;
    }

    //在缩放的时候进行边界以及位置的控制
    private void checkBorderAndCenterWhenScale() {
        RectF rectf = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        //缩放时进行边界检测,防止出现留白
        if (rectf.width() >= width) {
            if (rectf.left > 0) {
                deltaX = -rectf.left;
            }
            if (rectf.right < width) {
                deltaX = width - rectf.right;
            }
        }
        if (rectf.height() >= height) {
            if (rectf.top > 0) {
                deltaY = -rectf.top;
            }
            if (rectf.bottom < height) {
                deltaY = height - rectf.bottom;
            }
        }

        //如果宽度或者高度小于控件的宽度或高度,则让其居中
        if (rectf.width() < width) {
            deltaX = width / 2f - rectf.right + rectf.width() / 2f;
        }
        if (rectf.height() < height) {
            deltaY = height / 2f - rectf.bottom + rectf.height() / 2f;
        }

        mMatrix.postTranslate(deltaX,deltaY);

    }

    //获得图片缩放后的宽高,以及top,bottom,left,right
    private RectF getMatrixRectF(){
        Matrix matrix = mMatrix;
        RectF rectf = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectf.set(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
            matrix.mapRect(rectf);
        }
        return rectf;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();

        //累加x和y方向的距离
        for (int i = 0; i < pointerCount; i++){
            x += event.getX(i);
            y += event.getY(i);
        }

        //获得中心点位置
        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        mLastPointerCount = pointerCount;

        RectF rectF = getMatrixRectF();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                /**
                 * 此View在ViewPager中使用时,图片放大后自由移动的事件会与
                 * ViewPager的左右切换的事件发生冲突,导致图片放大后如果左右
                 * 移动时不能自由移动图片,而是使ViewPager切换图片.这是由于事
                 * 件分发时外层的优先级比内层的高,使用下列判断可以解决
                 */
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                //偏移量
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag){
                    isCanDrag = isMoveAction(dx,dy);
                }
                if (isCanDrag) {
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = true;
                        isCheckTopAndBottom = true;

                        //如果宽度小于控件的宽度,不允许横向移动
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }

                        //如果高度小于控件的高度,不允许纵向移动
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        mMatrix.postTranslate(dx,dy);
                        //当自由移动时进行边界检查,防止留白
                        checkBorderWhenTranslate();
                        setImageMatrix(mMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mLastPointerCount = 0;
                break;
        }

        return true;
    }

    //当自由移动时进行边界检查,防止留白
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width -rectF.right;
        }

        mMatrix.postTranslate(deltaX,deltaY);


    }

    //判断是否足以触发MOVE事件
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > MTouchSlop;
    }


    //实现缓慢缩放
    private class AutoScaleRunnable implements Runnable{
        //缩放的目标比例
        private float mTargetScale;
        //缩放的中心点
        private float x;
        private float y;

        private final float BIGGER = 1.07f;
        private final float SMALLER = 0.93f;

        //临时缩放比例
        private float tempScale;

        public AutoScaleRunnable(float mTargetScale,float x,float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tempScale = BIGGER;
            }
            if (getScale() > mTargetScale) {
                tempScale = SMALLER;
            }
        }

        @Override
        public void run() {
            //进行缩放
            mMatrix.postScale(tempScale,tempScale,x,y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);

            float currentScale = getScale();
            //如果可以放大或者缩小
            if ((tempScale > 1.0f && currentScale < mTargetScale) || (tempScale < 1.0f && currentScale > mTargetScale) ){
                postDelayed(this,16);
            }
            //设置为目标缩放比例
            else {
                float scale = mTargetScale / currentScale;
                mMatrix.postScale(scale,scale,x,y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mMatrix);
                isAutoScale = false;
            }
        }
    }
}
