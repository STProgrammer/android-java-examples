package no.hin.dt.lifecycledemo1;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String MY_TAG = "LIFECYCLEDEMO";

    private TextView tvCounter;
    private int minTeller = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        if (savedInstanceState!=null)
            minTeller = savedInstanceState.getInt("minTeller");
        */

        Log.d(MY_TAG, "MainActivity: onCreate()");

        Button myButton = (Button)findViewById(R.id.myButton);
        myButton.setOnClickListener(this);

        tvCounter = (TextView)findViewById(R.id.tvCounter);
        tvCounter.setText(String.valueOf(minTeller));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("minTeller", minTeller);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        minTeller = savedInstanceState.getInt("minTeller");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MY_TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MY_TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MY_TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MY_TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MY_TAG, "MainActivity: onDestroy()");
    }

    public void doIncrement(View view) {
        minTeller++;
        tvCounter.setText(String.valueOf(minTeller));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myButton:
                //Starter OtherActivity:
                Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                startActivity(intent);
                break;

        }
    }
}
