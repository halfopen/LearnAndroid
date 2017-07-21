package com.example.h.learn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.h.learn.R;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    public void onBackPressed() {
        this.finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int[] ids = new int[]{
                R.id.btn_share,     //分享按钮
                R.id.btn_volley,     //volley
                R.id.btn_gson,       //Gson
                R.id.btn_fragment
        };
        //绑定点击事件
        for (int id:ids
             ) {
            findViewById(id).setOnClickListener(this);
        }

        Log.d("flag--","onCreate(MainActivity.java:28)-->>"+"test log");
    }


    //相应点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击分享按钮
            case R.id.btn_share:
                Log.i("info", "click share");
                Intent intent = new Intent(this, ShareActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_volley:
                startActivity(new Intent(this, VolleyActivity.class));
                break;
            case R.id.btn_gson:
                startActivity(new Intent(this, GsonActivity.class));
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(this, FragmentActivity.class));
                break;
            default:
        }
    }
}
