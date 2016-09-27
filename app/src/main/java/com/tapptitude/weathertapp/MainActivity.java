package com.tapptitude.weathertapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mWeatherContentRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherContentRV = (RecyclerView) findViewById(R.id.am_rv_dailyWeatherContent);
        mWeatherContentRV.setAdapter(new WeatherContentAdapter());
        mWeatherContentRV.setLayoutManager(new LinearLayoutManager(this));
        mWeatherContentRV.setItemAnimator(new DefaultItemAnimator());
    }
}
