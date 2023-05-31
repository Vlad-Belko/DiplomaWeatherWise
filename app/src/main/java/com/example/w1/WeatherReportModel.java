package com.example.w1;

public class WeatherReportModel {
    private double current_temperature;
    private double current_windSpeed;
    private String maxTemperature;
    private String maxWindspeed;
    private String sunrise;
    private String sunset;
    private int weather_code;

    public WeatherReportModel() {

    }

    public static String removefirstNchars(String str, int n) {
        if (str == null || str.length() < n) {
            return str;
        }
        return str.substring(n);
    }

    @Override
    public String toString() {
        return  "Текущая температура: " + current_temperature + '\n' +
                "Скорость ветра: " + current_windSpeed + '\n' +
                "Максимальная температура: " + maxTemperature + '\n' +
                "Максимальная скорость ветра: " + maxWindspeed + '\n' +
                "Рассвет: " + sunrise + '\n' +
                "Закат: " + sunset + '\n' +
                "Погодные условия: " + weather_code;
    }

    public double getCurrent_temperature() {
        return current_temperature;
    }

    public void setCurrent_temperature(double current_temperature) {
        this.current_temperature = current_temperature;
    }

    public double getCurrent_windSpeed() {
        return current_windSpeed;
    }

    public void setCurrent_windSpeed(double current_windSpeed) {
        this.current_windSpeed = current_windSpeed;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        String maxTemperature_right;
        String chars = "[]\"";
        for (char c : chars.toCharArray()) {
            maxTemperature = maxTemperature.replace(String.valueOf(c), "");
        }
        this.maxTemperature = maxTemperature;
    }

    public String getMaxWindspeed() {
        return maxWindspeed;
    }

    public void setMaxWindspeed(String maxWindspeed) {
        String maxWindspeed_right;
        String chars = "[]\"";
        for (char c : chars.toCharArray()) {
            maxWindspeed = maxWindspeed.replace(String.valueOf(c), "");
        }
        this.maxWindspeed = maxWindspeed;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        String sunrise_right;
        String chars = "[]\"";
        for (char c : chars.toCharArray()) {
            sunrise = sunrise.replace(String.valueOf(c), "");
        }
        sunrise_right = removefirstNchars(sunrise, 11);
        this.sunrise = sunrise_right;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        String sunset_right;
        String chars = "[]\"";
        for (char c : chars.toCharArray()) {
            sunset = sunset.replace(String.valueOf(c), "");
        }
        sunset_right = removefirstNchars(sunset, 11);
        this.sunset = sunset_right;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }

    public WeatherReportModel(double current_temperature, double current_windSpeed, String maxTemperature, String maxWindspeed, String sunrise, String sunset, int weather_code) {
        this.current_temperature = current_temperature;
        this.current_windSpeed = current_windSpeed;
        this.maxTemperature = maxTemperature;
        this.maxWindspeed = maxWindspeed;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.weather_code = weather_code;
    }
 }
