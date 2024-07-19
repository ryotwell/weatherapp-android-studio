package com.example.belajar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class WeatherService {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?appid=fe4feefa8543e06d4f3c66d92c61b69c&q=";

    private final OkHttpClient client;

    private final Gson gson;

    private String query;

    public WeatherService() {
        client = new OkHttpClient();
        gson = new GsonBuilder().create();
    }

    public void search(String query)
    {
        this.query = query;
    }

    public void fetchWeatherData(WeatherDataCallback callback) {
        Request request = new Request.Builder()
                .url(API_URL + query)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    WeatherResponse weatherResponse = gson.fromJson(jsonData, WeatherResponse.class);

                    if (weatherResponse.main != null) {
                        float temperature = weatherResponse.main.temp != null ? weatherResponse.main.temp : Float.NaN;
                        int pressure = weatherResponse.main.pressure != null ? weatherResponse.main.pressure : -1;
                        int humidity = weatherResponse.main.humidity != null ? weatherResponse.main.humidity : -1;
                        String name = weatherResponse.name != null ? weatherResponse.name : "Unknown";
                        float wind = weatherResponse.wind.speed != null ? weatherResponse.wind.speed : Float.NaN;
                        String sys_country = weatherResponse.sys.country != null ? weatherResponse.sys.country : "Unknown";

                        String icon_description = weatherResponse.weather.get(0).icon;
                        String description = weatherResponse.weather.get(0).description;

                        icon_description = getDescriptionForIcon(icon_description);
                        temperature = (int) temperature;
                        name = name + ", " + sys_country;

                        WeatherData weatherData = new WeatherData(temperature - 273, pressure, humidity, name, wind, icon_description, description);
                        callback.onSuccess(weatherData);
                    } else {
                        callback.onFailure(new NullPointerException("Main data is not available."));
                    }
                } else {
                    callback.onFailure(new IOException("Response not successful."));
                }
            }
        });
    }

    public interface WeatherDataCallback {
        void onSuccess(WeatherData data);
        void onFailure(Exception e);
    }

    protected String getDescriptionForIcon(String icon) {
        String[][] weatherDescriptions = {
                {"01d", "Siang yang cerah"},
                {"01n", "Malam yang cerah"},
                {"02d", "Siang berawan sebagian"},
                {"02n", "Malam berawan sebagian"},
                {"03d", "Siang berawan"},
                {"03n", "Malam berawan"},
                {"04d", "Siang yang sempurna"},
                {"04n", "Malam berawan"},
                {"09d", "Hujan di siang hari"},
                {"09n", "Hujan di malam hari"},
                {"10d", "Hujan di siang hari"},
                {"10n", "Hujan di malam hari"},
                {"11d", "Badai di siang hari"},
                {"11n", "Badai di malam hari"}
        };

        for (String[] pair : weatherDescriptions) {
            if (pair[0].equals(icon)) {
                return pair[1];
            }
        }
        return null;
    }
}

