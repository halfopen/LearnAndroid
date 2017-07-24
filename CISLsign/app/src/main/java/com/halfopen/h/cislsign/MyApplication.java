package com.halfopen.h.cislsign;

import android.app.Application;
import android.content.Context;

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
}
