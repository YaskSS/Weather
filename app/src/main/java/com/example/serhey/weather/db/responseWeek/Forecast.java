
package com.example.serhey.weather.db.responseWeek;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Forecast {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = new ArrayList<Weather>();
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("sys")
    @Expose
    private Sys_ sys;
    @SerializedName("dt_txt")
    @Expose
    private Date dtTxt;
    @SerializedName("rain")
    @Expose
    private Rain rain;

    /**
     *
     * @return
     *     The dt
     */
    public Integer getDt() {
        return dt;
    }

    /**
     *
     * @param dt
     *     The dt
     */
    public void setDt(Integer dt) {
        this.dt = dt;
    }

    /**
     *
     * @return
     *     The main
     */
    public Main getMain() {
        return main;
    }

    /**
     *
     * @param main
     *     The main
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     *
     * @return
     *     The weather
     */
    public java.util.List<Weather> getWeather() {
        return weather;
    }

    /**
     *
     * @param weather
     *     The weather
     */
    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    /**
     *
     * @return
     *     The clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     *
     * @param clouds
     *     The clouds
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     *
     * @return
     *     The wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     *
     * @param wind
     *     The wind
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     *
     * @return
     *     The sys
     */
    public Sys_ getSys() {
        return sys;
    }

    /**
     *
     * @param sys
     *     The sys
     */
    public void setSys(Sys_ sys) {
        this.sys = sys;
    }

    /**
     *
     * @return
     *     The dtTxt
     */
    public Date getDtTxt() {
        return dtTxt;
    }

    /**
     *
     * @param dtTxt
     *     The dt_txt
     */
    public void setDtTxt(Date dtTxt) {
        this.dtTxt = dtTxt;
    }

    /**
     *
     * @return
     *     The rain
     */
    public Rain getRain() {
        return rain;
    }

    /**
     *
     * @param rain
     *     The rain
     */
    public void setRain(Rain rain) {
        this.rain = rain;
    }

}
