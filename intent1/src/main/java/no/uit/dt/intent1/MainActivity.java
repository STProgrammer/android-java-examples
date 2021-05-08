package no.uit.dt.intent1;

import android.Manifest;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Disse brukes til å sjekke rettigheter og ev. spørre bruker om lov til å ringe, se R.id.action_call...
    private static final int CALLBACK_REQUEST_CALL_PHONE = 1;
    private static String[] requiredCallPhonePermission = {
        Manifest.permission.CALL_PHONE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppBar / ActionBar:
        Toolbar myToolbar = findViewById(R.id.toolbarMain);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setNavigationIcon(R.drawable.ic_action_pallette);
        this.setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_other:
                Intent intent = new Intent(this, MyOtherActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("EkstraInfo", "Sender med en subtittel...");
                startActivity(intent);
                break;

            case R.id.action_webBrowser:
                IntentsUtils.invokeWebBrowser(this);
                break;

            case R.id.action_WebSearch:
                IntentsUtils.invokeWebSearch(this);
                break;

            case R.id.action_dial:
                IntentsUtils.dial(this);
                break;

            case R.id.action_call:
                // NB! Krever permission/tillatelse fra bruker:
                this.verifyCallPhonePermission();
                break;

            case R.id.action_showMap:
                IntentsUtils.showMapAtLatLong(this);
                break;

            case R.id.action_setAlarm:
                IntentsUtils.setAlarm(this);
                break;

            case R.id.action_settings:
                return true;

            case R.id.action_end:
                this.finish();
                break;

            case R.id.action_myimplicit:
                IntentsUtils.startMyImplicitActivity(this);
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    /**
     * SE: http://developer.android.com/training/permissions/requesting.html#handle-response
     * TIPS: sørg for å "wipe" emulatoren før kjøring (fikk problemer med rettigheter før jeg wipa emulatoren (gjøres fra AVD Manager).
     * Sjekker om appen har lov til å ringe direkte (CALL_PHONE).
     * Hvis ikke vil brukeren bli spurt om tillatelse.
     */
    public void verifyCallPhonePermission() {
        // Kontrollerer om vi har tilgang:
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Dersom vi ikke har nøfvendige tilganger spør bruker om tilgang.
            // Fortsetter i metoden onRequestPermissionsResult()
            ActivityCompat.requestPermissions(this, requiredCallPhonePermission, CALLBACK_REQUEST_CALL_PHONE);
        } else {
            //Ringer dersom tillatelse allerede gitt:
            IntentsUtils.call(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CALLBACK_REQUEST_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED ) {
                    //Ringer dersom tillatelse gitt:
                    IntentsUtils.call(this);
                }
                return;

            default:
                Toast.makeText(this, "Feil ... mangler rettigheter til å ringe direkte.", Toast.LENGTH_SHORT).show();
        }
    }
}
