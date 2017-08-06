package com.example.h.learn;

import android.app.Application;
import android.content.Context;

/**
 * Created by h on 2017/7/30.
 */

public class MyApplication extends Application{
    public static Context getContext() {
        return context;
    }

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();

        //MultiDex.install(getContext());
    }
}
