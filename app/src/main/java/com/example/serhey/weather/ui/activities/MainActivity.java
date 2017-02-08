package com.example.serhey.weather.ui.activities;

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

import com.example.serhey.weather.db.responseNow.BackNow;
import com.example.serhey.weather.db.responseNow.Weather;
import com.example.serhey.weather.db.responseWeek.BackWeek;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.adapters.PictureAdapter;
import com.example.serhey.weather.adapters.ForecastAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private TextView cityTextView;
    private TextView temperatureTextView;
    private TextView dateTextView;
    private TextView windTextView;
    private ImageView nowImageView;
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private String codeIcon;
    private List<Weather> mListWeather;
    private SearchView searchView;
    private PictureAdapter mPictureAdapter = new PictureAdapter();
    private AppBridge appBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchView1);
        appBridge = (AppBridge) getApplication();

        cityTextView = (TextView) findViewById(R.id.textViewCity);
        temperatureTextView = (TextView) findViewById(R.id.textViewTemperature);
        dateTextView = (TextView) findViewById(R.id.textViewDate);
        dateTextView.setText(new SimpleDateFormat("yyyy.MM.dd_HH.mm").format(Calendar.getInstance().getTime()));
        windTextView = (TextView) findViewById(R.id.textViewVeter);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        forecastAdapter = new ForecastAdapter(this);
        recyclerView.setAdapter(forecastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nowImageView = (ImageView) findViewById(R.id.imageView);
        searchView.setQueryHint("Search View");

        searchView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean consumed = false;
                if (keyCode == EditorInfo.IME_ACTION_SEARCH) {

                    consumed = true;
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
                    cityTextView.setText(response.body().getName());
                    temperatureTextView.setText(String.format("%s°C", response.body().getMain().getTemp()));
                    windTextView.setText(String.format("Ветер %s м/с \nОблачность %s%%", response.body().getWind()
                            .getSpeed().toString(), response.body().getClouds().getAll().toString()));
                    mListWeather = response.body().getWeather();
                    codeIcon = mListWeather.get(0).getIcon();
                    Log.d("qwe", codeIcon);
                    nowImageView.setImageResource(mPictureAdapter.setImage(codeIcon));
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
