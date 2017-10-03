package com.example.chen1.uncom.access;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chen1.uncom.R;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.bean.UserBeanAndJsonUtils;
import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.main.MainActivity;
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
    private static RequestQueue requestQueue;
    private static LoginPageFragment loginPageFragment = null;

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


    public static LoginPageFragment newInstance(String param1, String param2) {
        LoginPageFragment fragment = new LoginPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
        String id=SharedPreferencesUtil.getUserId(getContext());
        if(id!=""){
            jumpToMainActivity();
        }
    }

    private PopupWindow popupWindow(String str,int layout,int width,int height,int timers) {
        View view = LayoutInflater.from(getContext()).inflate(layout, null);
        final PopupWindow menu = new PopupWindow(view, width, height);
        if(str!=null){
            TextView textview = (TextView) view.findViewById(R.id.popup_win_textview);
            textview.setText(str);
            menu.setAnimationStyle(R.style.popwin_anim_style);
        }
        // PopupWindow定义，显示view，以及初始化长和宽
        // 显示在某个位置
        menu.showAtLocation(rootView, Gravity.TOP, 0, 0);
        if(timers==-1){
            return menu;
        }else{
            CountDownTimer timer = new CountDownTimer(1500, 10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    menu.dismiss();
                }
            };
            timer.start();
        }
        return null;
    }


    private void attemptLogin() throws AuthFailureError {
        String tag_json_obj = "json_obj_req";
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        user_name = userTextView.getText().toString();
        user_password = passwordTextView.getText().toString();
        Log.v("attemptLogin:", "test");
        Log.v("user_name:", String.valueOf(userTextView.getText().length()));
        Log.v("user_password:", user_password);
        if (user_name.length() == 0) {
            popupWindow("请输入账号",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500);
            userTextView.setFocusable(true);
            userTextView.setFocusableInTouchMode(true);
            userTextView.requestFocus();
            userTextView.findFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else if (user_password.length() == 0) {
            popupWindow("请输入密码",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500);
            passwordTextView.setFocusable(true);
            passwordTextView.setFocusableInTouchMode(true);
            passwordTextView.requestFocus();
            passwordTextView.findFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
          /*  Intent intent =new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();*/
        } else {
            /*
            use: sor,
			user: $("#login_input_username").val(),
			password: $("#login_input_password").val()
			*/
            final PopupWindow popwin= popupWindow(null,R.layout.access_popupwindow_loginwaiting_layout, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,-1);
            //传一个参数，user=zhangqi

            CountDownTimer timer = new CountDownTimer(1000, 10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("use", "phone");
                    map.put("user", user_name);
                    map.put("password", user_password);
                    JSONObject params = new JSONObject(map);
                    Log.v("json", String.valueOf(params));
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://10.0.2.2:8080/Uncom/login_Login.action",
                            params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status= response.getString("status");
                                popwin.dismiss();
                                if(status=="1"){
                                    Log.v("resopnse", String.valueOf(response));
                                    Log.v("resopnse", String.valueOf(response.getJSONObject("results").get("id")));
                                    UserBeanDao userBeanDao =BeanDaoManager.getInstance(getContext()).getNewSession().getUserBeanDao();
                                    UserBean userBean=userBeanDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(response.getJSONObject("results").get("id"))).build().unique();
                                    Log.v("resopnse", String.valueOf(userBean));
                                    if(userBean==null){
                                        userBean= UserBeanAndJsonUtils.getUserBean(response.getJSONObject("results"));
                                               userBeanDao.insert(userBean);
                                    }else{
                                        userBean=UserBeanAndJsonUtils.getUpdatedUserBean(userBean,response.getJSONObject("results"));
                                        userBeanDao.update(userBean);
                                    }
                                    SharedPreferencesUtil.setUserId((String) response.getJSONObject("results").get("id"),getContext());
                                    jumpToMainActivity();
                                }else{
                                    popupWindow("账号密码错误",R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,1500);
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
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
            };
            timer.start();

        }

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
