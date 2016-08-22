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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.VH> {

    private List<Forecast> data;
    private LayoutInflater inflater;

    public ForecastAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(inflater.inflate(R.layout.item_forecast,parent,false));
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
        TextView temp;
        ImageView icon;

        public VH(View itemView) {
            super(itemView);
            temp = (TextView) itemView.findViewById(R.id.temp);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }

        public void fill(Forecast forecast, int position){
            temp.setText( new SimpleDateFormat(" HH.mm").format(forecast.getDtTxt()) + "  "+forecast.getMain().getTemp() + "C");
            icon.setImageResource(R.mipmap.ic_launcher);
            icon.setColorFilter(new Random().nextInt());
        }
    }
}
