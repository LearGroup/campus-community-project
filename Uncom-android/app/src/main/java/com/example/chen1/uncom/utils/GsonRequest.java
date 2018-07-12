package com.example.chen1.uncom.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by chen1 on 2017/10/20.
 */public abstract class GsonRequest<T> extends Request<T>
{

    private Response.Listener<T> mListener;
    private Map<String, String> mParams;

    private final String mRequestBody;
    private Gson mGson;
    private Class<T> mClass;



    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        if (mParams != null)
        {
            return mParams;
        }
        return super.getParams();
    }

    @Override
    abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

    public GsonRequest(int method, Map<String, String> params, String url, String requestBody,
                       Response.Listener listener, Response.ErrorListener errorListener)
    {
        this(Method.DEPRECATED_GET_OR_POST, url, requestBody, listener, errorListener);
        mParams = params;
    }


    /**
     * @param method
     * @param url
     * @param listener
     * @param errorListener
     */
    public GsonRequest(int method, String url,String requestBody, Response.Listener listener,
                       Response.ErrorListener errorListener)
    {
        super(method, url, errorListener);
        this.mListener = listener;
        mListener = listener;
        this.mRequestBody = requestBody;
        this.mGson = new Gson();
        setTag(listener);
    }


    @Override
    protected void deliverResponse(T response)
    {
        mListener.onResponse(response);
    }
}

