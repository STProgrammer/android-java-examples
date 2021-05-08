package com.wfamedia.dptest0_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.wfamedia.dptest0_1.viewmodel.MyViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Demonstrerer dependenciy injection.
 * I MainActivity brukes "field injection" for å initialisere myViewModel-instansen.
 *
 * For at dette skal fungere må Hilt vite hvordan MyViewModel instansieres.
 *
 * En "binding/kopling" beskriver hvordan en instans av avhengig type (her: MyViewModel)
 * instansieres.
 *
 * En slik binding/kopling kan lages vha. @Inject på konstruktøren ("constructor injection")
 * i (her) MyViewModel-klassen.
 *
 * HER brukes @Bind
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    MyViewModel myViewModel;    //"Field injection". Merk! denne kan ikke være private.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        textView.setText(myViewModel.getCountry() + " (" + String.valueOf(myViewModel.getPopulation()) + ")");
    }
}