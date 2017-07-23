package com.halfopen.h.cislsign.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by h on 2017/7/22.
 */

public class MyOptionMessageReceiver extends BroadcastReceiver{
    public MyOptionMessageReceiver() {
        super();
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
