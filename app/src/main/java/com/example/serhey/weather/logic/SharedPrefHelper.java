package com.example.serhey.weather.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.serhey.weather.callBackNow.BackNow;
import com.example.serhey.weather.callBackWeek.BackWeek;
import com.example.serhey.weather.core.App;

/**
 * Created by Serhey on 22.11.2016.
 */

public class SharedPrefHelper {

    private static final String APP_PREFERENCES = "settings";
    private static final String CITY = "city";
    private static final String DEFAULT_CITY = "Kiev";
    private static final String REFRESH_TIME = "refresh_time";
    private static final int DEFAULT_REFRESH_TIME = 3;
    private static final String LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "en";

    private static final String TEMPERATURE_VISIBILITY = "temperature";
    private static final int DEFAULT_TEMPERATURE = 1;

    private static final String HUMIDITY_VISIBILITY = "humidity";
    private static final int DEFAULT_HUMIDITY = 1;

    private static final String WIND_VISIBILITY = "wind";
    private static final int DEFAULT_WIND = 1;

    private static final String CITY_VISIBILITY = "cityVisibility";
    private static final int DEFAULT_CITY_VISIBILITY = 1;

    private static final String CLOUDINESS_VISIBILITY = "cloudiness";
    private static final int DEFAULT_CLOUDINESS = 1;

    private static final String ICON_VISIBILITY = "icon";
    private static final int DEFAULT_ICON = 1;

    private static final String DATE_VISIBILITY = "date";
    private static final int DEFAULT_DATE = 1;

    private static final String BACKGROUND = "background";
    public static final String BACKGROUND_DEFAULT = "off";

    public static final int REFRESH_TIME_ONE_HOUR = 3600000;
    public static final int REFRESH_TIME_TWO_HOURS = 7200000;
    public static final int REFRESH_TIME_THREE_HOURS = 10800000;

    public static final String DATA_WEATHER_NOW = "weather_now";
    public static final String DATA_WEATHER_NOW_DEFAULT = null;

    public static final String DATA_WEATHER_WEEK = "weather_week";
    private static final String DATA_WEATHER_WEEK_DEFAULT = null;

    public static final String NOTIFICATION = "notification";
    public static final String NOTIFICATION_DEFAULT = "off";
    private static final String TAG = "SharedPrefHelper";

    private static SharedPrefHelper ourInstance;

    public static SharedPrefHelper getInstance() {
        Context context = App.getContext();
        if (ourInstance == null) {
            ourInstance = new SharedPrefHelper(context);
        }

        return ourInstance;
    }

    private SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    private SharedPreferences sharedPreferences;

    public void saveCity(String city) {
        sharedPreferences.edit().putString(CITY, city).apply();
    }

    public String getCity() {
        return sharedPreferences.getString(CITY, DEFAULT_CITY);
    }

    public void saveTimeRefresh(int time) {
        sharedPreferences.edit().putInt(REFRESH_TIME, time).apply();
    }

    public int getTimeRefresh() {
        return sharedPreferences.getInt(REFRESH_TIME, DEFAULT_REFRESH_TIME);
    }

    public void saveLanguage(String status) {
        sharedPreferences.edit().putString(LANGUAGE, status).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(LANGUAGE, DEFAULT_LANGUAGE);
    }

    public void saveTemperatureVisibility(int status) {
        sharedPreferences.edit().putInt(TEMPERATURE_VISIBILITY, status).apply();
    }

    public int getTemperatureVisibility() {
        return sharedPreferences.getInt(TEMPERATURE_VISIBILITY, DEFAULT_TEMPERATURE);
    }

    public void saveHumidityVisibility(int status) {
        sharedPreferences.edit().putInt(HUMIDITY_VISIBILITY, status).apply();
    }

    public int getHumidityVisibility() {
        return sharedPreferences.getInt(HUMIDITY_VISIBILITY, DEFAULT_HUMIDITY);
    }

    public void saveWindVisibility(int status) {
        sharedPreferences.edit().putInt(WIND_VISIBILITY, status).apply();
    }

    public int getWindVisibility() {
        return sharedPreferences.getInt(WIND_VISIBILITY, DEFAULT_WIND);
    }

    public void saveCityVisibility(int status) {
        sharedPreferences.edit().putInt(CITY_VISIBILITY, status).apply();
    }

    public int getCityVisibility() {
        return sharedPreferences.getInt(CITY_VISIBILITY, DEFAULT_CITY_VISIBILITY);
    }

    public void saveCloudinessVisibility(int status) {
        sharedPreferences.edit().putInt(CLOUDINESS_VISIBILITY, status).apply();
    }

    public int getCloudinessVisibility() {
        return sharedPreferences.getInt(CLOUDINESS_VISIBILITY, DEFAULT_CLOUDINESS);
    }

    public void saveIconVisibility(int status) {
        sharedPreferences.edit().putInt(ICON_VISIBILITY, status).apply();
    }

    public int getIconVisibility() {
        return sharedPreferences.getInt(ICON_VISIBILITY, DEFAULT_ICON);
    }

    public void saveDateVisibility(int status) {
        sharedPreferences.edit().putInt(DATE_VISIBILITY, status).apply();
    }

    public int getDateVisibility() {
        return sharedPreferences.getInt(DATE_VISIBILITY, DEFAULT_DATE);
    }

    public void saveStatusBackground(String status) {
        sharedPreferences.edit().putString(BACKGROUND, status).apply();
    }

    public String getStatusBackground() {
        return sharedPreferences.getString(BACKGROUND, BACKGROUND_DEFAULT);
    }

    public void saveStatusNotification(String status){
        sharedPreferences.edit().putString(NOTIFICATION, status).apply();
    }

    public String getStatusNotification(){
       return sharedPreferences.getString(NOTIFICATION, NOTIFICATION_DEFAULT);
    }

    public BackNow getWeatherNowData() {

        if (sharedPreferences.getString(DATA_WEATHER_NOW, DATA_WEATHER_NOW_DEFAULT) == null) {
            return null;
        }
        return BackNow.fromJsonCollection(sharedPreferences.getString(DATA_WEATHER_NOW, DATA_WEATHER_NOW_DEFAULT));
    }


    public boolean saveWeatherNowData(BackNow backNowData) {
        // Obtain previously saved
        if (backNowData == null) {
            Log.i(TAG, "saveWeatherNowData = false");
            return false;
        } else {
            String jsonStringWeatherData = BackNow.toJSONCollection(backNowData);
            sharedPreferences.edit().putString(DATA_WEATHER_NOW, jsonStringWeatherData).apply();
            Log.i(TAG, "saveWeatherNowData = true");
            return true;
        }
    }

    public BackWeek getWeatherWeekData() {

        if (sharedPreferences.getString(DATA_WEATHER_WEEK, DATA_WEATHER_WEEK_DEFAULT) == null) {
            Log.i(TAG, "saveWeatherNowData = false");
            return null;
        }
        Log.i(TAG, "saveWeatherNowData = true");
        return BackWeek.fromJsonCollection(sharedPreferences.getString(DATA_WEATHER_WEEK, DATA_WEATHER_WEEK_DEFAULT));
    }


    public boolean saveWeatherWeekData(BackWeek backWeekData) {

        if (backWeekData == null) {
            Log.i(TAG, "saveWeatherWeekData = false");
            return false;
        } else {
            Log.i(TAG, "saveWeatherWeekData = true");
            String jsonStringWeatherData = BackWeek.toJSONCollection(backWeekData);
            sharedPreferences.edit().putString(DATA_WEATHER_WEEK, jsonStringWeatherData).apply();
            return true;
        }
    }
}
