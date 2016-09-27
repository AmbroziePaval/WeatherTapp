package com.tapptitude.weathertapp.weather.utils;

import android.graphics.Color;

/**
 * Created by ambroziepaval on 9/27/16.
 */
public class TemperatureColorPicker {

    public static int getTemperatureColor(int mTemp) {
        if (mTemp <= 10) {
            return getColdTemperatureColor(mTemp);
        } else if (mTemp > 10 && mTemp <= 15) {
            return getMediumTemperatureColor(mTemp);
        } else if (mTemp > 15 && mTemp <= 20) {
            return getWarmTemperatureColor(mTemp);
        } else {
            return getHotTemperatureColor(mTemp);
        }
    }

    /**
     * Get a color with blue 255, red 0 and green value variable(10temp=250)
     * 10 temp values included above 0
     *
     * @return Color value
     */
    private static int getColdTemperatureColor(int mTemp) {
        if (mTemp < 0)
            return Color.rgb(0, 0, 255);
        else {
            int greenValue = 25 * mTemp;
            return Color.rgb(0, greenValue, 255);
        }
    }

    /**
     * Get a color with green 255, red 0 and blue value variable(15temp=0)
     * 5 temp values included
     *
     * @return Color value
     */
    public static int getMediumTemperatureColor(int mTemp) {
        int blueValue = 255 - (mTemp - 10) * 51;
        return Color.rgb(0, 255, blueValue);
    }

    /**
     * Get a color with red 255, blue 0 and green value variable(40temp=255)
     * 20 temp values included
     *
     * @return Color value
     */
    public static int getWarmTemperatureColor(int mTemp) {
        int redValue = (mTemp - 15) * 51;
        return Color.rgb(redValue, 255, 0);
    }

    public static int getHotTemperatureColor(int mTemp) {
        if(mTemp >40){
            return Color.rgb(255, 0, 0);
        } else {
            int greenValue = 255 - (mTemp - 20) * 12;
            return Color.rgb(255, greenValue, 0);
        }
    }
}
