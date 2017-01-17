package com.example.serhey.weather.ui;

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;

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
        String answerMethodIcon = "";
        if (cod_id >= 0 && cod_id <= 45) {
            answerMethodIcon = App.getContext().getString(R.string.northeast);
        } else if (cod_id >= 45 && cod_id <= 90) {
            answerMethodIcon = App.getContext().getString(R.string.east);
        } else if (cod_id >= 90&& cod_id <= 135) {
            answerMethodIcon = App.getContext().getString(R.string.southeast);
        } else if (cod_id >= 135 && cod_id <= 180) {
            answerMethodIcon = App.getContext().getString(R.string.southern);
        } else if (cod_id >= 180 && cod_id <= 225) {
            answerMethodIcon = App.getContext().getString(R.string.southwest);
        } else if (cod_id >= 225 && cod_id <= 270) {
            answerMethodIcon = App.getContext().getString(R.string.west);
        } else if (cod_id >= 270 && cod_id <= 315) {
            answerMethodIcon = App.getContext().getString(R.string.northwest);
        } else if (cod_id >= 315&& cod_id <= 360) {
            answerMethodIcon = App.getContext().getString(R.string.north);
        }
        return answerMethodIcon;
    }
}
