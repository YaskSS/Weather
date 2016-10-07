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

import com.example.serhey.weather.CallBackWeek.BackWeek;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.ui.ForecastAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Serhey on 04.09.2016.
 */
public class WeekForecastAdapter extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    protected AppBridge appBridge;
    ForecastAdapter forecastAdapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge= (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp3, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        forecastAdapter = new ForecastAdapter(getActivity().getApplication());
        recyclerView.setAdapter(forecastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_week);
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


    @Override
    public void onRefresh() {
        loadWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
