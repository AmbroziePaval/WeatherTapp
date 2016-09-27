package com.tapptitude.weathertapp.weather.utils.json;

import java.util.Map;

/**
 * Created by ambroziepaval on 9/27/16.
 */
public class WeatherDataJSON {
    Map<String, ItemInfoJSON> weatherData;

    public Map<String, ItemInfoJSON> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Map<String, ItemInfoJSON> weatherData) {
        this.weatherData = weatherData;
    }
}
