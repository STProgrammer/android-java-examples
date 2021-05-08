package com.wfamedia.location3.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.wfamedia.location3.MainActivity;
import com.wfamedia.location3.R;
import com.wfamedia.location3.entities.MyLocation;
import com.wfamedia.location3.viewmodel.MyLocationsRepository;
import com.wfamedia.location3.viewmodel.MyLocationsViewModel;

public class MyLocationService extends Service {

    private Handler handler = new Handler(); //(Looper.getMainLooper());
    private static final int LOCATION_NOTIFICATION_ID = 1010;

    private LocationCallback locationCallback;
    private Location previousLocation=null;
    private  NotificationManager notificationManager;
    private String channelId;

    private FusedLocationProviderClient fusedLocationClient;

    // NB! Bruker repositoryklassen direkte - går ikke via ViewModel.
    private MyLocationsRepository myLocationsRepository;

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
        Notification notification = createNotification("0.0 0.0");
        startForeground(LOCATION_NOTIFICATION_ID, notification);

        // NB! Bruker repositoryklassen direkte - går ikke via ViewModel.
        myLocationsRepository = new MyLocationsRepository(getApplication());
    }

    private Notification createNotification(String notificationText){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);
        // The PendingIntent to launch our activity if the user selects
        // this notification
        Notification notification =  new Notification.Builder(this, channelId)
            .setContentTitle("Lokasjon")
            .setOnlyAlertOnce(false)
            .setContentText("Din posisjon: " + notificationText)
            .setSmallIcon(R.drawable.ic_baseline_add_location_24)
            .setContentIntent(pendingIntent)
            .setTicker("Tracker din lokasjon ...")
            .build();

        return notification;
    }

    private String createNotificationChannel(NotificationManager notificationManager){
        String channelId = "my_location_channelid";
        String channelName = "MyLocationService";
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        // omitted the LED color
        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = this.getApplicationContext();
        Toast.makeText(context, "Starter service", Toast.LENGTH_SHORT).show();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        Log.d("MY-LOCATION", location.toString());
                        //Lagrer i Room-database via ViewModel & Repository - klasser.
                        myLocationsRepository.insert(new MyLocation(location.getLongitude(), location.getLatitude(), location.getAltitude()));
                    }
                }

                // Viser/oppdaterer varsel:
                Notification notification = createNotification(locationResult.getLastLocation().toString());
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(LOCATION_NOTIFICATION_ID, notification);

            }
        };

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        final LocationRequest locationRequest = MainActivity.createLocationRequest();
        startLocationUpdates(locationRequest);

        //START_STICKY sørger for at onStartCommand() kjører ved omstart (dvs. når service terminerer og starter på nytt ved ressursbehov):
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("MY_SERVICE", "onDestroy(...)");
        this.stopLocationUpdates();
        Toast.makeText(this, "Stopper service", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Runnable som inneholder metoden som bakgrunnstråden starter:
    private Runnable doBackgroundThreadProcessing = new Runnable() {
        public void run() {
            backgroundThreadProcessing();
        }
    };

    // Metoden som kjører i tråden.
    private void backgroundThreadProcessing() {
        //[ ... Tidskrevende kode ... ]
        int res = 0;
        for (int i = 0; i < 10; i++) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.post(doShowResult);

        //Avslutt service når "oppdraget" er ferdig!
        this.stopSelf();
    }

    private Runnable doShowResult = new Runnable() {
        public void run() {
            showResult();
        }
    };

    private void showResult() {
        // Viser en Toast - kan kun fremvises i GUI-tråden:
        Context context = this.getApplicationContext();
        Toast.makeText(context, "Oppdrag fullført!!!!", Toast.LENGTH_SHORT).show();

        //Send beskjed til Activity vha. en broadcast / BroadcastReceiver.
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates(LocationRequest locationRequest) {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */);
    }

    private void stopLocationUpdates() {
        if (fusedLocationClient != null)
            fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
