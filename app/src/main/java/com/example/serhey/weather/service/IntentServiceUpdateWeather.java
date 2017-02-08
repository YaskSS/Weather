package com.example.serhey.weather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.serhey.weather.R;
import com.example.serhey.weather.db.SharedPrefHelper;
import com.example.serhey.weather.network.Request;
import com.example.serhey.weather.ui.fragments.TodayForecastFragment;
import com.example.serhey.weather.ui.activities.SettingsActivity;

/**
 * Created by yass on 1/13/17.
 */

public class IntentServiceUpdateWeather extends IntentService {

    private static final String TAG = "IntentService";
    private static final int POLL_INTERVAL =  SharedPrefHelper.getInstance().getTimeRefresh();
    Request request;

    public IntentServiceUpdateWeather() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        request = new Request();
        Log.i(TAG, "onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressWarnings("deprecation")
        boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;

        if (isNetworkAvailable) {
            startRequest();
        } else {
            return;
        }
        Log.i(TAG, "Received an intent: " + intent);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = new Intent(context, IntentServiceUpdateWeather.class);
        PendingIntent pi = PendingIntent.getService(
                context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setRepeating(AlarmManager.RTC,
                    System.currentTimeMillis(), POLL_INTERVAL, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    private void startRequest() {
        Log.i(TAG, "I am working! I am service");
        if (SharedPrefHelper.getInstance().getStatusBackground().equals(SettingsActivity.BACKGROUND_ON)) {
            SharedPrefHelper.getInstance().saveWeatherDataNow(null);
            request.requestTodayWeather();
            request.requestWeekWeather();
            if (SharedPrefHelper.getInstance().getStatusNotification().equals(SettingsActivity.NOTIFICATION_ON)) {
                sendNotificationMessage();
            }
        }
    }

    private void sendNotificationMessage() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), TodayForecastFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.weather))
                .setContentText(getString(R.string.updateDate)+ " " + SharedPrefHelper.getInstance().getWeatherDataNow().getName()); // Текст уведомления

        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(15, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "OnDestroy");
    }
}
