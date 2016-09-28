package com.tapptitude.weathertapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tapptitude.weathertapp.weather.DailyWeatherInfo;
import com.tapptitude.weathertapp.weather.WeatherConditions;
import com.tapptitude.weathertapp.weather.WeatherInfo;
import com.tapptitude.weathertapp.weather.utils.TemperatureColorPicker;
import com.tapptitude.weathertapp.weather.utils.json.DataReaderJSON;

import java.util.List;
import java.util.Random;

/**
 * Created by ambroziepaval on 9/27/16.
 */
public class WeatherContentAdapter extends RecyclerView.Adapter<WeatherContentAdapter.WeatherContentVH> {
    private final Random random;
    private List<WeatherInfo> mWeatherInfoList;
    private int lastPosition = -1;

    public WeatherContentAdapter(Context context) {
        this.mWeatherInfoList = DataReaderJSON.getWeatherData(context);
        random = new Random();
    }

    @Override
    public WeatherContentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflatedView = layoutInflater.inflate(R.layout.item_content, parent, false);
        return new WeatherContentVH(inflatedView);
    }

    @Override
    public void onBindViewHolder(final WeatherContentVH holder, final int position) {
        WeatherInfo weatherInfo = mWeatherInfoList.get(position);
        holder.mWeatherTempTV.setText(weatherInfo.mTemperature + "° Celsius");
        holder.mWeatherConditionTV.setText(weatherInfo.mWeatherConditions.toString());
        holder.mWeatherSelectionTV.setText(((DailyWeatherInfo) weatherInfo).mDay);
        holder.mWeatherCardCV.setCardBackgroundColor(TemperatureColorPicker.getTemperatureColor(weatherInfo.mTemperature));
        holder.setConditionIcon(weatherInfo.mWeatherConditions);

        setAnimation(holder.itemView, position);

        holder.mRemoveCardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int poz = holder.getAdapterPosition();
                Animation animation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.slide_out_right);
                v.startAnimation(animation);
                lastPosition -= 1;
                mWeatherInfoList.remove(poz);
                notifyItemRemoved(poz);
//                notifyItemRangeChanged(poz, mWeatherInfoList.size());
            }
        });
    }

    public void swapItemsInList(int position, int target){
        WeatherInfo draggedCard = mWeatherInfoList.get(position);
        mWeatherInfoList.remove(position);
        mWeatherInfoList.add(target, draggedCard);
    }

    public void removeItem(int position){
        mWeatherInfoList.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeRemoved(position, mWeatherInfoList.size());
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mWeatherInfoList.size();
    }

    public void addNewItem() {
        int temp = random.nextInt(40);
        WeatherConditions weatherConditions = WeatherConditions.values()[random.nextInt(5)];
        DailyWeatherInfo newInfo = new DailyWeatherInfo("NEW", temp, weatherConditions);
        mWeatherInfoList.add(newInfo);
        int size = mWeatherInfoList.size();
        notifyItemInserted(size - 1);
//        notifyItemRangeChanged(size - 1, size);
    }

    class WeatherContentVH extends RecyclerView.ViewHolder {
        private final Resources resources;
        CardView mWeatherCardCV;
        TextView mWeatherTempTV;
        TextView mWeatherConditionTV;
        TextView mWeatherSelectionTV;
        ImageView mWeatherConditionImageIV;
        Button mRemoveCardB;

        public WeatherContentVH(final View itemView) {
            super(itemView);

            mWeatherTempTV = (TextView) itemView.findViewById(R.id.ic_tv_weather_temp);
            mWeatherCardCV = (CardView) itemView.findViewById(R.id.ic_cv_weather);
            mWeatherConditionTV = (TextView) itemView.findViewById(R.id.ic_tv_weather_condition);
            mWeatherSelectionTV = (TextView) itemView.findViewById(R.id.ic_tv_weather_selection);
            mWeatherConditionImageIV = (ImageView) itemView.findViewById(R.id.ic_iv_weather_condition);
            mRemoveCardB = (Button) itemView.findViewById(R.id.ic_b_remove);
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
