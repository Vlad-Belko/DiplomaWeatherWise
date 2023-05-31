package com.example.w1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class HourWeatherListAdapter extends RecyclerView.Adapter<HourWeatherListAdapter.ViewHolder> {

    private final Context context;
    View view;
    HourlyWeatherModel arr;
    List<HourlyWeatherModel> hourlyWeatherModels;
    List<HourlyAirModel> hourlyAirModels;
    Calendar calendarNow;
    int day;
    String cityName;
    float elevation;

    public HourWeatherListAdapter(Context context, List<HourlyWeatherModel> hourlyWeatherModels, List<HourlyAirModel> hourlyAirModels, int day, String city, float elevation) {
        this.context = context;
        this.hourlyWeatherModels = hourlyWeatherModels;
        this.hourlyAirModels = hourlyAirModels;
        this.day = day;
        this.elevation = elevation;
        this.cityName = city;
    }

    public HourWeatherListAdapter(Context context, List<HourlyWeatherModel> hourlyWeatherModels, int day, String city, float elevation) {
        this.context = context;
        this.hourlyWeatherModels = hourlyWeatherModels;
        this.day = day;
        this.elevation = elevation;
        this.cityName = city;
    }

    @NonNull
    @Override
    public HourWeatherListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        calendarNow = Calendar.getInstance();
        if(calendarNow.get(Calendar.HOUR_OF_DAY) <= 4 || calendarNow.get(Calendar.HOUR_OF_DAY) >= 22){
            view = LayoutInflater.from(context).inflate(R.layout.my_hourweather_listview_horizontal_night, parent, false);
        } else if(calendarNow.get(Calendar.HOUR_OF_DAY) > 4 && calendarNow.get(Calendar.HOUR_OF_DAY) < 12){
            view = LayoutInflater.from(context).inflate(R.layout.my_hourweather_listview_horizontal_mornig, parent, false);
        } else if(calendarNow.get(Calendar.HOUR_OF_DAY) >= 12 && calendarNow.get(Calendar.HOUR_OF_DAY) < 17){
            view = LayoutInflater.from(context).inflate(R.layout.my_hourweather_listview_horizontal_day, parent, false);
        } else if(calendarNow.get(Calendar.HOUR_OF_DAY) >= 17 && calendarNow.get(Calendar.HOUR_OF_DAY) < 22){
            view = LayoutInflater.from(context).inflate(R.layout.my_hourweather_listview_horizontal_evening, parent, false);
        }
        assert view != null;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(hourlyWeatherModels.size() > 0){
            arr = hourlyWeatherModels.get(position);

            if(calendarNow.get(Calendar.HOUR_OF_DAY) <= 4 || calendarNow.get(Calendar.HOUR_OF_DAY) >= 22){
                if(position == calendarNow.get(Calendar.HOUR_OF_DAY) && day == 1) {
                    holder.cardView_night.setBackgroundResource(R.drawable.card_back_night_now);
                } else holder.cardView_night.setBackgroundResource(R.drawable.card_back_night);
            }
            if(calendarNow.get(Calendar.HOUR_OF_DAY) > 4 && calendarNow.get(Calendar.HOUR_OF_DAY) < 12){
                if(position == calendarNow.get(Calendar.HOUR_OF_DAY) && day == 1) {
                    holder.cardView_morning.setBackgroundResource(R.drawable.card_back_morning_now);
                } else holder.cardView_morning.setBackgroundResource(R.drawable.card_back_morning);
            }
            if(calendarNow.get(Calendar.HOUR_OF_DAY) >= 12 && calendarNow.get(Calendar.HOUR_OF_DAY) < 17){
                if(position == calendarNow.get(Calendar.HOUR_OF_DAY) && day == 1) {
                    holder.cardView_day.setBackgroundResource(R.drawable.card_back_day_now);
                } else holder.cardView_day.setBackgroundResource(R.drawable.card_back_day);
            }
            if(calendarNow.get(Calendar.HOUR_OF_DAY) >= 17 && calendarNow.get(Calendar.HOUR_OF_DAY) < 22){
                if(position == calendarNow.get(Calendar.HOUR_OF_DAY) && day == 1) {
                    holder.cardView_evening.setBackgroundResource(R.drawable.card_back_evening_now);
                } else holder.cardView_evening.setBackgroundResource(R.drawable.card_back_evening);
            }

            holder.lv_time.setText(String.format("%s: %s", context.getResources().getString(R.string.time), arr.getTime()));
            holder.lv_temp.setText(String.format("%.2f %s", arr.getTemperature_2m(), context.getResources().getString(R.string.celsius)));
            holder.lv_a_temp.setText(String.format("%.2f %s", arr.getApparent_temperature(), context.getResources().getString(R.string.celsius)));
            holder.lv_windspeed.setText(String.format("%.2f %s", arr.getWindspeed_10m(), context.getResources().getString(R.string.ms)));
            holder.lv_weather_cond.setText(arr.getWeather_code_hourly());

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), DestinationActivity.class);
                double temperature_2m = hourlyWeatherModels.get(holder.getAdapterPosition()).getTemperature_2m();
                double windspeed_10m = hourlyWeatherModels.get(holder.getAdapterPosition()).getWindspeed_10m();
                double apparent_temperature = hourlyWeatherModels.get(holder.getAdapterPosition()).getApparent_temperature();
                String weather_code_hourly = hourlyWeatherModels.get(holder.getAdapterPosition()).getWeather_code_hourly();
                int weather_code = hourlyWeatherModels.get(holder.getAdapterPosition()).getWeather_code();
                int relativehumidity_2m = hourlyWeatherModels.get(holder.getAdapterPosition()).getRelativehumidity_2m();
                int precipitation_probability = hourlyWeatherModels.get(holder.getAdapterPosition()).getPrecipitation_probability();
                double pressure_msl = hourlyWeatherModels.get(holder.getAdapterPosition()).getPressure_msl();
                double uv_index = hourlyWeatherModels.get(holder.getAdapterPosition()).getUv_index();
                double windgusts_10m = hourlyWeatherModels.get(holder.getAdapterPosition()).getWindgusts_10m();
                double winddirection_10m = hourlyWeatherModels.get(holder.getAdapterPosition()).getWinddirection_10m();
                int cloudcover = hourlyWeatherModels.get(holder.getAdapterPosition()).getCloudcover();
                String time = hourlyWeatherModels.get(holder.getAdapterPosition()).getTime();

                double pm10 = hourlyAirModels.get(holder.getAdapterPosition()).getPm10();
                double pm2_5 = hourlyAirModels.get(holder.getAdapterPosition()).getPm2_5();
                double carbon_monoxide = hourlyAirModels.get(holder.getAdapterPosition()).getCarbon_monoxide();
                double nitrogen_dioxide = hourlyAirModels.get(holder.getAdapterPosition()).getNitrogen_dioxide();
                double sulphur_dioxide = hourlyAirModels.get(holder.getAdapterPosition()).getSulphur_dioxide();
                double ozone = hourlyAirModels.get(holder.getAdapterPosition()).getOzone();
                double aerosol_optical_depth = hourlyAirModels.get(holder.getAdapterPosition()).getAerosol_optical_depth();
                double dust = hourlyAirModels.get(holder.getAdapterPosition()).getDust();
                int european_aqi = hourlyAirModels.get(holder.getAdapterPosition()).getEuropean_aqi();
                int european_aqi_pm2_5 = hourlyAirModels.get(holder.getAdapterPosition()).getEuropean_aqi_pm2_5();
                int european_aqi_pm10 = hourlyAirModels.get(holder.getAdapterPosition()).getEuropean_aqi_pm10();
                int european_aqi_no2 = hourlyAirModels.get(holder.getAdapterPosition()).getEuropean_aqi_no2();
                int european_aqi_o3 = hourlyAirModels.get(holder.getAdapterPosition()).getEuropean_aqi_o3();
                int european_aqi_so2 = hourlyAirModels.get(holder.getAdapterPosition()).getEuropean_aqi_so2();

                String inforecycle = "";
                if(day == 1){
                    inforecycle = String.format("%s\n %s %s", cityName, context.getResources().getString(R.string.today), time);
                } else if (day == 2) {
                    inforecycle = String.format("%s\n%s %s", cityName, context.getResources().getString(R.string.tomorrow), time);
                }

                Bundle bundle = new Bundle();
                bundle.putDouble("temperature_2m", temperature_2m);
                bundle.putDouble("windspeed_10m", windspeed_10m);
                bundle.putDouble("apparent_temperature", apparent_temperature);
                bundle.putString("weather_code_hourly", weather_code_hourly);
                bundle.putInt("weather_code", weather_code);
                bundle.putInt("relativehumidity_2m", relativehumidity_2m);
                bundle.putInt("precipitation_probability", precipitation_probability);
                bundle.putDouble("pressure_msl", pressure_msl);
                bundle.putDouble("uv_index", uv_index);
                bundle.putDouble("windgusts_10m", windgusts_10m);
                bundle.putDouble("winddirection_10m", winddirection_10m);
                bundle.putFloat("elev", elevation);
                bundle.putInt("cloudcover", cloudcover);
                bundle.putString("inforecycle", inforecycle);
                bundle.putInt("hour", holder.getAdapterPosition());
                bundle.putInt("european_aqi", european_aqi);
                bundle.putInt("european_aqi_pm2_5", european_aqi_pm2_5);
                bundle.putInt("european_aqi_pm10", european_aqi_pm10);
                bundle.putInt("european_aqi_no2", european_aqi_no2);
                bundle.putInt("european_aqi_o3", european_aqi_o3);
                bundle.putInt("european_aqi_so2", european_aqi_so2);
                bundle.putDouble("pm10", pm10);
                bundle.putDouble("pm2_5", pm2_5);
                bundle.putDouble("carbon_monoxide", carbon_monoxide);
                bundle.putDouble("nitrogen_dioxide", nitrogen_dioxide);
                bundle.putDouble("sulphur_dioxide", sulphur_dioxide);
                bundle.putDouble("ozone", ozone);
                bundle.putDouble("aerosol_optical_depth", aerosol_optical_depth);
                bundle.putDouble("dust", dust);

                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lv_time, lv_temp, lv_a_temp, lv_windspeed, lv_weather_cond;
        RelativeLayout cardView_day, cardView_morning, cardView_evening, cardView_night;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cardView_day = itemView.findViewById(R.id.card_day);
            cardView_morning = itemView.findViewById(R.id.card_morning);
            cardView_evening = itemView.findViewById(R.id.card_evening);
            cardView_night = itemView.findViewById(R.id.card_night);
            lv_time = itemView.findViewById(R.id.lv_time);
            lv_temp = itemView.findViewById(R.id.lv_temp);
            lv_a_temp = itemView.findViewById(R.id.lv_a_temp);
            lv_windspeed = itemView.findViewById(R.id.lv_windspeed);
            lv_weather_cond = itemView.findViewById(R.id.lv_weather_cond);
        }
    }
}
