package com.halfopen.h.cislsign.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.halfopen.h.cislsign.R;
import com.halfopen.h.cislsign.service.MQTTService;
import com.halfopen.h.cislsign.service.TimeService;
import com.halfopen.h.cislsign.view.SignView;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SignView sv;
    String username="";
    String password = "";
    String userid="";
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //抽屉
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //签到按钮
        sv = (SignView) findViewById(R.id.sign_view);
        //绑定点击事件
        sv.setOnClickListener(v->{
            sign(!sv.isSign());
            sv.change();
        });

        this.startService(new Intent(this, TimeService.class));
        
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        startService(new Intent(this, MQTTService.class));
    }

    /**
     * 方法描述：判断某一Service是否正在运行
     *
     * @param context     上下文
     * @param serviceName Service的全路径： 包名 + service的类名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            this.finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        sharedPref = getSharedPreferences(getString(R.string.preference_userdata_key), Context.MODE_PRIVATE);
        username= sharedPref.getString("username","");
        password = sharedPref.getString("password", "");
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            startActivity(new Intent(this, LoginActivity.class));
            super.onResume();
        }else {
            //检查状态
            checkSign(username, password);

            super.onResume();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("flag--","onOptionsItemSelected(MainActivity.java:144)-->>"+"refresh");
            checkSign(username,password);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, SignRecordActivity.class));
        } else if (id == R.id.user_settings) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sign(Boolean bflag){
        String flag=bflag?"1":"0";
        //建立缓存
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);

        //
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue rQueue = new RequestQueue(cache, network);

        rQueue.start();

        String url = getString(R.string.sign_api)+"?userid="+this.userid+"&signflag="+flag;
        Log.d("flag--","sign(MainActivity.java:192)-->>"+url);
        StringRequest stringRequest;
        stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                Log.d("flag--","onResponse(MainActivity.java:190)-->>"+response);

                sv.refresh();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        rQueue.add(stringRequest);
    }

    //检测签入状态
    public void checkSign(String username, String password){
        //建立缓存
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);

        //
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue rQueue = new RequestQueue(cache, network);

        rQueue.start();
        Log.d("flag--","checkSign(MainActivity.java:255)-->>"+getString(R.string.get_status_api)+"?username="+username+"&password="+password);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.get_status_api)+"?username="+username+"&password="+password, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JsonParser parse = new JsonParser();  //创建json解析器
                    JsonObject json = (JsonObject) parse.parse(response);
                    Log.d("flag--", "onResponse(MainActivity.java:190)-->>" + json.get("result").getAsString());
                    Log.d("flag--", "onResponse(MainActivity.java:191)-->>" + json.get("signflag").getAsString());
                    if (json.get("signflag").getAsString().equals("1")) {
                        sv.setSign(true);
                    } else {
                        sv.setSign(false);
                    }
                    userid = json.get("userid").getAsString();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //保存数据
                    editor.putString("userid", userid);
                    editor.apply();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "解析失败", Toast.LENGTH_SHORT).show();
                }
                sv.refresh();
            }
        }, (error) ->{
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
        });
        rQueue.add(stringRequest);

    }
}
