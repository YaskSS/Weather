package com.example.serhey.weather.tabs;

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

import com.example.serhey.weather.CallBackNow.Weather;
import com.example.serhey.weather.CallBackWeek.BackWeek;
import com.example.serhey.weather.CallBackWeek.Forecast;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.picture.PictureAdapter;
import com.example.serhey.weather.ui.DayOfWeek;
import com.example.serhey.weather.ui.TomorrowWeatherOnAllDayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TomorrowForecastAdapter extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected AppBridge appBridge;
    private PictureAdapter mPictureAdapter = new PictureAdapter();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    List<Forecast> forecasts = new ArrayList<>();
    TomorrowWeatherOnAllDayAdapter mTomorrowWeatherOnAllDayAdapter;
    RecyclerView recyclerView;
    TextView tvCity;
    TextView tvTemperature;
    TextView textViewWind;
    TextView textViewDate;
    List<Weather> mListWeather;
    ImageView imageView;
    private DayOfWeek dayOfWeek = new DayOfWeek();

    private PictureAdapter pictureAdapter = new PictureAdapter();

    LinearLayoutManager mLinearLayoutManager;

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
        mTomorrowWeatherOnAllDayAdapter = new TomorrowWeatherOnAllDayAdapter(getActivity().getApplication());
        recyclerView.setAdapter(mTomorrowWeatherOnAllDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication(), LinearLayoutManager.HORIZONTAL, false));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_tomorrow);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        loadWeather();
        return view;
    }

    private void loadWeather() {
        appBridge.getNetManager().getWeekWeather(new Callback<BackWeek>() {
            @Override
            public void onResponse(Call<BackWeek> call, Response<BackWeek> response) {
                if (response.isSuccessful()) {
                    Log.d("qwe", "ok");
                    tvCity.setText(response.body().getCity().getName());
                    textViewDate.setText(new SimpleDateFormat("dd.MM").format(response.body().getList().get(0).getDtTxt()));
                    tvTemperature.setText(String.format("%s°C", response.body().getList().get(0).getMain().getTemp()));
                    imageView.setImageResource(pictureAdapter.setImage(response.body().getList().get(0).getWeather().get(0).getIcon()));
                    textViewWind.setText(String.format("Ветер %s м/с   Облачность %s%%", response.body().getList().get(0).getWind()
                            .getSpeed().toString(), response.body().getList().get(0).getClouds().getAll().toString()));
                    mTomorrowWeatherOnAllDayAdapter.addData(response.body().getList());
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

    @Override
    public void onRefresh() {
        loadWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
