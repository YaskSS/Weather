package com.example.serhey.weather.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TomorrowForecastAdapter extends Fragment {

    protected AppBridge appBridge;
    private PictureAdapter mPictureAdapter = new PictureAdapter();

    List<Forecast> forecasts = new ArrayList<>();

    TextView tvCity;
    TextView tvTemperature;
    TextView textViewWind;
    TextView textViewDate;
    List<Weather> mListWeather;
    ImageView imageView;
    private DayOfWeek dayOfWeek = new DayOfWeek();

    private PictureAdapter pictureAdapter = new PictureAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        appBridge= (AppBridge) getActivity().getApplication();
        View view = inflater.inflate(R.layout.fragment_temp2, null);
        tvCity = (TextView) view.findViewById(R.id.textViewCity);
        tvTemperature = (TextView) view.findViewById(R.id.textViewTemperature2);
        textViewWind = (TextView) view.findViewById(R.id.textViewVeter);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        imageView = (ImageView) view.findViewById(R.id.imageView);


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
                    textViewDate.setText(dayOfWeek.getDayOfWeek(response.body().getList().get(0).getDtTxt()) + " "+new SimpleDateFormat("MM.dd").format(response.body().getList().get(0).getDtTxt()));
                    tvTemperature.setText(String.format("%s°C",response.body().getList().get(0).getMain().getTemp()));
                    imageView.setImageResource(pictureAdapter.setImage(response.body().getList().get(0).getWeather().get(0).getIcon()));
                    textViewWind.setText(String.format("Ветер %s м/с   Облачность %s%%", response.body().getList().get(0).getWind()
                            .getSpeed().toString(), response.body().getList().get(0).getClouds().getAll().toString()));

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
