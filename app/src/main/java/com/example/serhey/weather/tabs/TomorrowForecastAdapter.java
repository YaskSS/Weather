package com.example.serhey.weather.tabs;

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
import android.widget.Toast;

import com.example.serhey.weather.callBackNow.Weather;
import com.example.serhey.weather.callBackWeek.BackWeek;
import com.example.serhey.weather.callBackWeek.Forecast;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.logic.SharedPrefHelper;
import com.example.serhey.weather.picture.PictureAdapter;
import com.example.serhey.weather.ui.DayOfWeek;
import com.example.serhey.weather.ui.TomorrowWeatherOnAllDayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TomorrowForecastAdapter extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "TomorrowForecast";
    protected AppBridge appBridge;
    private PictureAdapter mPictureAdapter = new PictureAdapter();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    List<Forecast> forecasts = new ArrayList<>();
    private SharedPreferences tomorrowWeatherSharedPreferences;
    private TomorrowWeatherOnAllDayAdapter mTomorrowWeatherOnAllDayAdapter;
    private RecyclerView recyclerView;
    private TextView tvCity;
    private TextView tvTemperature;
    private TextView textViewWind;
    private TextView textViewDate;
    private List<Weather> mListWeather;
    ImageView imageView;
    private DayOfWeek dayOfWeek = new DayOfWeek();
    private BackWeek backWeek;

    private PictureAdapter pictureAdapter = new PictureAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge = (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp2, null);
        tvCity = (TextView) view.findViewById(R.id.textViewCity);
        recyclerView = (RecyclerView) view.findViewById(R.id.listTomorrow);
        tvTemperature = (TextView) view.findViewById(R.id.textViewTemperature2);
        textViewWind = (TextView) view.findViewById(R.id.textViewVeter);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        mTomorrowWeatherOnAllDayAdapter = new TomorrowWeatherOnAllDayAdapter(App.getContext());
        recyclerView.setAdapter(mTomorrowWeatherOnAllDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_tomorrow);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadTomorrowWeather();
        return view;
    }

    public void loadTomorrowWeather() {
        backWeek = SharedPrefHelper.getInstance().getWeatherWeekData();
        tvCity.setText(backWeek.getCity().getName());
        textViewDate.setText(new SimpleDateFormat("MM.dd").format(backWeek.getList().get(0).getDtTxt()));
        tvTemperature.setText(String.format("%s°C", backWeek.getList().get(0).getMain().getTemp()));
        imageView.setImageResource(pictureAdapter.setImage(backWeek.getList().get(0).getWeather().get(0).getIcon()));
        textViewWind.setText(String.format(getString(R.string.wind) + " %s " + getString(R.string.mVs)+ " " + getString(R.string.cloudy) + "   %s%%",
                backWeek.getList().get(0).getWind()
                        .getSpeed().toString(), backWeek.getList().get(0).getClouds().getAll().toString()));
        mTomorrowWeatherOnAllDayAdapter.addData(getTomorrowWeatherList(backWeek.getList()));
        /*appBridge.getNetManager().getWeekWeather(new Callback<BackWeek>() {
            @Override
            public void onResponse(Call<BackWeek> call, Response<BackWeek> response) {
                if (response.isSuccessful()) {
                    Log.d("qwe", "ok");
                    сityTextView.setText(response.body().getCity().getName());
                    dateTextView.setText(new SimpleDateFormat("MM.dd").format(response.body().getList().get(0).getDtTxt()));
                    temperatureTextView.setText(String.format("%s°C", response.body().getList().get(0).getMain().getTemp()));
                    imageView.setImageResource(pictureAdapter.setImage(response.body().getList().get(0).getWeather().get(0).getIcon()));
                    windTextView.setText(String.format(getString(R.string.wind) + " %s " + getString(R.string.mVs)+ " " + getString(R.string.cloudy) + "   %s%%",
                            response.body().getList().get(0).getWind()
                            .getSpeed().toString(), response.body().getList().get(0).getClouds().getAll().toString()));
                    mTomorrowWeatherOnAllDayAdapter.addData(getTomorrowWeatherList(response.body().getList()));
                } else {
                    Log.d("qwe", "!ok");
                }
            }

            @Override
            public void onFailure(Call<BackWeek> call, Throwable t) {
                t.printStackTrace();

            }
        });*/
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
       // loadTomorrowWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tomorrowWeatherSharedPreferences  =  getActivity().getSharedPreferences(SharedPrefHelper.DATA_WEATHER_WEEK, 0);
        tomorrowWeatherSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTomorrowWeather();
        tomorrowWeatherSharedPreferences
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        tomorrowWeatherSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(TAG, "onSharedPreferenceChanged");
        Toast.makeText(getContext() , "Listener is working!", Toast.LENGTH_LONG).show();
        if (s.equals(SharedPrefHelper.DATA_WEATHER_WEEK)) {
            loadTomorrowWeather();
        }
    }
}
