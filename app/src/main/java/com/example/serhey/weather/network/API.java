package com.example.serhey.weather.network;

import com.example.serhey.weather.callBackNow.BackNow;
import com.example.serhey.weather.callBackWeek.BackWeek;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Serhey on 18.08.2016.
 */
public interface API {
    String BASE_URL = "http://api.openweathermap.org/";

    @GET("data/2.5/forecast")
    Call<BackWeek> getWeekWeather(@QueryMap Map<String, String> queryWeek);

    @GET("data/2.5/weather")
    Call<BackNow> getTodayWeather(@QueryMap Map<String, String> query);
}
