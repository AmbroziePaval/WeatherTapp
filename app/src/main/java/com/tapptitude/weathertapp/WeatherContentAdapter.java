package com.tapptitude.weathertapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tapptitude.weathertapp.weather.DailyWeatherInfo;
import com.tapptitude.weathertapp.weather.WeatherConditions;
import com.tapptitude.weathertapp.weather.WeatherInfo;
import com.tapptitude.weathertapp.weather.utils.TemperatureColorPicker;
import com.tapptitude.weathertapp.weather.utils.json.DataReaderJSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ambroziepaval on 9/27/16.
 */
public class WeatherContentAdapter extends RecyclerView.Adapter<WeatherContentAdapter.WeatherContentVH> {
    private List<WeatherInfo> mWeatherInfoList;

    public WeatherContentAdapter(Context context) {
        this.mWeatherInfoList = DataReaderJSON.getWeatherData(context);
    }

    @Override
    public WeatherContentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_content, parent, false);
        return new WeatherContentVH(inflatedView);
    }

    @Override
    public void onBindViewHolder(WeatherContentVH holder, int position) {
        WeatherInfo weatherInfo = mWeatherInfoList.get(position);
        holder.mWeatherTempTV.setText(weatherInfo.mTemperature + " degrees Celsius");
        holder.mWeatherConditionTV.setText(weatherInfo.mWeatherConditions.toString());
        holder.mWeatherSelectionTV.setText(((DailyWeatherInfo) weatherInfo).mDay);
        holder.mWeatherCardCV.setCardBackgroundColor(TemperatureColorPicker.getTemperatureColor(weatherInfo.mTemperature));
        holder.setConditionIcon(weatherInfo.mWeatherConditions);
    }

    @Override
    public int getItemCount() {
        return mWeatherInfoList.size();
    }

    class WeatherContentVH extends RecyclerView.ViewHolder {
        private final Resources resources;
        CardView mWeatherCardCV;
        TextView mWeatherTempTV;
        TextView mWeatherConditionTV;
        TextView mWeatherSelectionTV;
        ImageView mWeatherConditionImageIV;

        public WeatherContentVH(View itemView) {
            super(itemView);

            mWeatherTempTV = (TextView) itemView.findViewById(R.id.ic_tv_weather_temp);
            mWeatherCardCV = (CardView) itemView.findViewById(R.id.ic_cv_weather);
            mWeatherConditionTV = (TextView) itemView.findViewById(R.id.ic_tv_weather_condition);
            mWeatherSelectionTV = (TextView) itemView.findViewById(R.id.ic_tv_weather_selection);
            mWeatherConditionImageIV = (ImageView) itemView.findViewById(R.id.ic_iv_weather_condition);
            resources = itemView.getResources();
        }

        public void setConditionIcon(WeatherConditions weatherConditions) {
            Bitmap bitmapCondition;
            if (weatherConditions == WeatherConditions.CLEAR)
                bitmapCondition = BitmapFactory.decodeResource(resources, R.drawable.clear);
            else if (weatherConditions == WeatherConditions.RAIN)
                bitmapCondition = BitmapFactory.decodeResource(resources, R.drawable.rain);
            else if (weatherConditions == WeatherConditions.CLOUDS)
                bitmapCondition = BitmapFactory.decodeResource(resources, R.drawable.clouds);
            else if (weatherConditions == WeatherConditions.SNOW)
                bitmapCondition = BitmapFactory.decodeResource(resources, R.drawable.snow);
            else
                bitmapCondition = BitmapFactory.decodeResource(resources, R.drawable.fog);
            mWeatherConditionImageIV.setImageBitmap(bitmapCondition);
        }
    }
}
