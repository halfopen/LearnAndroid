package com.halfopen.h.cislsign.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.halfopen.h.cislsign.service.TimeService;

/**
 * Created by h on 2017/7/21.
 */

public class BootedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("flag--","onReceive(BootedReceiver.java:18)-->>"+"startService");
        context.startService(new Intent(context, TimeService.class));
    }

}