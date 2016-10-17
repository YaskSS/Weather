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
import java.util.Date;
import java.util.List;

/**
 * Created by Serhey on 10.10.2016.
 */

public class TomorrowWeatherOnAllDayAdapter extends RecyclerView.Adapter<TomorrowWeatherOnAllDayAdapter.VH> {

    private List<Forecast> data;
    private LayoutInflater inflater;
    private PictureAdapter pictureAdapter2 = new PictureAdapter();

    Date mDate;


    public TomorrowWeatherOnAllDayAdapter (Context context){
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(inflater.inflate(R.layout.item_forecast_tomorrow,parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
            holder.fill(data.get(position),position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<Forecast> forecasts){
        data.addAll(forecasts);
        notifyDataSetChanged();

    }

    public class VH extends RecyclerView.ViewHolder{

        TextView tvDate;
        TextView tvTemperature;
        ImageView ivImage;

        public VH(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.textViewTomorrowDate);
            tvTemperature= (TextView) itemView.findViewById(R.id.textViewTomorrowTemperature);
            ivImage = (ImageView) itemView.findViewById(R.id.imageViewTomorrow);
        }

        public void fill(Forecast forecast, int position){
            tvDate.setText( new SimpleDateFormat("HH:mm").format(forecast.getDtTxt()));
            tvTemperature.setText(String.format("%s°C",forecast.getMain().getTemp()));
            ivImage.setImageResource(pictureAdapter2.setImage(forecast.getWeather().get(0).getIcon()));
        }
    }
}
