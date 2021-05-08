package com.wfamedia.fragment1_1;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectionFragment extends Fragment implements OnClickListener {

    private String wifiText="", mobileText="", gpsText="", bluetoothText="";
    private int initialColorSelection=0;

    public SelectionFragment() {
        super(R.layout.selection_fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initialColorSelection = requireArguments().getInt("initial_color_selection");

        //NB! Merk bruk av view.findViewById(...);
        RadioButton rbRed = view.findViewById(R.id.rbRed);
        rbRed.setOnClickListener(this);
        rbRed.setChecked(this.initialColorSelection==0);

        RadioButton rbGreen = view.findViewById(R.id.rbGreen);
        rbGreen.setOnClickListener(this);
        rbGreen.setChecked(this.initialColorSelection==1);

        RadioButton rbBlue = view.findViewById(R.id.rbBlue);
        rbBlue.setOnClickListener(this);
        rbBlue.setChecked(this.initialColorSelection==2);

        CheckBox cbWiFi = view.findViewById(R.id.cbWiFi);
        cbWiFi.setOnClickListener(this);
        CheckBox cbMobil = view.findViewById(R.id.cbMobileData);
        cbMobil.setOnClickListener(this);
        CheckBox cbGPS = view.findViewById(R.id.cbGPS);
        cbGPS.setOnClickListener(this);
        CheckBox cmBlue = view.findViewById(R.id.cbBluetooth);
        cmBlue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int color = R.color.myred;

        switch(view.getId()) {
            //Radioknappene:
            case R.id.rbRed:
                if (((RadioButton) view).isChecked()) {
                    color = R.color.myred;
                }
                break;

            case R.id.rbGreen:
                if (((RadioButton) view).isChecked()) {
                    color = R.color.mygreen;
                }
                break;

            case R.id.rbBlue:
                if (((RadioButton) view).isChecked()) {
                    color = R.color.myblue;
                }
                break;

            //Checkboksene:
            case R.id.cbWiFi:
                if (((CheckBox) view).isChecked())
                    this.wifiText = "WiFi";
                else
                    this.wifiText = "";
                break;
            case R.id.cbMobileData:
                if (((CheckBox) view).isChecked())
                    mobileText = "MobilData";
                else
                    mobileText = "";
                break;
            case R.id.cbGPS:
                if (((CheckBox) view).isChecked())
                    gpsText = "GPS";
                else
                    gpsText = "";
                break;
            case R.id.cbBluetooth:
                if (((CheckBox) view).isChecked())
                    bluetoothText = "Bluetooth";
                else
                    bluetoothText = "";
                break;
        }
    }
}
