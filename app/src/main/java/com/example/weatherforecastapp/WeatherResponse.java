package com.example.weatherforecastapp;
import java.util.List;

public class WeatherResponse {

    private List<Weather> weather; // Weather conditions
    private Main main;            // Temperature-related data
    private Wind wind;            // Wind data
    private Sys sys;              // Country and sunrise/sunset info
    private String name;          // City name

    // Getters for each field
    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }

    // Weather nested class
    public class Weather {
        private String main;
        private String description;
        private String icon;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    // Main nested class
    public class Main {
        private float temp;
        private float feels_like;
        private float temp_min;
        private float temp_max;
        private int pressure;
        private int humidity;

        public float getTemp() {
            return temp;
        }

        public float getFeelsLike() {
            return feels_like;
        }

        public float getTempMin() {
            return temp_min;
        }

        public float getTempMax() {
            return temp_max;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    // Wind nested class
    public class Wind {
        private float speed;
        private int deg;

        public float getSpeed() {
            return speed;
        }

        public int getDeg() {
            return deg;
        }
    }

    // Sys nested class
    public class Sys {
        private String country;
        private long sunrise;
        private long sunset;

        public String getCountry() {
            return country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }
    }
}
