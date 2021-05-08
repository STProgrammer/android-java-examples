package no.uit.dt.androiddel2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
    }

    public void tilbake(View view) {
        Intent intent = new Intent(this, ActivityA.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);    //<== NB!
        intent.putExtra("Tall", 42);
        startActivity(intent);
    }
}
/**
 * FLAG_ACTIVITY_CLEAR_TOP
 * Anta at A startet B som startet C som startet D. Fra D (her) ønsker vi å gå tilbake til A.
 * Må da sørge for at C og B avsluttes.
 * Ved å bruke FLAG_ACTIVITY_CLEAR_TOP i Intent-objektet, som brukes til å starte A, vil nettop dette skje, og A blir aktiv.
 *
 * FLAG_ACTIVITY_SINGLE_TOP
 * Gjør at A ikke starter på nytt dersom den kjører. Intent leveres da via onNewIntent() i A.
 *
 * "If set, the activity will not be launched if it is already running at the top of the history stack."
 */
