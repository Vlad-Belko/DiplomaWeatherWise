package com.example.w1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CityForecast extends AppCompatActivity {
    float lon, lat, elevation;
    WeatherCode weathercode;
    WeatherCodeEng weatherCodeEng;
    SharedPreferences reselect_city, app_language;
    String cityName, weatherForTomorrow;
    TextView city, sunset, sunrise, MaxWind, MaxTemp, wind, temp, weathercodeText, dateTV;
    RecyclerView lv;
    Button today, tomorrow;
    Map<Integer, String> weather_codes;
    private Calendar calendarNow, calendarTomorrow;
    private long backPressedTime;
    ImageButton btnOpenDrawer;
    private Toast backToast;
    public static final String APP_LANGUAGE = "language";
    public static final String APP_LANGUAGE_ALARM_PREFERENCES = "my_language_settings";
    public static final String APP_ALARMS = "alarmsOff";
    ConstraintLayout constraintLayout;
    WeatherDataService weatherDataService = new WeatherDataService(CityForecast.this);
    AirDataService airDataService = new AirDataService(CityForecast.this);

    private void createNotificationChannelHourly() {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String descr = "descr";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(descr);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannelTomorrow() {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {
            CharSequence name = "channelTomorrow";
            String descr = "descrTomorrow";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channel_id_tomorrow", name, importance);
            channel.setDescription(descr);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void cancelAlarms(SharedPreferences.Editor editor) {
        if(app_language.getString(APP_ALARMS, "0").equals("2")){
            AlarmManager alarmManager;
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent i = createMyIntent();
            PendingIntent pendingIntentNow;
            for (int num = calendarNow.get(Calendar.HOUR_OF_DAY); num < 24; num++) {
                pendingIntentNow = PendingIntent.getBroadcast(CityForecast.this, num, i, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
                alarmManager.cancel(pendingIntentNow);
            }
            editor.putString(APP_ALARMS, "0");
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), getResources().getString(R.string.clickExit), Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public Intent createMyIntent(){
        return new Intent(this, NotificationMy.class);
    }
    public Intent createMyIntentTomorrow(){
        return new Intent(this, NotificationMyTomorrow.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_forecast);
        constraintLayout = findViewById(R.id.forecast_layout);

        calendarNow = Calendar.getInstance();

        calendarTomorrow = Calendar.getInstance();
        calendarTomorrow.add(Calendar.DATE, 1);
        app_language = getSharedPreferences(APP_LANGUAGE_ALARM_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = app_language.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = sdf.format(calendarNow.getTime());
        String lastDate = app_language.getString("lastDate", "");
        cancelAlarms(editor);
        LocalDate date = LocalDate.now();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if(calendarNow.get(Calendar.HOUR_OF_DAY) <= 4 || calendarNow.get(Calendar.HOUR_OF_DAY) >= 22){
            constraintLayout.setBackground(getDrawable(R.drawable.v1night));
        } else if(calendarNow.get(Calendar.HOUR_OF_DAY) > 4 && calendarNow.get(Calendar.HOUR_OF_DAY) < 12){
            constraintLayout.setBackground(getDrawable(R.drawable.v1morning));
        } else if(calendarNow.get(Calendar.HOUR_OF_DAY) >= 12 && calendarNow.get(Calendar.HOUR_OF_DAY) < 17){
            constraintLayout.setBackground(getDrawable(R.drawable.v1day));
        } else if(calendarNow.get(Calendar.HOUR_OF_DAY) >= 17 && calendarNow.get(Calendar.HOUR_OF_DAY) < 22){
            constraintLayout.setBackground(getDrawable(R.drawable.v1evening));
        }

        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        lon = intent.getFloatExtra("lon", 0);
        lat = intent.getFloatExtra("lat", 0);
        int check = intent.getIntExtra("check", 0);
        elevation = intent.getFloatExtra("elev", 0);

        if(app_language.getString(APP_LANGUAGE, "").equals("ru")){
            weathercode = new WeatherCode();
            weather_codes = weathercode.getMap();
        } else {
            weatherCodeEng = new WeatherCodeEng();
            weather_codes = weatherCodeEng.getMap();
        }

        city = findViewById(R.id.cityName);
        city.setText(cityName);
        lv = findViewById(R.id.hourly_list);
        sunset = findViewById(R.id.sunsetText);
        sunrise = findViewById(R.id.sunriseText);
        MaxWind = findViewById(R.id.MaxWindText);
        MaxTemp = findViewById(R.id.MaxTempText);
        wind = findViewById(R.id.windText);
        temp = findViewById(R.id.tempText);
        weathercodeText = findViewById(R.id.weathercode_Text);
        dateTV = findViewById(R.id.dateTV);
        today = findViewById(R.id.todayBTN);
        tomorrow = findViewById(R.id.tomorrowBTN);
        btnOpenDrawer = findViewById(R.id.openMenu);
        WeatherReportModel city_weather = new WeatherReportModel();

        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&hourly=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,rain,showers,snowfall,weathercode,pressure_msl,visibility,windspeed_10m,winddirection_10m,uv_index&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,uv_index_clear_sky_max,precipitation_sum,rain_sum,showers_sum,snowfall_sum,precipitation_hours,precipitation_probability_max,windspeed_10m_max&windspeed_unit=ms&timezone=auto&current_weather=true&start_date=" + date + "&end_date=" + date.plusDays(1);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject cur_weather = response.getJSONObject("current_weather");
                JSONObject daily = response.getJSONObject("daily");
                JSONObject hourly = response.getJSONObject("hourly");
                JSONArray temperature_max = daily.getJSONArray("temperature_2m_max");
                JSONArray windspeed_max = daily.getJSONArray("windspeed_10m_max");
                JSONArray sunrise_arr = daily.getJSONArray("sunrise");
                JSONArray sunset_arr = daily.getJSONArray("sunset");
                JSONArray weather_code = daily.getJSONArray("weathercode");
                JSONArray weather_code_hourly = hourly.getJSONArray("weathercode");

                int[] codes = new int[24];
                for (int i = 0; i < codes.length; i++) codes[i] = weather_code_hourly.getInt(i);
                int maxNum = 0;
                for (int j = calendarNow.get(Calendar.HOUR_OF_DAY); j < codes.length; j++) {
                    if (codes[j] > maxNum)  maxNum = codes[j];
                }

                city_weather.setCurrent_temperature(cur_weather.getDouble("temperature"));
                city_weather.setCurrent_windSpeed(cur_weather.getDouble("windspeed"));
                city_weather.setMaxTemperature(temperature_max.getString(0));
                city_weather.setMaxWindspeed(windspeed_max.getString(0));
                city_weather.setSunrise(sunrise_arr.getString(0));
                city_weather.setSunset(sunset_arr.getString(0));
                city_weather.setWeather_code(weather_code.getInt(0));

                weathercodeText.setText(getString(R.string.weathercodeText, weather_codes.get(maxNum)));
                weatherForTomorrow = String.valueOf(weather_codes.get(weather_code.getInt(1)));

            } catch (Exception e) { e.printStackTrace(); }
        }, error -> Toast.makeText(CityForecast.this, getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show());
        MySingleton.getInstance(CityForecast.this).addToRequestQueue(request);

        temp.setText(String.format("%s %s",city_weather.getCurrent_temperature(), getResources().getString(R.string.celsius)));
        wind.setText(String.format("%s %s",city_weather.getCurrent_windSpeed(), getResources().getString(R.string.ms)));
        MaxTemp.setText(getString(R.string.maxtempText, String.valueOf(city_weather.getMaxTemperature()), getResources().getString(R.string.celsius)));
        MaxWind.setText(getString(R.string.maxwindSpeed, String.valueOf(city_weather.getMaxWindspeed()), getResources().getString(R.string.ms)));
        sunrise.setText(getString(R.string.sunriseText, String.valueOf(city_weather.getSunrise()), getResources().getString(R.string.am)));
        sunset.setText(getString(R.string.sunsetText, String.valueOf(city_weather.getSunset()), getResources().getString(R.string.pm)));
        dateTV.setText(DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()));


        today.setOnClickListener(v -> {
            dateTV.setText(DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime()));
            tomorrow.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#08939F")));
            today.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#076f80")));

            weatherDataService.getWeather(lon, lat, date, weather_codes, new WeatherDataService.WeatherResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(CityForecast.this, getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
                }
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(List<HourlyWeatherModel> hourlyWeatherModels) {
                    airDataService.getAir(lon, lat, date, new AirDataService.AirResponseListener() {
                        @Override
                        public void onError(String message) {
                            HourWeatherListAdapter arrayAdapter = new HourWeatherListAdapter(CityForecast.this,  hourlyWeatherModels, 1, cityName, elevation);
                            lv.setAdapter(arrayAdapter);
                            if (calendarNow.get(Calendar.HOUR_OF_DAY) != 0){
                                lv.scrollToPosition(calendarNow.get(Calendar.HOUR_OF_DAY) - 1);
                            } else  lv.scrollToPosition(calendarNow.get(Calendar.HOUR_OF_DAY));
                            arrayAdapter.notifyDataSetChanged();
                            Toast.makeText(CityForecast.this, getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(List<HourlyAirModel> airReportModels) {
                            HourWeatherListAdapter arrayAdapter = new HourWeatherListAdapter(CityForecast.this,  hourlyWeatherModels, airReportModels, 1, cityName, elevation);
                            lv.setAdapter(arrayAdapter);
                            if (calendarNow.get(Calendar.HOUR_OF_DAY) != 0){
                                lv.scrollToPosition(calendarNow.get(Calendar.HOUR_OF_DAY) - 1);
                            } else  lv.scrollToPosition(calendarNow.get(Calendar.HOUR_OF_DAY));
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        });
        today.performClick();

        tomorrow.setOnClickListener(v -> {
            dateTV.setText(DateFormat.getDateInstance(DateFormat.FULL).format(calendarTomorrow.getTime()));
            today.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#08939F")));
            tomorrow.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#076f80")));

            weatherDataService.getWeather(lon, lat, date.plusDays(1), weather_codes, new WeatherDataService.WeatherResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(CityForecast.this, getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
                }
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(List<HourlyWeatherModel> hourlyWeatherModels) {
                    airDataService.getAir(lon, lat, date, new AirDataService.AirResponseListener() {
                        @Override
                        public void onError(String message) {
                            HourWeatherListAdapter arrayAdapter = new HourWeatherListAdapter(CityForecast.this,  hourlyWeatherModels, 2, cityName, elevation);
                            lv.setAdapter(arrayAdapter);
                            if (calendarNow.get(Calendar.HOUR_OF_DAY) != 0){
                                lv.scrollToPosition(calendarNow.get(Calendar.HOUR_OF_DAY) - 1);
                            } else  lv.scrollToPosition(calendarNow.get(Calendar.HOUR_OF_DAY));
                            arrayAdapter.notifyDataSetChanged();
                            Toast.makeText(CityForecast.this, getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(List<HourlyAirModel> airReportModels) {
                            HourWeatherListAdapter arrayAdapter = new HourWeatherListAdapter(CityForecast.this,  hourlyWeatherModels, airReportModels, 2, cityName, elevation);
                            lv.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        });

        btnOpenDrawer.setOnClickListener(view -> {
            View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(CityForecast.this);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            TextView dialogCityText = dialogView.findViewById(R.id.dialogCityText);
            TextView dialogNotifText = dialogView.findViewById(R.id.dilogNotifText);
            Button button1 = dialogView.findViewById(R.id.reSelect);
            Button button2 = dialogView.findViewById(R.id.onNotif);
            Button button3 = dialogView.findViewById(R.id.offNotif);
            dialogCityText.setText(getResources().getString(R.string.ifWantChangeCity));

            button2.setOnClickListener(v -> {
                weatherDataService.getWeather(lon, lat, date, weather_codes, new WeatherDataService.WeatherResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(CityForecast.this, getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
                    }
                    @RequiresApi(api = Build.VERSION_CODES.S)
                    @SuppressLint({"NotifyDataSetChanged"})
                    @Override
                    public void onResponse(List<HourlyWeatherModel> hourlyWeatherModels) {
                        airDataService.getAir(lon, lat, date, new AirDataService.AirResponseListener() {
                            @Override
                            public void onError(String message) {
                                createNotificationChannelHourly();
                                createNotificationChannelTomorrow();
                                calendarNow = Calendar.getInstance();
                                if (check == 1) {
                                    for (int i = calendarNow.get(Calendar.HOUR_OF_DAY); i < 24; i++) {
                                        generateInfoHourly(hourlyWeatherModels,  i, 0, i);
                                    }
                                    generateInfoForTomorrow(hourlyWeatherModels,22, 5);
                                }
                            }
                            @Override
                            public void onResponse(List<HourlyAirModel> airReportModels) {
                                createNotificationChannelHourly();
                                createNotificationChannelTomorrow();
                                calendarNow = Calendar.getInstance();
                                if (check == 1) {
                                    for (int i = calendarNow.get(Calendar.HOUR_OF_DAY); i < 24; i++) {
                                        generateInfoHourly(hourlyWeatherModels, airReportModels,  i, 0, i);
                                    }
                                    generateInfoForTomorrow(hourlyWeatherModels,22, 5);
                                }
                            }
                        });
                    }
                });
                dialog.dismiss();
                editor.putString(APP_ALARMS, "1");
                editor.apply();
            });

            button3.setOnClickListener(v -> {
                AlarmManager alarmManager;
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent i = createMyIntent();
                PendingIntent pendingIntentNow;
                for (int num = calendarNow.get(Calendar.HOUR_OF_DAY); num < 24; num++) {
                    pendingIntentNow = PendingIntent.getBroadcast(CityForecast.this, num, i, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
                    alarmManager.cancel(pendingIntentNow);
                }
                dialog.dismiss();
                editor.putString(APP_ALARMS, "0");
                editor.apply();
            });
            if (app_language.getString(APP_ALARMS, "0").equals("1") && !currentDate.equals(lastDate)){
                dialogNotifText.setText(getResources().getString(R.string.ifWantOnNotif));
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.GONE);
                editor.putString("lastDate", currentDate);
                editor.apply();
            } else if(app_language.getString(APP_ALARMS, "0").equals("1")){
                dialogNotifText.setText(getResources().getString(R.string.ifWantOffNotif));
                button3.setVisibility(View.VISIBLE);
                button2.setVisibility(View.GONE);
            }else if (app_language.getString(APP_ALARMS, "0").equals("0")) {
                dialogNotifText.setText(getResources().getString(R.string.ifWantOnNotif));
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.GONE);
            }
            button1.setOnClickListener(v -> {
                reselect_city = getSharedPreferences(MainActivity.APP_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor1 = reselect_city.edit();
                editor1.clear();
                editor1.apply();
                Intent intent1 = new Intent(CityForecast.this, MainActivity.class);
                startActivity(intent1);
                button3.performClick();
                editor.putString(APP_ALARMS, "2");
                finish();
                dialog.dismiss();
            });

            dialog.show();
        });
    }
    public void generateInfoHourly(List<HourlyWeatherModel> hourlyWeatherModels, List<HourlyAirModel> hourlyAirModels, int hour, int minute, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 5);
        calendar.set(Calendar.MILLISECOND, 1);

        String info = "";
        String moreInfo = getResources().getString(R.string.clickDetailedForecast);
        int hourNext;
        String hourNowText, hourNextText;
        if (hour < 23) {
            hourNext = hour + 1;
        } else hourNext = hour;

        double temperature_2m = hourlyWeatherModels.get(hour).getTemperature_2m();
        double windspeed_10m = hourlyWeatherModels.get(hour).getWindspeed_10m();
        double apparent_temperature = hourlyWeatherModels.get(hour).getApparent_temperature();
        String weather_code_hourly = hourlyWeatherModels.get(hour).getWeather_code_hourly();
        int weather_code = hourlyWeatherModels.get(hour).getWeather_code();
        int relativehumidity_2m = hourlyWeatherModels.get(hour).getRelativehumidity_2m();
        int precipitation_probability = hourlyWeatherModels.get(hour).getPrecipitation_probability();
        double pressure_msl = hourlyWeatherModels.get(hour).getPressure_msl();
        double  visibility = hourlyWeatherModels.get(hour).getVisibility();
        double  uv_index = hourlyWeatherModels.get(hour).getUv_index();
        double windgusts_10m = hourlyWeatherModels.get(hour).getWindgusts_10m();
        double  winddirection_10m = hourlyWeatherModels.get(hour).getWinddirection_10m();
        int cloudcover = hourlyWeatherModels.get(hour).getCloudcover();

        double pm10 = hourlyAirModels.get(hour).getPm10();
        double pm2_5 = hourlyAirModels.get(hour).getPm2_5();
        double carbon_monoxide = hourlyAirModels.get(hour).getCarbon_monoxide();
        double nitrogen_dioxide = hourlyAirModels.get(hour).getNitrogen_dioxide();
        double sulphur_dioxide = hourlyAirModels.get(hour).getSulphur_dioxide();
        double ozone = hourlyAirModels.get(hour).getOzone();
        double aerosol_optical_depth = hourlyAirModels.get(hour).getAerosol_optical_depth();
        double dust = hourlyAirModels.get(hour).getDust();
        int european_aqi = hourlyAirModels.get(hour).getEuropean_aqi();
        int european_aqi_pm2_5 = hourlyAirModels.get(hour).getEuropean_aqi_pm2_5();
        int european_aqi_pm10 = hourlyAirModels.get(hour).getEuropean_aqi_pm10();
        int european_aqi_no2 = hourlyAirModels.get(hour).getEuropean_aqi_no2();
        int european_aqi_o3 = hourlyAirModels.get(hour).getEuropean_aqi_o3();
        int european_aqi_so2 = hourlyAirModels.get(hour).getEuropean_aqi_so2();

        hourNowText = String.valueOf(hourlyWeatherModels.get(hour).getWeather_code_hourly());
        hourNextText = String.valueOf(hourlyWeatherModels.get(hourNext).getWeather_code_hourly());

        if (hourNowText.equals(hourNextText)) {
            info = hourNowText;
        } else info = hourNowText + ", " + hourNextText;
        String notificationInfo = String.format("%s\n%s %.1f %s %.2f %s\n\n%s", info, getResources().getString(R.string.tempNotif), temperature_2m, getResources().getString(R.string.celswind), windspeed_10m, getResources().getString(R.string.ms), moreInfo);

        AlarmManager alarmManager;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntentNow;
        Bundle bundle = new Bundle();
        bundle.putString("info", notificationInfo);
        bundle.putString("tomorrow", weatherForTomorrow);
        bundle.putInt("requestCode", num);
        bundle.putDouble("temperature_2m", temperature_2m);
        bundle.putDouble("windspeed_10m", windspeed_10m);
        bundle.putDouble("apparent_temperature", apparent_temperature);
        bundle.putString("weather_code_hourly", weather_code_hourly);
        bundle.putInt("weather_code", weather_code);
        bundle.putInt("relativehumidity_2m", relativehumidity_2m);
        bundle.putInt("precipitation_probability", precipitation_probability);
        bundle.putDouble("pressure_msl", pressure_msl);
        bundle.putDouble("visibility", visibility);
        bundle.putDouble("uv_index", uv_index);
        bundle.putDouble("windgusts_10m", windgusts_10m);
        bundle.putDouble("winddirection_10m", winddirection_10m);
        bundle.putFloat("elev", elevation);
        bundle.putInt("cloudcover", cloudcover);
        bundle.putString("city", cityName);

        bundle.putInt("european_aqi", european_aqi);
        bundle.putInt("european_aqi_pm2_5", european_aqi_pm2_5);
        bundle.putInt("european_aqi_pm10", european_aqi_pm10);
        bundle.putInt("european_aqi_no2", european_aqi_no2);
        bundle.putInt("european_aqi_o3", european_aqi_o3);
        bundle.putInt("european_aqi_so2", european_aqi_so2);
        bundle.putDouble("pm10", pm10);
        bundle.putDouble("pm2_5", pm2_5);
        bundle.putDouble("carbon_monoxide", carbon_monoxide);
        bundle.putDouble("nitrogen_dioxide", nitrogen_dioxide);
        bundle.putDouble("sulphur_dioxide", sulphur_dioxide);
        bundle.putDouble("ozone", ozone);
        bundle.putDouble("aerosol_optical_depth", aerosol_optical_depth);
        bundle.putDouble("dust", dust);

        Intent i = createMyIntent();    
        i.putExtras(bundle);
        pendingIntentNow = PendingIntent.getBroadcast(CityForecast.this, num, i, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentNow);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentNow);
        }
        else alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentNow);
    }

    public void generateInfoHourly(List<HourlyWeatherModel> hourlyWeatherModels, int hour, int minute, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 5);
        calendar.set(Calendar.MILLISECOND, 1);

        String info = "";
        String moreInfo = getResources().getString(R.string.clickDetailedForecast);
        int hourNext;
        String hourNowText, hourNextText;
        if (hour < 23) {
            hourNext = hour + 1;
        } else hourNext = hour;

        double temperature_2m = hourlyWeatherModels.get(hour).getTemperature_2m();
        double windspeed_10m = hourlyWeatherModels.get(hour).getWindspeed_10m();
        double apparent_temperature = hourlyWeatherModels.get(hour).getApparent_temperature();
        String weather_code_hourly = hourlyWeatherModels.get(hour).getWeather_code_hourly();
        int weather_code = hourlyWeatherModels.get(hour).getWeather_code();
        int relativehumidity_2m = hourlyWeatherModels.get(hour).getRelativehumidity_2m();
        int precipitation_probability = hourlyWeatherModels.get(hour).getPrecipitation_probability();
        double pressure_msl = hourlyWeatherModels.get(hour).getPressure_msl();
        double  visibility = hourlyWeatherModels.get(hour).getVisibility();
        double  uv_index = hourlyWeatherModels.get(hour).getUv_index();
        double windgusts_10m = hourlyWeatherModels.get(hour).getWindgusts_10m();
        double  winddirection_10m = hourlyWeatherModels.get(hour).getWinddirection_10m();
        int cloudcover = hourlyWeatherModels.get(hour).getCloudcover();

        hourNowText = String.valueOf(hourlyWeatherModels.get(hour).getWeather_code_hourly());
        hourNextText = String.valueOf(hourlyWeatherModels.get(hourNext).getWeather_code_hourly());

        if (hourNowText.equals(hourNextText)) {
            info = hourNowText;
        } else info = hourNowText + ", " + hourNextText;
        String notificationInfo = String.format("%s\n%s %.1f %s %.2f %s\n\n%s", info, getResources().getString(R.string.tempNotif), temperature_2m, getResources().getString(R.string.celswind), windspeed_10m, getResources().getString(R.string.ms), moreInfo);

        AlarmManager alarmManager;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntentNow;
        Bundle bundle = new Bundle();
        bundle.putString("info", notificationInfo);
        bundle.putString("tomorrow", weatherForTomorrow);
        bundle.putInt("requestCode", num);
        bundle.putDouble("temperature_2m", temperature_2m);
        bundle.putDouble("windspeed_10m", windspeed_10m);
        bundle.putDouble("apparent_temperature", apparent_temperature);
        bundle.putString("weather_code_hourly", weather_code_hourly);
        bundle.putInt("weather_code", weather_code);
        bundle.putInt("relativehumidity_2m", relativehumidity_2m);
        bundle.putInt("precipitation_probability", precipitation_probability);
        bundle.putDouble("pressure_msl", pressure_msl);
        bundle.putDouble("visibility", visibility);
        bundle.putDouble("uv_index", uv_index);
        bundle.putDouble("windgusts_10m", windgusts_10m);
        bundle.putDouble("winddirection_10m", winddirection_10m);
        bundle.putFloat("elev", elevation);
        bundle.putInt("cloudcover", cloudcover);
        bundle.putString("city", cityName);

        Intent i = createMyIntent();
        i.putExtras(bundle);
        pendingIntentNow = PendingIntent.getBroadcast(CityForecast.this, num, i, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentNow);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentNow);
        }
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentNow);
    }
    public void generateInfoForTomorrow(List<HourlyWeatherModel> hourlyWeatherModels,int hour, int minute){
        int weather_code;
        Calendar calendarTomorrow = Calendar.getInstance();
        calendarTomorrow.set(Calendar.HOUR_OF_DAY, hour);
        calendarTomorrow.set(Calendar.MINUTE, minute);
        calendarTomorrow.set(Calendar.SECOND, 5);
        calendarTomorrow.set(Calendar.MILLISECOND, 1);
        weather_code = hourlyWeatherModels.get(hour).getWeather_code();

        AlarmManager alarmManager;
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntentNow;
        Bundle bundle = new Bundle();
        bundle.putString("tomorrow", weatherForTomorrow);
        bundle.putInt("weather_code", weather_code);
        Intent i = createMyIntentTomorrow();
        i.putExtras(bundle);
        pendingIntentNow = PendingIntent.getBroadcast(CityForecast.this, 25, i, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendarTomorrow.getTimeInMillis(), pendingIntentNow);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarTomorrow.getTimeInMillis(), pendingIntentNow);
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendarTomorrow.getTimeInMillis(), pendingIntentNow);
    }
}