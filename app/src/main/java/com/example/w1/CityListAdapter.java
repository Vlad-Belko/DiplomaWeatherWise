package com.example.w1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
public class CityListAdapter extends ArrayAdapter<CityReportModel> {
    private final Context context;
    CityReportModel arr;
    List<CityReportModel> cityReportModels;
    public CityListAdapter(@NonNull Context context, int resource, List<CityReportModel> cityReportModels){
        super(context, resource, cityReportModels);
        this.context = context;
        this.cityReportModels = cityReportModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View contentView, @NonNull ViewGroup parent){
        if(contentView == null){
            LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = i.inflate(R.layout.my_city_listview, null);
        }
        if(cityReportModels.size() > 0){
            arr = cityReportModels.get(position);
            ImageView lv_img = contentView.findViewById(R.id.lv_img);
            TextView lv_country = contentView.findViewById(R.id.lv_country);
            TextView lv_city = contentView.findViewById(R.id.lv_city);
            TextView lv_lat = contentView.findViewById(R.id.lv_lat);
            TextView lv_lon = contentView.findViewById(R.id.lv_lon);

            lv_img.setImageResource(arr.getImage());
            lv_country.setText(arr.getCountry());
            lv_city.setText(arr.getName());
            lv_lat.setText("Lat: " + arr.getLatitude());
            lv_lon.setText("Lon: " + arr.getLongitude());
        }
        return  contentView;
    }
}
