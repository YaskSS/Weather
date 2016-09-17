package com.example.serhey.weather.core;

import android.app.Application;
import com.example.serhey.weather.network.NetManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class App extends Application implements AppBridge {

    private NetManager netManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        netManager = new NetManager(this);
    }

    @Override
    public NetManager getNetManager() {
        return netManager;
    }
}
