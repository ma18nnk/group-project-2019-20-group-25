package com.example.loginregister;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainCalActiv extends AppCompatActivity {
CustomCalendarView customCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cal);
    customCalendarView=(CustomCalendarView)findViewById(R.id.custom_calendar_view);
    }
}
