package com.example.serhey.weather.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.serhey.weather.R;
import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.network.Request;

/**
 * Created by Serhey on 20.11.2016.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView sunImageView;
    private ImageView cloudImageView;
    private ImageView bigCloudImageView;
    private ImageView titleImageView;
    private ImageView snowImageView2;
    private ImageView snowImageView3;
    private ImageView snowImageView4;
    private ImageView snowImageView5;
    private ImageView snowImageView;
    private Request request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        request = new Request();

        if (SharedPrefHelper.getInstance().getWeatherDataNow() == null) {
            request.requestTodayWeather();
            request.requestWeekWeather();
        }
        initViews();
        setSunImageViewAnimation();
    }

    private void initViews() {
        sunImageView = (ImageView) findViewById(R.id.sun_imageView);
        cloudImageView = (ImageView) findViewById(R.id.cloud_imageView);
        bigCloudImageView = (ImageView) findViewById(R.id.big_cloud_imageView);
        titleImageView = (ImageView) findViewById(R.id.title_weather_imageView);
        snowImageView = (ImageView) findViewById(R.id.snow_imageView);
        snowImageView2 = (ImageView) findViewById(R.id.snow_imageView2);
        snowImageView3 = (ImageView) findViewById(R.id.snow_imageView3);
        snowImageView4 = (ImageView) findViewById(R.id.snow_imageView4);
        snowImageView5 = (ImageView) findViewById(R.id.snow_imageView5);
    }

    private void setSunImageViewAnimation() {
        sunImageView.startAnimation(AnimationUtils.loadAnimation(getApplication()
                , R.anim.animation_sun));
        titleImageView.startAnimation(AnimationUtils.loadAnimation(getApplication()
                , R.anim.animation_sun));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sunImageView.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_circle));
                cloudImageView.setVisibility(View.VISIBLE);
                cloudImageView.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_left));
                bigCloudImageView.setVisibility(View.VISIBLE);
                bigCloudImageView.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_right));
                snowImageView.setVisibility(View.VISIBLE);
                snowImageView2.setVisibility(View.VISIBLE);
                snowImageView3.setVisibility(View.VISIBLE);
                snowImageView4.setVisibility(View.VISIBLE);
                snowImageView5.setVisibility(View.VISIBLE);
                snowImageView.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_snow_douwn));
                snowImageView2.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_snow_douwn_vertical));
                snowImageView3.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_snow_douwn));
                snowImageView4.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_snow_douwn));
                snowImageView5.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.animation_snow_douwn_long));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        snowImageView.setVisibility(View.GONE);
                        snowImageView2.setVisibility(View.GONE);
                        snowImageView3.setVisibility(View.GONE);
                        snowImageView4.setVisibility(View.GONE);
                        snowImageView5.setVisibility(View.GONE);
                    }
                }, 2900);
                startToTabActivity();
            }
        }, 590);
    }

    private void startToTabActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 5500);
    }
}
