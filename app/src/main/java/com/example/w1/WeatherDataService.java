package com.example.w1;

import android.content.Context;
import android.os.Build;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeatherDataService {
    Context context;
    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface WeatherResponseListener {
        void onError(String message);
        void onResponse(List<HourlyWeatherModel> weatherReportModels);
    }

    public void getWeather(float lon, float lat, LocalDate date, Map<Integer, String> weather_codes, final WeatherResponseListener weatherResponseListener){
        final List<HourlyWeatherModel> hourlyWeatherModels = new ArrayList<>();
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&hourly=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,cloudcover,rain,showers,snowfall,weathercode,surface_pressure" +
                ",visibility,windspeed_10m,windgusts_10m,winddirection_10m,uv_index&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_sum,precipitation_hours,windspeed_10m_max,windgusts_10m_max,winddirection_10m_dominant&current_weather=true&timezone=auto&start_date=" + date + "&end_date=" + date;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                HourlyWeatherModel hour_weather = new HourlyWeatherModel();
                JSONObject hourly = response.getJSONObject("hourly");
                JSONArray time = hourly.getJSONArray("time");
                JSONArray temperature_2m = hourly.getJSONArray("temperature_2m");
                JSONArray apparent_temperature = hourly.getJSONArray("apparent_temperature");
                JSONArray windspeed_10m = hourly.getJSONArray("windspeed_10m");
                JSONArray windgusts_10m = hourly.getJSONArray("windgusts_10m");
                JSONArray winddirection_10m = hourly.getJSONArray("winddirection_10m");
                JSONArray weather_code_hourly = hourly.getJSONArray("weathercode");
                JSONArray relativehumidity_2m = hourly.getJSONArray("relativehumidity_2m");
                JSONArray precipitation_probability = hourly.getJSONArray("precipitation_probability");
                JSONArray pressure_msl = hourly.getJSONArray("surface_pressure");
                JSONArray visibility = hourly.getJSONArray("visibility");
                JSONArray uv_index = hourly.getJSONArray("uv_index");
                JSONArray cloudcover = hourly.getJSONArray("cloudcover");

                for (int i = 0; i < time.length(); i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        hour_weather.setTime(time.getString(i));
                    }
                    hour_weather.setTemperature_2m(temperature_2m.getDouble(i));
                    hour_weather.setApparent_temperature(apparent_temperature.getDouble(i));
                    hour_weather.setWindspeed_10m(windspeed_10m.getDouble(i) / 3.6);
                    hour_weather.setWeather_code_hourly(weather_codes.get(weather_code_hourly.getInt(i)));
                    hour_weather.setWeather_code(weather_code_hourly.getInt(i));
                    hour_weather.setRelativehumidity_2m(relativehumidity_2m.getInt(i));
                    hour_weather.setPrecipitation_probability(precipitation_probability.getInt(i));
                    hour_weather.setPressure_msl(pressure_msl.getDouble(i));
                    hour_weather.setVisibility(visibility.getDouble(i));
                    hour_weather.setUv_index(uv_index.getDouble(i));
                    hour_weather.setWinddirection_10m(winddirection_10m.getDouble(i));
                    hour_weather.setWindgusts_10m(windgusts_10m.getDouble(i));
                    hour_weather.setCloudcover(cloudcover.getInt(i));
                    hourlyWeatherModels.add(hour_weather);
                }
                weatherResponseListener.onResponse(hourlyWeatherModels);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }, error -> weatherResponseListener.onError(context.getResources().getString(R.string.volleyError)));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}



