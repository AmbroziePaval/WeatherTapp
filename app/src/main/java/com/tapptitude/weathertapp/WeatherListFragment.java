package com.tapptitude.weathertapp;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tapptitude.weathertapp.weather.DailyWeatherInfo;

/**
 * Created by ambroziepaval on 9/29/16.
 */
public class WeatherListFragment extends Fragment {
    private WeatherDetailFragment mWeatherDetailsFragment;
    private RecyclerView mWeatherContentRV;
    private ViewGroup parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        parent = container;

        mWeatherContentRV = (RecyclerView) view.findViewById(R.id.am_rv_dailyWeatherContent);
        final WeatherContentAdapter weatherAdapter = new WeatherContentAdapter(container.getContext());
        mWeatherContentRV.setAdapter(weatherAdapter);
        mWeatherContentRV.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mWeatherContentRV.setItemAnimator(new DefaultItemAnimator());

        FloatingActionButton mAddCardFAB = (FloatingActionButton) view.findViewById(R.id.am_fab_addItem);
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
                if (direction == ItemTouchHelper.RIGHT) {
                    weatherAdapter.removeItem(viewHolder.getAdapterPosition());
                } else {
                    DailyWeatherInfo weatherInfo = (DailyWeatherInfo) weatherAdapter.getItemDetails(viewHolder.getAdapterPosition());
                    ImageView mWeatherConditionImageIV = ((WeatherContentAdapter.WeatherContentVH) viewHolder).mWeatherConditionImageIV;
                    TextView mWeatherTempTV = ((WeatherContentAdapter.WeatherContentVH) viewHolder).mWeatherTempTV;

                    mWeatherDetailsFragment = new WeatherDetailFragment();
                    mWeatherDetailsFragment.updateDetails(weatherInfo.mTemperature, mWeatherTempTV.getText().toString(),weatherInfo.mWeatherConditions.toString(), weatherInfo.mDay, ((BitmapDrawable)mWeatherConditionImageIV.getDrawable()).getBitmap());
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.am_fl_placeholder, mWeatherDetailsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mWeatherContentRV);

        return view;
    }
}
