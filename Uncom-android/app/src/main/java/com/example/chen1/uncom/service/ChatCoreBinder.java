package com.example.chen1.uncom.service;

import android.content.Context;
import android.os.Binder;
import android.view.View;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by chen1 on 2017/10/3.
 */

public class ChatCoreBinder extends Binder {

    private CoreService coreService;
    public  ChatCoreBinder(CoreService coreService){
        this.coreService=coreService;
    }

    public CoreService getCoreService() throws URISyntaxException {
        return coreService;
    }

}
