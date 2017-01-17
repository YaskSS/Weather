
package com.example.serhey.weather.CallBackWeek;

import com.example.serhey.weather.CallBackNow.BackNow;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class BackWeek {

    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Float message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<Forecast> list = new ArrayList<Forecast>();

    private static transient Gson gson = new Gson();

    /**
     *
     * @return
     *     The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     *     The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     *
     * @return
     *     The cod
     */
    public String getCod() {
        return cod;
    }

    /**
     *
     * @param cod
     *     The cod
     */
    public void setCod(String cod) {
        this.cod = cod;
    }

    /**
     *
     * @return
     *     The message
     */
    public Float getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The message
     */
    public void setMessage(Float message) {
        this.message = message;
    }

    /**
     *
     * @return
     *     The cnt
     */
    public Integer getCnt() {
        return cnt;
    }

    /**
     *
     * @param cnt
     *     The cnt
     */
    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    /**
     *
     * @return
     *     The list
     */
    public java.util.List<Forecast> getList() {
        return list;
    }

    /**
     *
     * @param list
     *     The list
     */
    public void setList(java.util.List<Forecast> list) {
        this.list = list;
    }


    public static String toJSONCollection(BackWeek backWeekData){
        return gson.toJson(backWeekData);
    }

    public static BackWeek fromJsonCollection(String backWeekData){
        return gson.fromJson(backWeekData, new TypeToken<BackWeek>() {
        }.getType());
    }
}
