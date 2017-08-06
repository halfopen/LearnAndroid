package com.halfopen.h.cislsign.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.halfopen.h.cislsign.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

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
            Date date= new Date();//创建一个时间对象，获取到当前的时间
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//设置时间显示格式
            String str = sdf.format(date);//将当前时间格式化为需要的类型
            Log.d("flag--","onReceive(TimeTickReceiver.java:38)-->>"+str);

            switch (str){
                case "08:55":
                case "08:56":
                case "12:00":
                case "18:00":
                    MyApplication.notifySign("请及时签入");
                    break;
                case "11:00":
                case "11:01":
                case "17:00":
                case "17:01":
                    MyApplication.notifySign("请即时签出");
            }
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
