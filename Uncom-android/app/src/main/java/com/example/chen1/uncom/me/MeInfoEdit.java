package com.example.chen1.uncom.me;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.chen1.uncom.FragmentBackHandler;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.bean.PersonDynamicsBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.chat.PersonChatFragment;
import com.example.chen1.uncom.expression.SoftKeyBoardListener;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.utils.BackHandlerHelper;
import com.example.chen1.uncom.utils.DisplayUtils;
import com.example.chen1.uncom.utils.GlideApp;
import com.example.chen1.uncom.utils.GlideCircleTransform;
import com.example.chen1.uncom.utils.GlideEngine;
import com.example.chen1.uncom.utils.LoadImageUtils;
import com.example.chen1.uncom.utils.MessageEvent;
import com.example.chen1.uncom.utils.OssService;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SessionStoreJsonRequest;
import com.example.chen1.uncom.utils.SwipLayout;
import com.example.chen1.uncom.utils.SyncPersonInfoUtils;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

import static android.app.Activity.RESULT_OK;
import static com.example.chen1.uncom.thinker.WriteThinkFragment.TAKE_PHOTO;


public class MeInfoEdit extends Fragment implements FragmentBackHandler,View.OnTouchListener{
    private boolean change=false;
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private String domain="http://uncomapp.oss-cn-beijing.aliyuncs.com";
    private UIDisplayer uiDisplayer;
    private ImageView headImg;
    private String imgUrl;
    private  PhotoSelectorDialog dialog;
    private AutoCompleteTextView username;
    private RadioGroup gender;
    private UserBean bean;
    private AutoCompleteTextView selfAbstract;
    private AutoCompleteTextView addr;
    private AutoCompleteTextView email;
    private OssService ossService;
    private AppCompatImageView backBtn;
    private AutoCompleteTextView university;
    private AutoCompleteTextView college;
    private RadioButton boy;
    private RadioButton girl;
    private AppCompatImageView saveBtn;
    private ValueAnimator scrollViewAnimator;
    private ImageView displayImage;
    private int scrollViewBottom;
    private TextView displayInfo;
    private SyncPersonInfoUtils syncPersonInfoUtils;
    private float raw_Y;
    private ScrollView scrollView;
    private String s_username;
    private Integer s_sex;
    private String  s_self_abstract;
    private String s_email;
    private String s_university;
    private String s_college;
    private View view;
    private SwipLayout swipLayout;
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    private SoftKeyBoardListener softKeyBoardListener;
    public MeInfoEdit() {
        // Required empty public constructor
    }

    public static MeInfoEdit newInstance() {
        MeInfoEdit fragment = new MeInfoEdit();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean=CoreApplication.newInstance().getUserBean();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("MeInfoEdit","onCreateView");
        if(view==null){
            view =inflater.inflate(R.layout.fragment_me_info_edit, container, false);
        }
        ((MainActivity)getActivity()).unBindDrawer();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipLayout=new SwipLayout(getContext());
        swipLayout.setLayoutParams(params);
        swipLayout.setBackgroundColor(Color.TRANSPARENT);
        swipLayout.removeAllViews();
        swipLayout.addView(view);
        if(CoreApplication.newInstance().getRoot()!=null){
            Log.v("MeinfoEdit","rootView not null");
        }
        swipLayout.setParentView(getTargetFragment().getView()).setFragment(MeInfoEdit.this);
        return swipLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headImg= (ImageView) view.findViewById(R.id.head_img);
        view.setClickable(true);
        displayInfo= (TextView) view.findViewById(R.id.display_info);
        displayImage= (ImageView) view.findViewById(R.id.display_image);
        username= (AutoCompleteTextView) view.findViewById(R.id.username);
        gender= (RadioGroup) view.findViewById(R.id.gender);
        boy= (RadioButton) view.findViewById(R.id.boy);
        girl= (RadioButton) view.findViewById(R.id.girl);
        backBtn= (AppCompatImageView) view.findViewById(R.id.back);
        saveBtn= (AppCompatImageView) view.findViewById(R.id.save);
        selfAbstract= (AutoCompleteTextView) view.findViewById(R.id.write);
        addr= (AutoCompleteTextView) view.findViewById(R.id.addr);
        email= (AutoCompleteTextView) view.findViewById(R.id.email);
        university= (AutoCompleteTextView) view.findViewById(R.id.univiersity);
        college= (AutoCompleteTextView) view.findViewById(R.id.college);
        scrollView= (ScrollView) view.findViewById(R.id.container);
        GlideApp.with(this)
                .load(CoreApplication.newInstance().getUserBean().getHeader_pic()).transition(new DrawableTransitionOptions().crossFade())
                .into(headImg);
        uiDisplayer=new UIDisplayer(displayImage,null,displayInfo,getActivity());
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    update();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBack();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                getFragmentManager().popBackStack();
                CoreApplication.newInstance().getRoot().startAnimation(AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(), R.anim.default_open_right));

            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.v("checkedId", String.valueOf(checkedId));
                RadioButton button= (RadioButton) gender.findViewById(checkedId);
                if((gender.findViewById(checkedId)).equals("boy")){
                    s_sex=1;
                }else{
                    s_sex=0;
                }
                Log.v("checkedId", (String) button.getText());
            }
        });
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfig();
            }
        });
        setTouchListener();
        setInfo();
        onSoftKeyBoardChangeListener= new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.v("height", String.valueOf(height));
                Log.v("top height", String.valueOf((DisplayUtils.getWindowHeight(CoreApplication.newInstance().getApplicationContext())-raw_Y)));
                if((DisplayUtils.getWindowHeight(CoreApplication.newInstance().getApplicationContext())-raw_Y)<height){
                    scrollViewBottom= (int)(height- (DisplayUtils.getWindowHeight(CoreApplication.newInstance().getApplicationContext())-raw_Y))+400;
                    if(scrollViewBottom>height){
                        scrollViewBottom=height;
                    }
                    scrollViewAnimator= ValueAnimator.ofInt(0,scrollViewBottom);
                    scrollViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            // 3.为目标对象的属性设置计算好的属性值
                            int animatorValue = (int)animation.getAnimatedValue();
                            LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) scrollView.getLayoutParams();
                            marginLayoutParams.bottomMargin = animatorValue;
                            scrollView.setLayoutParams(marginLayoutParams);
                            scrollView.smoothScrollBy(0, (int) raw_Y);
                        }
                    });
                    scrollViewAnimator.setDuration(180);
                    scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
                    scrollViewAnimator.setTarget(scrollView);
                    scrollViewAnimator.start();
                }
            }

            @Override
            public void keyBoardHide(int height) {
                if(scrollViewBottom!=0){
                    scrollViewAnimator= ValueAnimator.ofInt(scrollViewBottom,0);
                    scrollViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            // 3.为目标对象的属性设置计算好的属性值
                            int animatorValue = (int)animation.getAnimatedValue();
                            LinearLayout.MarginLayoutParams marginLayoutParams = (LinearLayout.MarginLayoutParams) scrollView.getLayoutParams();
                            marginLayoutParams.bottomMargin = animatorValue;
                            scrollView.setLayoutParams(marginLayoutParams);
                            scrollView.smoothScrollBy(0, (int) raw_Y);
                        }
                    });
                    scrollViewAnimator.setDuration(180);
                    scrollViewAnimator.setInterpolator(new DecelerateInterpolator());
                    scrollViewAnimator.setTarget(scrollView);
                    scrollViewAnimator.start();
                }
            }
        };
        softKeyBoardListener= SoftKeyBoardListener.setListener(getActivity().getWindow(),onSoftKeyBoardChangeListener);

    }

    public void setTouchListener(){
        username.setOnTouchListener(this);
        selfAbstract.setOnTouchListener(this);
        addr.setOnTouchListener(this);
        email.setOnTouchListener(this);
        university.setOnTouchListener(this);
        college.setOnTouchListener(this);
    }


    public String getPersosnFrendId(){
        String string="";
        ArrayList<RelationShipLevelBean> beans=CoreApplication.newInstance().getPersonFrendList();
        for (RelationShipLevelBean bean : beans){
            string+=bean.getMinor_user()+",";
        }
        return string;
    }


    private void setInfo(){
        username.setText(bean.getUsername());
        addr.setText(bean.getSprovince()+"-"+bean.getStown()+"-"+bean.getSarea());
        if(bean.getSex()==null){
            s_sex=-1;
        }else if(bean.getSex().equals(1)){
            boy.setChecked(true);
            s_sex=1;
        }else if(bean.getSex().equals(-1)){
            s_sex=0;
            girl.setChecked(true);
        }
        selfAbstract.setText(bean.getSelf_abstract());
        email.setText(bean.getEmail());
        university.setText(bean.getUniversity());
        college.setText(bean.getCollege());


    }


    private void dialogConfig(){
        dialog=PhotoSelectorDialog.newInstance();
        dialog.setItemOnclickListener(new PhotoSelectorDialog.ItemOnclickListener() {
            @Override
            public void onClick(String type) {
                if(type.equals("camera")){
                    Log.v("click ","camera");
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
                }else{
                    Log.v("click ","photo");
                    EasyPhotos.createAlbum(getActivity(), true, GlideEngine.getInstance())//参数说明：最大可选数，默认1//参数说明：上下文，是否显示相机按钮，图片加载引擎实现(ImageEngine说明)
                            .setCount(1) .setFileProviderAuthority("com.example.chen1.uncom.fileprovider")
                            .start(101);
                }
            }
        });
        dialog.show(getFragmentManager(),"MeInfoEditDialog");
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
                                    uploadImage(file.getAbsolutePath());
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
        Flowable.just(messageEvent.getList().get(0))
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
                        try {
                            uploadImage(file.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Log.v("syncEventBus",messageEvent.getList().toString());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag("MeInfoEditDialog");
        if (null != fragment) {
            Log.v("remove","dialog");
            dialog.onDestroyView();
            ft.remove(fragment);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        softKeyBoardListener.unLink(1);
        EventBus.getDefault().unregister(this);
    }


    private void onBack() throws JSONException {
        update();
        ((MeDetailPage)getTargetFragment()).updateInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public boolean onBackPressed() {
        try {
            onBack();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getFragmentManager().popBackStack();
        getTargetFragment().getView().setAnimation((AnimationUtils.loadAnimation(CoreApplication.newInstance().getBaseContext(),R.anim.default_open_right)));
        return true;
    }

    public void update() throws JSONException {
        s_username=username.getText().toString();
        s_college=college.getText().toString();
        s_email=email.getText().toString();
        s_university=university.getText().toString();
        s_self_abstract=selfAbstract.getText().toString();

        if(change==true || s_username.equals(bean.getUsername())==false || s_college.equals(bean.getCollege())==false
                || s_email.equals(bean.getEmail())==false || s_university.equals(bean.getUniversity())==false
                || s_self_abstract.equals(bean.getSelf_abstract())==false ){
            bean.setUsername(s_username);
            bean.setCollege(s_college);
            bean.setEmail(s_email);
            bean.setUniversity(s_university);
            bean.setSelf_abstract(s_self_abstract);
            bean.setSex(s_sex);
            change=false;
            JSONObject jsonObject= new JSONObject(com.alibaba.fastjson.JSONObject.toJSONString(bean));
            jsonObject.put("updateList", JSONArray.toJSONString(getPersosnFrendId()));
            SessionStoreJsonRequest sessionStoreJsonRequest=new SessionStoreJsonRequest(
                    "http://" + CoreApplication.newInstance().IP_ADDR + ":8081/syncPersonInfo", jsonObject,
                    new Response.Listener<JSONObject>() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("response",response.toString());
                            BeanDaoManager.getInstance().getDaoSession().getUserBeanDao().update(bean);
                            CoreApplication.newInstance().setUserBean(bean);
                            syncPersonInfoUtils=new SyncPersonInfoUtils();
                            syncPersonInfoUtils.syncPersonInfo(bean);
                            change=false;
                        }
                    }, new Response.ErrorListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("erroor",error.toString());
                    Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                            "网络错误", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 100);
                    toast.setDuration(500);
                    toast.show();

                }
            });
            CoreApplication.newInstance().getRequestQueue().add(sessionStoreJsonRequest);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      raw_Y= event.getRawY();
      Log.v("getRawY", String.valueOf(raw_Y));
        return false;
    }
    public void  uploadImage(final String imgUrl) throws IOException {
        SessionStoreJsonRequest sessionStoreJsonRequest = new SessionStoreJsonRequest("http://47.95.0.73:7080",
                null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String stsJson;
                stsJson = response.toString();
                JSONObject jsonObjs = null;
                try {
                    String img_url=imgUrl;
                    jsonObjs = new JSONObject(stsJson);
                    String ak = jsonObjs.getString("AccessKeyId");
                    String sk = jsonObjs.getString("AccessKeySecret");
                    String token = jsonObjs.getString("SecurityToken");
                    String expiration = jsonObjs.getString("Expiration");
                    Log.v("AccessKeyId",ak);
                    Log.v("AccessKeySecret",sk);
                    Log.v("token",token);
                    OSSFederationToken federationToken= new OSSFederationToken(ak, sk, token, expiration);
                    ossService=initOSS(endpoint,"uncomapp",uiDisplayer,federationToken);
                    ossService.setSyncPutImageCallBack(new OssService.SyncPutImageCallBack() {
                        @Override
                        public void callBack(final String url, String localFile) {
                            Log.v("callback","user_head/");
                            File file = new File(localFile);
                            if(file.exists() && file.isFile()){
                                file.delete();
                            }
                            try {
                                Log.v("load url",domain+"/"+url);
                                Log.v("before url",bean.getHeader_pic());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        GlideApp.with(MeInfoEdit.this)
                                                .load(domain+"/"+url).transition(new DrawableTransitionOptions().crossFade())
                                                .into(headImg);
                                    }
                                });
                                ossService.deleteImage( "user_head/"+ bean.getHeader_pic().split("/")[bean.getHeader_pic().split("/").length-1]);
                                bean.setHeader_pic(domain+"/"+url);
                                change=true;
                                BeanDaoManager.getInstance().getDaoSession().getUserBeanDao().update(bean);
                                CoreApplication.newInstance().setUserBean(bean);
                                try {
                                    update();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.v("bean url",bean.getHeader_pic());
                                ((MeDetailPage)getTargetFragment()).updateUserBean(CoreApplication.newInstance().getUserBean());
                            } catch (ClientException e) {
                                e.printStackTrace();
                            } catch (ServiceException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    String lis[]=img_url.split("\\.");
                    ossService.asyncPutImage("user_head/"+new Date().getTime()+"."+lis[lis.length-1], img_url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("SecurityToken", String.valueOf(error));
                PopupWindowUtils.popupWindow("网络错误", R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT, 150, 1500, CoreApplication.newInstance().getApplicationContext(), getView());
            }
        });

        CoreApplication.newInstance().getRequestQueue().add(sessionStoreJsonRequest);
    }

    //初始化一个OssService用来上传下载
    public OssService initOSS(String endpoint, String bucket, UIDisplayer displayer,OSSFederationToken federationToken) {
              //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        //OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);

        OSSCredentialProvider credentialProvider= new OSSStsTokenCredentialProvider(federationToken);
        //使用自己的获取STSToken的类

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        OSS oss = new OSSClient(CoreApplication.newInstance().getApplicationContext(), endpoint, credentialProvider, conf);
        return new OssService(oss, bucket, displayer);

    }



}
