package no.uit.dt.intent1;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class MyOtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_other);

        //AppBar / ActionBar:
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Henter intent-objektet som ble brukt til å starte denne aktiviteten:
        Intent intent = getIntent();
        String ekstraInfo = intent.getStringExtra("EkstraInfo");
        if (ekstraInfo==null)
            ekstraInfo="Det kom ingen ekstrainfo med Intent-objektet..";
        getSupportActionBar().setSubtitle(ekstraInfo);

        //Vis tilbakeknapp (må med når man bruker et Apptheme som arver fra ....NoActionBar):
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setHomeAsUpIndicator(android.R.drawable.ic_menu_revert);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sender mail ...", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

}
