package com.example.serhey.weather.core;

import com.example.serhey.weather.network.NetManager;
import com.example.serhey.weather.network.Request;

public interface AppBridge {
    NetManager getNetManager();
    Request getRequest();
}
