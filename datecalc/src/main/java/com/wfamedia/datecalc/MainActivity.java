package com.wfamedia.datecalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String MY_DATE_FORMAT = "dd.MM.yyyy";
    public static final String MY_DATETIME_FORMAT = "dd.MM.yyyy hh:mm:ss";
    private TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendarNow = Calendar.getInstance();
        String dateNow = new SimpleDateFormat(MY_DATETIME_FORMAT).format(calendarNow.getTime());
        tvHeader = this.findViewById(R.id.tvHeader);
        tvHeader.setText("Beregner tidsdifferanse fra " + dateNow);
    }

    public void calculate(View view) {
        Calendar calendarNow = Calendar.getInstance();
        String dateNow = new SimpleDateFormat(MY_DATETIME_FORMAT).format(calendarNow.getTime());

        tvHeader = this.findViewById(R.id.tvHeader);
        tvHeader.setText("Beregner tidsdifferanse fra " + dateNow);

        EditText etDateGiven = findViewById(R.id.etDateGiven);
        String dateGiven = etDateGiven.getText().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MY_DATE_FORMAT);
        try {
            Calendar calendarGiven = Calendar.getInstance();
            calendarGiven.setTime(simpleDateFormat.parse(dateGiven));

            long milsecs1= calendarNow.getTimeInMillis();
            long milsecs2 = calendarGiven.getTimeInMillis();
            long diffMilliseconds = milsecs2 - milsecs1;

            long diffSekunder = Math.abs(diffMilliseconds / 1000);

            long days = diffSekunder / (24*3600);               //Antall hele dager mellom gitte datoer.
            long restSecondsDays = diffSekunder % (24*3600);    //Restsekunder etter heltallsdivisjon.
            long hours = restSecondsDays / 3600;                //Antall hele dager timer av restSecondsDays.
            long restSeconds = restSecondsDays % 3600;          //Restsekunder etter heltallsdivisjon.
            long minutes = restSeconds / 60;                    //Antall hele minutter i restSeconds.
            long seconds = restSeconds % 60;                    //osv.

            TextView tvResult = this.findViewById(R.id.tvResult);
            tvResult.setText(days + "d " + hours + "t " + minutes + "m");

            if (diffMilliseconds<0)
                tvResult.setBackgroundColor(Color.RED);
            else
                tvResult.setBackgroundColor(Color.GREEN);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}