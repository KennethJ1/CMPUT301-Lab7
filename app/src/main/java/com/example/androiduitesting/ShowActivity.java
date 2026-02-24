package com.example.androiduitesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_NAME = "EXTRA_CITY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String cityName = getIntent().getStringExtra(EXTRA_CITY_NAME);

        TextView tv = findViewById(R.id.text_city_name);
        tv.setText(cityName);

        Button back = findViewById(R.id.button_back);
        back.setOnClickListener(v -> finish());
    }
}