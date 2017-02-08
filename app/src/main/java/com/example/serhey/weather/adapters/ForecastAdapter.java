package com.example.serhey.weather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.db.responseWeek.Forecast;
import com.example.serhey.weather.ui.WindDirection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.VH> {

    private List<Forecast> data;
    private LayoutInflater inflater;
    private PictureAdapter pictureAdapter = new PictureAdapter();
    private WindDirection windDirection = new WindDirection();

    public ForecastAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(inflater.inflate(R.layout.item_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.fill(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<Forecast> forecasts) {
        data.addAll(forecasts);
        notifyDataSetChanged();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView dayOfWeekTextView;
        TextView temperatureTextView;
        ImageView iconWind;
        ImageView iconWeatherImageView;
        TextView windDirectionTetView;

        public VH(View itemView) {
            super(itemView);
            dayOfWeekTextView = (TextView) itemView.findViewById(R.id.item_day_textView);
            temperatureTextView = (TextView) itemView.findViewById(R.id.temperature_item_textView);
            iconWind = (ImageView) itemView.findViewById(R.id.iconWind);
            iconWeatherImageView = (ImageView) itemView.findViewById(R.id.icon_weather);
            windDirectionTetView = (TextView) itemView.findViewById(R.id.textViewWindDirection);
        }

        public void fill(Forecast forecast, int position) {
            dayOfWeekTextView.setText(new SimpleDateFormat("EEEE").format(forecast.getDtTxt())+ " " + new SimpleDateFormat("MM.dd в HH.mm").format(forecast.getDtTxt()));
            temperatureTextView.setText(String.format("%s°C", forecast.getMain().getTemp()));
            iconWind.setImageResource(windDirection.setIconWind(forecast.getWind().getDeg()));
            iconWeatherImageView.setImageResource(pictureAdapter.setImage(forecast.getWeather().get(0).getIcon()));
            windDirectionTetView.setText(App.getContext().getString(R.string.wind)+ " " + windDirection.setWindDirection(forecast.getWind().getDeg()) + " " + forecast.getWind().getSpeed().toString() + " м/с");
            iconWeatherImageView.setColorFilter(new Random().nextInt());
        }
    }
}
