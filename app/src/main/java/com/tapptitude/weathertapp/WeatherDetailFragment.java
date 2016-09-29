package com.tapptitude.weathertapp;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tapptitude.weathertapp.weather.utils.TemperatureColorPicker;

/**
 * Created by ambroziepaval on 9/29/16.
 */
public class WeatherDetailFragment extends Fragment {
    private LinearLayout mHeaderLL;
    public TextView mTitleTV;
    public TextView mSubTitleTV;
    public ImageView mWeatherIconIV;
    private TextView mTimeTV;
    String mTitle;
    String mSubtitle;
    String mTime;
    Integer mTemp;
    Bitmap mImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitleTV = (TextView) view.findViewById(R.id.fd_tv_title);
        mSubTitleTV = (TextView) view.findViewById(R.id.fd_tv_subtitle);
        mWeatherIconIV = (ImageView) view.findViewById(R.id.fd_iv_weather_icon);
        mTimeTV = (TextView) view.findViewById(R.id.fd_tv_time);
        mHeaderLL = (LinearLayout) view.findViewById(R.id.fd_ll_header);

        mTitleTV.setText(mTitle);
        mSubTitleTV.setText(mSubtitle);
        mWeatherIconIV.setImageBitmap(mImage);
        mTimeTV.setText(mTime);
        mHeaderLL.setBackgroundColor(TemperatureColorPicker.getTemperatureColor(mTemp));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void updateDetails(Integer temp, String title, String subtitle, String time, Bitmap weatherIconId) {
        mTemp = temp;
        mTitle = title;
        mSubtitle = subtitle;
        mTime = time;
        mImage = weatherIconId;
    }
}
