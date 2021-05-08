package dt.hin.no;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Enkel Service-klasse som vil fungere som en background service - med sine begrensninger.
 * Se https://developer.android.com/about/versions/oreo/background
 */
public class MyBackgroundService extends Service {
	private static final String MY_TAG = "BACKGROUND_SERVICE";

	@Override
	public void onCreate() { 
        Log.d(MY_TAG, "onCreate()");
	}
	
	@Override
	public IBinder onBind(Intent arg0) {  
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Context context = this.getApplicationContext();
		
		Toast.makeText(context, "Starter service", Toast.LENGTH_SHORT).show();
		Log.d(MY_TAG, "onStartCommand(...)");
	
		// Her starter man typisk en tråd...
		// Når tråden har fullført melder man fra vha. Toast, NotificationManager,
		// starte en ny Activity eller sende en kringkasting.
		// og stopper servicen.
		
		// START_STICKY sørger for at onStartCommand() kjører ved omstart
		// (dvs. når service terminerer, pga. ressursbehov, og automatisk starter på nytt):
		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(MY_TAG, "onDestroy(...)");
		Context context = this.getApplicationContext();
		Toast.makeText(context, "Stopper service", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
}
