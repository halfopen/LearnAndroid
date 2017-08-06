package com.halfopen.h.cislsign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.halfopen.h.cislsign.R;
import com.halfopen.h.cislsign.bean.User;

import java.util.List;


/**
 * Created by h on 2017/7/20.
 */

public class MySpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> userList;


    public MySpinnerAdapter(Context context, List<User> user){
        this.mContext = context;
        this.userList = user;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    //重写获取id,返回用户id
    @Override
    public long getItemId(int position) {
        return Long.parseLong(userList.get(position).getUserid());
    }


    //使用自定义item样式
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
        convertView = _LayoutInflater.inflate(R.layout.spinner_item, null);
        if (convertView != null) {
            TextView _TextView1 = (TextView) convertView
                    .findViewById(R.id.userId);
            TextView _TextView2 = (TextView) convertView
                    .findViewById(R.id.userName);
            _TextView1.setText("用户id:"+userList.get(position).getUserid());
            _TextView2.setText(userList.get(position).getZh_name());
        }
        return convertView;
    }
}
