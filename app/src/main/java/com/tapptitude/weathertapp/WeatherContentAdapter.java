package com.tapptitude.weathertapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ambroziepaval on 9/27/16.
 */
public class WeatherContentAdapter extends RecyclerView.Adapter<WeatherContentAdapter.WeatherContentVH> {

    @Override
    public WeatherContentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_content, parent, false);
        return new WeatherContentVH(inflatedView);
    }

    @Override
    public void onBindViewHolder(WeatherContentVH holder, int position) {
        holder.mWeatherTV.setText("Weather " + position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class WeatherContentVH extends RecyclerView.ViewHolder {
        TextView mWeatherTV;

        public WeatherContentVH(View itemView) {
            super(itemView);

            mWeatherTV = (TextView) itemView.findViewById(R.id.ic_tv_weather_temp);
        }
    }
}
