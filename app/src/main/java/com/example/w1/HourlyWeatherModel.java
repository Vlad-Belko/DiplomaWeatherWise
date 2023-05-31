package com.example.w1;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.TimeZone;

public class HourlyWeatherModel {
    private String time;
    private double temperature_2m;
    private double apparent_temperature;
    private double windspeed_10m;
    private String weather_code_hourly;
    private int weather_code;
    private int relativehumidity_2m;
    private int precipitation_probability;
    private double pressure_msl;
    private double visibility;
    private double uv_index;
    private double windgusts_10m;
    private double winddirection_10m;
    private int cloudcover;

    private double pm10;
    private double pm2_5;
    private double carbon_monoxide;
    private double nitrogen_dioxide;
    private double sulphur_dioxide;
    private double ozone;
    private double aerosol_optical_depth;
    private double dust;
    private int european_aqi;
    private int european_aqi_pm2_5;
    private int european_aqi_pm10;
    private int european_aqi_no2;
    private int european_aqi_o3;
    private int european_aqi_so2;

    @Override
    public String toString() {
        return "HourlyWeatherModel{" +
                "time='" + time + '\'' +
                ", temperature_2m=" + temperature_2m +
                ", apparent_temperature=" + apparent_temperature +
                ", windspeed_10m=" + windspeed_10m +
                ", weather_code_hourly='" + weather_code_hourly + '\'' +
                ", weather_code=" + weather_code +
                ", relativehumidity_2m=" + relativehumidity_2m +
                ", precipitation_probability=" + precipitation_probability +
                ", pressure_msl=" + pressure_msl +
                ", visibility=" + visibility +
                ", uv_index=" + uv_index +
                ", windgusts_10m=" + windgusts_10m +
                ", winddirection_10m=" + winddirection_10m +
                ", cloudcover=" + cloudcover +
                ", pm10=" + pm10 +
                ", pm2_5=" + pm2_5 +
                ", carbon_monoxide=" + carbon_monoxide +
                ", nitrogen_dioxide=" + nitrogen_dioxide +
                ", sulphur_dioxide=" + sulphur_dioxide +
                ", ozone=" + ozone +
                ", aerosol_optical_depth=" + aerosol_optical_depth +
                ", dust=" + dust +
                ", european_aqi=" + european_aqi +
                ", european_aqi_pm2_5=" + european_aqi_pm2_5 +
                ", european_aqi_pm10=" + european_aqi_pm10 +
                ", european_aqi_no2=" + european_aqi_no2 +
                ", european_aqi_o3=" + european_aqi_o3 +
                ", european_aqi_so2=" + european_aqi_so2 +
                '}';
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public double getCarbon_monoxide() {
        return carbon_monoxide;
    }

    public void setCarbon_monoxide(double carbon_monoxide) {
        this.carbon_monoxide = carbon_monoxide;
    }

    public double getNitrogen_dioxide() {
        return nitrogen_dioxide;
    }

    public void setNitrogen_dioxide(double nitrogen_dioxide) {
        this.nitrogen_dioxide = nitrogen_dioxide;
    }

    public double getSulphur_dioxide() {
        return sulphur_dioxide;
    }

    public void setSulphur_dioxide(double sulphur_dioxide) {
        this.sulphur_dioxide = sulphur_dioxide;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public double getAerosol_optical_depth() {
        return aerosol_optical_depth;
    }

    public void setAerosol_optical_depth(double aerosol_optical_depth) {
        this.aerosol_optical_depth = aerosol_optical_depth;
    }

    public double getDust() {
        return dust;
    }

    public void setDust(double dust) {
        this.dust = dust;
    }

    public int getEuropean_aqi() {
        return european_aqi;
    }

    public void setEuropean_aqi(int european_aqi) {
        this.european_aqi = european_aqi;
    }

    public int getEuropean_aqi_pm2_5() {
        return european_aqi_pm2_5;
    }

    public void setEuropean_aqi_pm2_5(int european_aqi_pm2_5) {
        this.european_aqi_pm2_5 = european_aqi_pm2_5;
    }

    public int getEuropean_aqi_pm10() {
        return european_aqi_pm10;
    }

    public void setEuropean_aqi_pm10(int european_aqi_pm10) {
        this.european_aqi_pm10 = european_aqi_pm10;
    }

    public int getEuropean_aqi_no2() {
        return european_aqi_no2;
    }

    public void setEuropean_aqi_no2(int european_aqi_no2) {
        this.european_aqi_no2 = european_aqi_no2;
    }

    public int getEuropean_aqi_o3() {
        return european_aqi_o3;
    }

    public void setEuropean_aqi_o3(int european_aqi_o3) {
        this.european_aqi_o3 = european_aqi_o3;
    }

    public int getEuropean_aqi_so2() {
        return european_aqi_so2;
    }

    public void setEuropean_aqi_so2(int european_aqi_so2) {
        this.european_aqi_so2 = european_aqi_so2;
    }

    public int getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(int cloudcover) {
        this.cloudcover = cloudcover;
    }

    public HourlyWeatherModel(String time, double temperature_2m, double apparent_temperature, double windspeed_10m, String weather_code_hourly, int weather_code, int relativehumidity_2m, int precipitation_probability, double pressure_msl, double visibility, double uv_index, double windgusts_10m, double winddirection_10m, int cloudcover, double pm10, double pm2_5, double carbon_monoxide, double nitrogen_dioxide, double sulphur_dioxide, double ozone, double aerosol_optical_depth, double dust, int european_aqi, int european_aqi_pm2_5, int european_aqi_pm10, int european_aqi_no2, int european_aqi_o3, int european_aqi_so2) {
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.apparent_temperature = apparent_temperature;
        this.windspeed_10m = windspeed_10m;
        this.weather_code_hourly = weather_code_hourly;
        this.weather_code = weather_code;
        this.relativehumidity_2m = relativehumidity_2m;
        this.precipitation_probability = precipitation_probability;
        this.pressure_msl = pressure_msl;
        this.visibility = visibility;
        this.uv_index = uv_index;
        this.windgusts_10m = windgusts_10m;
        this.winddirection_10m = winddirection_10m;
        this.cloudcover = cloudcover;
        this.pm10 = pm10;
        this.pm2_5 = pm2_5;
        this.carbon_monoxide = carbon_monoxide;
        this.nitrogen_dioxide = nitrogen_dioxide;
        this.sulphur_dioxide = sulphur_dioxide;
        this.ozone = ozone;
        this.aerosol_optical_depth = aerosol_optical_depth;
        this.dust = dust;
        this.european_aqi = european_aqi;
        this.european_aqi_pm2_5 = european_aqi_pm2_5;
        this.european_aqi_pm10 = european_aqi_pm10;
        this.european_aqi_no2 = european_aqi_no2;
        this.european_aqi_o3 = european_aqi_o3;
        this.european_aqi_so2 = european_aqi_so2;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }

    public double getWindgusts_10m() {
        return windgusts_10m;
    }

    public void setWindgusts_10m(double windgusts_10m) {
        this.windgusts_10m = windgusts_10m;
    }

    public double getWinddirection_10m() {
        return winddirection_10m;
    }

    public void setWinddirection_10m(double winddirection_10m) {
        this.winddirection_10m = winddirection_10m;
    }

    public int getRelativehumidity_2m() {
        return relativehumidity_2m;
    }

    public void setRelativehumidity_2m(int relativehumidity_2m) {
        this.relativehumidity_2m = relativehumidity_2m;
    }

    public int getPrecipitation_probability() {
        return precipitation_probability;
    }

    public void setPrecipitation_probability(int precipitation_probability) {
        this.precipitation_probability = precipitation_probability;
    }

    public double getPressure_msl() {
        return pressure_msl;
    }

    public void setPressure_msl(double pressure_msl) {
        this.pressure_msl = pressure_msl;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getUv_index() {
        return uv_index;
    }

    public void setUv_index(double uv_index) {
        this.uv_index = uv_index;
    }

    public static String removefirstNchars(String str, int n) {
        if (str == null || str.length() < n) {
            return str;
        }
        return str.substring(n);
    }

    public String getTime() {
        return time;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTime(String time) throws ParseException {
        String time_right;
        String chars = "[]\"";
        for (char c : chars.toCharArray()) {
            time = time.replace(String.valueOf(c), "");
        }
        time_right = removefirstNchars(time, 11);
        this.time = time_right;
    }

    public double getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(double temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public double getApparent_temperature() {
        return apparent_temperature;
    }

    public void setApparent_temperature(double apparent_temperature) {
        this.apparent_temperature = apparent_temperature;
    }

    public double getWindspeed_10m() {
        return windspeed_10m;
    }

    public void setWindspeed_10m(double windspeed_10m) {
        this.windspeed_10m = windspeed_10m;
    }

    public HourlyWeatherModel() {
    }

    public String getWeather_code_hourly() {
        return weather_code_hourly;
    }

    public void setWeather_code_hourly(String weather_code_hourly) {
        this.weather_code_hourly = weather_code_hourly;
    }
}
