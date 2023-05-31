package com.example.w1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CityDataService {
    Context context;
    public CityDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(List<CityReportModel> cityReportModels);
    }

    public void getCitiesByName(String cityName, String language, final VolleyResponseListener volleyResponseListener){
        final List<CityReportModel> cityReportModels = new ArrayList<>();
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + cityName + "&language=" + language + "&count=50";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray cities_list = response.getJSONArray("results");
                for (int i = 0; i < cities_list.length(); i++) {
                    CityReportModel one_city = new CityReportModel();
                    JSONObject first_city = (JSONObject) cities_list.get(i);
                    one_city.setName(first_city.getString("name"));
                    one_city.setCountry(first_city.getString("country"));
                    one_city.setLatitude(first_city.getDouble("latitude"));
                    one_city.setLongitude(first_city.getDouble("longitude"));
                    one_city.setCountryCode(first_city.getString("country_code").toLowerCase());
                    one_city.setElevation(first_city.getDouble("elevation"));

                    String myCode = one_city.getCountryCode();
                    int imageResource = context.getResources().getIdentifier(myCode, "drawable", context.getPackageName());
                    one_city.setImage(imageResource);

                    cityReportModels.add(one_city);
                }
                volleyResponseListener.onResponse(cityReportModels);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> volleyResponseListener.onError(context.getResources().getString(R.string.volleyError)));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }


}
