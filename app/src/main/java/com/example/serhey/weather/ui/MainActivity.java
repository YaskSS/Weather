package com.example.serhey.weather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serhey.weather.CallBackNow.BackNow;
import com.example.serhey.weather.CallBackNow.Weather;
import com.example.serhey.weather.CallBackWeek.BackWeek;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.picture.PictureAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    TextView tvCity;
    TextView tvTemperature;
    TextView textViewDate;
    TextView textViewWind;
    ImageView imageViewNow;
    RecyclerView recyclerView;
    ForecastAdapter forecastAdapter;
    String codeIcon;
    List<Weather> mListWeather;
    SearchView searchView;
    private PictureAdapter mPictureAdapter = new PictureAdapter();
    private AppBridge appBridge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchView1);
        appBridge = (AppBridge) getApplication();

        tvCity = (TextView) findViewById(R.id.textViewCity);
        tvTemperature = (TextView) findViewById(R.id.textViewTemperature);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDate.setText(new SimpleDateFormat("yyyy.MM.dd_HH.mm").format(Calendar.getInstance().getTime()));
        textViewWind = (TextView) findViewById(R.id.textViewVeter);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        forecastAdapter = new ForecastAdapter(this);
        recyclerView.setAdapter(forecastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageViewNow = (ImageView) findViewById(R.id.imageView);
        searchView.setQueryHint("Search View");

        searchView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean consumed = false;
                if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                    //Делаем то, что нам нужно...

                    consumed = true; //это если не хотим, чтобы нажатая кнопка обрабатывалась дальше видом, иначе нужно оставить false
                }
                return consumed;
            }
        });

        loadWeather();

    }


    private void loadWeather() {
        appBridge.getNetManager().getTodayWeather(new Callback<BackNow>() {
            @Override
            public void onResponse(Call<BackNow> call, Response<BackNow> response) {
                if (response.isSuccessful()) {
                    tvCity.setText(response.body().getName());
                    tvTemperature.setText(String.format("%s°C", response.body().getMain().getTemp()));
                    textViewWind.setText(String.format("Ветер %s м/с \nОблачность %s%%", response.body().getWind()
                            .getSpeed().toString(), response.body().getClouds().getAll().toString()));
                    mListWeather = response.body().getWeather();
                    codeIcon = mListWeather.get(0).getIcon();
                    Log.d("qwe", codeIcon);
                    imageViewNow.setImageResource(mPictureAdapter.setImage(codeIcon));
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
