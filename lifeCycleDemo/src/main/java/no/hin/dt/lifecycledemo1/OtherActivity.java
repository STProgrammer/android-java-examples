package no.hin.dt.lifecycledemo1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class OtherActivity extends AppCompatActivity {

    private final String MY_TAG = "LIFECYCLEDEMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Log.d(MY_TAG, "OtherActivity: onCreate()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MY_TAG, "OtherActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MY_TAG, "OtherActivity: onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MY_TAG, "OtherActivity: onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MY_TAG, "OtherActivity: onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MY_TAG, "OtherActivity: onStart()");
    }

    //Andre metoder:
    public void doClose(View view) {
        this.finish();

    }
}
