package com.example.serhey.weather.tabs;


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
    TextView tvHumidity;
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
        textViewDate.setText(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
        textViewWind = (TextView) view.findViewById(R.id.textViewVeter);
        imageViewNow = (ImageView) view.findViewById(R.id.imageView);
        tvHumidity = (TextView) view.findViewById(R.id.humidity1);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        loadWeather();

        mSwipeRefreshLayout.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.KEYCODE_SEARCH)
                    if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                        loadWeather();
                        return true;
                    }
                return false;
            }
        });

        return view;
    }

    private void loadWeather() {
        appBridge.getNetManager().getTodayWeather(new Callback<BackNow>() {
            @Override
            public void onResponse(Call<BackNow> call, Response<BackNow> response) {
                if (response.isSuccessful()) {
                    Log.d("qwe", "TFF, ok");
                    tvCity.setText(response.body().getName());
                    tvTemperature.setText(String.format("%s°C", response.body().getMain().getTemp()));
                    textViewWind.setText(String.format("Ветер %s м/с", response.body().getWind().getSpeed().toString()));
                    tvHumidity.setText(String.format("Влажность %s%%", response.body().getClouds().getAll().toString()));
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
                //Toast.makeText(getActivity().getApplicationContext(), "Error in write City name " + "'" + getActivity().getApplicationContext().getSharedPreferences("CITY", Context.MODE_PRIVATE).getString("city_name","")+ "'", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        loadWeather();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void updateCityToday() {
        loadWeather();
    }
}
