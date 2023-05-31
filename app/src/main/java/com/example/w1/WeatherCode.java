package com.example.w1;

import java.util.HashMap;

public class WeatherCode {
    public HashMap<Integer, String> getMap() {
        HashMap<Integer, String> weather_codes = new HashMap<>();

        weather_codes.put(0, "Чистое небо");
        weather_codes.put(1, "Преимущественно ясно");
        weather_codes.put(2, "Преимущественно ясно");
        weather_codes.put(3, "Переменная облачность");
        weather_codes.put(45, "Туман и изморозь");
        weather_codes.put(48, "Туман и изморозь");
        weather_codes.put(51, "Морось: лёгкая интенсивность");
        weather_codes.put(53, "Морось: умеренная  интенсивность");
        weather_codes.put(55, "Морось: плотная  интенсивность");
        weather_codes.put(56, "Лёгкая ледяная морось");
        weather_codes.put(57, "Плотная ледяная морось");
        weather_codes.put(61, "Слабый дождь");
        weather_codes.put(63, "Умеренный дождь");
        weather_codes.put(65, "Сильный дождь");
        weather_codes.put(66, "Ледяной дождь: легкий");
        weather_codes.put(67, "Ледяной дождь: сильный");
        weather_codes.put(71, "Лёгкий снегопад");
        weather_codes.put(73, "Умеренный снегопад");
        weather_codes.put(75, "Сильный снегопад");
        weather_codes.put(77, "Снежные зерна");
        weather_codes.put(80, "Слабые ливненые дожди");
        weather_codes.put(81, "Умеренные ливненые дожди");
        weather_codes.put(82, "Сильные ливненые дожди");
        weather_codes.put(85, "Слабый снег");
        weather_codes.put(86, "Сильный снег");
        weather_codes.put(95, "Гроза");
        weather_codes.put(96, "Гроза со слабым градом");
        weather_codes.put(99, "Гроза с сильным градом");
        weather_codes.put(100, "Чистое небо");
        weather_codes.put(101, "Преимущественно ясно");
        weather_codes.put(102, "Преимущественно ясно");
        weather_codes.put(103, "Переменная облачность");

        return weather_codes;
    }
}

