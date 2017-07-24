package com.halfopen.h.cislsign.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.halfopen.h.cislsign.MyWebSocketClient;
import com.halfopen.h.cislsign.receiver.TimeTickReceiver;

/**
 * Created by h on 2017/7/21.
 */

public class TimeService extends Service{
    //监听时间变化的 这个receiver只能动态创建
    private TimeTickReceiver mTickReceiver;
    private IntentFilter mFilter;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("flag--","onCreate(TimeService.java:27)-->>");
        super.onCreate();
        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_TIME_TICK);     //每分钟变化的action
        mFilter.addAction(Intent.ACTION_TIME_CHANGED);  //设置了系统时间的action
        mTickReceiver = new TimeTickReceiver();
        registerReceiver(mTickReceiver, mFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("flag--","onStartCommand(TimeService.java:37)-->>"+"启动了服务");
        MyWebSocketClient myWebSocketClient = new MyWebSocketClient();
        myWebSocketClient.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTickReceiver);
    }
}
