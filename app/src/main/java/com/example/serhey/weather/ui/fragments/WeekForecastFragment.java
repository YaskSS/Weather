package com.example.serhey.weather.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.serhey.weather.db.responseWeek.BackWeek;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.adapters.ForecastAdapter;

/**
 * Created by Serhey on 04.09.2016.
 */
public class WeekForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    protected AppBridge appBridge;
    private ForecastAdapter forecastAdapter;
    private RecyclerView recyclerView;
    private BackWeek backWeek;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge = (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp3, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        forecastAdapter = new ForecastAdapter(getActivity().getApplication());
        recyclerView.setAdapter(forecastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_week);
        swipeRefreshLayout.setOnRefreshListener(this);

        loadWeekWeather();
        return view;
    }

    public void loadWeekWeather() {
        backWeek = SharedPrefHelper.getInstance().getWeatherWeekData();
        forecastAdapter.addData(backWeek.getList());
    }

    @Override
    public void onRefresh() {
        appBridge.getRequest().requestWeekWeather();
        loadWeekWeather();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWeekWeather();
    }
}
