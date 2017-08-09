package com.example.h.learn.activity;

import android.view.View;

/**
 * Created by h on 2017/8/8.
 */

public class NotificationPresenter {

    NotificationModel model;
    public NotificationPresenter(NotificationModel m){
        model = m;
    }

    public void onClick(View v){
        //Log.d("flag--","onClick(NotificationPresenter.java:17)-->>");
        model.setBtnText("叠罗汉");
    }
}
