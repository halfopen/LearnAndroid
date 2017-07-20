package com.example.h.learn;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyActivity extends AppCompatActivity  implements View.OnClickListener{

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        int[] ids = new int[]{
                R.id.btn_send_simple_request,
                R.id.btn_request_queue,
                R.id.send_by_singleton
        };

        for (int id:ids
             ) {
            findViewById(id).setOnClickListener(this);
        }

        //获取控件
        imageView= (ImageView) findViewById(R.id.my_image);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_simple_request:
                Log.i("info", "click btn_send_simple_request");
                sendSimpleRequest();
                break;
            case R.id.btn_request_queue:
                sendRequestQueue();
                break;
            case R.id.send_by_singleton:
                getImageByUrl();
                break;
            default:
        }
    }

    private void getImageByUrl() {
        Toast.makeText(getApplicationContext(), "开始加载图片", Toast.LENGTH_SHORT).show();
        String url = "http://i.imgur.com/7spzG.png";
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener(){
                @Override
                public void onResponse(Object response) {
                    imageView.setImageBitmap((Bitmap)response);
                    Toast.makeText(getApplicationContext(), "加载成功", Toast.LENGTH_SHORT).show();
                }
            },0,0,null, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        );
        MySingleton.getInstance(this).addToRequestQueue(imageRequest);
    }

    private void sendRequestQueue() {
        //建立缓存
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);

        //
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue rQueue = new RequestQueue(cache, network);

        rQueue.start();

        String url = "https://www.baidu.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("info", response);
                Toast.makeText(getApplicationContext(), response.substring(0, 500), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("info", error.toString());
            }
        });

        //把请求添加到队列
        rQueue.add(stringRequest);
    }

    public void sendSimpleRequest(){

        RequestQueue rQuene = Volley.newRequestQueue(this);
        String url = "https://www.baidu.com";

        Log.i("info", "send start");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener(){
            @Override
            public void onResponse(Object response) {
                Log.i("info", response.toString());
                Toast.makeText(getApplicationContext(), response.toString().substring(0,500),
                        Toast.LENGTH_SHORT).show();

                Log.i("info", "send complete");
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("info", "error");
                Toast.makeText(getApplicationContext(), "error",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //添加到请求队列，发送请求
        rQuene.add(stringRequest);
        Log.i("info", "send end");
    }
}
