package com.example.h.learn.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.h.learn.R;
import com.example.h.learn.databinding.ActivityNotifycationBinding;

public class NotifycationActivity extends AppCompatActivity {

    NotificationModel notificationModel = new NotificationModel();
    NotificationPresenter notificationPresenter = new NotificationPresenter(notificationModel);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotifycationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_notifycation);
//        setContentView(R.layout.activity_notifycation);

        binding.setNotificationModel(notificationModel);
        binding.setNotificationPresenter(notificationPresenter);

        notificationModel.setBtnText("立即发送通知");
    }
}
