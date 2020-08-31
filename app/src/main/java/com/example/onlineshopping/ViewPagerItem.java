package com.example.onlineshopping;

import androidx.fragment.app.Fragment;

public class ViewPagerItem {
    private String name;
    private Fragment fragment;

    public ViewPagerItem(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
