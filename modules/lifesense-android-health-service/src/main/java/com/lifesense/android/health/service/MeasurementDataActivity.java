package com.lifesense.android.health.service;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MeasurementDataActivity extends AppCompatActivity {

    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_data);
        String measurementData = getIntent().getStringExtra("measurementData");
        text = findViewById(R.id.editText);
        text.setText(measurementData);
    }



}
