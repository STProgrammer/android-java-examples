package com.example.calculatetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String MY_TIME_FORMAT = "dd.MM.yyyy hh:mm";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendarNow = Calendar.getInstance();
        String date = new SimpleDateFormat(MY_TIME_FORMAT).format(calendarNow.getTime());

        String message = "Beregner tidsdifferanse fra " + date + " til ...";

        TextView timeNow = findViewById(R.id.timeNow);
        timeNow.setText(message);

    }

    public void calculateDifference(View view) {

        Calendar calendarNow = Calendar.getInstance();
        String date = new SimpleDateFormat(MY_TIME_FORMAT).format(calendarNow.getTime());

        String message = "Beregner tidsdifferanse fra " + date + " til ...";

        TextView timeNow = findViewById(R.id.timeNow);
        timeNow.setText(message);

        Intent intent = new Intent(this, MainActivity.class);
        CalendarView dateInput = (CalendarView) findViewById(R.id.calendarView);

        dateInput.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day, 0, 0);
                dateInput.setDate(c.getTimeInMillis());
            }
        });
        long dateGiven = dateInput.getDate();
/*
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendarGiven = Calendar.getInstance();
        try {
            calendarGiven.setTime(simpleDateFormat.parse(dateGiven));
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/
        TextView timeDifference = findViewById(R.id.timeDifference);

        long start = calendarNow.getTimeInMillis();
        //long end = calendarGiven.getTimeInMillis();

        long difference = dateGiven - start;

        if (difference < 0) {
            timeDifference.setBackgroundColor(Color.RED);
            difference = -1 * difference;
        } else {
            timeDifference.setBackgroundColor(Color.GREEN);
        }

        long days = (difference / (1000*24*60*60));
        long hours = (difference / (1000*60*60)) % 24;
        long minutes = (difference / (1000*60)) % 60;

        String timeDifferenceStr = days + "d " + hours + "t " + minutes + "m";

        timeDifference.setText(timeDifferenceStr);
    }
}