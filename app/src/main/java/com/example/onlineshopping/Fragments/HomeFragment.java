package com.example.onlineshopping.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onlineshopping.R;
import com.example.onlineshopping.ViewPagerAdapter;
import com.example.onlineshopping.ViewPagerItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), getViewPagerItems());
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        TabLayout tabs = rootView.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(2).setIcon(R.drawable.ic1);
        tabs.getTabAt(0).setIcon(R.drawable.ic2);
        tabs.getTabAt(1).setIcon(R.drawable.ic3);


        return rootView;

    }

    private List<ViewPagerItem> getViewPagerItems() {
        List<ViewPagerItem> viewPagerItems = new ArrayList<>();
        viewPagerItems.add(new ViewPagerItem("clothes", ClothesFragment.newInstance()));
        viewPagerItems.add(new ViewPagerItem("laptops", LaptopsFragment.newInstance()));
        viewPagerItems.add(new ViewPagerItem("Mobiles", MobilesFragment.newInstance()));

        return viewPagerItems;
    }

}