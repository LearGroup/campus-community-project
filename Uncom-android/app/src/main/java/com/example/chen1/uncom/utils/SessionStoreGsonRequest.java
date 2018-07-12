package com.example.chen1.uncom.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.chen1.uncom.application.CoreApplication;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen1 on 2017/10/21.
 */

public class SessionStoreGsonRequest  extends GsonObjectRequest{
    public SessionStoreGsonRequest(int method, String url, JsonObject jsonRequest, Response.Listener<JsonObject> listener, Response.ErrorListener errorListener) {

        super(method, url, jsonRequest, listener, errorListener);

    }

    public SessionStoreGsonRequest(String url, JsonObject jsonRequest, Response.Listener<JsonObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }




    @Override
    protected Response<JsonObject> parseNetworkResponse(NetworkResponse response) {
        // 检查本地是否有session.如果没哦有，就将header中的session数据保存到本地
        CoreApplication.newInstance().checkSessionCookie(response.headers);
        JsonObject jb =null;
        try {
            String parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            jb =new JsonParser().parse(parsed).getAsJsonObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(jb, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        CoreApplication.newInstance().addSessionCookie(headers);
        return headers;
    }
}
