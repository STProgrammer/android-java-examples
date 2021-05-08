package com.wfamedia.location3;

import android.Manifest;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.wfamedia.location3.entities.MyLocation;
import com.wfamedia.location3.service.MyLocationService;
import com.wfamedia.location3.viewmodel.MyLocationsViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int CALLBACK_REQUEST_FINE_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 10;
    private static String[] requiredPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE
    };

    // Indikerer om servicen er startet eller stoppet:
    private boolean requestingLocationUpdates = false;

    private MyLocationsViewModel myLocationsViewModel;
    private TextView tvTrackedLocation;

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

        // ViewModel:
        myLocationsViewModel = new ViewModelProvider(this).get(MyLocationsViewModel.class);
        myLocationsViewModel.getAllMyLocations().observe(this, locations -> {
            StringBuffer stringBuffer = new StringBuffer();
            for (MyLocation myLocation: locations) {
                stringBuffer.append("ID " + myLocation.getId());
                stringBuffer.append(": ");
                stringBuffer.append(myLocation.getLatitude());
                stringBuffer.append(", ");
                stringBuffer.append(myLocation.getLongitude());
                stringBuffer.append(", ");
                stringBuffer.append(myLocation.getAltitude());
                stringBuffer.append("\n");
            }
            tvTrackedLocation.setText(stringBuffer.toString());
        });

        //SLETT ALLE LOKASJONER FRA DATABASEN:
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocationsViewModel.deleteAll();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ved skjermrotasjon må vi starte servicen på nytt (siden vi stopper servicen i onDestroy()):
        if (this.requestingLocationUpdates) {
            verifyFineLocationPermissions();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALLBACK_REQUEST_FINE_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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