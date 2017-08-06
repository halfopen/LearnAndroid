package com.example.h.learn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by h on 2017/7/29.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> list;
    String[] titles;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] titles){
        super(fm);
        this.list = list;
        this.titles =titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
