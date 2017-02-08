package com.example.serhey.weather.ui.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.db.TodayWeatherCursor;
import com.example.serhey.weather.network.Request;
import com.example.serhey.weather.adapters.PictureAdapter;
import com.example.serhey.weather.ui.activities.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Handler;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TodayForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "TodayForecastFragment";

    private static final int TIME_REFRESHING = 1000;
    protected AppBridge appBridge;
    private PictureAdapter pictureAdapter = new PictureAdapter();
    private SharedPreferences todayWeatherSharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Request request;
    private com.example.serhey.weather.db.models.Weather weather;

    private BroadcastReceiver receiver;
    public final static String BROADCAST_ACTION = "com.example.serhey.weather.tabs";

    private TextView сityTextView;
    private TextView temperatureTextView;
    private TextView windTextView;
    private TextView dateTextView;
    private TextView humidityTextView;
    private TextView cloudyTextView;
    private ImageView imageNowImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todayWeatherSharedPreferences = getActivity().getSharedPreferences(SharedPrefHelper.DATA_WEATHER_NOW, 0);
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Log.i(TAG, "SharedPrefHelper was call");
                if (sharedPreferences.getString(SharedPrefHelper.DATA_WEATHER_NOW, SharedPrefHelper.DATA_WEATHER_NOW_DEFAULT) != null) {
                    loadTodayWeather();
                }
            }
        };

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadTodayWeather();
            }
        };

        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        getActivity().registerReceiver(receiver, intFilt);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge = (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp1, null);
        сityTextView = (TextView) view.findViewById(R.id.textViewCity);
        temperatureTextView = (TextView) view.findViewById(R.id.textViewTemperature);
        dateTextView = (TextView) view.findViewById(R.id.textViewDate);
        windTextView = (TextView) view.findViewById(R.id.textViewVeter);
        imageNowImageView = (ImageView) view.findViewById(R.id.imageView);
        cloudyTextView = (TextView) view.findViewById(R.id.textView_cloud);
        humidityTextView = (TextView) view.findViewById(R.id.humidity1);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        todayWeatherSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        todayWeatherSharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

        request = new Request();
        loadTodayWeather();

        swipeRefreshLayout.setOnKeyListener(new View.OnKeyListener() {
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
        TodayWeatherCursor cursorWrapper = request.queryWeatherToday(null, null);

        cursorWrapper.moveToLast();
        weather = cursorWrapper.getBackNow();
        cursorWrapper.close();
        сityTextView.setText(weather.getCity());
        dateTextView.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));// new SimpleDateFormat("HH:mm").format(weather.getDate()));
        temperatureTextView.setText(String.format("%s°C", weather.getTemperature()));
        windTextView.setText(String.format(getString(R.string.wind) + " %s м/с", weather.getWind()));
        humidityTextView.setText(String.format(getString(R.string.humidity) + " %s%%", weather.getHumidity()));
        cloudyTextView.setText(String.format(getString(R.string.cloudy) + " %s%%", weather.getCloudy()));
        imageNowImageView.setImageResource(pictureAdapter.setImage(weather.getIdImage()));
    }

    @Override
    public void onRefresh() {
        appBridge.getRequest().requestTodayWeather();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
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
        getActivity().unregisterReceiver(receiver);
    }

    private void updateVisibilityViews() {
        if (SharedPrefHelper.getInstance().getTemperatureVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            temperatureTextView.setVisibility(View.VISIBLE);
        } else {
            temperatureTextView.setVisibility(View.GONE);
        }

        if (SharedPrefHelper.getInstance().getHumidityVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            humidityTextView.setVisibility(View.VISIBLE);
        } else {
            humidityTextView.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getWindVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            windTextView.setVisibility(View.VISIBLE);
        } else {
            windTextView.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getCityVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            сityTextView.setVisibility(View.VISIBLE);
        } else {
            сityTextView.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getCloudinessVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            cloudyTextView.setVisibility(View.VISIBLE);
        } else {
            cloudyTextView.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getIconVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            imageNowImageView.setVisibility(View.VISIBLE);
        } else {
            imageNowImageView.setVisibility(View.GONE);
        }
        if (SharedPrefHelper.getInstance().getDateVisibility() == SettingsActivity.ON_VISIBILITY_VIEW) {
            dateTextView.setVisibility(View.VISIBLE);
        } else {
            dateTextView.setVisibility(View.GONE);
        }
    }
}
