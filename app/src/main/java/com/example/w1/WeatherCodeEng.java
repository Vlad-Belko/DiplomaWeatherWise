package com.example.w1;

import java.util.HashMap;

public class WeatherCodeEng {
    public HashMap<Integer, String> getMap() {
        HashMap<Integer, String> weather_codes = new HashMap<>();

        weather_codes.put(0, "Clear sky");
        weather_codes.put(1, "Mostly Clear");
        weather_codes.put(2, "Mostly Clear");
        weather_codes.put(3, "Partly cloudy");
        weather_codes.put(45, "Fog and frost");
        weather_codes.put(48, "Fog and frost");
        weather_codes.put(51, "Drizzle: light intensity");
        weather_codes.put(53, "Drizzle: moderate intensity");
        weather_codes.put(55, "Drizzle: dense intensity");
        weather_codes.put(56, "Light ice drizzle");
        weather_codes.put(57, "Dense ice drizzle");
        weather_codes.put(61, "Light rain");
        weather_codes.put(63, "Moderate rain");
        weather_codes.put(65, "Heavy rain");
        weather_codes.put(66, "Freezing Rain: Light");
        weather_codes.put(67, "Freezing Rain: Strong");
        weather_codes.put(71, "Light snowfall");
        weather_codes.put(73, "Moderate snowfall");
        weather_codes.put(75, "Heavy snowfall");
        weather_codes.put(77, "Snow grains");
        weather_codes.put(80, "Light rain showers");
        weather_codes.put(81, "Moderate rain showers");
        weather_codes.put(82, "Heavy rain showers");
        weather_codes.put(85, "Light snow");
        weather_codes.put(86, "Heavy snow");
        weather_codes.put(95, "Thunderstorm");
        weather_codes.put(96, "Thunderstorm with light hail");
        weather_codes.put(99, "Thunderstorm with heavy hail");
        weather_codes.put(100, "Clear sky");
        weather_codes.put(101, "Mostly Clear");
        weather_codes.put(102, "Mostly Clear");
        weather_codes.put(103, "Partly cloudy");

        return weather_codes;
    }
}

