package com.example.instancestatedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
    Eksempel på bruk av onSaveInstanceState og onRestoreInstanceState.

    Eksemplet viser også bruk av Timer og scheduleAtFixedRate. Denne metoden kjører hvert sekund OG i sin egen tråd. Vi bør ikke oppdatere/endre
    UI-komponenter direkte fra en annen tråd (gir CalledFromWrongThreadException).
    Vi må i stedet få UI/hoved-tråden til å gjøre oppdateringa.
    Dette kan gjøres på flere måter - her er det vist to ulike måter;
    1) vha. Handler/mainHandler eller
    2) this.runOnUiThread(...).
    Begge gjør det samme operasjon. Activity.runOnUiThread(...) gjør dette enklere (mindre kode).
 */
public class MainActivity extends AppCompatActivity {
    public static final int MAX_TIME = 60;

    private long elapsedSeconds=0;
    private Timer timer;
    private TextView tvElapsedTime;

    private long counter=0;
    private Button btnCountUp;
    private Button btnCountDown;
    private TextView tvCounter;

    // Create a handler that associated with Looper of the main thread
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Oppretter et Handler-objekt som vi bruker til å gi UI-tråden beskjed om å oppdatere et View.
        // DETALJER:Dette objektet må koples til hovedtrådens "Looper"-objekt. Hovedtråden har et Looper- og et MessageQueue-objekt.
        //          For å kunnne oppdater et View fra en annen tråd må vi opprette et Runnable-objekt som sendes til meldingskøen.
        mainHandler = new Handler(Looper.getMainLooper());

        setContentView(R.layout.activity_main);

        tvElapsedTime = findViewById(R.id.tvElapsedTime);

        btnCountUp = this.findViewById(R.id.btnCountUp);
        btnCountUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.increase();
            }
        });

        btnCountDown = this.findViewById(R.id.btnCountDown);
        btnCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        tvCounter = this.findViewById(R.id.tvCounter);

        TextView btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        TextView btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });



        // Alternativt til onRestoreInstanceState():
        /*
        if (savedInstanceState!=null) {
            elapsedSeconds = savedInstanceState.getLong("elapsedSeconds");
            startTimer(null);
        }

         */
    }

    public void startTimer() {
        timer = new Timer();
        try {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                        elapsedSeconds++;

                        // Sender en Runnable til hovedtrådens MessageQueue:
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Kode som vil bli kjørt av hovedtråden:
                                tvElapsedTime.setText(String.valueOf(elapsedSeconds));
                            }
                        });

                        // Alternativ til mainHandler.post:
                        /*
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvElapsedTime.setText(String.valueOf(elapsedSeconds));
                            }
                        });
                         */
                }
            }, 0, 1000);

        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    public void stopTimer() {
        elapsedSeconds = 0;
        tvElapsedTime.setText("0");
        timer.cancel();
        timer.purge();
    }

    private void increase() {
        this.counter++;
        tvCounter.setText(String.valueOf(counter));
    }

    private void decrease() {
        this.counter--;
        tvCounter.setText(String.valueOf(counter));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("elapsedSeconds", elapsedSeconds);
        outState.putLong("counter", counter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        elapsedSeconds = savedInstanceState.getLong("elapsedSeconds");
        counter = savedInstanceState.getLong("counter");
        tvCounter.setText(String.valueOf(counter));
        startTimer();
    }
}
