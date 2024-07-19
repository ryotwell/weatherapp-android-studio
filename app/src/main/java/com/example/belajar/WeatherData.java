package com.example.belajar;

public class WeatherData {
    public int temperature;
    public int pressure;
    public int humidity;

    public String name;

    public float wind;

    public String icon_description;

    public String description;

    public WeatherData(float temperature, int pressure, int humidity, String name, float wind, String icon_description, String description) {
        this.temperature = (int) temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.name = name;
        this.wind = wind;
        this.icon_description = icon_description;
        this.description = description;
    }
}

