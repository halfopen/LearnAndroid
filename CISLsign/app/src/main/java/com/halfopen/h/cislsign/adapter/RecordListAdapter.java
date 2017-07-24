package com.halfopen.h.cislsign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.halfopen.h.cislsign.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by h on 2017/7/24.
 */

public class RecordListAdapter extends BaseAdapter {
    //数据
    private ArrayList<HashMap<String, String>> list;
    private Context context;

    public RecordListAdapter(Context context, ArrayList<HashMap<String, String>> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //自定义界面
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(this.context);
        convertView = _LayoutInflater.inflate(R.layout.record_listitem, null);

        if(convertView!=null){
            TextView nameView = (TextView) convertView.findViewById(R.id.ItemTitle);        //用户名
            TextView actionView = (TextView) convertView.findViewById(R.id.ItemAction);     //签入/签出
            TextView timeView = (TextView) convertView.findViewById(R.id.ItemText);         //时间

            String name = list.get(position).get("ItemTitle");
            String action = list.get(position).get("ItemAction");
            String time = list.get(position).get("ItemText");

            nameView.setText(name);
            actionView.setText(action);
            timeView.setText(time);

            if (action.equals("签出")){
                actionView.setTextColor(convertView.getResources().getColorStateList(R.color.colorAccent));
            }
        }

        return convertView;
    }
}
