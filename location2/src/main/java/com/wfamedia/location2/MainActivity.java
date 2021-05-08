package com.wfamedia.location2;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final int CALLBACK_REQUEST_FINE_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 10;
    private static String[] requiredPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE
    };

    // Indikerer om servicen er startet eller stoppet:
    private boolean requestingLocationUpdates = false;

    private TextView tvTrackedLocation;

    // Inner broadcastmottaker-klasse:
    private class MyLocationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String position = intent.getStringExtra("POSITION");
            if (position!=null) {
                String currentContent = tvTrackedLocation.getText().toString();
                tvTrackedLocation.setText(currentContent + position);
            }
        }
    }

    // Instans av inner broadcastmottaker-klasse:
    private MyLocationReceiver myLocationReceiver = new MyLocationReceiver();

    // Intentfilter for broadcastmottaker:
    public final static String LOCATION_FILTER_STRING = "com.wfamedia.location2.MY_LOCATION_RECEIVER";
    private IntentFilter myBroadcastFilter = new IntentFilter(LOCATION_FILTER_STRING);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTrackedLocation = findViewById(R.id.tvTrackedLocation);

        //STARTER SERVICE:
        Button btnStartService = findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFineLocationPermissions();
            }
        });

        //STOPP SERVICE:
        Button btnStoppService = findViewById(R.id.btnStoppService);
        btnStoppService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestingLocationUpdates = false;
                Intent myIntent = new Intent(MainActivity.this, MyLocationService.class);
                stopService(myIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocationReceiver, myBroadcastFilter);

        // Ved skjermrotasjon må vi starte servicen på nytt (siden vi stopper servicen i onDestroy()):
        if (this.requestingLocationUpdates) {
            verifyFineLocationPermissions();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myLocationReceiver!=null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(myLocationReceiver);
    }

    @Override
    protected void onDestroy() {
        //Sikrer at servicen stopper når appen avslutter.
        Intent myIntent = new Intent(MainActivity.this, MyLocationService.class);
        stopService(myIntent);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("requestingLocationUpdates", requestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.keySet().contains("requestingLocationUpdates")) {
            this.requestingLocationUpdates = savedInstanceState.getBoolean("requestingLocationUpdates");
        } else {
            this.requestingLocationUpdates = false;
        }
    }

    private void verifyFineLocationPermissions() {
        // Kontrollerer om vi har tilgang til ACCESS_FINE_LOCATION:
        int locationPermissionFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (locationPermissionFine != PackageManager.PERMISSION_GRANTED) {
            // Dersom vi ikke har nødvendige tilganger spør bruker om tilgang.
            // Fortsetter i metoden onRequestPermissionsResult() ...\
            ActivityCompat.requestPermissions(this, requiredPermissions, CALLBACK_REQUEST_FINE_LOCATION_PERMISSION);
        } else {
            // Fortsetter dersom tilgang gitt fra før:
            verifyLocationUpdatesRequirements();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //Kalles når bruker har akseptert og gitt tillatelse til bruk av posisjon:
            case REQUEST_CHECK_SETTINGS:
                verifyFineLocationPermissions();
                return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CALLBACK_REQUEST_FINE_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                    verifyLocationUpdatesRequirements();
                }
                return;

            default:
                Toast.makeText(this, "Feil ...! Ingen tilgang!!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sjekker om kravene satt i locationRequest kan oppfylles.
     * Hvis ikke vises en dialog.
     *
     */
    private void verifyLocationUpdatesRequirements() {
        final LocationRequest locationRequest = createLocationRequest();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // NB! Sjekker om kravene satt i locationRequest kan oppfylles:
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Starter location dervice!
                Intent myIntent = new Intent(MainActivity.this, MyLocationService.class);
                startForegroundService(myIntent);

                requestingLocationUpdates = true;
            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Lokasjopnsinnstillinger er IKKE OK, men det kan fikses ved å vise brukeren en dialog!!
                    try {
                        // Viser dialogen ved å kalle startResolutionForResult() OG SJEKKE resultatet i onActivityResult()
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    // LocationRequest: Setter krav til posisjoneringa:
    // Merk: public static, brukes også fra MyLocationService.
    public static LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        // Hvor ofte ønskes lokasjonsoppdateringer (her: hvert 10.sekund)
        locationRequest.setInterval(3000);
        // Her settes intervallet for hvor raskt appen kan håndtere oppdateringer.
        locationRequest.setFastestInterval(2000);
        // Ulike verderi; Her: høyest mulig nøyaktighet som også normalt betyr bruk av GPS.
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}