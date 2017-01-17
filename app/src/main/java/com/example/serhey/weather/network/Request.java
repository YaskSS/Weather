package com.example.serhey.weather.network;

import android.util.Log;
import android.widget.Toast;

import com.example.serhey.weather.CallBackNow.BackNow;
import com.example.serhey.weather.CallBackWeek.BackWeek;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.logic.SharedPrefHelper;
import com.example.serhey.weather.ui.TabActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yass on 1/11/17.
 */

public class Request {

    private static final String LOG_REQUEST = "Request.class";
    protected AppBridge appBridge;

    public void requestTodayWeather() {
        appBridge = (AppBridge) App.getContext().getApplicationContext();
        if (checkConnectionInternet()) {
            appBridge.getNetManager().getTodayWeather(new Callback<BackNow>() {
                @Override
                public void onResponse(Call<BackNow> call, Response<BackNow> response) {
                    if (response.isSuccessful()) {
                        Log.d(LOG_REQUEST, "todayWeather, ok");
                        SharedPrefHelper.getInstance().saveWeatherNowData(response.body());

                    }
                }

                @Override
                public void onFailure(Call<BackNow> call, Throwable t) {
                    Log.d(LOG_REQUEST, "todayWeather, !ok");
                    t.printStackTrace();
                    SharedPrefHelper.getInstance().saveWeatherNowData(null);
                    //Toast.makeText(getActivity().getApplicationContext(), "Error in write City name " + "'" + getActivity().getApplicationContext().getSharedPreferences("CITY", Context.MODE_PRIVATE).getString("city_name","")+ "'", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(App.getContext().getApplicationContext(),
                    App.getContext().getString(R.string.changeNetwork), Toast.LENGTH_LONG).show();
        }
    }

    public void requestWeekWeather() {
        appBridge = (AppBridge) App.getContext().getApplicationContext();
        if (checkConnectionInternet()) {
            appBridge.getNetManager().getWeekWeather(new Callback<BackWeek>() {
                @Override
                public void onResponse(Call<BackWeek> call, Response<BackWeek> response) {
                    if (response.isSuccessful()) {
                        Log.d(LOG_REQUEST, "weekWeather ok");
                        SharedPrefHelper.getInstance().saveWeatherWeekData(response.body());
                    } else {
                        Log.d(LOG_REQUEST, "weekWeather !ok");
                    }
                }

                @Override
                public void onFailure(Call<BackWeek> call, Throwable t) {
                    t.printStackTrace();
                    SharedPrefHelper.getInstance().saveWeatherWeekData(null);
                }
            });
        } else {
            Toast.makeText(App.getContext().getApplicationContext(),
                    App.getContext().getString(R.string.changeNetwork), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkConnectionInternet() {
        if (!appBridge.getNetManager().checkConnectionWithInternet()){
            return false;
        } else {
            return true;
        }
    }
}
