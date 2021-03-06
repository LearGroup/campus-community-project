package com.example.chen1.uncom.access;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.MessageHistoryBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.utils.UserBeanAndJsonUtils;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.utils.ChatUserDataUtil;
import com.example.chen1.uncom.expression.SoftKeyBoardListener;
import com.example.chen1.uncom.main.MainActivity;
import com.example.chen1.uncom.utils.PopupWindowUtils;
import com.example.chen1.uncom.utils.SessionStoreJsonRequest;
import com.example.chen1.uncom.utils.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginPageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button loginButton;
    private AutoCompleteTextView userTextView;
    private AutoCompleteTextView passwordTextView;
    private String user_name;
    private String user_password;
    private View rootView;
    private SoftKeyBoardListener.OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    private static RequestQueue requestQueue;
    private static LoginPageFragment loginPageFragment = null;
    private SoftKeyBoardListener softKeyBoardListener;


    /**
     * 跳转到MainActivity
     */
    private void jumpToMainActivity(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive()) {
            //如果开启
            Log.v("keybordstatus","true");
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
        Intent intent =new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    public LoginPageFragment() {

        // Required empty public constructor
    }


    public static LoginPageFragment getInstance() {
        if (loginPageFragment == null) {
            loginPageFragment = new LoginPageFragment();
        }
        return loginPageFragment;
    }



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



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id=SharedPreferencesUtil.getUserId(CoreApplication.newInstance().getBaseContext());
        onSoftKeyBoardChangeListener=new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
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
                    SharedPreferencesUtil.setSoftInputHeight(softInputHeight,CoreApplication.newInstance().getBaseContext());
                }

            }

            @Override
            public void keyBoardHide(int height) {
            /*    KeyBoardHeight=height;*/


            }
        };
       softKeyBoardListener= SoftKeyBoardListener.setListener(getActivity().getWindow(), onSoftKeyBoardChangeListener);
        if(id!=""){
            jumpToMainActivity();
        }

    }


    /**
     * 登陆方法
     * @throws AuthFailureError
     */
    private void attemptLogin() throws AuthFailureError {
        String tag_json_obj = "json_obj_req";
        InputMethodManager imm =  (InputMethodManager) CoreApplication.newInstance().getBaseContext().getSystemService(CoreApplication.newInstance().getBaseContext().INPUT_METHOD_SERVICE);
        user_name = userTextView.getText().toString();
        user_password = passwordTextView.getText().toString();
        Log.v("attemptLogin:", "test");
        Log.v("user_name:", String.valueOf(userTextView.getText().length()));
        Log.v("user_password:", user_password);
        if (user_name.length() == 0) {
            PopupWindowUtils.popupWindow("请输入账号",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,CoreApplication.newInstance().getBaseContext(),rootView);
            userTextView.setFocusable(true);
            userTextView.setFocusableInTouchMode(true);
            userTextView.requestFocus();
            userTextView.findFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else if (user_password.length() == 0) {
            PopupWindowUtils.popupWindow("请输入密码",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,CoreApplication.newInstance().getBaseContext(),rootView);
            passwordTextView.setFocusable(true);
            passwordTextView.setFocusableInTouchMode(true);

            passwordTextView.requestFocus();
            passwordTextView.findFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            final PopupWindow popwin= PopupWindowUtils.popupWindow(null,R.layout.access_popupwindow_loginwaiting_layout, LinearLayout.LayoutParams.MATCH_PARENT,200,-1,CoreApplication.newInstance().getBaseContext(),rootView);
            //传一个参数，user=zhangqi
            //为了保证popupWindow能有好的视觉体验，故延迟了600毫秒...
            CountDownTimer timer = new CountDownTimer(600, 10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    Map<String, String> map = new HashMap<String, String>();
                    String use=null;
                    Log.v("@", String.valueOf(user_name.split("@").length));
                    Log.v(",com", String.valueOf(user_name.indexOf(".com")));
                    if(user_name.split("@").length==2 &&user_name.indexOf(".com")==-1){
                        //该str为邮箱号
                        use="email";
                    }else if(user_name.length()==11){
                        //该str为手机号
                        use="phone";
                    }
                    map.put("use", use);
                    map.put("username", user_name);
                    map.put("password", user_password);
                    JSONObject params = new JSONObject(map);
                    Log.v("json", String.valueOf(params));
                    /*http://10.0.2.2:8081 本地调试用IP地址，本地调试时不能使用127.0.0.1:8081 47.95.0.73*/
                    SessionStoreJsonRequest sessionStoreJsonRequest = new SessionStoreJsonRequest("http://"+CoreApplication.newInstance().IP_ADDR+":8081/login",
                            params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v("response", String.valueOf(response));
                            try {
                                String status= response.getString("status");
                                popwin.dismiss();
                                if(status.equals("1")){
                                    Log.v("resopnse", String.valueOf(response));
                                    Log.v("resopnse", String.valueOf(response.getJSONObject("results").get("id")));
                                    SharedPreferencesUtil.setUserId((String)response.getJSONObject("results").get("id"),CoreApplication.newInstance().getBaseContext());
                                    //必须使用getInstance(1)这样会创建一个新的数据库
                                    UserBeanDao userBeanDao =BeanDaoManager.getInstance(1).getNewSession().getUserBeanDao();
                                    UserBean userBean=userBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(response.getJSONObject("results").get("id"))).build().unique();
                                    Log.v("resop nse", String.valueOf(userBean));
                                    if(userBean==null){
                                        userBean= UserBeanAndJsonUtils.getUserBean(response.getJSONObject("results"));
                                               userBeanDao.insert(userBean);
                                        Log.v("username",userBean.getUsername());
                                        Log.v("user_id",userBean.getId());
                                        Log.v("saveUserBean", "1");
                                    }else{
                                        userBean=UserBeanAndJsonUtils.getUpdatedUserBean(userBean,response.getJSONObject("results"));
                                        userBeanDao.update(userBean);
                                        Log.v("saveUserBean", "2");
                                    }
                                    CoreApplication.newInstance().setUserBean(userBean);
                                    new ChatUserDataUtil().getFriendList( CoreApplication.newInstance().getRequestQueue(),CoreApplication.newInstance().getBaseContext(),rootView);
                                    final PopupWindow popupWindows=PopupWindowUtils.popupWindow("正在加载资源...",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,CoreApplication.newInstance().getBaseContext(),rootView);
                                    CountDownTimer timer = new CountDownTimer(1000,10) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                        }

                                        @Override
                                        public void onFinish() {
                                            //当用户登录后，进行一次强制同步，会将在内存缓存的关系数据清空，重新从磁盘中读取，目的是避免多用户登陆造成信息泄露
                                            //同时初始化内存中的相关变量
                                            CoreApplication.newInstance().setUser_id(SharedPreferencesUtil.getUserId(CoreApplication.newInstance().getBaseContext()));
                                            Log.v("ssetUser_id",SharedPreferencesUtil.getUserId(CoreApplication.newInstance().getBaseContext()));
                                            Log.v("setUser_id2",CoreApplication.newInstance().getUser_id());
                                            RelationShipLevelBeanDao relationShipLevelBeanDao=BeanDaoManager.getInstance().getDaoSession().getRelationShipLevelBeanDao();
                                            MessageHistoryBeanDao messageHistoryBeanDao=BeanDaoManager.getInstance().getDaoSession().getMessageHistoryBeanDao();
                                            CoreApplication.newInstance().setRelationShipLevelBeanDao(relationShipLevelBeanDao);
                                            CoreApplication.newInstance().setMessageHistoryBeanDao(messageHistoryBeanDao);
                                            CoreApplication.newInstance().SyncData(CoreApplication.newInstance().getBaseContext());
                                            CoreApplication.newInstance().initPhoto=true;
                                            jumpToMainActivity();
                                        }
                                    };
                                    timer.start();

                                }else{
                                    PopupWindowUtils.popupWindow("账号密码错误",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,CoreApplication.newInstance().getBaseContext(),rootView);
                                    passwordTextView.setFocusable(true);
                                    passwordTextView.setText(null,true);
                                    passwordTextView.setFocusableInTouchMode(true);
                                    passwordTextView.requestFocus();
                                    passwordTextView.findFocus();
                                }
                                Log.v("status",status);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.v("status", String.valueOf(e));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOGIN-ERROR", error.getMessage(), error);
                            popwin.dismiss();
                            PopupWindowUtils.popupWindow("网络错误",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500,CoreApplication.newInstance().getBaseContext(),rootView);

                        }
                    });
                   // jumpToMainActivity();
                    CoreApplication.newInstance().getRequestQueue().add(sessionStoreJsonRequest);
                }
            };
            timer.start();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("LoginFragment ","Destroy");
        softKeyBoardListener.unLink(1);
        onSoftKeyBoardChangeListener=null;
        rootView=null;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);
        rootView = view;
        Button loginButton = (Button) view.findViewById(R.id.email_sign_in_button);
        userTextView = (AutoCompleteTextView) view.findViewById(R.id.user);
        passwordTextView = (AutoCompleteTextView) view.findViewById(R.id.password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptLogin();
                } catch (AuthFailureError authFailureError) {
                    authFailureError.printStackTrace();
                }
            }
        });
        return view;
    }
}
