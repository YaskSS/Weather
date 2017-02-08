package com.example.serhey.weather.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.db.responseWeek.BackWeek;
import com.example.serhey.weather.db.responseWeek.Forecast;
import com.example.serhey.weather.adapters.PictureAdapter;
import com.example.serhey.weather.adapters.TomorrowWeatherAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TomorrowForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "TomorrowForecast";
    protected AppBridge appBridge;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SharedPreferences tomorrowWeatherSharedPref;
    private TomorrowWeatherAdapter tomorrowWeatherAdapter;
    private RecyclerView recyclerView;
    private TextView cityTextView;
    private TextView temperatureTextView;
    private TextView windTextView;
    private TextView dateTextView;
    private ImageView imageView;
    private BackWeek backWeek;

    private PictureAdapter pictureAdapter = new PictureAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge = (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp2, null);
        cityTextView = (TextView) view.findViewById(R.id.textViewCity);
        recyclerView = (RecyclerView) view.findViewById(R.id.listTomorrow);
        temperatureTextView = (TextView) view.findViewById(R.id.textViewTemperature2);
        windTextView = (TextView) view.findViewById(R.id.textViewVeter);
        dateTextView = (TextView) view.findViewById(R.id.textViewDate);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        tomorrowWeatherAdapter = new TomorrowWeatherAdapter(App.getContext());
        recyclerView.setAdapter(tomorrowWeatherAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_tomorrow);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadTomorrowWeather();
        return view;
    }

    public void loadTomorrowWeather() {
        backWeek = SharedPrefHelper.getInstance().getWeatherWeekData();
        cityTextView.setText(backWeek.getCity().getName());
        dateTextView.setText(new SimpleDateFormat("MM.dd").format(backWeek.getList().get(0).getDtTxt()));
        temperatureTextView.setText(String.format("%s°C", backWeek.getList().get(0).getMain().getTemp()));
        imageView.setImageResource(pictureAdapter.setImage(backWeek.getList().get(0).getWeather().get(0).getIcon()));
        windTextView.setText(String.format(getString(R.string.wind) + " %s " + getString(R.string.mVs)+ " " + getString(R.string.cloudy) + "   %s%%",
                backWeek.getList().get(0).getWind()
                        .getSpeed().toString(), backWeek.getList().get(0).getClouds().getAll().toString()));
        tomorrowWeatherAdapter.addData(getTomorrowWeatherList(backWeek.getList()));
    }

    private List<Forecast> getTomorrowWeatherList(List<Forecast> list){
        List<Forecast> weatherList = new ArrayList<>();
        Date currentDate = new Date();
        Date dateForChange = new Date();
        Long time = currentDate.getTime();
        time = time + (60*60*24*1000);
        currentDate = new Date(time);

        for (int i = 0; i < list.size();i++){
            if (list.get(i).getDtTxt().getTime() <= currentDate.getTime() && list.get(i).getDtTxt().getTime() >= dateForChange.getTime() ){
                weatherList.add(list.get(i));
            }
        }
        return  weatherList;
    }

    @Override
    public void onRefresh() {
        appBridge.getRequest().requestWeekWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tomorrowWeatherSharedPref =  getActivity().getSharedPreferences(SharedPrefHelper.APP_PREFERENCES, 0);
        tomorrowWeatherSharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTomorrowWeather();
        tomorrowWeatherSharedPref
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        tomorrowWeatherSharedPref.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(TAG, "onSharedPreferenceChanged");
        if (s.equals(SharedPrefHelper.DATA_WEATHER_WEEK)) {
            loadTomorrowWeather();
        }
    }
}
