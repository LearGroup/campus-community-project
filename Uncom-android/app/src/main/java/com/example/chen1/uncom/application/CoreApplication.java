package com.example.chen1.uncom.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by chen1 on 2017/10/3.
 */

public class CoreApplication extends Application {

    private static final String SET_COOKIE_KEY = "set-cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "skey";

    private static CoreApplication instance;
    private RequestQueue requestQueue;
    private SharedPreferences preferences;

    public static CoreApplication newInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }


    /**
     * 检查返回的Response header中有没有session
     * @param responseHeaders Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> responseHeaders) {
        Log.v("session", String.valueOf(responseHeaders));
        Log.v("sessionid", String.valueOf(responseHeaders.get(SET_COOKIE_KEY)));
        if (responseHeaders.containsKey(SET_COOKIE_KEY)) {
            String cookie = responseHeaders.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * 添加session到Request header中
     */
    public final void addSessionCookie(Map<String, String> requestHeaders) {
        String sessionId = preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (requestHeaders.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(requestHeaders.get(COOKIE_KEY));
            }
            requestHeaders.put(COOKIE_KEY, builder.toString());
            Log.v("headers", String.valueOf(requestHeaders));
        }
    }
}
