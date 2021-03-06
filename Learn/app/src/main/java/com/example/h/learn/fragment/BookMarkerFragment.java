package com.example.h.learn.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.h.learn.R;

import java.util.ArrayList;
import java.util.List;

public class BookMarkerFragment extends Fragment {

    public interface BookmarkListener {
        public void onChangeBookmark(String bookmark);
    }

    public BookmarkListener myActionObject = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        initListView(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {

        if (!(activity instanceof BookmarkListener)) {
            throw new ClassCastException();
        }
        myActionObject = (BookmarkListener) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item1 = menu.add(1, 1, 1, "分享");
        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT
                | MenuItem.SHOW_AS_ACTION_ALWAYS);
        ImageView imageView = new ImageView(
                BookMarkerFragment.this.getActivity());
        imageView.setBackgroundResource(R.drawable.ic_plusone_medium_off_client);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
        item1.setActionView(imageView);

    }


    // 初始化listview
    public void initListView(View view) {
        ListView lv = (ListView) view.findViewById(R.id.listview);
        List<String> list = new ArrayList<String>();
        list.add("网易");
        list.add("腾讯");
        list.add("新浪");
        list.add("搜狐");
        ArrayAdapter adapter = new ArrayAdapter(
                BookMarkerFragment.this.getActivity(), R.layout.mytext,
                R.id.text, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                switch (position) {
                    case 0:
                        myActionObject.onChangeBookmark("http://www.163.com");
                        break;
                    case 1:
                        myActionObject.onChangeBookmark("http://www.qq.com");
                        break;
                    case 2:
                        myActionObject.onChangeBookmark("http://www.sina.com");
                        break;
                    case 3:
                        myActionObject.onChangeBookmark("http://www.sohu.com");
                        break;

                }
            }

        });

    }
}
