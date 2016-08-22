package com.example.serhey.weather;

import com.example.serhey.weather.CallBackWeek.BackWeek;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Serhey on 18.08.2016.
 */
public interface ApiRequestWeek {
    @GET("data/2.5/forecast")
    Call<BackWeek> search(@QueryMap Map<String, String> queryWeek);
}
