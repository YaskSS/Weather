
package com.example.serhey.weather.db.responseNow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Main {

    @SerializedName("temp")
    @Expose
    private float temp;
    @SerializedName("pressure")
    @Expose
    private float pressure;
    @SerializedName("humidity")
    @Expose
    private float humidity;
    @SerializedName("temp_min")
    @Expose
    private float tempMin;
    @SerializedName("temp_max")
    @Expose
    private float tempMax;

    public float getTemp() {
        return temp;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }
}
