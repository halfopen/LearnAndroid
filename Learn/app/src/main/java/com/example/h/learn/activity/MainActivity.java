package com.example.h.learn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.h.learn.LayoutActivity;
import com.example.h.learn.R;
import com.example.h.learn.SlidePanelActivity;
import com.example.h.learn.ViewPagerHome;
import com.example.h.learn.WebViewActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private DrawerLayout drawer_layout;

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
                R.id.btn_fragment,
                R.id.btn_view_pager,
                R.id.btn_green_dao,
                R.id.btn_slide_panel,
                R.id.btn_web_view,
                R.id.btn_layout,
                R.id.btn_notification
        };
        //绑定点击事件
        for (int id:ids
             ) {
            findViewById(id).setOnClickListener(this);
        }

        drawer_layout = (DrawerLayout) findViewById(R.id.my_drawer_layout);

        //drawer_layout.openDrawer(Gravity.END);

        //drawer_layout.closeDrawer(Gravity.END);

        drawer_layout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.d("flag--","onDrawerSlide(MainActivity.java:53)-->>"+slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d("flag--","onDrawerOpened(MainActivity.java:58)-->>");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d("flag--","onDrawerClosed(MainActivity.java:63)-->>");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d("flag--","onDrawerStateChanged(MainActivity.java:68)-->>"+newState);
            }
        });

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
            case R.id.btn_view_pager:
                startActivity(new Intent(this, ViewPagerHome.class));
                break;
            case R.id.btn_green_dao:
                startActivity(new Intent(this, GreenDaoActivity.class));
                break;
            case R.id.btn_slide_panel:
                startActivity(new Intent(this, SlidePanelActivity.class));
                break;
            case R.id.btn_web_view:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.btn_layout:
                startActivity(new Intent(this, LayoutActivity.class));
                break;
            case R.id.btn_notification:
                startActivity(new Intent(this, NotifycationActivity.class));
                break;

            default:
        }
    }
}
