package com.example.serhey.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.serhey.weather.CallBackNow.BackNow;
import com.example.serhey.weather.CallBackWeek.BackWeek;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textViewCity, textViewTemperature, textViewDate, textViewVeter;
    String city, temperature, methodeTemp, OTVETTEMPERAT;
    int k;
    double d;
    private final String BASE_URL = "http://api.openweathermap.org/";

    Retrofit client = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();

    ApiRequestNow apiRequestNow = client.create(ApiRequestNow.class);
    ApiRequestWeek apiRequestWeek = client.create(ApiRequestWeek.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH.mm");
    String currentDateandTime = sdf.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCity = (TextView) findViewById(R.id.textViewCity);
        textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
        textViewTemperature.setTextSize(80);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDate.setText(currentDateandTime);
        textViewVeter = (TextView) findViewById(R.id.textViewVeter);
       //  mRequest();
        mRequestWeek();
    }

    public void mRequest() {
        Map<String, String> query = new HashMap<String, String>();
        query.put("q", "London,uk");
        query.put("appid", "38473f8892bdb27f903a1adb9b4ed01a");
        apiRequestNow.search(query);
        Log.d("qwe", "Map created");

        Call<BackNow> callNow = apiRequestNow.search(query);
        //textViewVeter.setText( callNow.request().toString());
        callNow.enqueue(new Callback<BackNow>() {
            @Override
            public void onResponse(Call<BackNow> call, Response<BackNow> response) {

                if (response.isSuccessful()) {
                    BackNow backNow = response.body();

                    city = backNow.getName().toString();
                    temperature = backNow.getMain().getTemp().toString();
                    OTVETTEMPERAT = kelInCel(temperature);
                    textViewCity.setText(city);
                    textViewTemperature.setText(OTVETTEMPERAT + "°C");
                    textViewVeter.setText("Ветер " + backNow.getWind().getSpeed().toString() + " м/с " + "\n" + "Облачность " + backNow.getClouds().getAll().toString() + "%");
                    Log.d("qwe", "Запрос успешный");
                } else {
                    Log.d("qwe", "Запрос не успешный");
                }
            }

            @Override
            public void onFailure(Call<BackNow> call, Throwable t) {
                Log.d("qwe", "ОШИБКА!");
            }
        });
    }


    public void mRequestWeek() {
        Map<String, String> queryWeek = new HashMap<String, String>();
        queryWeek.put("q", "London,uk");
        queryWeek.put("mode", "json");
        queryWeek.put("appid", "8540c83849390e378a1c0819df42c3c5");
        apiRequestWeek.search(queryWeek);
        Call<BackWeek> callWeek = apiRequestWeek.search(queryWeek);

        callWeek.enqueue(new Callback<BackWeek>() {
            @Override
            public void onResponse(Call<BackWeek> call, Response<BackWeek> responses) {
                if (responses.isSuccessful()) {
                    Log.d("qwe", "ok");
                } else {
                    Log.d("qwe", "!ok");
                }
            }

            @Override
            public void onFailure(Call<BackWeek> call, Throwable t) {
                Log.d("qwe", "Error");
            }
        });
    }


    public String kelInCel(String temp) {
        d = Double.valueOf(temp);
        k = (int) ((1.8 * (d - 273)) + 32);
        k = (k - 32) / 2;
        methodeTemp = Integer.toString(k);
        return methodeTemp;
    }


}
