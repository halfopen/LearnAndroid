package com.example.h.learn.activity;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.h.learn.R;

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
        String image_url = "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic.962.net%2Fup%2F2014-4%2F2014043015462944035.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D3323231745%2C3304164027%26fm%3D26%26gp%3D0.jpg";
        String imamge_url_thumb = "http://www.100eshu.com/uploads/MemberPrepareEbook/ba11c930-1dbf-4cfe-a33d-8f9cc5a3b818_online/OEBPS/images/cover_thumb.jpg";

        //http://www.100eshu.com/uploads/MemberPrepareEbook/ba11c930-1dbf-4cfe-a33d-8f9cc5a3b818_online/OEBPS/images/cover_Thumb.jpg
        String url = "http://i.imgur.com/7spzG.png";
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH)

                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).
                load(image_url).
                thumbnail(0.1f).//缩略图
                transition(new DrawableTransitionOptions().crossFade(500)).//动画
                apply(options).
                into(imageView);

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
