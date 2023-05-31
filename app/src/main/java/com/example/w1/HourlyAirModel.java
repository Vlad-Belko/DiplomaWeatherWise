package com.example.w1;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;

public class HourlyAirModel {
    private String time;
    double pm10;
    double pm2_5;
    double carbon_monoxide;
    double nitrogen_dioxide;
    double sulphur_dioxide;
    double ozone;
    double aerosol_optical_depth;
    double dust;
    int european_aqi;
    int european_aqi_pm2_5;
    int european_aqi_pm10;
    int european_aqi_no2;
    int european_aqi_o3;
    int european_aqi_so2;

    public HourlyAirModel() {

    }

    @Override
    public String toString() {
        return "AirWeatherModel{" +
                "time='" + time + '\'' +
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

    public HourlyAirModel(String time, double pm10, double pm2_5, double carbon_monoxide, double nitrogen_dioxide, double sulphur_dioxide, double ozone, double aerosol_optical_depth, double dust, int european_aqi, int european_aqi_pm2_5, int european_aqi_pm10, int european_aqi_no2, int european_aqi_o3, int european_aqi_so2) {
        this.time = time;
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

}
