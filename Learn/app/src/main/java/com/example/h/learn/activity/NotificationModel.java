package com.example.h.learn.activity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.h.learn.BR;

/**
 * Created by h on 2017/8/8.
 */

public class NotificationModel extends BaseObservable{
    private String btnText;

    @Bindable
    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {

        this.btnText = btnText;
        notifyPropertyChanged(BR.btnText);
        //Log.d("flag--","setBtnText(NotificationModel.java:25)-->>"+this.btnText);
    }
}
