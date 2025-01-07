package com.example.weatherforecastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etCity;
    private Button btnGetWeather;
    private TextView tvCity, tvTemperature, tvFeelsLike, tvWeather, tvHumidity, tvWindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.et_city);
        btnGetWeather = findViewById(R.id.btn_get_weather);
        tvCity = findViewById(R.id.tv_city);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvFeelsLike = findViewById(R.id.tv_feels_like);
        tvWeather = findViewById(R.id.tv_weather);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWindSpeed = findViewById(R.id.tv_wind_speed);

        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCity.getText().toString();
                if (!city.isEmpty()) {
                    fetchWeather(city);
                }
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
                        tvCity.setText("City: " + weather.getName());
                        tvTemperature.setText("Temperature: " + weather.getMain().getTemp() + " ℃");
                        tvFeelsLike.setText("Feels Like: " + weather.getMain().getFeelsLike() + " ℃");
                        tvWeather.setText("Weather: " + weather.getWeather().get(0).getDescription());
                        tvHumidity.setText("Humidity: " + weather.getMain().getHumidity() + "%");
                        tvWindSpeed.setText("Wind Speed: " + weather.getWind().getSpeed() + " m/s");
                    }
                } else {
                    tvCity.setText("City not found");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                tvCity.setText("Error: " + t.getMessage());
            }
        });
    }
}
