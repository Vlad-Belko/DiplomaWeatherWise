package com.example.w1;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

public class NotificationMyTomorrow extends BroadcastReceiver {
    WeatherCodeIcon weatherCodeIcon = new WeatherCodeIcon();
    Map<Integer, Integer> weather_code_icon =  weatherCodeIcon.getMap();
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        String tomorrow = extras.getString("tomorrow");
        int weather_code = extras.getInt("weather_code");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id_tomorrow")
                .setSmallIcon(weather_code_icon.get(weather_code))
                .setContentTitle(context.getResources().getString(R.string.tomorroExpect))
                .setContentText(tomorrow)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, context.getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
            return;
        }
        notificationManagerCompat.notify(121, builder.build());
    }
}
