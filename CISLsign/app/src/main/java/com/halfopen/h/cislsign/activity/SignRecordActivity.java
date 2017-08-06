package com.halfopen.h.cislsign.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.halfopen.h.cislsign.R;
import com.halfopen.h.cislsign.adapter.MySpinnerAdapter;
import com.halfopen.h.cislsign.adapter.RecordListAdapter;
import com.halfopen.h.cislsign.bean.Record;
import com.halfopen.h.cislsign.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignRecordActivity extends AppCompatActivity {

    private String getRecordApi;
    private String getUsernamesApi;
    private Spinner spinner;
    private ListView recordListView;
    private SwipeRefreshLayout swapRefreshLayout;
    private String userId="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        getRecordApi = getString(R.string.get_record_api);
        getUsernamesApi = getString(R.string.get_names_api);
        spinner = (Spinner) findViewById(R.id.names_spinner);
        recordListView = (ListView) findViewById(R.id.record_listview);
        swapRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swapRefreshLayout);

        swapRefreshLayout.setOnRefreshListener(()->{

            Toast.makeText(getApplicationContext(), "开始刷新", Toast.LENGTH_SHORT).show();
            new Thread(()->{
                getRecord(userId);
            }).start();
            //swapRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    protected void onResume() {
        getNames();
        super.onResume();
    }

    private void getNames() {
        //建立缓存
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);

        //
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue rQueue = new RequestQueue(cache, network);

        rQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getUsernamesApi, (response) ->{
                Log.i("datainfo", response);
                Gson gson = new Gson();
                JsonParser parse =new JsonParser();  //创建json解析器
                JsonObject json=(JsonObject) parse.parse(response);
                Log.i("datainfo", json.get("result").getAsString());
                //Log.i("datainfo", json.get("data").getAsJsonArray().toString());
                List<User> userlist=gson.fromJson(json.get("data").getAsJsonArray().toString(), new TypeToken<List<User>>(){}.getType());

                for (User u:userlist
                     ) {
                    Log.i("datainfo", u.getUserid());
                    Log.i("datainfo", u.getUsername());
                }

                MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(getApplicationContext(), userlist);
                //mySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //将适配器添加到下拉列表上
                spinner.setAdapter(mySpinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("datainfo", "click"+id);
                        userId = id+"";
                        Log.i("datainfo", "url"+getRecordApi+"?userid="+userId);
                        getRecord(userId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        },(error)->Log.i("datainfo", error.toString())
        );

        //把请求添加到队列
        rQueue.add(stringRequest);
    }

    public void getRecord(String userId){
        //建立缓存
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);

        //
        Network network = new BasicNetwork(new HurlStack());
        RequestQueue rQueue = new RequestQueue(cache, network);

        rQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getRecordApi+"?userid="+userId, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.i("datainfo", response);
                Gson gson = new Gson();
                JsonParser parse =new JsonParser();  //创建json解析器

                try {
                    JsonObject json=(JsonObject) parse.parse(response);
                    Log.i("datainfo", json.get("result").getAsString());

                    Log.i("datainfo", json.get("data").getAsJsonArray().toString());
                    List<Record> recordList = gson.fromJson(json.get("data").getAsJsonArray().toString(), new TypeToken<List<Record>>() {
                    }.getType());

                    //生成动态数组，并且转载数据
                    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
                    for (Record r : recordList
                            ) {
                        Log.i("datainfo", r.getZh_name() + " " + r.getSignflag() + " " + r.getTimestamp());
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("ItemTitle", r.getZh_name());
                        map.put("ItemAction", r.getSignflag());
                        map.put("ItemText", r.getTimestamp());
                        mylist.add(map);
                    };
                    //生成适配器，数组===》ListItem
                    RecordListAdapter mSchedule = new RecordListAdapter(getApplicationContext(), mylist);
                    //添加并且显示
                    recordListView.setAdapter(mSchedule);
                }catch (NullPointerException e){
                    //生成动态数组，并且转载数据
                    ArrayList<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();

                    //生成适配器，数组===》ListItem
                    SimpleAdapter mSchedule = new SimpleAdapter(getApplicationContext(), tempList, R.layout.record_listitem,
                            new String[] {"ItemTitle", "ItemText"},
                            new int[] {R.id.ItemTitle,R.id.ItemText});
                    //添加并且显示
                    recordListView.setAdapter(mSchedule);
                    Toast.makeText(getApplicationContext(), "无记录", Toast.LENGTH_SHORT).show();
                }finally {
                    swapRefreshLayout.setRefreshing(false);
                    Log.d("flag--","onResponse(SignRecordActivity.java:179)-->>"+"刷新完成");
                    Toast.makeText(getApplicationContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                }
            }
        }, (error) ->{
                Log.i("datainfo", error.toString());
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();

            }
        );

        //把请求添加到队列
        rQueue.add(stringRequest);
    }


}
