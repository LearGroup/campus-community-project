package com.example.chen1.uncom.relationDynamics;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.DaoMaster;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.bean.PersonDynamicsBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.ThinkerBean;
import com.example.chen1.uncom.expression.ChatExpressionTypePageSwitchAdapter;
import com.example.chen1.uncom.expression.GrallyAdapter;
import com.example.chen1.uncom.expression.SoftKeyBoardListener;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.me.MeDetailPage;
import com.example.chen1.uncom.me.MeInfoEdit;
import com.example.chen1.uncom.previewAlbum.AlbumEdit;
import com.example.chen1.uncom.previewAlbum.AlbumEditCallBack;
import com.example.chen1.uncom.previewAlbum.EventMessage;
import com.example.chen1.uncom.thinker.PhotoAdapter;
import com.example.chen1.uncom.thinker.ThinkerMainFragment;
import com.example.chen1.uncom.thinker.WriteThinkFragment;
import com.example.chen1.uncom.utils.AsynLoadImageUtils;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.DisplayUtils;
import com.example.chen1.uncom.utils.GestureListener;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.GlideEngine;
import com.example.chen1.uncom.utils.MessageEvent;
import com.example.chen1.uncom.utils.OssService;
import com.example.chen1.uncom.utils.SessionStoreJsonRequest;
import com.example.chen1.uncom.utils.StateCode;
import com.example.chen1.uncom.utils.SwipLayout;
import com.example.chen1.uncom.utils.UIDisplayer;
import com.huantansheng.easyphotos.EasyPhotos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.flowable.FlowableAll;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;
import static com.example.chen1.uncom.thinker.WriteThinkFragment.TAKE_PHOTO;

public class WriteDynamics extends Fragment implements FragmentBackHandler ,AlbumEditCallBack{

    private boolean changePhoto=false;
    private AppCompatImageView backBtn;
    private LinearLayout bottomContainer;
    private EditText text;
    private String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private String domain="http://uncomapp.oss-cn-beijing.aliyuncs.com";
    private RecyclerView photoContainer;
    private int REQUEST_CODE_CHOOSE;
    private AppCompatImageView cameraBtn;
    private List<Integer> list = new ArrayList<>();
    private String imgUrl;
    private int softInputHeight;
    private RecyclerView expressionMenuType;
    private LinearLayout content;
    private ViewPager expressionViewPager;
    private List<String> mSelected = new ArrayList<>();
    private int window_height;
    private int titleRawY=0;
    private int titleY;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout ExpressionLinearLayout;
    private ValueAnimator scrollViewAnimator;
    private int scrollViewBottom=0;
    private PersonDynamicsBeanDao dao;
    private Query query;
    private Flowable<PersonDynamicsBean> uploadDynamics;
    private AppCompatImageView sendBtn;
    private AppBarLayout appbarLayout;
    private QueryBuilder queryBuilder;
    private PhotoAdapter photoAdatper;
    private ScrollView scrollView;
    private AppCompatImageView photoListBtn;
    private AsynLoadImageUtils asynLoadImageUtils;
    private GestureDetector gestureDetector;
    private float pressedX;
    private InputMethodManager mInputManager;
    private float pressedY;
    private PersonDynamicsBean bean;
    private SoftKeyBoardListener softKeyBoardListener;
    private AppCompatImageView expressionBtn;
    private FragmentTransaction fragmentTransaction;
    private int  scrollOriginBottom;
    private SwipLayout swipLayout;
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    public WriteDynamics() {
        // Required empty public constructor
    }

    public static WriteDynamics newInstance() {
        WriteDynamics fragment = new WriteDynamics();
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(asynLoadImageUtils==null){
            asynLoadImageUtils=new AsynLoadImageUtils();
        }


        uploadDynamics=Flowable.create(new FlowableOnSubscribe<PersonDynamicsBean>() {

            @Override
            public void subscribe(final FlowableEmitter<PersonDynamicsBean> e) throws Exception {
                Log.v("uploadDynamics","begin");
                final List<String> arrys= Arrays.asList(bean.getPhoto_list().split(","));
                Log.v("Arraylist", String.valueOf(arrys.size()));
                CoreApplication.newInstance().getUploadCertificate();
                if(CoreApplication.newInstance().checkUploadCertificate()==false){
                    Log.v("checkUploadCertificate","false");
                    SessionStoreJsonRequest sessionStoreJsonRequest = new SessionStoreJsonRequest("http://47.95.0.73:7080",
                            null, new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String stsJson;
                            stsJson = response.toString();
                            JSONObject jsonObjs = null;
                            Log.v("UploadCertificate","response");
                            try {
                                jsonObjs = new JSONObject(stsJson);
                                String ak = jsonObjs.getString("AccessKeyId");
                                String sk = jsonObjs.getString("AccessKeySecret");
                                String token = jsonObjs.getString("SecurityToken");
                                String expiration = jsonObjs.getString("Expiration");
                                CoreApplication.newInstance().setUploadCertificate(ak,sk,token,expiration);
                                CoreApplication.newInstance().setUploadTime(new Date());
                                OSSFederationToken federationToken= new OSSFederationToken(ak, sk, token, expiration);
                                uploadImage(federationToken,arrys,bean,e);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("SecurityToken", String.valueOf(error));
                            Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                                    "网络错误", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                    CoreApplication.newInstance().getRequestQueue().add(sessionStoreJsonRequest);
                }else{
                    Log.v("checkUploadCertificate","true");
                    OSSFederationToken federationToken=new OSSFederationToken(CoreApplication.newInstance().ak,CoreApplication.newInstance().sk,CoreApplication.newInstance().token,CoreApplication.newInstance().expiration);
                    Log.v("federationToken","begin");
                    uploadImage(federationToken, arrys,bean,e);
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io());

        dao=BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
        queryBuilder=dao.queryBuilder();
        bean= (PersonDynamicsBean) queryBuilder.where(PersonDynamicsBeanDao.Properties.Edit.eq(1)).unique();
        mInputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(bean==null ||bean.getPhoto_list()==null){
            photoAdatper=new PhotoAdapter(CoreApplication.newInstance().getBaseContext(),new ArrayList<ArrayList<String>>(),this);
        }else if(bean.getPhoto_list()!=null){
            photoAdatper=new PhotoAdapter(CoreApplication.newInstance().getBaseContext(),asynLoadImageUtils.addImageUrlList(bean.getPhoto_list()),this);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_write_dynamics, container, false);

        ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipLayout=new SwipLayout(getContext());
        swipLayout.setLayoutParams(params);
        swipLayout.setBackgroundColor(Color.TRANSPARENT);
        swipLayout.removeAllViews();
        swipLayout.addView(view);
        swipLayout.setParentView(getTargetFragment().
                getView()).
                setFragment(WriteDynamics.this).
                setPop(true).
                setRunnable(()->{
                    if(isSoftInputShown()){
                        Log.v("hide softInput","start");
                        hideSoftInput();
                    }
                });
        return swipLayout;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        bottomContainer= (LinearLayout) view.findViewById(R.id.bottom_linearlayout);
        sendBtn= (AppCompatImageView) view.findViewById(R.id.send);
        text= (EditText) view.findViewById(R.id.title);
        appbarLayout= (AppBarLayout) view.findViewById(R.id.appbar_layout);
        expressionViewPager= (ViewPager) view.findViewById(R.id.chat_expression_viewpager);
        expressionMenuType= (RecyclerView) view.findViewById(R.id.chat_listmenuitem_view);
        expressionBtn= (AppCompatImageView) view.findViewById(R.id.expression);
        backBtn= (AppCompatImageView) view.findViewById(R.id.back_btn);
        gestureDetector=new GestureDetector(CoreApplication.newInstance().getApplicationContext(),new GestureListener(100,10,getFragmentManager()));
        window_height=DisplayUtils.getWindowHeight(CoreApplication.newInstance().getApplicationContext());
        photoContainer= (RecyclerView) view.findViewById(R.id.photo_container);
        coordinatorLayout= (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        content= (LinearLayout) view.findViewById(R.id.content);
        photoListBtn= (AppCompatImageView) view.findViewById(R.id.photo_list);
        scrollView= (ScrollView) view.findViewById(R.id.scrollView);
        CoordinatorLayout.LayoutParams lp= (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
        scrollOriginBottom=lp.bottomMargin;
        ExpressionLinearLayout= (LinearLayout) view.findViewById(R.id.Expression_LinearLayout);
        showSoftInputFromWindow(getActivity(),text);
        cameraBtn= (AppCompatImageView) view.findViewById(R.id.camera);
        photoContainer= (RecyclerView) view.findViewById(R.id.photo_container);
        photoContainer.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        photoContainer.setHasFixedSize(true);
        photoAdatper.setItemMaxHeight(800);
        photoContainer.setAdapter(photoAdatper);
        photoContainer.setNestedScrollingEnabled(false);
        GrallyAdapter grallyAdapter = new GrallyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoreApplication.newInstance().getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        expressionMenuType.setLayoutManager(linearLayoutManager);
        expressionMenuType.setHasFixedSize(true);
        expressionMenuType.setItemAnimator(new DefaultItemAnimator());
        expressionMenuType.setAdapter(grallyAdapter);
        expressionViewPager.setAdapter(new ChatExpressionTypePageSwitchAdapter(getChildFragmentManager(), list,text));
        expressionViewPager.setCurrentItem(0);
        if(bean!=null && bean.getContent()!=null){
            text.setText(bean.getContent());
        }


        sendBtn.setOnClickListener((v)->{
            Log.v("photoAdatper", String.valueOf(photoAdatper.getItemCount()));
            Log.v("ttext", String.valueOf(text.getText().toString().length()));
            if(photoAdatper.getItemCount()!=0 || text.getText().toString().length()>0){
                Log.v("begin","upload");
                upload();
            }
        });
        backBtn.setOnClickListener((v)->{
            if(bean.getPhoto_list()!=null|| text.getText().length()!=0){
                save();
            }
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            getFragmentManager().popBackStack();
            CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_open_right));

        });
        grallyAdapter.setOnItemClickListener((View v,int position)->{
            expressionViewPager.setCurrentItem(position);
        });
        photoAdatper.setItemOnClickListener((View v, int position, ArrayList<ArrayList<String>> imageUrlList)-> {
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
                fragmentTransaction.add(R.id.drawer_layout, albumEdit, "AlbumEdit").commitAllowingStateLoss();
            }
        });
        text.setOnTouchListener((View v, MotionEvent event)-> {
            titleRawY= (int) event.getY()+v.getTop();
            titleY= (int) event.getRawY();
            Log.v("距离屏幕上方距离", String.valueOf(event.getRawY()));
            Log.v("titleRawY", String.valueOf(titleRawY));
            Log.v("titleRawY", String.valueOf(event.getY()));
            // 解决scrollView中嵌套EditText导致不能上下滑动的问题
            if (canVerticalScroll(text))
                v.getParent().requestDisallowInterceptTouchEvent(true);
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            if (event.getAction() == MotionEvent.ACTION_UP && ExpressionLinearLayout.isShown()) {
                if(ExpressionLinearLayout.isShown()){
                    hideEmotionLayoutFromInput(true);
                }
            }
            return false;
        });

        expressionBtn.setOnClickListener((View v)->{
            if (ExpressionLinearLayout.isShown()) {
                hideEmotionLayout(true);//隐藏表情布局，显示软件盘
            } else {
                if (isSoftInputShown()) {//同上
                    //    lockContentHeight();
                    showEmotionLayout();
                    //    unlockContentHeightDelayed();
                } else {
                    showEmotionLayout();//两者都没显示，直接显示表情布局
                }
            }
        });
        if(bean!=null){
            photoContainer.setVisibility(View.VISIBLE);
        }else{
            photoContainer.setVisibility(View.GONE);
            bean=new PersonDynamicsBean();
        }
        onSoftKeyBoardChangeListener=new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(final int height) {

                if(bottomContainer.getTranslationY()==0){
                    ObjectAnimator animator=ObjectAnimator.ofFloat(bottomContainer,"translationY",-height+100,-height);
                    animator.setDuration(180);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                }
                softInputHeight=height;
                CoordinatorLayout.LayoutParams lp= (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
                Log.v("conent", String.valueOf(content.getHeight()));
                Log.v("text Height", String.valueOf(text.getHeight()));
                Log.v("cycler Height", String.valueOf(photoContainer.getHeight()));

                if( lp.bottomMargin==scrollOriginBottom && window_height-content.getHeight()<(bottomContainer.getHeight()+height)){
                    scrollViewBottom=bottomContainer.getHeight()+height-(window_height-content.getHeight())+scrollOriginBottom;
                    if(scrollViewBottom>(height+bottomContainer.getHeight())){
                        Log.v("max heigt","scueess");
                        scrollViewBottom=height+bottomContainer.getHeight();
                    }

                    scrollViewBottom=height+bottomContainer.getHeight();
                    if((window_height-titleY)>(height+bottomContainer.getHeight())){
                        scrollViewBottom=scrollOriginBottom;
                    }
                    Log.v("scrollViewBottom", String.valueOf(scrollViewBottom));
                    Log.v("scrollOriginBottom", String.valueOf(scrollOriginBottom));
                    scrollViewAnimator= ValueAnimator.ofInt(scrollOriginBottom,scrollViewBottom);
                    scrollViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            // 3.为目标对象的属性设置计算好的属性值
                            int animatorValue = (int)animation.getAnimatedValue();
                            LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) scrollView.getLayoutParams();
                            marginLayoutParams.bottomMargin = animatorValue;
                            scrollView.setLayoutParams(marginLayoutParams);
                            if(animatorValue==scrollViewBottom){
                                scrollView.setSmoothScrollingEnabled(true);
                                if((window_height-titleY)<(height+bottomContainer.getHeight()) &&titleRawY>(window_height-bottomContainer.getHeight())){
                                    Log.v("发生修正", String.valueOf((titleRawY-scrollView.getScrollY())));
                                    scrollView.smoothScrollBy(0,   height+bottomContainer.getHeight()-(window_height-titleY));
                                }
                            }
                        }
                    });
                    scrollView.setSmoothScrollingEnabled(false);
                    scrollViewAnimator.setDuration(230);
                    scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
                    scrollViewAnimator.setTarget(scrollView);
                    scrollViewAnimator.start();
                }
            }

            @Override
            public void keyBoardHide(int height) {
                if(bottomContainer.getTranslationY()!=0){
                    ObjectAnimator animator=ObjectAnimator.ofFloat(bottomContainer,"translationY",-height,0);
                    animator.setDuration(180);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                }

                Log.v("keyBoardHide", String.valueOf(ExpressionLinearLayout.isShown()));
                if(ExpressionLinearLayout.isShown()==false){
                    scrollViewAnimator= ValueAnimator.ofInt(scrollViewBottom,scrollOriginBottom);
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
            }
        };
        softKeyBoardListener= SoftKeyBoardListener.setListener(getActivity().getWindow(),onSoftKeyBoardChangeListener);
        photoListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPhotos.createAlbum(getActivity(), true, GlideEngine.getInstance())//参数说明：最大可选数，默认1//参数说明：上下文，是否显示相机按钮，图片加载引擎实现(ImageEngine说明)
                        .setCount(9) .setFileProviderAuthority("com.example.chen1.uncom.fileprovider")
                        .start(101);
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                File vFile = new File( CoreApplication.newInstance().getDir());
                imgUrl=CoreApplication.newInstance().getDir()+"/"+new Date().getTime()+".jpg";
                //如果状态不是mounted，无法读写
                if (!state.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                if(!vFile.exists()) {
                    boolean tag=vFile.mkdirs();
                }
                //必须确保文件夹路径存在，否则拍照后无法完成回调
                File imgFile=new File(imgUrl);
                Uri uri = FileProvider.getUriForFile(getActivity(),"com.example.chen1.uncom.fileprovider",imgFile);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
    }

    private void upload(){
        save();
        dao=BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
        bean.setEdit(3);
        dao.update(bean);
        EventBus.getDefault().post(new DynamicsMessage(bean, StateCode.PERSON_DYNAMICS_ADD));
        getFragmentManager().popBackStack();
        Log.v("upload","begin");
        if(photoContainer.getChildCount()!=0){
            uploadDynamics.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<PersonDynamicsBean>() {
                @Override
                public void onSubscribe(Subscription s) {
                    s.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(final PersonDynamicsBean bean) {
                    Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                            "图片上传完毕", Toast.LENGTH_LONG);
                    toast.show();
                    Log.v("on","next");
                    bean.setEdit(3);
                    BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao().update(bean);
                    if(CoreApplication.newInstance().getCoreService()==null){
                        CoreApplication.newInstance().startServices();
                    }
                    try {
                        JSONObject jsonObject=new JSONObject(JSON.toJSONString(bean));
                        jsonObject.put("updateList", JSONArray.toJSONString(getPersosnFrendId()));
                        SessionStoreJsonRequest sessionStoreJsonRequest = new SessionStoreJsonRequest(
                                "http://" + CoreApplication.newInstance().IP_ADDR + ":8081/uploadDynamics",
                                jsonObject,
                                new com.android.volley.Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                                                "动态上传完毕 1", Toast.LENGTH_LONG);
                                        toast.show();
                                        bean.setEdit(4);
                                        BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao().update(bean);
                                    }
                                }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.v("SecurityToken", String.valueOf(error));
                                Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                                        "网路错误", Toast.LENGTH_LONG);
                                toast.show();
                                bean.setEdit(3);
                                BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao().update(bean);

                            }
                        });
                        sessionStoreJsonRequest.setRetryPolicy(new DefaultRetryPolicy(30*1000,0,0f));


                        CoreApplication.newInstance().getRequestQueue().add(sessionStoreJsonRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Throwable t) {
                    Log.v("on","onError");
                    Log.v("on",t.getMessage());
                }

                @Override
                public void onComplete() {
                    Log.v("on","onComplete");
                }
            });
        }else{
            try {
                bean.setEdit(4);
                JSONObject jsonObject=new JSONObject(JSON.toJSONString(bean));
                jsonObject.put("updateList", JSONArray.toJSONString(getPersosnFrendId()));
                SessionStoreJsonRequest sessionStoreJsonRequest = new SessionStoreJsonRequest(
                        "http://" + CoreApplication.newInstance().IP_ADDR + ":8081/uploadDynamics",
                        jsonObject,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao().update(bean);
                                Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                                        "动态上传完毕  2", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("SecurityToken", String.valueOf(error));
                        bean.setEdit(3);
                        BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao().update(bean);
                        Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                                "网络错误", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
                sessionStoreJsonRequest.setRetryPolicy(new DefaultRetryPolicy(30*1000,0,0f));
                CoreApplication.newInstance().getRequestQueue().add(sessionStoreJsonRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        hideSoftInput();
    }


    private void uploadImage(OSSFederationToken federationToken, final List<String> imgList, final PersonDynamicsBean bean, final FlowableEmitter<PersonDynamicsBean> e){
        Log.v("uploadImage","begin");
        OssService ossService=initOSS(endpoint,"uncomapp",null,federationToken);
        ossService.setSyncPutImageCallBack(new OssService.SyncPutImageCallBack() {
            @Override
            public void callBack(final String url, String localFile) {

                if(bean.getPhoto_online_list()==null){
                    bean.setPhoto_online_list(domain+"/"+url);
                }else{
                    bean.setPhoto_online_list(bean.getPhoto_online_list()+","+domain+"/"+url);
                }
                Log.v("getPhoto_online_list", String.valueOf(bean.getPhoto_online_list().split(",").length));
                if(bean.getPhoto_online_list().split(",").length==imgList.size()){
                    e.onNext(bean);
                    e.onComplete();
                    Log.v("上传完毕","success");
                }else{
                    Log.v("上传完毕","failde");
                }
                BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao().update(bean);

            }
        });
        for (int i = 0; i <imgList.size() ; i++) {
            String lis[]=imgList.get(i).split("\\.");
            Log.v("img url","uncom_dynamics/"+new Date().getTime()+"."+lis[lis.length-1]);
            ossService.asyncPutImage("uncom_dynamics/"+new Date().getTime()+"."+lis[lis.length-1], imgList.get(i));
        }
    }

    private void save(){
        bean.setContent(text.getText().toString());
        dao=BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
        bean.setUser_id(CoreApplication.newInstance().getUserBean().getId());
        bean.setCreate_time(new Date());
        bean.setUsername(CoreApplication.newInstance().getUserBean().getUsername());
        bean.setUser_photo(CoreApplication.newInstance().getUserBean().getHeader_pic());
        if(bean.getId()==null){
            bean.setEdit(1);
            bean.setComment("[]");
            bean.setComment_count(0);
            bean.setLike("[]");
            bean.setLike_count(0);
            bean.setId(UUID.randomUUID().toString());
            dao.insert(bean);
        }else{
            dao.update(bean);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                "onActivityResult", Toast.LENGTH_LONG);
        toast.show();
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
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
                                    Log.v("accept","success");
                                    if(bean.getPhoto_list()==null || bean.getPhoto_list().length()<6){
                                        bean.setPhoto_list(file.getAbsolutePath());
                                        Log.v("accept","null");
                                    }else{
                                        Log.v("accept","somthing");
                                        bean.setPhoto_list(bean.getPhoto_list()+","+file.getAbsolutePath());
                                    }
                                    changePhoto=true;
                                    photoAdatper.addImageView(file.getAbsolutePath());
                                    if(photoContainer.getVisibility()==View.GONE){
                                        photoContainer.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                }else{
                    File file = new File(imgUrl);
                    if(file.exists() && file.isFile()){
                        file.delete();
                    }
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void syncEventBus(MessageEvent messageEvent){

        Flowable.just(messageEvent.getList()).map(new Function<ArrayList<String>, ArrayList<String>>() {
            @Override
            public ArrayList<String> apply(ArrayList<String> strings) throws Exception {
                ArrayList<String> files=new ArrayList<>();
                for (int i = 0; i <strings.size() ; i++) {
                   files.add(Luban.with(CoreApplication.newInstance().getBaseContext()).load(strings.get(i)).get(strings.get(i)).getAbsolutePath());
                }
                return files;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ArrayList<String>>() {
            @Override
            public void accept(ArrayList<String> strings) throws Exception {
                if(bean.getPhoto_list()==null ||bean.getPhoto_list().length()<6){
                    bean.setPhoto_list(parseToString(strings));
                }else{
                    bean.setPhoto_list(bean.getPhoto_list()+","+parseToString(strings));
                }
                changePhoto=true;
                if(photoContainer.getVisibility()==View.GONE){
                    photoContainer.setVisibility(View.VISIBLE);
                }
                for (String url :strings){
                    photoAdatper.addImageView(url);
                }
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
     * 隐藏表情布局
     *
     * @param showSoftInput 是否显示软件盘
     */
    private void hideEmotionLayoutFromInput(boolean showSoftInput) {
        if (ExpressionLinearLayout.isShown()) {
            ExpressionLinearLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInputFromInput();
            }
        }
    }
    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        text.requestFocus();
        bottomContainer.setTranslationY(-softInputHeight);
        text.post(new Runnable() {
            @Override
            public void run() {
                if(isSoftInputShown()==false){
                    showSoftInputFromWindow(getActivity(),text);
                }
            }
        });
    }
    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInputFromInput() {
        text.requestFocus();
        bottomContainer.setTranslationY(-softInputHeight);
    }
    private void showEmotionLayout() {
        Log.v("showEmotionLayout", String.valueOf(softInputHeight));
        int softInputHeight =this.softInputHeight;
        if(softInputHeight>0){
            bottomContainer.setTranslationY(0);
            ViewGroup.LayoutParams lp;
            lp= ExpressionLinearLayout.getLayoutParams();
            lp.height=softInputHeight;
            ExpressionLinearLayout.setLayoutParams(lp);
            ExpressionLinearLayout.setVisibility(View.VISIBLE);
            Log.v("showEmotionLayout", String.valueOf(ExpressionLinearLayout.getHeight()));
            hideSoftInput();
        }

    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
        params.height = bottomContainer.getHeight();
        params.weight = 0.0F;
    }
    private void unlockContentHeightDelayed() {
        text.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) scrollView.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }
    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        Log.v("hideSoftInput","true");
        mInputManager.hideSoftInputFromWindow(text.getWindowToken(), 0);
        bottomContainer.setTranslationY(0);
        CoordinatorLayout.LayoutParams lp= (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();

        if( lp.bottomMargin==0 && window_height-content.getHeight()<(bottomContainer.getHeight()+softInputHeight)){
            scrollViewBottom=bottomContainer.getHeight()+softInputHeight-(window_height-content.getHeight())+150;
            scrollViewAnimator= ValueAnimator.ofInt(0,scrollViewBottom);
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
            scrollViewAnimator.setDuration(180);
            scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
            scrollViewAnimator.setTarget(scrollView);
            scrollViewAnimator.start();
            scrollView.smoothScrollBy(0,titleRawY);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden==false){
            ((MainActivity)getActivity()).unBindDrawer();
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
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm=(InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        softKeyBoardListener.unLink(1);
    }

    private String parseToString(List<String> list){
        String result="";
        for (int i=0;i<list.size();i++){
            result+=list.get(i);
            if(i<(list.size()-1)){
                result+=",";
            }
        }
        return  result;
    }

    @Override
    public boolean onBackPressed() {
        Log.v(" onBackPressed", "onBackPressed");
        if(bean.getPhoto_list()!=null|| text.getText().length()!=0){
            save();
        }
        if(ExpressionLinearLayout.isShown()){
            ExpressionLinearLayout.setVisibility(View.GONE);
            CoordinatorLayout.LayoutParams lp= (CoordinatorLayout.LayoutParams) scrollView.getLayoutParams();
            if(lp.bottomMargin!=0){
                scrollViewAnimator= ValueAnimator.ofInt(scrollViewBottom,0);
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
                scrollViewAnimator.setDuration(180);
                scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
                scrollViewAnimator.setTarget(scrollView);
                scrollViewAnimator.start();
            }

            return true;
        }
        Log.v(" isSoftInputShown", String.valueOf(isSoftInputShown()));
//        if(isSoftInputShown()){
//            Log.v("hide softInput","start");
//            hideSoftInput();
//            return true;
//        }
        getFragmentManager().popBackStack();
        return true;
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void albumResult(EventMessage eventMessage) {
        Log.v("results", String.valueOf(eventMessage.getList()));
        bean.setPhoto_list(parseToString(eventMessage.getList()));
        photoAdatper.setImgUrl(eventMessage.getList());
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


    /**
     * EditText竖直方向是否可以滚动
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    public  boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
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


    //初始化一个OssService用来上传下载
    public OssService initOSS(String endpoint, String bucket, UIDisplayer displayer, OSSFederationToken federationToken) {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        //OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        Log.v("init oss","begin");
        OSSCredentialProvider credentialProvider= new OSSStsTokenCredentialProvider(federationToken);
        //使用自己的获取STSToken的类
        Log.v("init oss","federationToken");
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        OSS oss = new OSSClient(CoreApplication.newInstance().getApplicationContext(), endpoint, credentialProvider, conf);
        Log.v("init oss","complete");
        return new OssService(oss, bucket, displayer);
    }


    public String getPersosnFrendId(){
        String string="";
        ArrayList<RelationShipLevelBean> beans=CoreApplication.newInstance().getPersonFrendList();
        for (RelationShipLevelBean bean : beans){
            string+=bean.getMinor_user()+",";
        }

        return string;
    }

}
