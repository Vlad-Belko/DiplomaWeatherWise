package com.example.w1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class DestinationActivity extends AppCompatActivity {
    RecommendationsGenerator generator = new RecommendationsGenerator(DestinationActivity.this);
    private double temperature_2m;
    float elevation;
    double pressure_msl_mm;
    TextView temperature_2m_TV, cond, apparent_temperature_TV, windspeed_10m_TV,winddirection_10m_notification, weather_code_hourly_TV, relativehumidity_2m_TV, precipitation_probability_TV,
            pressure_msl_TV, atmPressure_TV,cond_text, cond_recomend_TV, uv_recomend_TV, temp_recomend_TV, wind_recomend_TV, humidity_recomend_TV, uv_index_TV, windgusts_10m_TV, winddirection_10m_TV, winddirection_10m_icon, time_TV, cloudcover_TV, normPres, nowPres;
    TextView air_text, air_rec, pm_2_5_text, pm_2_5_rec, pm_10_text, pm_10_rec, co_text, co_rec, no2_text, no2_rec, so2_text, so2_rec, o3_text, o3_rec, aerosol_text, aerosol_rec, dust_text, dust_rec;
    ScrollView scrollView;

    private double getNormalPressure(float elev){
        double pressure = 760;
        double g = 9.81;
        double exp = 2.71828;
        double normalPressure = pressure * Math.pow(exp, ((-0.02897 * g * elev) / (8.31 * (temperature_2m + 273.15))) );
        return normalPressure;
    }

    private String nearest(int n) {
        String wind = "wind";
        int[] angles = {15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180, 195, 210, 225, 240, 255, 270, 285, 300, 315, 330, 345, 360};
        int nearest = 0;
        long value = 2L *Integer.MAX_VALUE;
        for(int arg : angles)
            if (value > Math.abs(n - arg)){
                value = Math.abs(n-arg);
                nearest = arg;}

        String myWind = wind + nearest;
        return myWind;
    }

    private final Handler mHandler = new Handler();
    // закрыть активность
    private final Runnable mRunnable = this::finish;

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 1000 * 60 * 15);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Intent intent = getIntent();
        int hour = intent.getIntExtra("hour", 0);
        scrollView = findViewById(R.id.notification_layout);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if(hour <= 4 || hour >= 22){
            scrollView.setBackground(getDrawable(R.drawable.v1night));
        } else if(hour > 4 && hour < 12){
            scrollView.setBackground(getDrawable(R.drawable.v1morning));
        } else if(hour >= 12 && hour < 17){
            scrollView.setBackground(getDrawable(R.drawable.v1day));
        } else if(hour >= 17 && hour < 22){
            scrollView.setBackground(getDrawable(R.drawable.v1evening));
        }

        cond = findViewById(R.id.cond);
        winddirection_10m_notification = findViewById(R.id.winddirection_10m_notification);
        cond_text = findViewById(R.id.cond_text);
        cond_recomend_TV = findViewById(R.id.cond_recomend);
        temp_recomend_TV = findViewById(R.id.temp_recomend);
        humidity_recomend_TV = findViewById(R.id.humidity_recomend);
        wind_recomend_TV = findViewById(R.id.wind_recomend);
        uv_recomend_TV = findViewById(R.id.uv_recomend);
        atmPressure_TV = findViewById(R.id.atmPres_recomend);
        temperature_2m_TV = findViewById(R.id.temperature_2m_notification);
        apparent_temperature_TV = findViewById(R.id.apparent_temperature_notification);
        windspeed_10m_TV = findViewById(R.id.windspeed_10m_notification);
        weather_code_hourly_TV = findViewById(R.id.weather_code_hourly_notification);
        relativehumidity_2m_TV = findViewById(R.id.relativehumidity_2m_notification);
        precipitation_probability_TV = findViewById(R.id.precipitation_probability_notification);
        pressure_msl_TV = findViewById(R.id.pressure_msl_notification);
        uv_index_TV = findViewById(R.id.uv_index_notification);
        windgusts_10m_TV = findViewById(R.id.windgusts_10m_notification);
        winddirection_10m_TV = findViewById(R.id.winddirection_10m_notification);
        winddirection_10m_icon = findViewById(R.id.winddirection_10m_icon);
        time_TV = findViewById(R.id.time_notification);
        Button btn = findViewById(R.id.btn_to_rec);
        normPres = findViewById(R.id.normPressure);
        nowPres = findViewById(R.id.nowPressure);
        cloudcover_TV = findViewById(R.id.cloudcover_notification);


        air_text = findViewById(R.id.air_text);
        air_rec = findViewById(R.id.air_rec);
        pm_2_5_text = findViewById(R.id.PM2_5_text);
        aerosol_text = findViewById(R.id.aerosol_text);
        pm_2_5_rec = findViewById(R.id.PM2_5_recomend);
        pm_10_text = findViewById(R.id.PM10_text);
        pm_10_rec = findViewById(R.id.PM10_recomend);
        co_text = findViewById(R.id.CO_text);
        co_rec = findViewById(R.id.CO_recomend);
        no2_text = findViewById(R.id.NO2_text);
        no2_rec = findViewById(R.id.NO2_recomend);
        so2_text = findViewById(R.id.SO2_text);
        so2_rec = findViewById(R.id.SO2_recomend);
        o3_text = findViewById(R.id.O3_text);
        o3_rec = findViewById(R.id.O3_recomend);
        aerosol_text = findViewById(R.id.aerosol_text);
        aerosol_rec = findViewById(R.id.aerosol_recomend);
        dust_text = findViewById(R.id.dust_text);
        dust_rec = findViewById(R.id.dust_recomend);

        temperature_2m = intent.getDoubleExtra("temperature_2m", 0);
        double apparent_temperature = intent.getDoubleExtra("apparent_temperature", 0);
        double windspeed_10m = intent.getDoubleExtra("windspeed_10m", 0);
        String weather_code_hourly = intent.getStringExtra("weather_code_hourly");
        int relativehumidity_2m = intent.getIntExtra("relativehumidity_2m", 0);
        int precipitation_probability = intent.getIntExtra("precipitation_probability", 0);
        double pressure_msl = intent.getDoubleExtra("pressure_msl", 0);
        double uv_index = intent.getDoubleExtra("uv_index", 0);
        double windgusts_10m = intent.getDoubleExtra("windgusts_10m", 0);
        double winddirection_10m = intent.getDoubleExtra("winddirection_10m", 0);
        String time = intent.getStringExtra("time");
        elevation = intent.getFloatExtra("elev", 0);
        int cloudcover = intent.getIntExtra("cloudcover", 0);
        int weather_code = intent.getIntExtra("weather_code", 0);

        int european_aqi = intent.getIntExtra("european_aqi", 0);
        int european_aqi_pm2_5 = intent.getIntExtra("european_aqi_pm2_5", 0);
        int european_aqi_pm10 = intent.getIntExtra("european_aqi_pm10", 0);
        int european_aqi_no2 = intent.getIntExtra("european_aqi_no2", 0);
        int european_aqi_o3 = intent.getIntExtra("european_aqi_o3", 0);
        int european_aqi_so2 = intent.getIntExtra("european_aqi_so2", 0);
        double pm10 = intent.getDoubleExtra("pm10", 0);
        double pm2_5 = intent.getDoubleExtra("pm2_5", 0);
        double carbon_monoxide = intent.getDoubleExtra("carbon_monoxide", 0);
        double nitrogen_dioxide = intent.getDoubleExtra("nitrogen_dioxide", 0);
        double sulphur_dioxide = intent.getDoubleExtra("sulphur_dioxide", 0);
        double ozone = intent.getDoubleExtra("ozone", 0);
        double aerosol_optical_depth = intent.getDoubleExtra("aerosol_optical_depth", 0);
        double dust = intent.getDoubleExtra("dust", 0);

        pressure_msl_mm = pressure_msl / 1.334;
        double normalPressure = getNormalPressure(elevation);
        String pressureInfo = generator.pressureInfo(pressure_msl_mm, normalPressure);
        String uvInfo = generator.uvIndexInfo(uv_index);
        String windInfo = generator.windInfo(windspeed_10m, temperature_2m);
        String humidityInfo = generator.humidityInfo(relativehumidity_2m, temperature_2m);
        String tempInfo = generator.temperarureInfo(temperature_2m, apparent_temperature);
        String condInfo = generator.weatherCond(weather_code);
        String inforecycle = intent.getStringExtra("inforecycle");
        int imageResource = DestinationActivity.this.getResources().getIdentifier(nearest((int) winddirection_10m), "drawable", DestinationActivity.this.getPackageName());
        winddirection_10m_icon.setCompoundDrawablesRelativeWithIntrinsicBounds(imageResource, 0, 0, 0);

        winddirection_10m_notification.setText("°");
        temperature_2m_TV.setText(getString(R.string.temperature_2m_not_text, String.valueOf(temperature_2m)));
        apparent_temperature_TV.setText(getString(R.string.apparent_temperature_not_text, String.valueOf(apparent_temperature),getResources().getString(R.string.celsius)));
        windspeed_10m_TV.setText(getString(R.string.windspeed_10m_not_text, String.format("%.2f", windspeed_10m), getResources().getString(R.string.ms)));
        weather_code_hourly_TV.setText(getString(R.string.weather_code_hourly_not_text, weather_code_hourly));
        relativehumidity_2m_TV.setText(getString(R.string.relativehumidity_2m_not_text, String.valueOf(relativehumidity_2m), getResources().getString(R.string.percent)));
        precipitation_probability_TV.setText(getString(R.string.precipitation_probability_not_text, String.valueOf(precipitation_probability), getResources().getString(R.string.percent)));
        pressure_msl_TV.setText(getString(R.string.pressure_msl_not_text, String.format("%.2f", pressure_msl_mm), getResources().getString(R.string.mmHg)));
        uv_index_TV.setText(getString(R.string.uv_index_not_text, String.valueOf(uv_index)));
        windgusts_10m_TV.setText(getString(R.string.windgusts_10m_not_text, String.format("%.2f", windgusts_10m / 3.6), getResources().getString(R.string.ms)));
        winddirection_10m_TV.setText(String.valueOf(winddirection_10m) + getResources().getString(R.string.degree));
        cloudcover_TV.setText(getString(R.string.cloudcover_not_text, String.valueOf(cloudcover), getResources().getString(R.string.percent)));
        time_TV.setText(String.valueOf(time));

        String airCond = generator.getInfoAir(european_aqi);
        String pm2_5_cond = generator.getInfoPm2_5(pm2_5, european_aqi_pm2_5);
        String pm10_cond = generator.getInfoPm10(pm10, european_aqi_pm10);

        String no2_condResult = generator.getInfoNO2(nitrogen_dioxide, european_aqi_no2);
        String[] no2_parts = no2_condResult.split("!");
        String no2_cond = no2_parts[0];
        int no2_num = Integer.parseInt(no2_parts[1]);

        String so2_condResult = generator.getInfoSO2(sulphur_dioxide, european_aqi_so2);
        String[] so2_parts = so2_condResult.split("!");
        String so2_cond = so2_parts[0];
        int so2_num = Integer.parseInt(so2_parts[1]);

        String o3_condResult = generator.getInfoO3(ozone, european_aqi_o3);
        String[] o3_parts = o3_condResult.split("!");
        String o3_cond = o3_parts[0];
        int o3_num = Integer.parseInt(o3_parts[1]);

        String co_condResult = generator.getInfoCO(carbon_monoxide);
        String[] co_parts = co_condResult.split("!");
        String co_cond = co_parts[0]; String plus_air = "";
        int co_num = Integer.parseInt(co_parts[1]);
        int[] numbers = {no2_num, so2_num, o3_num, co_num}; // Массив с числами
        int maxNumber = 0;
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i] > maxNumber) maxNumber = numbers[i];
        }
        if(maxNumber == no2_num && maxNumber > 2) {
            plus_air = getResources().getString(R.string.air_plus_no2);
        } else if (maxNumber == so2_num && maxNumber > 2) {
            plus_air = getResources().getString(R.string.air_plus_so2);
        } else if (maxNumber == o3_num && maxNumber > 2) {
            plus_air = getResources().getString(R.string.air_plus_o3);
        } else if (maxNumber == co_num && maxNumber > 2) {
            plus_air = getResources().getString(R.string.air_plus_co);
        }

        String aerosol_cond = generator.getInfoAerosol(aerosol_optical_depth);
        String dust_cond = generator.getInfoDust(dust);

        air_text.setText(getResources().getString(R.string.air_text) + getResources().getString(R.string.aiq) + european_aqi);
        air_rec.setText(airCond + plus_air);
        pm_2_5_text.setText(getResources().getString(R.string.pm_2_5_text) + ":\n" + pm2_5 + getResources().getString(R.string.mkg) + getResources().getString(R.string.index) +" (PM2.5): " + european_aqi_pm2_5);
        pm_2_5_rec.setText(pm2_5_cond);
        pm_10_text.setText(getResources().getString(R.string.pm_10_text) + ":\n" + pm10 + getResources().getString(R.string.mkg)  + getResources().getString(R.string.index) +" (PM10): " + european_aqi_pm10);
        pm_10_rec.setText(pm10_cond);
        co_text.setText(getResources().getString(R.string.co_text) + ":\n" + carbon_monoxide + " " + getResources().getString(R.string.mkg));
        co_rec.setText(co_cond);
        no2_text.setText(getResources().getString(R.string.no2_text) + ":\n" + nitrogen_dioxide + getResources().getString(R.string.mkg) + getResources().getString(R.string.index) +" (NO₂): " + european_aqi_no2);
        no2_rec.setText(no2_cond);
        so2_text.setText(getResources().getString(R.string.so2_text) + ":\n" + sulphur_dioxide + getResources().getString(R.string.mkg) + getResources().getString(R.string.index) +" (SO₂): " + european_aqi_so2);
        so2_rec.setText(so2_cond);
        o3_text.setText(getResources().getString(R.string.o3_text) + ":\n" + ozone + getResources().getString(R.string.mkg) + getResources().getString(R.string.index) +" (O₃): " + european_aqi_o3);
        o3_rec.setText(o3_cond);
        aerosol_text.setText(getResources().getString(R.string.aerosol_text) + ":\n" + aerosol_optical_depth);
        aerosol_rec.setText(aerosol_cond);
        dust_text.setText(getResources().getString(R.string.dust_text) + ":\n" + dust + " " + getResources().getString(R.string.mkg));
        dust_rec.setText(dust_cond);

        nowPres.setText(getString(R.string.pressure_msl_now, String.format("%.2f", pressure_msl_mm), getResources().getString(R.string.mmHg)));
        normPres.setText(getString(R.string.pressure_msl_city_normal, String.format("%.2f", normalPressure), getResources().getString(R.string.mmHg)));

        atmPressure_TV.setText(pressureInfo);
        uv_recomend_TV.setText(uvInfo);
        wind_recomend_TV.setText(windInfo);
        humidity_recomend_TV.setText(humidityInfo);
        temp_recomend_TV.setText(tempInfo);
        cond_recomend_TV.setText(condInfo);

        if(pm2_5 == 0 && pm10 == 0 && carbon_monoxide == 0 && nitrogen_dioxide == 0 && sulphur_dioxide == 0 && ozone == 0 && dust == 0 && aerosol_optical_depth == 0) {
                air_text.setVisibility(View.GONE);
                air_rec.setVisibility(View.GONE);
                pm_2_5_text.setVisibility(View.GONE);
                pm_2_5_rec.setVisibility(View.GONE);
                pm_10_text.setVisibility(View.GONE);
                pm_10_rec.setVisibility(View.GONE);
                co_text.setVisibility(View.GONE);
                co_rec.setVisibility(View.GONE);
                no2_text.setVisibility(View.GONE);
                no2_rec.setVisibility(View.GONE);
                so2_text.setVisibility(View.GONE);
                so2_rec.setVisibility(View.GONE);
                o3_text.setVisibility(View.GONE);
                o3_rec.setVisibility(View.GONE);
                aerosol_text.setVisibility(View.GONE);
                aerosol_rec.setVisibility(View.GONE);
                dust_text.setVisibility(View.GONE);
                dust_rec.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(condInfo)) {
            cond_text.setVisibility(View.GONE);
            cond_recomend_TV.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(time)){
            cond.setVisibility(View.GONE);
            time_TV.setText(inforecycle);
        }

        btn.setOnClickListener(v -> scrollView.smoothScrollBy(0, 2300));

    }
}