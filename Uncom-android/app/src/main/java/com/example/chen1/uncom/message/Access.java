package com.example.chen1.uncom.message;


import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by chen1 on 2018/2/16.
 */

public interface Access {


    /**
     *
     * @param handler
     * @param jsonArray
     * message初步处理
     */
    void parseMessage(Handler handler,JSONArray jsonArray) throws JSONException, ParseException;

    /**
     *
     * @param handler
     * 收到个人聊天信息
     */
    void receivedMessageForPersonChat(Handler handler,JSONObject object) throws JSONException, ParseException;

    /**
     *
     * @param handler
     * 收到组织聊天信息
     */
    void receivedMessageForGroupChat(Handler handler,JSONObject object);

    void receivedMessageForPersonInfo(Handler handler,JSONObject object) throws JSONException;


    void receivedMessageForPersonDynamics(Handler handler,JSONObject jsonObject) throws JSONException;
}
