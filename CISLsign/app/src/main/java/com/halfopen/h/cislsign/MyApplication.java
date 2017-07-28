package com.halfopen.h.cislsign;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.halfopen.h.cislsign.activity.MainActivity;

/**
 * Created by h on 2017/7/24.
 */

public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }


    public static  void notifySign(String message){

        Log.d("flag--","notifySign(TimeTickReceiver.java:70)-->>");
        Context context1 = MyApplication.getContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context1)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("信息系统")    //通知标题
                        .setContentText(message)   //通知详情
                        .setDefaults(Notification.DEFAULT_ALL);
        //点击通知跳转的activity
        Intent resultIntent = new Intent(context1, MainActivity.class);
        //
        //resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        NotificationManager mNotifyMgr =
                (NotificationManager) context1.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context1,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        //发布通知
        mNotifyMgr.notify(110, mBuilder.build());
    }
}
