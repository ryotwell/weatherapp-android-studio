package com.example.belajar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button buka, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.buka).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
        });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText search = (EditText) findViewById(R.id.search);

                WeatherService weatherService = new WeatherService();
                weatherService.search(search.getText().toString());
                weatherService.fetchWeatherData(new WeatherService.WeatherDataCallback() {

                    @Override
                    public void onSuccess(WeatherData data) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                float temperature = data.temperature;
                                int pressure = data.pressure;
                                int humidity = data.humidity;
                                String name = data.name;
                                float wind = data.wind;
                                String icon_description = data.icon_description;
                                String description = data.description;

                                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

                                intent.putExtra("icon_description", icon_description);
                                intent.putExtra("description", description);
                                intent.putExtra("name", name);
                                intent.putExtra("humidity", Float.toString(humidity));
                                intent.putExtra("pressure", Float.toString(pressure));
                                intent.putExtra("temperature", Float.toString(temperature));
                                intent.putExtra("wind", Float.toString(wind));

                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Kota tidak di temukan.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
    }

    @Override
    protected void onPause() {
        Toast.makeText(getApplicationContext(), "MainActivity Paused.", Toast.LENGTH_SHORT).show();

        super.onPause();
    }
}