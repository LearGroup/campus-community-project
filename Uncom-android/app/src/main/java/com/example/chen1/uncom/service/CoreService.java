package com.example.chen1.uncom.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.utils.PopupWindowUtils;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by chen1 on 2017/10/3.
 */

public class CoreService extends Service {
    private Context context;
    private View rootView;
    private ChatCoreBinder binder=new ChatCoreBinder();
    private Socket socket;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        context=binder.getContext();
        rootView=binder.getRootView();
        return binder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("createCoreService","ok");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.v("createNewThread","ok");
                    socket= IO.socket("http://10.0.2.2:8081");
                    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Log.v("DISCONNECT","ok");
                            socket.emit("connection","ok");
                        }
                    }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            PopupWindowUtils.popupWindow("账号密码错误", R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,-1,context,rootView);
                        }
                    }).on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {

                        }
                    });
                    socket.connect();
                //    socket.emit("connection","ok");
                    Log.v("createNewThreaded","ok");
                } catch (URISyntaxException e) {
                    Log.v("createPopupWindow","ok");
                    //PopupWindowUtils.popupWindow("账号密码错误", R.layout.access_popupwindow_statustag_layout, LinearLayout.LayoutParams.MATCH_PARENT,150,-1,context,rootView);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
