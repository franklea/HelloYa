package com.pwj.helloya;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by leon on 3/7/18.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 4;
    private Context context;
    public SimpleFragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        int type;
        switch (position) {
            case 1:
                return new FragmentLocation();
            case 2:
                return new FragmentPhone();
            case 3:
                return new FragmentNews();
            default:
                return new FragmentHome();

        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
