package com.wfamedia.viewbinding0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.wfamedia.viewbinding0.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityMainBinding.tvHello1.setText("Heisann!!");
        activityMainBinding.tvHello2.setText("Noe annet ...");
    }
}