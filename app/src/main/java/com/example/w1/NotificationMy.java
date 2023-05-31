package com.example.w1;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class NotificationMy extends BroadcastReceiver {
    WeatherCodeIcon weatherCodeIcon = new WeatherCodeIcon();
    Map<Integer, Integer> weather_code_icon =  weatherCodeIcon.getMap();

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, DestinationActivity.class);
        Bundle extras = intent.getExtras();
        String info = extras.getString("info");
        int num = extras.getInt("requestCode");

        String city = extras.getString("city");
        double temperature_2m = extras.getDouble("temperature_2m");
        double windspeed_10m = extras.getDouble("windspeed_10m");
        double apparent_temperature = extras.getDouble("apparent_temperature");
        String weather_code_hourly = extras.getString("weather_code_hourly");
        int weather_code = extras.getInt("weather_code");
        int relativehumidity_2m = extras.getInt("relativehumidity_2m");
        int precipitation_probability = extras.getInt("precipitation_probability");
        double pressure_msl = extras.getDouble("pressure_msl");
        double visibility = extras.getDouble("visibility");
        double uv_index = extras.getDouble("uv_index");
        double windgusts_10m = extras.getDouble("windgusts_10m");
        double winddirection_10m = extras.getDouble("winddirection_10m");
        float elevation = extras.getFloat("elev");
        int cloudcover = extras.getInt("cloudcover");

        double pm10 = extras.getDouble("pm10");
        double pm2_5 = extras.getDouble("pm2_5");
        double carbon_monoxide = extras.getDouble("carbon_monoxide");
        double nitrogen_dioxide = extras.getDouble("nitrogen_dioxide");
        double sulphur_dioxide = extras.getDouble("sulphur_dioxide");
        double ozone = extras.getDouble("ozone");
        double aerosol_optical_depth = extras.getDouble("aerosol_optical_depth");
        double dust = extras.getDouble("dust");
        int european_aqi = extras.getInt("european_aqi");
        int european_aqi_pm2_5 = extras.getInt("european_aqi_pm2_5");
        int european_aqi_pm10 = extras.getInt("european_aqi_pm10");
        int european_aqi_no2 = extras.getInt("european_aqi_no2");
        int european_aqi_o3 = extras.getInt("european_aqi_o3");
        int european_aqi_so2 = extras.getInt("european_aqi_so2");

        i.putExtra("temperature_2m", temperature_2m);
        i.putExtra("windspeed_10m", windspeed_10m);
        i.putExtra("apparent_temperature", apparent_temperature);
        i.putExtra("weather_code_hourly", weather_code_hourly);
        i.putExtra("relativehumidity_2m", relativehumidity_2m);
        i.putExtra("precipitation_probability", precipitation_probability);
        i.putExtra("pressure_msl", pressure_msl);
        i.putExtra("visibility", visibility);
        i.putExtra("uv_index", uv_index);
        i.putExtra("windgusts_10m", windgusts_10m);
        i.putExtra("winddirection_10m", winddirection_10m);
        i.putExtra("elev", elevation);
        i.putExtra("cloudcover", cloudcover);

        i.putExtra("pm10", pm10);
        i.putExtra("pm2_5", pm2_5);
        i.putExtra("carbon_monoxide", carbon_monoxide);
        i.putExtra("nitrogen_dioxide", nitrogen_dioxide);
        i.putExtra("sulphur_dioxide", sulphur_dioxide);
        i.putExtra("ozone", ozone);
        i.putExtra("aerosol_optical_depth", aerosol_optical_depth);
        i.putExtra("dust", dust);
        i.putExtra("european_aqi", european_aqi);
        i.putExtra("european_aqi_pm2_5", european_aqi_pm2_5);
        i.putExtra("european_aqi_pm10", european_aqi_pm10);
        i.putExtra("european_aqi_no2", european_aqi_no2);
        i.putExtra("european_aqi_o3", european_aqi_o3);
        i.putExtra("european_aqi_so2", european_aqi_so2);

        Calendar calendarNow = Calendar.getInstance();
        String time = String.valueOf(calendarNow.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatterHour = new SimpleDateFormat("HH");
        String formattedDateHour = formatter.format(calendarNow.getTime());
        int t = Integer.parseInt(formatterHour.format(calendarNow.getTime()));
        String cityTime = "";

        int hour = Integer.parseInt(formatterHour.format(calendarNow.getTime()));
        cityTime = city + context.getResources().getString(R.string.infoAtNow);
        if(hour >= 0 && hour < 4) {
            time = String.format("%s %s %s",context.getResources().getString(R.string.now), formattedDateHour, context.getResources().getString(R.string.night));
            if (hour == 0 ) cityTime = city + ", " + 12 + " " +  context.getResources().getString(R.string.night);
            if(weather_code == 0 || weather_code == 1 || weather_code == 2 || weather_code == 3){
                weather_code += 100;
            }
        } else if (hour >= 4 && hour < 12) {
            time = String.format("%s %s %s",context.getResources().getString(R.string.now), formattedDateHour, context.getResources().getString(R.string.morning));
        } else if (hour >= 11 && hour < 17) {
            time = String.format("%s %s %s",context.getResources().getString(R.string.now), formattedDateHour, context.getResources().getString(R.string.day));
        } else if (hour >= 17 && hour <= 23) {
            time = String.format("%s %s %s",context.getResources().getString(R.string.now), formattedDateHour, context.getResources().getString(R.string.evening));
        }

        i.putExtra("weather_code", weather_code);
        i.putExtra("time", cityTime);
        i.putExtra("hour", hour);
        i.putExtra("requestCode", num);
        String title = time + context.getResources().getString(R.string.weatherCondNear);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, num, i, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(weather_code_icon.get(weather_code))
                .setColorized(true)
                .setContentTitle(title)
                .setContentText(info)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(info))
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, context.getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
            return;
        }
        notificationManagerCompat.notify(111, builder.build());
    }
}
