package com.example.w1;

import android.content.Context;

public class RecommendationsGenerator {
    Context recomendContext;
    public RecommendationsGenerator(Context context) {
        this.recomendContext = context;
    }

    public String pressureInfo(double pressure_msl_mm, double normalPressure){
        String pressureInfo = "";
        if((normalPressure - pressure_msl_mm) > 10.0) {
            pressureInfo = recomendContext.getResources().getString(R.string.R_high_pressure);
        } else if((pressure_msl_mm - normalPressure) > 10.0) {
            pressureInfo = recomendContext.getResources().getString(R.string.R_low_pressure);
        } else {
            pressureInfo = recomendContext.getResources().getString(R.string.norm_pressure);
        }
        return pressureInfo;
    }

    public String uvIndexInfo(double uv_index) {
        String uvIndexInfo = "";
        if(uv_index < 3) {
            uvIndexInfo = recomendContext.getResources().getString(R.string.uv_0_2);
        } else if (uv_index > 2 && uv_index < 6){
            uvIndexInfo = recomendContext.getResources().getString(R.string.uv_3_5);
        } else if (uv_index > 5 && uv_index < 8){
            uvIndexInfo = recomendContext.getResources().getString(R.string.uv_6_7);
        } else if (uv_index > 8 && uv_index < 11){
            uvIndexInfo = recomendContext.getResources().getString(R.string.uv_8_10);
        } else if (uv_index > 11){
            uvIndexInfo = recomendContext.getResources().getString(R.string.uv_11);
        }
        return uvIndexInfo;
    }

    public String windIndex(double windspeed_10m, double temperature_2m){
        double index = (10 * Math.pow(windspeed_10m, 0.5) - windspeed_10m + 10.5) * (33 - temperature_2m);
        String indexInfo = "";
        if(index > 0) {
            indexInfo = recomendContext.getResources().getString(R.string.windIndex_1);
        } else if( index > -10 && index <= 0) {
            indexInfo = recomendContext.getResources().getString(R.string.windIndex_2);
        } else if( index > -28 && index <= -10) {
            indexInfo = recomendContext.getResources().getString(R.string.windIndex_3);
        } else if( index > -40 && index <= -28) {
            indexInfo = recomendContext.getResources().getString(R.string.windIndex_4);
        } else if( index > -48 && index <= -40) {
            indexInfo = recomendContext.getResources().getString(R.string.windIndex_5);
        } else if( index > -55 && index <= -48) {
            indexInfo = recomendContext.getResources().getString(R.string.windIndex_6);
        } else if (index <= -55) {
            indexInfo = recomendContext.getResources().getString(R.string.windIndex_7);
        }
        return indexInfo;
    }

    public String windInfo(double windspeed_10m, double temperature_2m) {
        String indexInfo = windIndex(windspeed_10m, temperature_2m);
        String windInfo = "";
        if(windspeed_10m < 0.3) {
            windInfo = recomendContext.getResources().getString(R.string.calm) + indexInfo;
        } else if (windspeed_10m >= 0.3 && windspeed_10m < 1.6) {
            if (temperature_2m >= 12 && temperature_2m <= 25) {
                windInfo = recomendContext.getResources().getString(R.string.quietWind)  + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_2_12_25);
            } else if (temperature_2m < 12) {
                windInfo = recomendContext.getResources().getString(R.string.quietWind)  + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_2_12m);
            } else if (temperature_2m > 25) {
                windInfo = recomendContext.getResources().getString(R.string.quietWind)  + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_2_25p);
            }
        } else if (windspeed_10m >= 1.6 && windspeed_10m < 3.4) {
            windInfo = recomendContext.getResources().getString(R.string.lightBreeze)  + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_3);
        } else if (windspeed_10m >= 3.4 && windspeed_10m < 5.5) {
            windInfo = recomendContext.getResources().getString(R.string.weakWind) + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_4);
        } else if (windspeed_10m >= 5.5 && windspeed_10m < 8) {
            windInfo = recomendContext.getResources().getString(R.string.moderateWind) + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_5);
        } else if (windspeed_10m >= 8 && windspeed_10m < 10.8) {
            windInfo = recomendContext.getResources().getString(R.string.freshBreeze) + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_6);
        } else if (windspeed_10m >= 10.8 && windspeed_10m < 13.9) {
            windInfo = recomendContext.getResources().getString(R.string.strongWind) + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_7);
        } else if (windspeed_10m >= 13.9 && windspeed_10m < 17.2) {
            windInfo = recomendContext.getResources().getString(R.string.hardWind) + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_8);
        } else if (windspeed_10m >= 17.2 && windspeed_10m < 20.8) {
            windInfo = recomendContext.getResources().getString(R.string.veryHardWind)  + indexInfo +  "\n\n" + recomendContext.getResources().getString(R.string.wind_9);
        } else if (windspeed_10m >= 20.8 && windspeed_10m < 24.5) {
            windInfo = recomendContext.getResources().getString(R.string.storm) + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_10);
        } else if (windspeed_10m >= 24.5 && windspeed_10m < 28.5) {
            windInfo = recomendContext.getResources().getString(R.string.heavyStorm)  + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_11);
        } else if (windspeed_10m >= 28.5 && windspeed_10m < 32.6) {
            windInfo = recomendContext.getResources().getString(R.string.violentStorm)  + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_12);
        } else if (windspeed_10m >= 32.6) {
            windInfo = recomendContext.getResources().getString(R.string.hurricane)  + indexInfo + "\n\n" + recomendContext.getResources().getString(R.string.wind_13);
        }
        return windInfo;
    }

    public String humidityInfo(int relativehumidity_2m, double temperature_2m) {
        String humidityInfo = "";
        if (relativehumidity_2m >= 40 && relativehumidity_2m <= 70){
            humidityInfo = recomendContext.getResources().getString(R.string.hum_norm);
        }
        if (relativehumidity_2m > 70) {
            if (temperature_2m >= 35) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp35);
            } else if (temperature_2m >= 30 && temperature_2m < 35) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp30_35);
            } else if (temperature_2m >= 24 && temperature_2m < 30) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp24_30);
            } else if (temperature_2m >= 18 && temperature_2m < 24) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp18_24);
            } else if (temperature_2m >= 12 && temperature_2m < 18) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp12_18);
            } else if (temperature_2m >= 6 && temperature_2m < 12) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp6_12);
            } else if (temperature_2m >= 0 && temperature_2m < 6) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp0_6);
            }

            if (temperature_2m < -30) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tm30);
            } else if (temperature_2m >= -30 && temperature_2m < -24) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tm24_30);
            } else if (temperature_2m >= -24 && temperature_2m < -18) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tm18_24);
            } else if (temperature_2m >= -18 && temperature_2m < -12) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tm12_18);
            } else if (temperature_2m >= -12 && temperature_2m < -6) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tp6_12);
            } else if (temperature_2m >= -6 && temperature_2m < 0) {
                humidityInfo = recomendContext.getResources().getString(R.string.high_hum_tm0_6);
            }
        }
        if (relativehumidity_2m < 40) {
            if (temperature_2m >= 35) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp35);
            } else if (temperature_2m >= 30 && temperature_2m < 35) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp30_35);
            } else if (temperature_2m >= 24 && temperature_2m < 30) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp24_30);
            } else if (temperature_2m >= 18 && temperature_2m < 24) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp18_24);
            } else if (temperature_2m >= 12 && temperature_2m < 18) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp12_18);
            } else if (temperature_2m >= 6 && temperature_2m < 12) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp6_12);
            } else if (temperature_2m >= 0 && temperature_2m < 6) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp0_6);
            }

            if (temperature_2m < -30) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tm30);
            } else if (temperature_2m >= -30 && temperature_2m < -24) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tm24_30);
            } else if (temperature_2m >= -24 && temperature_2m < -18) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tm18_24);
            } else if (temperature_2m >= -18 && temperature_2m < -12) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tm12_18);
            } else if (temperature_2m >= -12 && temperature_2m < -6) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tp6_12);
            } else if (temperature_2m >= -6 && temperature_2m < 0) {
                humidityInfo = recomendContext.getResources().getString(R.string.low_hum_tm0_6);
            }
        }
        return humidityInfo;
    }

    public String temperarureInfo(double temperature_2m, double apparent_temperature) {
        String temperatureInfo = "";
        String apparentTemp = "";
        if(temperature_2m >= 35) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_35);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_p35);
        } else if (temperature_2m >= 30 && temperature_2m < 35) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_30_35);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_p30_35);
        } else if (temperature_2m >= 24 && temperature_2m < 30) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_24_30);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_p24_30);
        } else if (temperature_2m >= 18 && temperature_2m < 24) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_18_24);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_p18_24);
        } else if (temperature_2m >= 12 && temperature_2m < 18) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_12_18);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_p12_18);
        } else if (temperature_2m >= 6 && temperature_2m < 12) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_6_12);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_p6_12);
        } else if (temperature_2m >= 0 && temperature_2m < 6) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_0_6);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_p0_6);
        } else if (temperature_2m <= 0 && temperature_2m > -6) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_6_0);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_m0_6);
        } else if (temperature_2m <= -12 && temperature_2m > -6) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_12_6);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_m6_12);
        } else if (temperature_2m <= -18 && temperature_2m > -12) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_18_12);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_m12_18);
        } else if (temperature_2m <= -18 && temperature_2m > -24) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_24_18);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_m18_24);
        } else if (temperature_2m <= -24 && temperature_2m > -30) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_30_24);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_m24_30);
        } else if (temperature_2m < -30) {
            apparentTemp = recomendContext.getResources().getString(R.string.t_30);
            temperatureInfo = recomendContext.getResources().getString(R.string.temp_m30);
        }
        return apparentTemp + "\n" +temperatureInfo;
    }

    public String weatherCond(int code) {
        String weatherInfo = "";
        switch (code){
            case 45:
            case 48:
                weatherInfo = recomendContext.getResources().getString(R.string.code_45_48); break;
            case 51: weatherInfo = recomendContext.getResources().getString(R.string.code_51); break;
            case 53: weatherInfo = recomendContext.getResources().getString(R.string.code_53); break;
            case 55: weatherInfo = recomendContext.getResources().getString(R.string.code_55); break;
            case 56: weatherInfo = recomendContext.getResources().getString(R.string.code_56); break;
            case 57: weatherInfo = recomendContext.getResources().getString(R.string.code_57); break;
            case 61: weatherInfo = recomendContext.getResources().getString(R.string.code_61); break;
            case 63: weatherInfo = recomendContext.getResources().getString(R.string.code_63); break;
            case 65: weatherInfo = recomendContext.getResources().getString(R.string.code_65); break;
            case 66: weatherInfo = recomendContext.getResources().getString(R.string.code_66); break;
            case 67: weatherInfo = recomendContext.getResources().getString(R.string.code_67); break;
            case 71: weatherInfo = recomendContext.getResources().getString(R.string.code_71); break;
            case 73: weatherInfo = recomendContext.getResources().getString(R.string.code_73); break;
            case 75: weatherInfo = recomendContext.getResources().getString(R.string.code_75); break;
            case 77: weatherInfo = recomendContext.getResources().getString(R.string.code_77); break;
            case 80: weatherInfo = recomendContext.getResources().getString(R.string.code_80); break;
            case 81: weatherInfo = recomendContext.getResources().getString(R.string.code_81); break;
            case 82: weatherInfo = recomendContext.getResources().getString(R.string.code_82); break;
            case 85: weatherInfo = recomendContext.getResources().getString(R.string.code_85); break;
            case 86: weatherInfo = recomendContext.getResources().getString(R.string.code_86); break;
            case 95: weatherInfo = recomendContext.getResources().getString(R.string.code_95); break;
            case 96: weatherInfo = recomendContext.getResources().getString(R.string.code_96); break;
            case 99: weatherInfo = recomendContext.getResources().getString(R.string.code_99); break;
        }
        return weatherInfo;
    }

    public String getInfoAir(int european_aqi_air) {
        String airInfo = "";
        if (european_aqi_air >= 0 && european_aqi_air <= 20) {
            airInfo = recomendContext.getString(R.string.air_1);
        } else if (european_aqi_air > 20 && european_aqi_air <= 40) {
            airInfo = recomendContext.getString(R.string.air_2);
        } else if (european_aqi_air > 40 && european_aqi_air <= 60) {
            airInfo = recomendContext.getString(R.string.air_3);
        } else if (european_aqi_air > 60 && european_aqi_air <= 80) {
            airInfo = recomendContext.getString(R.string.air_4);
        } else if (european_aqi_air > 80 && european_aqi_air <= 100) {
            airInfo = recomendContext.getString(R.string.air_5);
        } else airInfo = recomendContext.getString(R.string.air_6);
        return airInfo;
    }

    public String getInfoPm2_5(double pm2_5, int european_aqi_pm2_5){
        String pm2_5Info = "";
        if(european_aqi_pm2_5 >= 0 && european_aqi_pm2_5 <= 10) {
            pm2_5Info = recomendContext.getString(R.string.pm2_5_1);
        } else if(european_aqi_pm2_5 > 10 && european_aqi_pm2_5 <= 20) {
            pm2_5Info = recomendContext.getString(R.string.pm2_5_2);
        } else if(european_aqi_pm2_5 > 20 && european_aqi_pm2_5 <= 25) {
            pm2_5Info = recomendContext.getString(R.string.pm2_5_3);
        } else if(european_aqi_pm2_5 > 25 && european_aqi_pm2_5 <= 50) {
            pm2_5Info = recomendContext.getString(R.string.pm2_5_4);
        } else if(european_aqi_pm2_5 > 50 && european_aqi_pm2_5 <= 75) {
            pm2_5Info = recomendContext.getString(R.string.pm2_5_5);
        } else {
            pm2_5Info = recomendContext.getString(R.string.pm2_5_6);
        }

        return pm2_5Info;
    }

    public String getInfoPm10(double pm10, int european_aqi_pm10){
        String pm10Info = "";
        if(european_aqi_pm10 >= 0 && european_aqi_pm10 <= 20) {
            pm10Info = recomendContext.getString(R.string.pm10_1);
        } else if(european_aqi_pm10 > 20 && european_aqi_pm10 <= 40) {
            pm10Info = recomendContext.getString(R.string.pm10_2);
        } else if(european_aqi_pm10 > 40 && european_aqi_pm10 <= 50) {
            pm10Info = recomendContext.getString(R.string.pm10_3);
        } else if(european_aqi_pm10 > 50 && european_aqi_pm10 <= 100) {
            pm10Info = recomendContext.getString(R.string.pm10_4);
        } else if(european_aqi_pm10 > 100 && european_aqi_pm10 <= 150) {
            pm10Info = recomendContext.getString(R.string.pm10_5);
        } else {
            pm10Info = recomendContext.getString(R.string.pm10_6);
        }
        return pm10Info;
    }

    public String getInfoNO2(double NO2, int european_aqi_NO2){
        String NO2Info = "";
        int num = 0;
        if(european_aqi_NO2 >= 0 && european_aqi_NO2 <= 40) {
            NO2Info = recomendContext.getString(R.string.NO2_1);
            num = 1;
        } else if(european_aqi_NO2 > 40 && european_aqi_NO2 <= 90) {
            NO2Info = recomendContext.getString(R.string.NO2_2);
            num = 2;
        } else if(european_aqi_NO2 > 90 && european_aqi_NO2 <= 120) {
            NO2Info = recomendContext.getString(R.string.NO2_3);
            num = 3;
        } else if(european_aqi_NO2 > 120 && european_aqi_NO2 <= 230) {
            NO2Info = recomendContext.getString(R.string.NO2_4);
            num = 4;
        } else if(european_aqi_NO2 > 230 && european_aqi_NO2 <= 340) {
            NO2Info = recomendContext.getString(R.string.NO2_5);
            num = 5;
        } else {
            NO2Info = recomendContext.getString(R.string.NO2_6);
            num = 6;
        }
        return NO2Info + "!" + num;
    }

    public String getInfoSO2(double SO2, int european_aqi_SO2){
        String SO2Info = "";
        int num = 0;
        if(european_aqi_SO2 >= 0 && european_aqi_SO2 <= 100) {
            SO2Info = recomendContext.getString(R.string.SO2_1);
            num = 1;
        } else if(european_aqi_SO2 > 100 && european_aqi_SO2 <= 200) {
            SO2Info = recomendContext.getString(R.string.SO2_2);
            num = 2;
        } else if(european_aqi_SO2 > 200 && european_aqi_SO2 <= 350) {
            SO2Info = recomendContext.getString(R.string.SO2_3);
            num = 3;
        } else if(european_aqi_SO2 > 350 && european_aqi_SO2 <= 500) {
            SO2Info = recomendContext.getString(R.string.SO2_4);
            num = 4;
        } else if(european_aqi_SO2 > 500 && european_aqi_SO2 <= 750) {
            SO2Info = recomendContext.getString(R.string.SO2_5);
            num = 5;
        } else {
            SO2Info = recomendContext.getString(R.string.SO2_6);
            num = 6;
        }
        return SO2Info + "!" + num;
    }

    public String getInfoO3(double O3, int european_aqi_O3){
        String O3Info = "";
        int num = 0;
        if(european_aqi_O3 >= 0 && european_aqi_O3 <= 50) {
            O3Info = recomendContext.getString(R.string.O3_1);
            num = 1;
        } else if(european_aqi_O3 > 50 && european_aqi_O3 <= 100) {
            O3Info = recomendContext.getString(R.string.O3_2);
            num = 2;
        } else if(european_aqi_O3 > 100 && european_aqi_O3 <= 130) {
            O3Info = recomendContext.getString(R.string.O3_3);
            num = 3;
        } else if(european_aqi_O3 > 130 && european_aqi_O3 <= 240) {
            O3Info = recomendContext.getString(R.string.O3_4);
            num = 4;
        } else if(european_aqi_O3 > 240 && european_aqi_O3 <= 380) {
            O3Info = recomendContext.getString(R.string.O3_5);
            num = 5;
        } else  {
            O3Info = recomendContext.getString(R.string.O3_6);
            num = 6;
        }
        return O3Info + "!" + num;
    }

    public String getInfoCO(double CO){
        String COInfo = "";
        int num = 0;
        if(CO >= 0 && CO <= 300) {
            COInfo = recomendContext.getString(R.string.CO_1);
            num = 1;
        } else if(CO > 300 && CO <= 700) {
            COInfo = recomendContext.getString(R.string.CO_2);
            num = 2;
        } else if(CO > 700 && CO <= 1200) {
            COInfo = recomendContext.getString(R.string.CO_3);
            num = 3;
        } else if(CO > 1200 && CO <= 2000) {
            COInfo = recomendContext.getString(R.string.CO_4);
            num = 4;
        } else if(CO > 2000 && CO <= 4000) {
            COInfo = recomendContext.getString(R.string.CO_5);
            num = 5;
        } else {
            COInfo = recomendContext.getString(R.string.CO_6);
            num = 6;
        }
        return COInfo + "!" + num;
    }
    public String getInfoAerosol(double aerosol){
        String aerosolInfo = "";
        if(aerosol < 0.1){
            aerosolInfo = recomendContext.getString(R.string.aerosol_1);
        } else if (aerosol >= 0.1 && aerosol < 0.3){
            aerosolInfo = recomendContext.getString(R.string.aerosol_2);
        } else if (aerosol >= 0.3 && aerosol < 0.5){
            aerosolInfo = recomendContext.getString(R.string.aerosol_3);
        } else if (aerosol >= 0.5){
            aerosolInfo = recomendContext.getString(R.string.aerosol_4);
        }
        return aerosolInfo;
    }
    public String getInfoDust(double dust){
        String dustInfo = "";
        if(dust < 50){
            dustInfo = recomendContext.getString(R.string.dust_1);
        } else if (dust >= 50 && dust < 150){
            dustInfo = recomendContext.getString(R.string.dust_2);
        } else if (dust >= 150 && dust < 250){
            dustInfo = recomendContext.getString(R.string.dust_3);
        } else if (dust >= 250){
            dustInfo = recomendContext.getString(R.string.dust_4);
        }
        return dustInfo;
    }
}
