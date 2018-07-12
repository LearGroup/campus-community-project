package com.example.chen1.uncom.communication;

import android.util.Log;

import com.example.chen1.uncom.application.CoreApplication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by chen1 on 2018/3/12.
 */

public class SendThread extends  Thread {
    @Override
    public void run() {
        super.run();
        Log.v("sendThread","onCreated");
        byte[] buf="hello android! ".getBytes();
        DatagramSocket sendSocket = null;
        try {
            sendSocket = new DatagramSocket();
            InetAddress serverAddr = InetAddress.getByName(CoreApplication.newInstance().IP_ADDR);
            DatagramPacket outPacket = new DatagramPacket(buf, buf.length,serverAddr,4064);
            sendSocket.send(outPacket);
            sendSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
