package com.example.w1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button searchCity;
    TextInputEditText inputCity;
    TextInputLayout et_layout;
    ListView lv_city_list;
    CheckBox checkCity;
    ConstraintLayout constraintLayout;
    private long backPressedTime;
    private Toast backToast;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_LANGUAGE_PREFERENCES = "my_language_settings";
    public static final String APP_PREFERENCES_CITYNAME = "cityName";
    public static final String APP_PREFERENCES_LAT = "lat";
    public static final String APP_PREFERENCES_LON = "lon";
    public static final String APP_LANGUAGE = "language";
    public static final String APP_ELEVATION = "elev";
    SharedPreferences settings, language_settings;

    CityDataService cityDataService = new CityDataService(MainActivity.this);

    public static void keybordHide(Activity yourActivity, View mSearchView){
        InputMethodManager inputMethodManager = (InputMethodManager)
                yourActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
        mSearchView.clearFocus();
    }
    public String encodeCityName(String givenText) {
        String encodeText = givenText;
        try {
            encodeText = URLEncoder.encode(givenText, StandardCharsets.UTF_8.name());
        } catch (Exception e){
            e.printStackTrace();
        }
        return encodeText;
    }

    @Override
    public void onBackPressed() {
        et_layout.clearFocus();
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), getResources().getString(R.string.clickExit), Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void callCityDataService(String cityName, String language){
        cityDataService.getCitiesByName(cityName, language, new CityDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.volleyError), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(List<CityReportModel> cityReportModels) {
                keybordHide(MainActivity.this, searchCity);
                ArrayAdapter arrayAdapter = new CityListAdapter(MainActivity.this, android.R.layout.simple_list_item_1, cityReportModels);
                lv_city_list.setAdapter(arrayAdapter);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.activity_main);

        constraintLayout.setOnClickListener(v -> {
            et_layout.clearFocus();
            keybordHide(MainActivity.this, et_layout);
        });

        settings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        language_settings = getSharedPreferences(APP_LANGUAGE_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        SharedPreferences.Editor language_editor = language_settings.edit();
        String language = Locale.getDefault().getLanguage();
        if (language.equals("ru")){
            language_editor.putString(APP_LANGUAGE, "ru");
        } else language_editor.putString(APP_LANGUAGE, "en");
        language_editor.apply();
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if(!settings.getString(APP_PREFERENCES_CITYNAME, "").equals(""))  {
            Intent intent = new Intent(MainActivity.this, CityForecast.class);
            intent.putExtra("cityName", settings.getString(APP_PREFERENCES_CITYNAME,""));
            intent.putExtra("lat", settings.getFloat(APP_PREFERENCES_LAT,0));
            intent.putExtra("lon", settings.getFloat(APP_PREFERENCES_LON,0));
            intent.putExtra("elev", settings.getFloat(APP_ELEVATION, 0));
            intent.putExtra("check", 1);
            startActivity(intent);
            finish();
        }

        searchCity = findViewById(R.id.button);
        inputCity = findViewById(R.id.cityNameET);
        et_layout = findViewById(R.id.ET_layout);
        lv_city_list = findViewById(R.id.lv_city_list);
        checkCity = findViewById(R.id.checkBox);

        inputCity.setOnEditorActionListener((v, actionId, event) -> {
            if (language.equals("ru")) {
                callCityDataService(encodeCityName(inputCity.getText().toString().trim()), "ru");
            } else {
                callCityDataService(inputCity.getText().toString(), "en");
            }
            return false;
        });

        searchCity.setOnClickListener(v -> {
            if (language.equals("ru")) {
                callCityDataService(encodeCityName(inputCity.getText().toString().trim()), "ru");
            } else {
                callCityDataService(inputCity.getText().toString(), "en");
            }
        });

        lv_city_list.setOnItemClickListener((parent, itemClicked, position, id) -> {
            CityReportModel info = (CityReportModel) parent.getItemAtPosition(position);
            double lat = info.getLatitude();
            double lon = info.getLongitude();
            double elevation = info.getElevation();
            String cityName = info.getName();

            editor.putString(APP_PREFERENCES_CITYNAME, cityName);
            editor.putFloat(APP_PREFERENCES_LON, (float) lon);
            editor.putFloat(APP_PREFERENCES_LAT, (float) lat);
            editor.putFloat(APP_ELEVATION, (float) elevation);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, CityForecast.class);
            intent.putExtra("cityName", cityName);
            intent.putExtra("lat", settings.getFloat(APP_PREFERENCES_LAT, 0));
            intent.putExtra("lon", settings.getFloat(APP_PREFERENCES_LON, 0));
            intent.putExtra("elev", settings.getFloat(APP_ELEVATION, 0));
            if(checkCity.isChecked()){
                intent.putExtra("check", 1);
            }
            startActivity(intent);

            if(!checkCity.isChecked()){
                editor.putString(APP_PREFERENCES_CITYNAME, "");
                editor.putFloat(APP_PREFERENCES_LON, 0);
                editor.putFloat(APP_PREFERENCES_LAT, 0);
                editor.putFloat(APP_ELEVATION, 0);
                editor.apply();
            }
            finish();
        });
    }
}

