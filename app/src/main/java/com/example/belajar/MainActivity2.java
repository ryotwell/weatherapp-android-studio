package com.example.belajar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    Button back_to_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView city = (TextView) findViewById(R.id.city);
        TextView humidity = (TextView) findViewById(R.id.humidity);
        TextView temperature = (TextView) findViewById(R.id.temperature);
        TextView wind = (TextView) findViewById(R.id.wind);
        TextView icon_description = (TextView) findViewById(R.id.icon_description);
        TextView pressure = (TextView) findViewById(R.id.pressure);

        city.setText(getIntent().getStringExtra("name"));
        humidity.setText(getIntent().getStringExtra("humidity"));
        temperature.setText(getIntent().getStringExtra("temperature"));
        wind.setText(getIntent().getStringExtra("wind"));
        icon_description.setText(getIntent().getStringExtra("icon_description"));
        pressure.setText(getIntent().getStringExtra("pressure"));

        back_to_homepage = findViewById(R.id.back_to_homepage);
        back_to_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}