package com.tapptitude.weathertapp.weather;

/**
 * Created by ambroziepaval on 9/27/16.
 */
public class DailyWeatherInfo extends WeatherInfo {
    public String mDay;

    public DailyWeatherInfo(String mDay, Integer temp, WeatherConditions conditions) {
        this.mDay = mDay;
        super.mTemperature = temp;
        super.mWeatherConditions = conditions;
    }
}
