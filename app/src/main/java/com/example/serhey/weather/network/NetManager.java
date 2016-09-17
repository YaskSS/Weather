package com.example.serhey.weather.network;

import android.content.Context;

import com.example.serhey.weather.CallBackNow.BackNow;
import com.example.serhey.weather.CallBackWeek.BackWeek;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {

    private static final String APP_ID_NAME = "appid";
    private static final String APP_ID = "38473f8892bdb27f903a1adb9b4ed01a";
    private Context context;

    API api;

    public NetManager(Context context) {
        this.context = context;

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Retrofit client = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(API.BASE_URL)
                .build();
        api = client.create(API.class);
    }

    public void getTodayWeather(Callback<BackNow> callback) {
        Map<String, String> query = new HashMap<String, String>();
        String stringGetCity = context.getSharedPreferences("CITY", Context.MODE_PRIVATE).getString("city_name","");
        query.put("q", stringGetCity);
        query.put("units", "metric");
        query.put(APP_ID_NAME, APP_ID);

        Call<BackNow> callNow = api.getTodayWeather(query);
        callNow.enqueue(callback);
    }

    public void getWeekWeather(Callback<BackWeek> callback) {
        Map<String, String> query = new HashMap<String, String>();
        String stringGetCityWeek = context.getSharedPreferences("CITY", Context.MODE_PRIVATE).getString("city_name","");
        query.put("q", stringGetCityWeek);
        query.put("mode", "json");
        query.put("units", "metric");
        query.put(APP_ID_NAME, APP_ID);

        Call<BackWeek> callNow = api.getWeekWeather(query);
        callNow.enqueue(callback);
    }


}
