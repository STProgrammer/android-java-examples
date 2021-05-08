package no.uit.dt.androiddel2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void startNeste(View view) {
        Intent intent = new Intent(this, ActivityC.class);
        startActivity(intent);
    }
}
