package com.example.serhey.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yass on 1/31/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weatherData.db";
    private static final int VERSION = 1;
    public static final String TABLE = "DataToday";

    public static final String COLUMN_CURRENT_DATE = "current_date";
    public static final String COLUMN_CITY = "cityDB";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WIND = "wind";
    public static final String COLUMN_ID_IMAGE = "image";
    public static final String COLUMN_CLOUDY = "cloudy";
    public static final String COLUMN_HUMIDITY = "humidity";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE + "("
                +  "_id" +" integer primary key autoincrement, "
                + COLUMN_CURRENT_DATE + " ,"
                + COLUMN_CITY  + " ,"
                + COLUMN_TEMPERATURE  + " ,"
                + COLUMN_DATE  + " ,"
                + COLUMN_WIND  + " ,"
                + COLUMN_ID_IMAGE  + " ,"
                + COLUMN_CLOUDY  + " ,"
                + COLUMN_HUMIDITY
                +")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
    }
}
