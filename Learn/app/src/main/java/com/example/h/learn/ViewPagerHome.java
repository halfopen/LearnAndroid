package com.example.h.learn;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.h.learn.adapter.MyFragmentAdapter;
import com.example.h.learn.fragment.BlankFragment1;
import com.example.h.learn.fragment.BlankFragment2;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerHome extends AppCompatActivity  implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, BlankFragment1.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener{

    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = { BlankFragment1.class, BlankFragment2.class };
    private int imageViewArray[] = { R.drawable.tab_home_btn, R.drawable.tab_view_btn };
    private String textViewArray[] = { "首页", "分类"};
    private List<Fragment> list = new ArrayList<Fragment>();
    private ViewPager vp;

    private TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_home);

        initView();
        initPage();
    }

    private void initView() {
        tablayout = (TabLayout) findViewById(R.id.tab_layout);
        vp = (ViewPager) findViewById(R.id.view_pager);

        /*实现OnPageChangeListener接口,目的是监听Tab选项卡的变化，然后通知ViewPager适配器切换界面*/
        /*简单来说,是为了让ViewPager滑动的时候能够带着底部菜单联动*/

        vp.addOnPageChangeListener(this);//设置页面切换时的监听器
        layoutInflater = LayoutInflater.from(this);//加载布局管理器

        /*实例化FragmentTabHost对象并进行绑定*/
        mTabHost = (FragmentTabHost) findViewById(R.id.content_tab_host);//绑定tahost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.view_pager);//绑定viewpager

        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        /*简单来说,是为了当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment*/
        mTabHost.setOnTabChangedListener(this);

        //tablayout.setupWithViewPager(vp);

        int count = textViewArray.length;
        for(int i=0;i<count;++i){
            TabHost.TabSpec tabSpec= mTabHost.newTabSpec(textViewArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.setTag(i);
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);//设置Tab被选中的时候颜色改变
        }
    }


    /*初始化Fragment*/
    private void initPage() {
        BlankFragment1 fragment1 = new BlankFragment1();
        BlankFragment2 fragment2 = new BlankFragment2();

        list.add(fragment1);
        list.add(fragment2);

        //绑定Fragment适配器
        vp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), list, textViewArray));
        mTabHost.getTabWidget().setDividerDrawable(null);
        tablayout.setupWithViewPager(vp);

    }
    private View getTabItemView(int i) {
        //将xml布局转换为view对象
        View view = layoutInflater.inflate(R.layout.fragment_tab_content, null);
        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setBackgroundResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //Log.d("flag--","onPageSelected(ViewPagerHome.java:103)-->>");
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置View覆盖子类控件而直接获得焦点
        mTabHost.setCurrentTab(position);//根据位置Postion设置当前的Tab
        widget.setDescendantFocusability(oldFocusability);//设置取消分割线
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int position = mTabHost.getCurrentTab();
        vp.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
