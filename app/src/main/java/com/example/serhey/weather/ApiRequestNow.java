package com.example.serhey.weather;

import com.example.serhey.weather.CallBackNow.BackNow;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Serhey on 18.08.2016.
 */
public interface ApiRequestNow {
    @GET("data/2.5/weather")
    Call<BackNow> search(@QueryMap Map<String, String> query);

}
