package com.example.inputprosjekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView birthYear = findViewById(R.id.etFodselsaar);

        //Set year to 1970
        Resources res = this.getResources();
        int year = res.getInteger(R.integer.fodselsaar);
        birthYear.setText(String.valueOf(year));

        // add + 10 to the year
        int newValue = Integer.parseInt(birthYear.getText().toString()) + 10;
        birthYear.setText(String.valueOf(year));

        TextView currentDate = findViewById(R.id.viDagensDato);
        String text = res.getString(R.string.dagens_dato);

        String curDate = new SimpleDateFormat("\"dd.MM.yyyy hh:mm\"").format(Calendar.getInstance().getTime());

        currentDate.setText(text + curDate);

    }
}