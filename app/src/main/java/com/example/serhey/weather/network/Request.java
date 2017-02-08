package com.example.serhey.weather.network;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.serhey.weather.db.responseNow.BackNow;
import com.example.serhey.weather.db.responseNow.Weather;
import com.example.serhey.weather.db.responseWeek.BackWeek;
import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.core.AppBridge;
import com.example.serhey.weather.db.DBHelper;
import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.db.TodayWeatherCursor;
import com.example.serhey.weather.ui.fragments.TodayForecastFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request {


    private static final String TAG = "Request";
    protected AppBridge appBridge;
    private List<Weather> listWeatherForIcon;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;

    public Request() {
        context = App.getContext();
        sqLiteDatabase = new DBHelper(context).getWritableDatabase();
    }

    public void requestTodayWeather() {
        appBridge = (AppBridge) App.getContext().getApplicationContext();
        if (checkConnectionInternet()) {
            appBridge.getNetManager().getTodayWeather(new Callback<BackNow>() {
                @Override
                public void onResponse(Call<BackNow> call, Response<BackNow> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "todayWeatherResponse, ok");
                        //SharedPrefHelper.getInstance().saveWeatherDataNow(response.body());// for save in Shared

                        sqLiteDatabase.insert(DBHelper.TABLE, null,  getContentForSaveDB(response));

                        Intent intent = new Intent(TodayForecastFragment.BROADCAST_ACTION);
                        App.getContext().sendBroadcast(intent);
                    }
                }

                @Override
                public void onFailure(Call<BackNow> call, Throwable t) {
                    Log.d(TAG, "todayWeatherResponse, !ok");
                    t.printStackTrace();
                    //SharedPrefHelper.getInstance().saveWeatherDataNow(null);
                }
            });
        } else {
            Toast.makeText(App.getContext().getApplicationContext(),
                    App.getContext().getString(R.string.changeNetwork), Toast.LENGTH_LONG).show();
        }
    }

    public void requestWeekWeather() {
        appBridge = (AppBridge) App.getContext().getApplicationContext();
        if (checkConnectionInternet()) {
            appBridge.getNetManager().getWeekWeather(new Callback<BackWeek>() {
                @Override
                public void onResponse(Call<BackWeek> call, Response<BackWeek> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "weekWeather ok");
                        SharedPrefHelper.getInstance().saveWeatherWeekData(response.body());
                    } else {
                        Log.d(TAG, "weekWeather !ok");
                    }
                }

                @Override
                public void onFailure(Call<BackWeek> call, Throwable t) {
                    t.printStackTrace();
                    SharedPrefHelper.getInstance().saveWeatherWeekData(null);
                }
            });
        } else {
            Toast.makeText(App.getContext().getApplicationContext(),
                    App.getContext().getString(R.string.changeNetwork), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkConnectionInternet() {
        if (!appBridge.getNetManager().checkConnectionWithInternet()) {
            return false;
        } else {
            return true;
        }
    }

    public TodayWeatherCursor queryWeatherToday(String whereClause, String[] whereArgs) {
        Cursor cursor = sqLiteDatabase.query(
                DBHelper.TABLE,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TodayWeatherCursor(cursor);
    }

    public ContentValues getContentForSaveDB(Response<BackNow> response) {
        listWeatherForIcon = response.body().getWeather();
        contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_CURRENT_DATE, new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime()));
        contentValues.put(DBHelper.COLUMN_CITY, response.body().getName());
        contentValues.put(DBHelper.COLUMN_TEMPERATURE, response.body().getMain().getTemp());
        contentValues.put(DBHelper.COLUMN_DATE, new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
        contentValues.put(DBHelper.COLUMN_WIND, response.body().getWind().getSpeed().toString());
        contentValues.put(DBHelper.COLUMN_ID_IMAGE, listWeatherForIcon.get(0).getIcon());
        contentValues.put(DBHelper.COLUMN_CLOUDY, response.body().getClouds().getAll().toString());
        contentValues.put(DBHelper.COLUMN_HUMIDITY, response.body().getClouds().getAll().toString());

        return contentValues;
    }
}
