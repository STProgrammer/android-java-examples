package no.uit.dt.androiddel2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Demonstrerer bruk av FLAG_ACTIVITY_CLEAR_TOP.
 *
 * NB! *** SE ActivityD ***
 *
 */
public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        int tall = -1;

        Intent intent = getIntent(); //.getIntExtra("Tall", -1);
        if (intent!=null) {
            tall = intent.getIntExtra("Tall", -1);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int tall = intent.getIntExtra("Tall", -1);
    }

    public void startNeste(View view) {
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }
}
