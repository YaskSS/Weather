package com.example.serhey.weather.core;

import android.app.Application;
import com.example.serhey.weather.network.NetManager;

public class App extends Application implements AppBridge {

    private NetManager netManager;

    @Override
    public void onCreate() {
        super.onCreate();
        netManager = new NetManager(this);
    }

    @Override
    public NetManager getNetManager() {
        return netManager;
    }
}
