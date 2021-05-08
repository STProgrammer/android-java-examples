package com.wfamedia.fragment3_1;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SelectionFragment extends Fragment implements OnClickListener {

    private MyViewModel myViewModel;
    private String wifiText="", mobileText="", gpsText="", bluetoothText="";

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

        //NB! Merk bruk av view.findViewById(...);
        RadioButton rbRed = view.findViewById(R.id.rbRed);
        rbRed.setOnClickListener(this);
        RadioButton rbGreen = view.findViewById(R.id.rbGreen);
        rbGreen.setOnClickListener(this);
        RadioButton rbBlue = view.findViewById(R.id.rbBlue);
        rbBlue.setOnClickListener(this);
        //NB! Merk bruk av view.findViewById(...);
        CheckBox cbWiFi = view.findViewById(R.id.cbWiFi);
        cbWiFi.setOnClickListener(this);
        CheckBox cbMobil = view.findViewById(R.id.cbMobileData);
        cbMobil.setOnClickListener(this);
        CheckBox cbGPS = view.findViewById(R.id.cbGPS);
        cbGPS.setOnClickListener(this);
        CheckBox cmBlue = view.findViewById(R.id.cbBluetooth);
        cmBlue.setOnClickListener(this);
        
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.getResetColor().observe(getViewLifecycleOwner(), resetColorSelection -> {
            if (resetColorSelection) {
                //reset seletions
                rbRed.setChecked(false);
                rbGreen.setChecked(false);
                rbBlue.setChecked(false);
            }
        });

        myViewModel.getResetSelections().observe(getViewLifecycleOwner(), resetSelections -> {
            if (resetSelections) {
                //reset seletions
                cbWiFi.setChecked(false);
                cbMobil.setChecked(false);
                cbGPS.setChecked(false);
                cmBlue.setChecked(false);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int color = R.color.myred;
        switch(view.getId()) {
            //Radioknappene:
            case R.id.rbRed:
                if (((RadioButton) view).isChecked()) {
                    color = R.color.myred;
                    myViewModel.setTextColor(color);
                }
                break;

            case R.id.rbGreen:
                if (((RadioButton) view).isChecked()) {
                    color = R.color.mygreen;
                    myViewModel.setTextColor(color);
                }
                break;

            case R.id.rbBlue:
                if (((RadioButton) view).isChecked()) {
                    color = R.color.myblue;
                    myViewModel.setTextColor(color);
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
        myViewModel.setText(wifiText + " " + mobileText + " " + gpsText + " " + bluetoothText);
    }
}
