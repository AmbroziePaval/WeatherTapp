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
//            JSONObject jsonObject = new JSONObject(json);

            return parseJSONWeatherData(json);
        } catch (IOException ignored) {

        }
        return null;
    }

    private static List<WeatherInfo> parseJSONWeatherData(String json) {
        List<WeatherInfo> weatherInfoList = new ArrayList<>();

        Gson gson = new Gson();
        WeatherDataJSON weatherDataJSON = gson.fromJson(json, WeatherDataJSON.class);

        for(String day : weatherDataJSON.getWeatherData().keySet()){
            ItemInfoJSON itemInfoJSON = weatherDataJSON.getWeatherData().get(day);
            WeatherConditions weatherConditions = WeatherConditions.valueOf(itemInfoJSON.getCond().toUpperCase());
            DailyWeatherInfo dailyWeatherInfo = new DailyWeatherInfo(day, itemInfoJSON.getTemp(), weatherConditions);
            weatherInfoList.add(dailyWeatherInfo);
        }

        return weatherInfoList;
    }
}
