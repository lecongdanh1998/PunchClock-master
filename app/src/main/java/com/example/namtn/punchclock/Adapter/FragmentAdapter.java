package com.example.namtn.punchclock.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.namtn.punchclock.ModelView.FragmentInformation;

import java.util.List;


public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInformation> mListFragmentInformation;

    public FragmentAdapter(FragmentManager fm, List<FragmentInformation> mListFragmentInformation) {
        super(fm);
        this.mListFragmentInformation = mListFragmentInformation;
    }

    @Override
    public Fragment getItem(int i) {
        return mListFragmentInformation.get(i).getLayoutFragment();
    }

    @Override
    public int getCount() {
        return mListFragmentInformation.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mListFragmentInformation.get(position).getTitleFragment();
    }
}
