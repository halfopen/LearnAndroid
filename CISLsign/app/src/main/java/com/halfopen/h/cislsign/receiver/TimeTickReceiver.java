package com.halfopen.h.cislsign.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by h on 2017/7/21.
 */

public class TimeTickReceiver extends BroadcastReceiver{
    private boolean flag;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("flag--","onReceive(TimeTickReceiver.java:17)-->>"+"时间变了" + intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            //每过一分钟 触发
            Log.d("flag--", "onReceive(TimeTickReceiver.java:21)-->>" + "time change");

        } else {
            /*
             * 系统bug??
             * android.intent.action.TIME_SET  当调整系统时间后 这个action会收到两次
             */
            if (flag) {
                try {
                    /*  do some thing */
                } catch (Exception e) {
                    e.printStackTrace();
                }
                flag = false; //第二次置false
            } else {
                flag = true; //第一次置true
            }

        }
    }
}
