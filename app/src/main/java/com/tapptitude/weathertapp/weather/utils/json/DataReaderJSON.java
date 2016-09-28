package com.tapptitude.weathertapp.weather.utils.json;

import android.content.Context;

import com.google.gson.Gson;
import com.tapptitude.weathertapp.R;
import com.tapptitude.weathertapp.weather.DailyWeatherInfo;
import com.tapptitude.weathertapp.weather.WeatherConditions;
import com.tapptitude.weathertapp.weather.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ambroziepaval on 9/27/16.
 */
public class DataReaderJSON {
    public static List<WeatherInfo> getWeatherData(Context context) {

        InputStream inputStream = context.getResources().openRawResource(R.raw.weather_data);

        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer);

            return parseJSONWeatherData(json);
        } catch (IOException ignored) {

        }
        return null;
    }

    private static List<WeatherInfo> parseJSONWeatherData(String json) {
        List<WeatherInfo> weatherInfoList = new ArrayList<>();

        try {
            JSONObject weatherDataJSON = new JSONObject(json);
            JSONArray weatherDataArray = weatherDataJSON.getJSONArray("weather_info");
            for (int i = 0; i < weatherDataArray.length(); i++) {
                JSONObject entry = weatherDataArray.getJSONObject(i);
                String day = entry.keys().next();
                JSONObject info = entry.getJSONObject(day);
                String condition = info.getString("cond");
                int temp = info.getInt("temp");
                weatherInfoList.add(new DailyWeatherInfo(day, temp, WeatherConditions.valueOf(condition.toUpperCase())));
            }
        } catch (JSONException ignored) {
        }

        return weatherInfoList;
    }
}
