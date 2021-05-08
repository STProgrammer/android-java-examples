package com.wfamedia.dptest2.activitites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.wfamedia.dptest2.R;
import com.wfamedia.dptest2.viewmodel.MyViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        textView.setText(myViewModel.getCountry() + " (" + String.valueOf(myViewModel.getPopulation()) + ")");
    }
}