package com.wfamedia.location1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final int CALLBACK_REQUEST_COARSE_LOCATION_PERMISSION = 1;
    private static final int CALLBACK_REQUEST_FINE_LOCATION_PERMISSION = 2;
    private static final int REQUEST_CHECK_SETTINGS = 10;

    // Indikerer om servicen er startet eller stoppet:
    private boolean requestingLocationUpdates = false;

    private FusedLocationProviderClient fusedLocationClient;
    private Location previousLocation=null;
    private LocationCallback locationCallback;

    private TextView tvLocation;
    private TextView tvTrackedLocation;

    private static String[] requiredPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLocation = findViewById(R.id.tvLocation);
        tvTrackedLocation = findViewById(R.id.tvTrackedLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //DEL 2: Callback for å fange opp lokasjonsendringer:
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                StringBuffer locationBuffer = new StringBuffer();

                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    if (previousLocation==null)
                        previousLocation = location;

                    float distance = previousLocation.distanceTo(location);
                    Log.d("MY-LOCATION-DISTANCE", String.valueOf(distance));
                    Log.d("MY-LOCATION", location.toString());

                    locationBuffer.append(location.getLatitude() + ", " + location.getLongitude() + "\n");

                    previousLocation = location;
                }
                tvTrackedLocation.setText(locationBuffer);
            }
        };

        Button btnLastKnownLocation = findViewById(R.id.btnLastKnownLocation);
        btnLastKnownLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DEL 1:
                verifyCoarseLocationPermissions();
            }
        });

        Button btnStartTracking = findViewById(R.id.btnStartTracking);
        btnStartTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DEL 2:
                initLocationUpdates();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            this.verifyFineLocationPermissions();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fusedLocationClient != null && locationCallback != null)
            fusedLocationClient.removeLocationUpdates(locationCallback);
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

    private void verifyCoarseLocationPermissions() {
        // Kontrollerer om vi har tilgang til ACCESS_COARSE_LOCATION:
        int locationPermissionCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (locationPermissionCoarse != PackageManager.PERMISSION_GRANTED) {
            // Dersom vi ikke har nøfvendige tilganger spør bruker om tilgang.
            // Fortsetter i metoden onRequestPermissionsResult() ...\
            ActivityCompat.requestPermissions(this, requiredPermissions, CALLBACK_REQUEST_COARSE_LOCATION_PERMISSION);
        } else {
            // DEl 1:
            this.getLastKnownLocation();
        }
    }

    // DEL 1: Finner siste kjente posisjon.
    @SuppressLint("MissingPermission")
    private void getLastKnownLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.d("MY-LOCATION", "SIST KJENTE POSISJON: " + location.toString());
                            tvLocation.setText(location.getLatitude() + ", " + location.getLongitude());
                        }
                    }
                });

    }

    //DEL 2: Verifiserer kravene satt i locationRequest-objektet.
    //       Dersom OK verifiseres fine-location-tillatelse start av lokasjonsforespørsler.
    private void initLocationUpdates() {
        final LocationRequest locationRequest = this.createLocationRequest();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        // NB! Sjekker om kravene satt i locationRequest kan oppfylles:
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Alle lokasjopnsinnstillinger er OK, klienten kan nå initiere lokasjonsforespørsler her:
                verifyFineLocationPermissions();
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
    // DEL 1 og 2:
    public LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        // Hvor ofte ønskes lokasjonsoppdateringer (her: hvert 5.sekund)
        locationRequest.setInterval(5000);
        // Her settes intervallet for hvor raskt appen kan håndtere oppdateringer.
        locationRequest.setFastestInterval(3000);
        // Ulike verderi; Her: høyest mulig nøyaktighet som også normalt betyr bruk av GPS.
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    // DEL 2:
    private void verifyFineLocationPermissions() {
        // Kontrollerer om vi har tilgang til ACCESS_FINE_LOCATION:
        int locationPermissionFine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (locationPermissionFine != PackageManager.PERMISSION_GRANTED) {
            // Dersom vi ikke har nøfvendige tilganger spør bruker om tilgang.
            // Fortsetter i metoden onRequestPermissionsResult() ...\
            ActivityCompat.requestPermissions(this, requiredPermissions, CALLBACK_REQUEST_FINE_LOCATION_PERMISSION);
        } else {
            // Fortsetter dersom tilgang gitt fra før:
            startLocationUpdates();

            requestingLocationUpdates = true;
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        LocationRequest locationRequest = this.createLocationRequest();
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */);
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
            case CALLBACK_REQUEST_COARSE_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                    this.getLastKnownLocation();
                }
                return;

            case CALLBACK_REQUEST_FINE_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                    startLocationUpdates();
                }
                return;

            default:
                Toast.makeText(this, "Feil ...! Ingen tilgang!!", Toast.LENGTH_SHORT).show();
        }
    }
}