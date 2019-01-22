package com.example.namtn.punchclock.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Tobaco Mojito on 05-Nov-17.
 */

public class AdapterTabLayoutFrangment extends FragmentPagerAdapter {

    Context context;
    ArrayList<String> list_title_tab;
    ArrayList<Fragment> list_fragment;
    private int page_count = 6;

    public AdapterTabLayoutFrangment(FragmentManager fm,
                                     Context context, ArrayList<String> list_title_tab,
                                     ArrayList<Fragment> list_fragment) {
        super(fm);
        this.context = context;
        this.list_title_tab = list_title_tab;
        this.list_fragment = list_fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return  list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_title_tab.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  list_title_tab.get(position);
    }
}
