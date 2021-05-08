package dt.hin.no;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MyServiceActivity extends AppCompatActivity {
    public static final String MY_INTENT_FILTER = "dt.hin.no.MY_RESULTRECEIVER";

    private ComponentName service;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.main);
        
		//Knapper for MyBackgroundService:
		//Starter service:
		Button startButton = (Button) findViewById(R.id.btnStartService);
		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(MyServiceActivity.this, MyBackgroundService.class);
				service = startService(myIntent);
			}
		});
    }

    @Override
    protected void onDestroy() {
        //Sørger for å stoppe den UBUNDNE servicen (hvis ikke vil den fortsette å kjøre i bakgrunnen:
        //En BUNDET service stopper automatisk når aktiviteten avsluttes.
        stopService(new Intent(MyServiceActivity.this, MyBackgroundService.class));

        // TODO Auto-generated method stub
        super.onDestroy();
    }
}