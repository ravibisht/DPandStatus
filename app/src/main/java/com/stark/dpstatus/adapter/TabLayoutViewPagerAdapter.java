package com.stark.dpstatus.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class TabLayoutViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> allFragments;
    private List<String> titles;

    public TabLayoutViewPagerAdapter(@NonNull FragmentManager fm, List<Fragment> allFragments, List<String> titles) {
        super(fm);
        this.allFragments = allFragments;
        this.titles = titles;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return allFragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }
}
