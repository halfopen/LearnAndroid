package com.halfopen.h.cislsign.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.halfopen.h.cislsign.MyApplication;
import com.halfopen.h.cislsign.R;
import com.halfopen.h.cislsign.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.halfopen.h.cislsign.activity.MainActivity.userid;

/**
 * Created by h on 2017/7/21.
 */

public class TimeTickReceiver extends BroadcastReceiver{
    private boolean flag;


    //检测签入状态
    public void checkSign(String username, String password){
        //建立缓存
        Cache cache = new DiskBasedCache(MyApplication.getContext().getCacheDir(), 1024*1024);

        //
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue rQueue = new RequestQueue(cache, network);

        rQueue.start();
        Log.d("flag--","checkSign(MainActivity.java:255)-->>"+MyApplication.getContext().getString(R.string.get_status_api)+"?username="+username+"&password="+password);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyApplication.getContext().getString(R.string.get_status_api)+"?username="+username+"&password="+password, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Toast.makeText(MyApplication.getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JsonParser parse = new JsonParser();  //创建json解析器
                    JsonObject json = (JsonObject) parse.parse(response);
                    Log.d("flag--","onResponse(TimeTickReceiver.java:57)-->>"+"checksign"+json.get("signflag").getAsString());
                    if (json.get("signflag").getAsString().equals("1")) {
                        MainActivity.isSigned = true;
                    } else {
                        MainActivity.isSigned=false;
                    }
                }catch (Exception e){
                }
            }
        }, (error) ->{

        });
        rQueue.add(stringRequest);

    }

    public void sign(Boolean bflag){
        String flag=bflag?"1":"0";
        //建立缓存
        Cache cache = new DiskBasedCache(MyApplication.getContext().getCacheDir(), 1024*1024);

        //
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue rQueue = new RequestQueue(cache, network);

        rQueue.start();

        String url = MyApplication.getContext().getString(R.string.sign_api)+"?userid="+ userid+"&signflag="+flag;

        StringRequest stringRequest;
        stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.d("flag--","onResponse(TimeTickReceiver.java:94)-->>"+response);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rQueue.add(stringRequest);
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("flag--","onReceive(TimeTickReceiver.java:17)-->>"+"时间变了" + intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            //每过一分钟 触发
            Log.d("flag--", "onReceive(TimeTickReceiver.java:21)-->>" + "time change");
            Date date= new Date();//创建一个时间对象，获取到当前的时间
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//设置时间显示格式
            String str = sdf.format(date);//将当前时间格式化为需要的类型
            Log.d("flag--","onReceive(TimeTickReceiver.java:38)-->>"+str);

            checkSign(MainActivity.username, MainActivity.password);

            switch (str){
                case "08:55":
                case "08:56":
                case "12:02":
                case "12:22":
                case "18:02":
                case "18:12":
                    MyApplication.notifySign("请及时签入");
                    if (!MainActivity.isSigned){
                        sign(true);
                        MyApplication.notifySign("自动签入");
                    }
                    break;
                case "11:22":
                case "11:01":
                case "17:22":
                case "17:01":
                case "21:20":
                case "21:01":
                    MyApplication.notifySign("请即时签出");
                    if (MainActivity.isSigned){
                        sign(false);
                        MyApplication.notifySign("自动签出");
                    }
            }
        } else {
            /*
             * 系统bug??
             * android.intent.action.TIME_SET  当调整系统时间后 这个action会收到两次
             */
            if (flag) {
                try {
                    /*  do some thing */
                } catch (Exception e) {
                    e.printStackTrace();
                }
                flag = false; //第二次置false
            } else {
                flag = true; //第一次置true
            }

        }
    }


}
