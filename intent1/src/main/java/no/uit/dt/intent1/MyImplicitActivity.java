package no.uit.dt.intent1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MyImplicitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_implicit);

        //AppBar / ActionBar:
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Henter intent-objektet som ble brukt til å starte denne aktiviteten:
        Intent intent = getIntent();
        String subTitle = "AppBar subtitle . . .";
        try {
            subTitle = intent.getStringExtra("SubTitle");
        } catch (Exception e) {
            // overser...
        }
        getSupportActionBar().setSubtitle(subTitle);

        //Vis tilbakeknapp (må med når man bruker et Apptheme som arver fra ....NoActionBar):
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
