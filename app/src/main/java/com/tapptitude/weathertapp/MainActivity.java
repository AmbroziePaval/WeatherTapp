package com.tapptitude.weathertapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mWeatherContentRV;
    private FloatingActionButton mAddCardFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWeatherContentRV = (RecyclerView) findViewById(R.id.am_rv_dailyWeatherContent);
        final WeatherContentAdapter weatherAdapter = new WeatherContentAdapter(getApplicationContext());
        mWeatherContentRV.setAdapter(weatherAdapter);
        mWeatherContentRV.setLayoutManager(new LinearLayoutManager(this));
        mWeatherContentRV.setItemAnimator(new DefaultItemAnimator());

        mAddCardFAB = (FloatingActionButton) findViewById(R.id.am_fab_addItem);
        mAddCardFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherAdapter.addNewItem();
                mWeatherContentRV.scrollToPosition(weatherAdapter.getItemCount() - 1);
            }
        });


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                weatherAdapter.swapItemsInList(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                weatherAdapter.removeItem(viewHolder.getAdapterPosition());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mWeatherContentRV);
    }
}
