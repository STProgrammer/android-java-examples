package dt.hin.no;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Arver fra Service og settes til foreground service i onCreate()
 * Man må knytte en Notification til denne.
 */
public class MyForegroundService extends Service {
	private static final int LOCATION_NOTIFICATION_ID = 1010;
	private static final String MY_TAG = "FOREGROUND_SERVICE";
	private NotificationManager notificationManager;
	private String channelId;

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
		Notification notification = createNotification("Min Service");

		// NB! Sørger for å sette servicen denne til en forgruns-service:
		startForeground(LOCATION_NOTIFICATION_ID, notification);
	}

	@Override
	public IBinder onBind(Intent arg0) {  
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Context context = this.getApplicationContext();
		
		Toast.makeText(context, "Starter service", Toast.LENGTH_SHORT).show();
		// Starter jobben som egen tråd:
		MyAsyncTask myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute();
        
		//START_STICKY intent = null ved omstart (dvs. når service terminerer og starter på nytt ved ressursbehov):
		return Service.START_STICKY;
	}

	// Varsel:
	private Notification createNotification(String notificationText){
		Intent notificationIntent = new Intent(this, MyActivity.class);
		PendingIntent pendingIntent =
				PendingIntent.getActivity(this, 0, notificationIntent, 0);
		// The PendingIntent to launch our activity if the user selects this notification
		Notification notification =  new Notification.Builder(this, channelId)
				.setContentTitle("Service i forgrunnen")
				.setOnlyAlertOnce(false)
				.setContentText("Varsel: " + notificationText)
				.setSmallIcon(R.drawable.ic_baseline_add_location_24)
				.setContentIntent(pendingIntent)
				.setTicker("Kjører Service i forgrunnen ...")
				.build();

		return notification;
	}

	private String createNotificationChannel(NotificationManager notificationManager){
		String channelId = "my_channelid";
		String channelName = "MyTestService";
		NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
		channel.setImportance(NotificationManager.IMPORTANCE_NONE);
		channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
		notificationManager.createNotificationChannel(channel);
		return channelId;
	}

	@Override
	public void onDestroy() {
		Log.d(MY_TAG, "onDestroy(...)");
		Context context = this.getApplicationContext();
		Toast.makeText(context, "Stopper service", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	// AsyncTask: Gjør jobben - avslutter service når ferdig.
	public class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
		private Context context = null;
		public MyAsyncTask(Context context) {
			this.context = context;
		}

		@Override
		protected Integer doInBackground(String... params) {
			// Her gjør man det som evt. tar tid, dvs. laste ned fra nett m.m.
			 int res = 0;
			for (int i = 0; i < 5; i++) {
				res += 1.0 * 10;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//Trigger onProgressUpdate:
				publishProgress(new Integer(res));
			}

			// Det som returneres herfra kommer som parameter til onPostExecute();
			return new Integer(res);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// [... Update progress bar, Notification, or other UI element ...]
			Log.d(MY_TAG, "TELLER: " + String.valueOf(values[0]));
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Integer result) {
			//Viser en toast...
			Toast.makeText(context, "Resultat av asynctask = " + result.intValue(), Toast.LENGTH_LONG).show();
			super.onPostExecute(result);

			//... og sender Broadcast, fanges opp dersom aktivitet er aktiv (overses ellers):
			Intent intent = new Intent(MyActivity.MY_INTENT_FILTER);
			intent.putExtra("resultat", "Løsningen er " + result.intValue());
			LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

			//... og avslutter Service:
			MyForegroundService.this.stopSelf();

		}
	}
}
