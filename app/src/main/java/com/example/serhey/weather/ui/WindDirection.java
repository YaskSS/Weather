package com.example.serhey.weather.ui;

import com.example.serhey.weather.R;

/**
 * Created by Serhey on 30.08.2016.
 */
public class WindDirection {


    public int setIconWind(float cod_id) {
        int ansverMethodIcon = 0;
        if (cod_id >= 0 && cod_id <= 45) {
            ansverMethodIcon = R.drawable.ic_arrow_top_right;
        } else if (cod_id >= 45 && cod_id <= 90) {
            ansverMethodIcon = R.drawable.ic_arrow_right;
        } else if (cod_id >= 90&& cod_id <= 135) {
            ansverMethodIcon = R.drawable.ic_arrow_bottom_right;
        } else if (cod_id >= 135 && cod_id <= 180) {
            ansverMethodIcon = R.drawable.ic_arrow_down;
        } else if (cod_id >= 180 && cod_id <= 225) {
            ansverMethodIcon = R.drawable.ic_arrow_bottom_left;
        } else if (cod_id >= 225 && cod_id <= 270) {
            ansverMethodIcon = R.drawable.ic_arrow_left;
        } else if (cod_id >= 270 && cod_id <= 315) {
            ansverMethodIcon = R.drawable.ic_arrow_top_left;
        } else if (cod_id >= 315&& cod_id <= 360) {
            ansverMethodIcon = R.drawable.ic_arrow_up;
        }
        return ansverMethodIcon;
    }

    public String setWindDirection(float cod_id) {
        String ansverMethodIcon = "";
        if (cod_id >= 0 && cod_id <= 45) {
            ansverMethodIcon = "северо-восточный";
        } else if (cod_id >= 45 && cod_id <= 90) {
            ansverMethodIcon = "восточный";
        } else if (cod_id >= 90&& cod_id <= 135) {
            ansverMethodIcon = "юго-восточный";
        } else if (cod_id >= 135 && cod_id <= 180) {
            ansverMethodIcon = "южный";
        } else if (cod_id >= 180 && cod_id <= 225) {
            ansverMethodIcon = "юго-западный";
        } else if (cod_id >= 225 && cod_id <= 270) {
            ansverMethodIcon = "западный";
        } else if (cod_id >= 270 && cod_id <= 315) {
            ansverMethodIcon = "северо-западный";
        } else if (cod_id >= 315&& cod_id <= 360) {
            ansverMethodIcon = "северный";
        }
        return ansverMethodIcon;
    }
}
