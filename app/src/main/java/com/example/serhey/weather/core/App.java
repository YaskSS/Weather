package com.example.serhey.weather.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.network.NetManager;
import com.crashlytics.android.Crashlytics;
import com.example.serhey.weather.network.Request;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class App extends Application implements AppBridge {

    private NetManager netManager;
    private Request request;
    private static Context context;
    private Locale locale;
    private String localization;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        context = base;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        netManager = new NetManager(this);
        request = new Request();
        localization = SharedPrefHelper.getInstance().getLanguage();
        setupLocalization();
    }

    @Override
    public NetManager getNetManager() {
        return netManager;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    private void setupLocalization() {
        if (localization.equals("en")) {
            locale = new Locale(localization);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, null);
        } else if (localization.equals("ru")) {
            locale = new Locale(localization);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, null);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        locale = new Locale(localization);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
    }
}
