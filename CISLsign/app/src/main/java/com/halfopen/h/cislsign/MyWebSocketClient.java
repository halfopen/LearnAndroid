package com.halfopen.h.cislsign;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by h on 2017/7/23.
 */

public class MyWebSocketClient {

    //WebSocketClient 和 address
    private WebSocketClient mWebSocketClient;
    private String address;
    private Context context;

    /**
     * 初始化WebSocketClient
     * @throws URISyntaxException
     */
    private void initSocketClient() throws URISyntaxException {
        context = MyApplication.getContext();
        address = context.getResources().getString(R.string.web_socket_address);
        if(mWebSocketClient == null) {
            mWebSocketClient = new WebSocketClient(new URI(address)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("flag--","onOpen(MyWebSocketClient.java:32)-->>"+"成功建立websocket连接");
                }


                @Override
                public void onMessage(String s) {
                    //服务端消息
                    Log.d("flag--","onMessage(MyWebSocketClient.java:39)-->>"+"收到来自服务器的消息"+s);
                }


                @Override
                public void onClose(int i, String s, boolean remote) {
                    //连接断开，remote判定是客户端断开还是服务端断开
                    Log.d("flag--","onClose(MyWebSocketClient.java:46)-->>"+"Connection closed by " + ( remote ? "remote peer" : "us" ) + ", info=" + s);
                    closeConnect();
                }


                @Override
                public void onError(Exception e) {
                    Log.d("flag--","onError(MyWebSocketClient.java:53)-->>"+e);
                }
            };
        }
    }


    //连接
    private void connect() {
        new Thread(()->mWebSocketClient.connect()).start();
    }


    //断开连接
    private void closeConnect() {
        try {
            mWebSocketClient.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            mWebSocketClient = null;
        }
    }

    /**
     *发送消息
     */
    private void sendMsg(String msg) {
        mWebSocketClient.send(msg);
    }

    public void start(){
        try {

            initSocketClient();
            connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
