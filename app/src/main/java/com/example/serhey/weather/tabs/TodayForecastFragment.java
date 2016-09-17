package com.example.serhey.weather.tabs;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serhey.weather.CallBackNow.BackNow;
import com.example.serhey.weather.CallBackNow.Weather;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.picture.PictureAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TodayForecastFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected AppBridge appBridge;
    private PictureAdapter mPictureAdapter = new PictureAdapter();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    TextView tvCity;
    TextView tvTemperature;
    TextView textViewWind;
    TextView textViewDate;
    List<Weather> mListWeather;
    ImageView imageViewNow;
    String codeIcon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge = (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp1, null);
        tvCity = (TextView) view.findViewById(R.id.textViewCity);
        tvTemperature = (TextView) view.findViewById(R.id.textViewTemperature);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewDate.setText(new SimpleDateFormat("yyyy.MM.dd_HH.mm").format(Calendar.getInstance().getTime()));
        textViewWind = (TextView) view.findViewById(R.id.textViewVeter);
        imageViewNow = (ImageView) view.findViewById(R.id.imageView);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mSwipeRefreshLayout.setRefreshing(true);

                                        loadWeather();
                                        mSwipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);

        return view;
    }

    private void loadWeather() {
        appBridge.getNetManager().getTodayWeather(new Callback<BackNow>() {
            @Override
            public void onResponse(Call<BackNow> call, Response<BackNow> response) {
                if (response.isSuccessful()) {
                    Log.d("qwe", "TFF, ok");
                    getActivity().getBaseContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).edit().putString("city_weather", response.body().getName());
                    getActivity().getBaseContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).edit().putString("temperaure_weather", String.format("%s°C", response.body().getMain().getTemp()));
                    getActivity().getBaseContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).edit().putString("wind_weather", String.format("Ветер %s м/с \nОблачность %s%%", response.body().getWind()
                            .getSpeed().toString(), response.body().getClouds().getAll().toString()));
                    mListWeather = response.body().getWeather();
                    getActivity().getBaseContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).edit().putString("icon_weather", mListWeather.get(0).getIcon());

                    tvCity.setText(response.body().getName());
                    tvTemperature.setText( String.format("%s°C", response.body().getMain().getTemp()));
                    textViewWind.setText(String.format("Ветер %s м/с \nОблачность %s%%", response.body().getWind().getSpeed().toString(), response.body().getClouds().getAll().toString()));

                    codeIcon =mListWeather.get(0).getIcon();
                    Log.d("qwe", codeIcon);
                    imageViewNow.setImageResource(mPictureAdapter.setImage(codeIcon));
                }
            }

            @Override
            public void onFailure(Call<BackNow> call, Throwable t) {
                Log.d("qwe", "TFF, !ok");
                Toast.makeText(getActivity().getApplicationContext(), "Error in write City name", Toast.LENGTH_SHORT).show();

                tvCity.setText(getActivity().getApplicationContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).getString("city_weather", ""));
                tvTemperature.setText(getActivity().getApplicationContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).getString("temperaure_weather", ""));
                textViewWind.setText(getActivity().getApplicationContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).getString("wind_weather", ""));
                codeIcon = getActivity().getApplicationContext().getSharedPreferences("WEATHER", Context.MODE_PRIVATE).getString("icon_weather", "");
                Log.d("qwe", codeIcon);
                imageViewNow.setImageResource(mPictureAdapter.setImage(codeIcon));

            }
        });
    }

    @Override
    public void onRefresh() {
        loadWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
