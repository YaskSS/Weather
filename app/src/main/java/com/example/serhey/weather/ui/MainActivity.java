package com.example.serhey.weather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import android.widget.Toast;
import com.example.serhey.weather.CallBackNow.BackNow;
import com.example.serhey.weather.CallBackWeek.BackWeek;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvCity;
    TextView tvTemperature;
    TextView textViewDate;
    TextView textViewWind;
    RecyclerView recyclerView;
    ForecastAdapter forecastAdapter;

    private AppBridge appBridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBridge = (AppBridge) getApplication();

        tvCity = (TextView) findViewById(R.id.textViewCity);
        tvTemperature = (TextView) findViewById(R.id.textViewTemperature);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDate.setText(new SimpleDateFormat("yyyy.MM.dd_HH.mm").format(Calendar.getInstance().getTime()));
        textViewWind = (TextView) findViewById(R.id.textViewVeter);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        forecastAdapter  = new ForecastAdapter(this);
        recyclerView.setAdapter(forecastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadWeather();
    }

    private void loadWeather() {
        appBridge.getNetManager().getTodayWeather(new Callback<BackNow>() {
            @Override
            public void onResponse(Call<BackNow> call, Response<BackNow> response) {
                if(response.isSuccessful()){
                    tvCity.setText(response.body().getName());
                    tvTemperature.setText(String.format("%s°C", response.body().getMain().getTemp()));
                    textViewWind.setText(String.format("Ветер %s м/с \nОблачность %s%%", response.body().getWind()
                            .getSpeed().toString(), response.body().getClouds().getAll().toString()));
                }
            }

            @Override
            public void onFailure(Call<BackNow> call, Throwable t) {
                Toast.makeText(MainActivity.this, "sorry", Toast.LENGTH_SHORT).show();
            }
        });

        appBridge.getNetManager().getWeekWeather(new Callback<BackWeek>() {
            @Override
            public void onResponse(Call<BackWeek> call, Response<BackWeek> response) {
                if (response.isSuccessful()) {
                    Log.d("qwe", "ok");
                    forecastAdapter.addData(response.body().getList());
                } else {
                    Log.d("qwe", "!ok");
                }
            }

            @Override
            public void onFailure(Call<BackWeek> call, Throwable t) {
             t.printStackTrace();

            }
        });
    }
}
