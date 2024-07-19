package com.example.belajar;

import java.util.List;

public class WeatherResponse {
    public Main main;

    public Wind wind;

    public Sys sys;

    public String name;

    public List<Weather> weather;

    public Coord coord;

    public class Coord {
        public Float lon;

        public Float lat;
    }

    public class Wind {
        public Float speed;
    }

    public class Sys {
        public int id;
        public String country;
    }

    public class Weather {
        public Integer id;
        public String main;
        public String description;
        public String icon;
    }

    public class Main {
        public Float temp;

        public Integer pressure;

        public Integer humidity;
    }
}
