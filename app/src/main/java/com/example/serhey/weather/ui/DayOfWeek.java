package com.example.serhey.weather.ui;

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Serhey on 30.08.2016.
 */
public class DayOfWeek {
    int id_day;
    String day;

    public String getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        id_day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (id_day) {
            case 1:
                day = App.getContext().getString(R.string.monday);
                break;
            case 2:
                day = App.getContext().getString(R.string.tuesday);
                break;
            case 3:
                day = App.getContext().getString(R.string.wendesday);
                break;
            case 4:
                day = App.getContext().getString(R.string.thursday);
                break;
            case 5:
                day = App.getContext().getString(R.string.friday);
                break;
            case 6:
                day = App.getContext().getString(R.string.saturday);
                break;
            case 7:
                day = App.getContext().getString(R.string.sunday);
                break;
        }
        return day;
    }
}
