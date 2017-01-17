package com.example.serhey.weather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;
import com.example.serhey.weather.logic.SharedPrefHelper;
import com.example.serhey.weather.service.IntentServiceUpdateWeather;

/**
 * Created by Serhey on 22.11.2016.
 */

public class SettingsActivity extends AppCompatActivity {

    private static final String RUSSIAN_LANGUAGE = "ru";
    private static final String ENGLISH_LANGUAGE = "en";
    public static final int OFF_VISIBILITY_VIEW = 0;
    public static final int ON_VISIBILITY_VIEW = 1;

    public static final String BACKGROUND_ON = "on";
    public static final String BACKGROUND_OFF = "off";

    public static final String NOTIFICATION_ON = "on";
    public static final String NOTIFICATION_OFF = "off";

    RadioButton oneHourRadioButton;
    RadioButton twoHoursRadioButton;
    RadioButton threeHourRadioButton;
    RadioButton russianLanguageRadioButton;
    RadioButton englishLanguageRadioButton;

    RadioGroup refreshRadioGroup;

    CheckBox temperatureCheckBox;
    CheckBox humidityCheckBox;
    CheckBox windCheckBox;
    CheckBox cityCheckBox;
    CheckBox cloudinessCheckBox;
    CheckBox iconCheckBox;
    CheckBox dateCheckBox;
    CheckBox notificationCheckBox;

    Switch refreshSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        setTrueRefreshRadioButton();
        setTrueLanguageRadioButton();
        setTrueVisibilityViews();
        setVisibilityRefreshTime(SharedPrefHelper.getInstance().getStatusBackground());
        setListenerViews();
        setVisibility();
    }

    private void setListenerViews() {
        oneHourRadioButton.setOnClickListener(radioButtonClickListener);
        twoHoursRadioButton.setOnClickListener(radioButtonClickListener);
        threeHourRadioButton.setOnClickListener(radioButtonClickListener);
        russianLanguageRadioButton.setOnClickListener(radioButtonClickListener);
        englishLanguageRadioButton.setOnClickListener(radioButtonClickListener);

        temperatureCheckBox.setOnClickListener(checkBoxClickListener);
        humidityCheckBox.setOnClickListener(checkBoxClickListener);
        windCheckBox.setOnClickListener(checkBoxClickListener);
        cityCheckBox.setOnClickListener(checkBoxClickListener);
        cloudinessCheckBox.setOnClickListener(checkBoxClickListener);
        iconCheckBox.setOnClickListener(checkBoxClickListener);
        dateCheckBox.setOnClickListener(checkBoxClickListener);
        notificationCheckBox.setOnClickListener(notificationCheckBoxClickListener);

        refreshSwitch.setOnClickListener(onSwitchListener);
    }

    private void initViews() {
        oneHourRadioButton = (RadioButton) findViewById(R.id.one_hour_RB);
        twoHoursRadioButton = (RadioButton) findViewById(R.id.two_hours_RB);
        threeHourRadioButton = (RadioButton) findViewById(R.id.three_hours_RB);
        russianLanguageRadioButton = (RadioButton) findViewById(R.id.russian_RB);
        englishLanguageRadioButton = (RadioButton) findViewById(R.id.english_RB);

        temperatureCheckBox = (CheckBox) findViewById(R.id.temperature_checkBox);
        humidityCheckBox = (CheckBox) findViewById(R.id.humidity_checkBox);
        windCheckBox = (CheckBox) findViewById(R.id.wind_checkBox);
        cityCheckBox = (CheckBox) findViewById(R.id.city_checkBox);
        cloudinessCheckBox = (CheckBox) findViewById(R.id.cloudiness_checkBox);
        iconCheckBox = (CheckBox) findViewById(R.id.icon_checkBox);
        dateCheckBox = (CheckBox) findViewById(R.id.date_checkBox);
        notificationCheckBox = (CheckBox) findViewById(R.id.notification_checkBox);

        refreshSwitch = (Switch) findViewById(R.id.refresh_switch);
        refreshRadioGroup = (RadioGroup) findViewById(R.id.refresh_radioGroup);
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.one_hour_RB:
                    SharedPrefHelper.getInstance().saveTimeRefresh(SharedPrefHelper.REFRESH_TIME_ONE_HOUR);
                    break;
                case R.id.two_hours_RB:
                    SharedPrefHelper.getInstance().saveTimeRefresh(SharedPrefHelper.REFRESH_TIME_TWO_HOURS);
                    break;
                case R.id.three_hours_RB:
                    SharedPrefHelper.getInstance().saveTimeRefresh(SharedPrefHelper.REFRESH_TIME_THREE_HOURS);
                    break;
                case R.id.russian_RB:
                    SharedPrefHelper.getInstance().saveLanguage(RUSSIAN_LANGUAGE);
                    break;
                case R.id.english_RB:
                    SharedPrefHelper.getInstance().saveLanguage(ENGLISH_LANGUAGE);
                    break;
                default:
                    break;
            }
        }
    };

    private void setTrueRefreshRadioButton() {
        int resultRefreshTime = SharedPrefHelper.getInstance().getTimeRefresh();
        if (resultRefreshTime == SharedPrefHelper.REFRESH_TIME_ONE_HOUR) {
            oneHourRadioButton.setChecked(true);
            checkUpdatingTime(SharedPrefHelper.REFRESH_TIME_ONE_HOUR);
        } else if (resultRefreshTime == SharedPrefHelper.REFRESH_TIME_TWO_HOURS) {
            twoHoursRadioButton.setChecked(true);
            checkUpdatingTime(SharedPrefHelper.REFRESH_TIME_TWO_HOURS);
        } else if (resultRefreshTime == SharedPrefHelper.REFRESH_TIME_THREE_HOURS) {
            threeHourRadioButton.setChecked(true);
            checkUpdatingTime(SharedPrefHelper.REFRESH_TIME_THREE_HOURS);
        }
    }

    private void checkUpdatingTime(int newTime) {
        if (newTime != SharedPrefHelper.getInstance().getTimeRefresh() &&
                SharedPrefHelper.getInstance().getStatusBackground().equals(BACKGROUND_ON)){
           IntentServiceUpdateWeather.setServiceAlarm(getApplicationContext(), false);
           IntentServiceUpdateWeather.setServiceAlarm(getApplicationContext(), true);
        }
    }

    private void setTrueLanguageRadioButton() {
        String resultLanguage = SharedPrefHelper.getInstance().getLanguage();
        if (resultLanguage.equals(RUSSIAN_LANGUAGE)) {
            russianLanguageRadioButton.setChecked(true);
        } else if (resultLanguage.equals(ENGLISH_LANGUAGE)) {
            englishLanguageRadioButton.setChecked(true);
        }
    }

    private void setTrueVisibilityViews() {
        if (SharedPrefHelper.getInstance().getTemperatureVisibility() == ON_VISIBILITY_VIEW) {
            temperatureCheckBox.setChecked(true);
        } else {
            temperatureCheckBox.setChecked(false);
        }
        if (SharedPrefHelper.getInstance().getHumidityVisibility() == ON_VISIBILITY_VIEW) {
            humidityCheckBox.setChecked(true);
        } else {
            humidityCheckBox.setChecked(false);
        }
        if (SharedPrefHelper.getInstance().getWindVisibility() == ON_VISIBILITY_VIEW) {
            windCheckBox.setChecked(true);
        } else {
            windCheckBox.setChecked(false);
        }
        if (SharedPrefHelper.getInstance().getCityVisibility() == ON_VISIBILITY_VIEW) {
            cityCheckBox.setChecked(true);
        } else {
            cityCheckBox.setChecked(false);
        }
        if (SharedPrefHelper.getInstance().getCloudinessVisibility() == ON_VISIBILITY_VIEW) {
            cloudinessCheckBox.setChecked(true);
        } else {
            cloudinessCheckBox.setChecked(false);
        }
        if (SharedPrefHelper.getInstance().getIconVisibility() == ON_VISIBILITY_VIEW) {
            iconCheckBox.setChecked(true);
        } else {
            iconCheckBox.setChecked(false);
        }
        if (SharedPrefHelper.getInstance().getDateVisibility() == ON_VISIBILITY_VIEW) {
            dateCheckBox.setChecked(true);
        } else {
            dateCheckBox.setChecked(false);
        }
    }

    View.OnClickListener checkBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CheckBox checkBox = (CheckBox) view;
            switch (checkBox.getId()) {
                case R.id.temperature_checkBox:
                    SharedPrefHelper.getInstance().saveTemperatureVisibility(checkCheckBox(checkBox));
                    break;
                case R.id.humidity_checkBox:
                    SharedPrefHelper.getInstance().saveHumidityVisibility(checkCheckBox(checkBox));
                    break;
                case R.id.wind_checkBox:
                    SharedPrefHelper.getInstance().saveWindVisibility(checkCheckBox(checkBox));
                    break;
                case R.id.city_checkBox:
                    SharedPrefHelper.getInstance().saveCityVisibility(checkCheckBox(checkBox));
                    break;
                case R.id.cloudiness_checkBox:
                    SharedPrefHelper.getInstance().saveCloudinessVisibility(checkCheckBox(checkBox));
                    break;
                case R.id.icon_checkBox:
                    SharedPrefHelper.getInstance().saveIconVisibility(checkCheckBox(checkBox));
                    break;
                case R.id.date_checkBox:
                    SharedPrefHelper.getInstance().saveDateVisibility(checkCheckBox(checkBox));
                    break;
                default:
                    break;
            }
        }
    };

    View.OnClickListener onSwitchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Switch refreshSwitch = (Switch) view;
            if (refreshSwitch.isChecked()) {
                SharedPrefHelper.getInstance().saveStatusBackground(BACKGROUND_ON);
                setVisibilityRefreshTime(BACKGROUND_ON);
                IntentServiceUpdateWeather.setServiceAlarm(getApplicationContext(), true);
            } else if (!refreshSwitch.isChecked()) {
                SharedPrefHelper.getInstance().saveStatusBackground(BACKGROUND_OFF);
                setVisibilityRefreshTime(BACKGROUND_OFF);
                IntentServiceUpdateWeather.setServiceAlarm(getApplicationContext(), false);
            }
            setTrueRefreshRadioButton();
        }
    };

    View.OnClickListener notificationCheckBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CheckBox checkBox = (CheckBox) view;
            if (checkCheckBox(checkBox) == ON_VISIBILITY_VIEW){
                SharedPrefHelper.getInstance().saveStatusNotification(NOTIFICATION_ON);
            } else if(checkCheckBox(checkBox) == OFF_VISIBILITY_VIEW){
                SharedPrefHelper.getInstance().saveStatusNotification(NOTIFICATION_OFF);
            }
        }
    };

    private void setVisibility(){
        if (SharedPrefHelper.getInstance().getStatusNotification().equals(NOTIFICATION_ON)){
            notificationCheckBox.setChecked(true);
        } else {
            notificationCheckBox.setChecked(false);
        }
    }

    private void setVisibilityRefreshTime(String status) {
        if (status.equals(BACKGROUND_ON)) {
            refreshSwitch.setChecked(true);
            oneHourRadioButton.setEnabled(true);
            twoHoursRadioButton.setEnabled(true);
            threeHourRadioButton.setEnabled(true);
        } else if (status.equals(BACKGROUND_OFF)) {
            refreshSwitch.setChecked(false);
            oneHourRadioButton.setEnabled(false);
            twoHoursRadioButton.setEnabled(false);
            threeHourRadioButton.setEnabled(false);
        }
    }

    private int checkCheckBox(CheckBox checkBox) {
        if (checkBox.isChecked() == true) {
            return ON_VISIBILITY_VIEW;
        } else {
            return OFF_VISIBILITY_VIEW;
        }
    }
}

