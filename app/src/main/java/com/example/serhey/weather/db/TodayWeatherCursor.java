package com.example.serhey.weather.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.serhey.weather.db.models.Weather;

/**
 * Created by yass on 2/1/17.
 */

public class TodayWeatherCursor extends CursorWrapper{

    public TodayWeatherCursor(Cursor cursor) {
        super(cursor);
    }

    public Weather getBackNow() {
       Weather weather = new Weather();
        long current_date = getLong(getColumnIndex(DBHelper.COLUMN_CURRENT_DATE));
         weather.setCity(getString(getColumnIndex(DBHelper.COLUMN_CITY)));
         weather.setTemperature( getString(getColumnIndex(DBHelper.COLUMN_TEMPERATURE)));
         weather.setDate(getLong(getColumnIndex(DBHelper.COLUMN_DATE)));
         weather.setWind(getString(getColumnIndex(DBHelper.COLUMN_WIND)));
         weather.setIdImage(getString(getColumnIndex(DBHelper.COLUMN_ID_IMAGE)));
         weather.setCloudy(getString(getColumnIndex(DBHelper.COLUMN_CLOUDY)));
         weather.setHumidity(getString(getColumnIndex(DBHelper.COLUMN_HUMIDITY)));

        if (weather != null){
            return  weather;
        }

        return null ;
    }
}
