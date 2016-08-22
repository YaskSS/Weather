
package com.example.serhey.weather.CallBackNow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Wind {

    @SerializedName("speed")
    @Expose
    private Float speed;
    @SerializedName("deg")
    @Expose
    private Integer deg;
    @SerializedName("gust")
    @Expose
    private Float gust;

    /**
     *
     * @return
     *     The speed
     */
    public Float getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     *     The speed
     */
    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    /**
     *
     * @return
     *     The deg
     */
    public Integer getDeg() {
        return deg;
    }

    /**
     *
     * @param deg
     *     The deg
     */
    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    /**
     *
     * @return
     *     The gust
     */
    public Float getGust() {
        return gust;
    }

    /**
     *
     * @param gust
     *     The gust
     */
    public void setGust(Float gust) {
        this.gust = gust;
    }

}
