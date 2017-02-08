package com.example.serhey.weather.db.models;

/**
 * Created by yass on 2/1/17.
 */

public class Weather {

    private String city;
    private String temperature;
    private long date;
    private String wind;
    private String idImage;
    private String cloudy;
    private String humidity;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getCloudy() {
        return cloudy;
    }

    public void setCloudy(String cloudy) {
        this.cloudy = cloudy;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
