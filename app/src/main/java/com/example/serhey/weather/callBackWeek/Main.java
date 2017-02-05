
package com.example.serhey.weather.callBackWeek;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private float temp;
    @SerializedName("temp_min")
    @Expose
    private float tempMin;
    @SerializedName("temp_max")
    @Expose
    private float tempMax;
    @SerializedName("pressure")
    @Expose
    private float pressure;
    @SerializedName("sea_level")
    @Expose
    private float seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private float grndLevel;
    @SerializedName("humidity")
    @Expose
    private float humidity;
    @SerializedName("temp_kf")
    @Expose
    private float tempKf;

    public float getTemp() {
        return temp;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public float getPressure() {
        return pressure;
    }

    public float getSeaLevel() {
        return seaLevel;
    }

    public float getGrndLevel() {
        return grndLevel;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTempKf() {
        return tempKf;
    }
}
