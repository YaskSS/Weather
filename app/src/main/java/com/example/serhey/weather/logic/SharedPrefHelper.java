package com.example.serhey.weather.logic;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.serhey.weather.core.App;

/**
 * Created by Serhey on 22.11.2016.
 */

public class SharedPrefHelper {

    private static final String APP_PREFERENCES = "settings";
    private static final String CITY = "city";
    private static final String DEFAULT_CITY = "Kiev";

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
}
