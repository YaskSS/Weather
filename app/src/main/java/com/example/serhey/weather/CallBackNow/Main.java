
package com.example.serhey.weather.CallBackNow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Main {

    @SerializedName("temp")
    @Expose
    private Float temp;
    @SerializedName("pressure")
    @Expose
    private Integer pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("temp_min")
    @Expose
    private Float tempMin;
    @SerializedName("temp_max")
    @Expose
    private Float tempMax;

    /**
     *
     * @return
     *     The temp
     */
    public Float getTemp() {
        return temp;
    }

    /**
     *
     * @param temp
     *     The temp
     */
    public void setTemp(Float temp) {
        this.temp = temp;
    }

    /**
     *
     * @return
     *     The pressure
     */
    public Integer getPressure() {
        return pressure;
    }

    /**
     *
     * @param pressure
     *     The pressure
     */
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    /**
     *
     * @return
     *     The humidity
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     *
     * @param humidity
     *     The humidity
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    /**
     *
     * @return
     *     The tempMin
     */
    public Float getTempMin() {
        return tempMin;
    }

    /**
     *
     * @param tempMin
     *     The temp_min
     */
    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

    /**
     *
     * @return
     *     The tempMax
     */
    public Float getTempMax() {
        return tempMax;
    }

    /**
     *
     * @param tempMax
     *     The temp_max
     */
    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

}
