package com.example.serhey.weather.ui;

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
                day = "Понедельник";
                break;
            case 2:
                day = "Вторник";
                break;
            case 3:
                day = "Среда";
                break;
            case 4:
                day = "Четверг";
                break;
            case 5:
                day = "Пятница";
                break;
            case 6:
                day = "Суббота";
                break;
            case 7:
                day = "Воскресенье";
                break;
        }
        return day;
    }
}
