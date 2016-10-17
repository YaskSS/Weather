package com.example.serhey.weather.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.serhey.weather.CallBackWeek.Forecast;
import com.example.serhey.weather.R;
import com.example.serhey.weather.picture.PictureAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.VH> {

    private List<Forecast> data;
    private LayoutInflater inflater;
    private PictureAdapter pictureAdapter1 = new PictureAdapter();
    private DayOfWeek dayOfWeek = new DayOfWeek();
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
        TextView temp;
        TextView temperature_item_fragment;
        ImageView iconWind;
        ImageView icon;
        TextView tvWindDirection;

        public VH(View itemView) {
            super(itemView);
            temp = (TextView) itemView.findViewById(R.id.temp);
            temperature_item_fragment = (TextView) itemView.findViewById(R.id.temperature_item_fragment);
            iconWind = (ImageView) itemView.findViewById(R.id.iconWind);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            tvWindDirection = (TextView) itemView.findViewById(R.id.textViewWindDirection);
        }

        public void fill(Forecast forecast, int position) {
            temp.setText(dayOfWeek.getDayOfWeek(forecast.getDtTxt()) + " " + new SimpleDateFormat("MM.dd в HH.mm").format(forecast.getDtTxt()));
            temperature_item_fragment.setText(String.format("%s°C", forecast.getMain().getTemp()));
            iconWind.setImageResource(windDirection.setIconWind(forecast.getWind().getDeg()));
            icon.setImageResource(pictureAdapter1.setImage(forecast.getWeather().get(0).getIcon()));
            tvWindDirection.setText("Ветер " + windDirection.setWindDirection(forecast.getWind().getDeg()) + " " + forecast.getWind().getSpeed().toString() + " м/с");
            icon.setColorFilter(new Random().nextInt());
        }
    }
}
