package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<ViewPagerItem> viewPagerItems;

    public ViewPagerAdapter(FragmentManager fm, List<ViewPagerItem> viewPagerItems) {
        super(fm);
        this.viewPagerItems = viewPagerItems;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return viewPagerItems.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return viewPagerItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return viewPagerItems.get(position).getName();
    }
}