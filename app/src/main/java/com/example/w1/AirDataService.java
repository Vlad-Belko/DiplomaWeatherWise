package com.example.w1;

import android.content.Context;
import android.os.Build;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AirDataService {
    Context context;
    public AirDataService(Context context) {
        this.context = context;
    }

    public interface AirResponseListener {
        void onError(String message);
        void onResponse(List<HourlyAirModel> airReportModels);
    }

    public void getAir(float lon, float lat, LocalDate date, final AirResponseListener airResponseListener){
        final List<HourlyAirModel> hourlyAirModels = new ArrayList<>();
        String url = "https://air-quality-api.open-meteo.com/v1/air-quality?latitude=" + lat + "&longitude=" + lon + "&hourly=pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone,aerosol_optical_depth,dust,european_aqi,european_aqi_pm2_5,european_aqi_pm10,european_aqi_no2,european_aqi_o3,european_aqi_so2&timezone=auto&start_date=" + date + "&end_date=" + date;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject hourly = response.getJSONObject("hourly");
                JSONArray time = hourly.getJSONArray("time");
                JSONArray pm10 = hourly.getJSONArray("pm10");
                JSONArray pm2_5 = hourly.getJSONArray("pm2_5");
                JSONArray carbon_monoxide = hourly.getJSONArray("carbon_monoxide");
                JSONArray nitrogen_dioxide = hourly.getJSONArray("nitrogen_dioxide");
                JSONArray sulphur_dioxide = hourly.getJSONArray("sulphur_dioxide");
                JSONArray ozone = hourly.getJSONArray("ozone");
                JSONArray aerosol_optical_depth = hourly.getJSONArray("aerosol_optical_depth");
                JSONArray dust = hourly.getJSONArray("dust");
                JSONArray european_aqi = hourly.getJSONArray("european_aqi");
                JSONArray european_aqi_pm2_5 = hourly.getJSONArray("european_aqi_pm2_5");
                JSONArray european_aqi_pm10 = hourly.getJSONArray("european_aqi_pm10");
                JSONArray european_aqi_no2 = hourly.getJSONArray("european_aqi_no2");
                JSONArray european_aqi_o3 = hourly.getJSONArray("european_aqi_o3");
                JSONArray european_aqi_so2 = hourly.getJSONArray("european_aqi_so2");
                for (int i = 0; i < time.length(); i++) {
                    HourlyAirModel hour_air = new HourlyAirModel();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        hour_air.setTime(time.getString(i));
                    }
                    hour_air.setEuropean_aqi(european_aqi.getInt(i));
                    hour_air.setEuropean_aqi_no2(european_aqi_no2.getInt(i));
                    hour_air.setEuropean_aqi_o3(european_aqi_o3.getInt(i));
                    hour_air.setEuropean_aqi_pm2_5(european_aqi_pm2_5.getInt(i));
                    hour_air.setEuropean_aqi_pm10(european_aqi_pm10.getInt(i));
                    hour_air.setEuropean_aqi_so2(european_aqi_so2.getInt(i));
                    hour_air.setAerosol_optical_depth(aerosol_optical_depth.getDouble(i));
                    hour_air.setCarbon_monoxide(carbon_monoxide.getDouble(i));
                    hour_air.setDust(dust.getDouble(i));
                    hour_air.setNitrogen_dioxide(nitrogen_dioxide.getDouble(i));
                    hour_air.setOzone(ozone.getDouble(i));
                    hour_air.setPm2_5(pm2_5.getDouble(i));
                    hour_air.setPm10(pm10.getDouble(i));
                    hour_air.setSulphur_dioxide(sulphur_dioxide.getDouble(i));
                    hourlyAirModels.add(hour_air);
                }
                airResponseListener.onResponse(hourlyAirModels);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }, error -> airResponseListener.onError(context.getResources().getString(R.string.volleyError)));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
