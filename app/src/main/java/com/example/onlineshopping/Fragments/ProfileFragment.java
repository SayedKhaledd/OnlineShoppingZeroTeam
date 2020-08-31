package com.example.onlineshopping.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.Constants;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.example.onlineshopping.ShoppingDb;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    CircleImageView profileImage;
    private SharedPreferences sharedPreferences;
    TextView userName, userEmail;
    BarChart barChart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = rootView.findViewById(R.id.profile_image);
        userName = rootView.findViewById(R.id.user_name);
        userEmail = rootView.findViewById(R.id.user_email);
        MainActivity.homeFragment = true;
        sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String email = ((MainActivity) getActivity()).getEmail();
        String nameStr = ((MainActivity) getActivity()).getname();
        if (email != null) {
            userEmail.setText(email);
        }
        if (nameStr != null) {
            userName.setText(nameStr);
        }
        String strUri = ((MainActivity) getActivity()).getUriStr();
        if (strUri != null) {
            profileImage.setImageURI(Uri.parse(strUri));
        }
        barChart = (BarChart) rootView.findViewById(R.id.bar_chart);
        try {
            createChart();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void createChart() throws ParseException {
        Legend l = barChart.getLegend();


        ShoppingDb shoppingDb = new ShoppingDb(getContext());
        Cursor cursor = shoppingDb.showOrders();
        int id = ((MainActivity) getActivity()).getIdCus(userEmail.getText().toString());
        Log.d("profile", id + "");
        ArrayList<BarEntry> barChartArrayList = new ArrayList<>();
        int[] arr = new int[12];
        while (!cursor.isAfterLast()) {
            if (cursor.getInt(3) == id) {
                String dateString = cursor.getString(2);
                DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                Date date = dateFormat.parse(dateString);
                int month = date.getMonth();
                arr[month]++;
            }
            cursor.moveToNext();
        }
        for (int i = 0; i < arr.length; i++) {
            barChartArrayList.add(new BarEntry(i, arr[i]));

        }
        BarDataSet barDataSet = new BarDataSet(barChartArrayList, "mohamed");


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Jan");
        arrayList.add("Feb");
        arrayList.add("Mar");
        arrayList.add("Apr");
        arrayList.add("May");
        arrayList.add("Jun");
        arrayList.add("Jul");
        arrayList.add("Aug");
        arrayList.add("Sep");
        arrayList.add("Oct");
        arrayList.add("Nov");
        arrayList.add("Dec");


        barChart.setDragXEnabled(true);
        barChart.animateY(4000);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(arrayList));
        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.setDragEnabled(true);
        barChart.setTouchEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(true);
        barChart.setDoubleTapToZoomEnabled(true);
        barChart.setFitBars(true);
        barChart.setVisibleXRangeMaximum(5.5f);
        barChart.invalidate();


    }

}


