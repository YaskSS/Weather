package com.example.serhey.weather.tabs;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serhey.weather.CallBackNow.BackNow;
import com.example.serhey.weather.CallBackNow.Weather;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.logic.SharedPrefHelper;
import com.example.serhey.weather.picture.PictureAdapter;
import com.example.serhey.weather.ui.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.os.Handler;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TodayForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "TodayForecastFragment";
    private static final int TIME_REFRESHING = 1500;
    protected AppBridge appBridge;
    private PictureAdapter mPictureAdapter = new PictureAdapter();
    private SharedPreferences todayWeatherSharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    BackNow backNow;

    TextView tvCity;
    TextView tvTemperature;
    TextView textViewWind;
    TextView textViewDate;
    TextView tvHumidity;
    TextView cloudyTextView;
    List<Weather> mListWeather;
    ImageView imageViewNow;
    String codeIcon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todayWeatherSharedPreferences = getActivity().getSharedPreferences(SharedPrefHelper.DATA_WEATHER_NOW, 0);
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Log.i(TAG, "SharedPrefHelper was call");
                if (sharedPreferences.getString(SharedPrefHelper.DATA_WEATHER_NOW, SharedPrefHelper.DATA_WEATHER_NOW_DEFAULT) != null){
                    loadTodayWeather();
                }
            }
        };
        todayWeatherSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge = (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp1, null);
        tvCity = (TextView) view.findViewById(R.id.textViewCity);
        tvTemperature = (TextView) view.findViewById(R.id.textViewTemperature);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewWind = (TextView) view.findViewById(R.id.textViewVeter);
        imageViewNow = (ImageView) view.findViewById(R.id.imageView);
        cloudyTextView = (TextView) view.findViewById(R.id.textView_cloud);
        tvHumidity = (TextView) view.findViewById(R.id.humidity1);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        todayWeatherSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        loadTodayWeather();

        mSwipeRefreshLayout.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
               // if (event.getAction() == KeyEvent.KEYCODE_SEARCH)
                //    if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                 //       loadTodayWeather();
                //        return true;
                //    }
              //  return false;
                return true;
            }
        });

        return view;
    }

    public void loadTodayWeather() {
        // if (backNow != null){
        backNow = SharedPrefHelper.getInstance().getWeatherNowData();
        Log.i(TAG, "Views update");
        textViewDate.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
        tvCity.setText(backNow.getName());
        tvTemperature.setText(String.format("%s°C", backNow.getMain().getTemp()));
        textViewWind.setText(String.format(getString(R.string.wind) + " %s м/с",
                backNow.getWind().getSpeed().toString()));
        tvHumidity.setText(String.format(getString(R.string.humidity) + " %s%%",
                backNow.getClouds().getAll().toString()));
        cloudyTextView.setText(String.format(getString(R.string.cloudy) + " %s%%", backNow.getClouds().getAll().toString()));
        mListWeather = backNow.getWeather();
        codeIcon = mListWeather.get(0).getIcon();
        imageViewNow.setImageResource(mPictureAdapter.setImage(codeIcon));


       /* } else {
            appBridge.getNetManager().getTodayWeather(new Callback<BackNow>() {
            @Override
            public void onResponse(Call<BackNow> call, Response<BackNow> response) {
                if (response.isSuccessful()) {
                    Log.d("qwe", "TFF, ok");
                    tvCity.setText(response.body().getName());
                    tvTemperature.setText(String.format("%s°C", response.body().getMain().getTemp()));
                    textViewWind.setText(String.format(getString(R.string.wind) + " %s м/с",
                            response.body().getWind().getSpeed().toString()));
                    tvHumidity.setText(String.format(getString(R.string.humidity) + " %s%%",
                            response.body().getClouds().getAll().toString()));
                    cloudyTextView.setText(String.format(getString(R.string.cloudy) +" %s%%",
                    response.body().getClouds().getAll().toString()));
                    mListWeather = response.body().getWeather();
                    codeIcon = mListWeather.get(0).getIcon();
//                    Log.d("qwe", codeIcon);
                    imageViewNow.setImageResource(mPictureAdapter.setImage(codeIcon));
                }
            }

            @Override
            public void onFailure(Call<BackNow> call, Throwable t) {
                Log.d("qwe", "TFF, !ok");
                t.printStackTrace();
                /*Toast.makeText(getActivity().getApplicationContext(), "Error in write City name " +
                 " '" + getActivity().getApplicationContext().getSharedPreferences("CITY",
                 Context.MODE_PRIVATE).getString("city_name","")+ "'", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    }

    @Override
    public void onRefresh() {
        appBridge.getRequest().requestTodayWeather();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, TIME_REFRESHING);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateVisibilityViews();
        loadTodayWeather();
        todayWeatherSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        todayWeatherSharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    private void updateVisibilityViews() {
        if (SharedPrefHelper.getInstance().getTemperatureVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            tvTemperature.setVisibility(View.VISIBLE);
        } else {
            tvTemperature.setVisibility(View.GONE);
        }

        if (SharedPrefHelper.getInstance().getHumidityVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            tvHumidity.setVisibility(View.VISIBLE);
        } else {
            tvHumidity.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getWindVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            textViewWind.setVisibility(View.VISIBLE);
        } else {
            textViewWind.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getCityVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            tvCity.setVisibility(View.VISIBLE);
        } else {
            tvCity.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getCloudinessVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            cloudyTextView.setVisibility(View.VISIBLE);
        } else {
            cloudyTextView.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getIconVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            imageViewNow.setVisibility(View.VISIBLE);
        } else {
            imageViewNow.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getDateVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            textViewDate.setVisibility(View.VISIBLE);
        } else {
            textViewDate.setVisibility(View.GONE);
        }
    }
}
