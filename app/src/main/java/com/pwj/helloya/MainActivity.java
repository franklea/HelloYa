package com.pwj.helloya;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;


public class MainActivity extends AppCompatActivity {
    private String[] mTitles = new String[]{"首页", "地址","推广", "新闻"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(mTitles[0]).setIcon(R.mipmap.home);
        tabLayout.getTabAt(1).setText(mTitles[1]).setIcon(R.mipmap.location);
        tabLayout.getTabAt(2).setText(mTitles[2]).setIcon(R.mipmap.phone);
        tabLayout.getTabAt(3).setText(mTitles[3]).setIcon(R.mipmap.news);

    }

}
