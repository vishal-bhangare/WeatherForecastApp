package com.example.weatherforecastapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etCity;
    private Button btnGetWeather, btnBack;
    private LinearLayout layoutInput, layoutWeatherDetails;
    private TextView tvCity, tvTemperature, tvFeelsLike, tvWeather, tvHumidity, tvWindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.et_city);
        btnGetWeather = findViewById(R.id.btn_get_weather);
        layoutInput = findViewById(R.id.layout_input);
        layoutWeatherDetails = findViewById(R.id.layout_weather_details);
        tvCity = findViewById(R.id.tv_city);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvFeelsLike = findViewById(R.id.tv_feels_like);
        tvWeather = findViewById(R.id.tv_weather);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWindSpeed = findViewById(R.id.tv_wind_speed);
        btnBack = findViewById(R.id.back);
        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCity.getText().toString();
                if (!city.isEmpty()) {
                    fetchWeather(city);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = "";
                etCity.setText(city);
                layoutInput.setVisibility(View.VISIBLE);
                layoutWeatherDetails.setVisibility(View.GONE);
            }
        });
    }

    private void fetchWeather(String city) {
        WeatherApi api = RetrofitClient.getInstance().create(WeatherApi.class);
        Call<WeatherResponse> call = api.getWeather(city, "a3aed08d7c20cf9e50e3aa7f6c157eed", "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weather = response.body();
                    if (weather != null) {
                        tvCity.setText(weather.getName());
                        double temperatureFull = weather.getMain().getTemp();
                        double temperature = Math.floor(temperatureFull * 10) / 10;
                        tvTemperature.setText(temperature + " ℃");
                        changeTemperatureColor(temperature);

                        tvFeelsLike.setText("Feels Like: " + weather.getMain().getFeelsLike() + " ℃");
                        tvWeather.setText("Weather: " + weather.getWeather().get(0).getDescription());
                        tvHumidity.setText("Humidity: " + weather.getMain().getHumidity() + "%");
                        tvWindSpeed.setText("Wind Speed: " + weather.getWind().getSpeed() + " m/s");

                        layoutInput.setVisibility(View.GONE);
                        layoutWeatherDetails.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvCity.setText("City not found");
                    layoutWeatherDetails.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                tvCity.setText("Error: " + t.getMessage());
                layoutWeatherDetails.setVisibility(View.VISIBLE);
            }
        });
    }

    private void changeTemperatureColor(double temperature) {
        if (temperature < 10) {
            tvTemperature.setTextColor(Color.BLUE); // Cold
        } else if (temperature >= 10 && temperature <= 25) {
            tvTemperature.setTextColor(Color.GREEN); // Mild
        } else if (temperature > 25 && temperature <= 35) {
            tvTemperature.setTextColor(Color.parseColor("#FFA500")); // Warm
        } else {
            tvTemperature.setTextColor(Color.RED); // Hot
        }
    }
}
