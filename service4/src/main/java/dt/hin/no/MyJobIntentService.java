package dt.hin.no;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.util.Log;
import android.widget.Toast;

/**
 * Bruker JobIntentService.
 * Merk: Denne er også gjort til en forground service vha. startForeground()
 */
public class MyJobIntentService extends JobIntentService {
	private static final String MY_TAG = "JOBINTENT_FOREGROUND_SERVICE";
	private static final int LOCATION_NOTIFICATION_ID = 1010;
	private NotificationManager notificationManager;
	private String channelId;
	public static final int JOB_ID = 1000;	//Pga. JobIntentService

	//Bruker en Handler for å kunne vise resultatet i en Toast
	private Handler handler = new Handler();

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel(notificationManager) : "";
		Notification notification = createNotification("Min Service");

		// NB! Sørger for å sette servicen denne til en forgruns-service:
		startForeground(LOCATION_NOTIFICATION_ID, notification);
	}

	public static void enqueueWork(Context context, Intent intent) {
		enqueueWork(context, MyJobIntentService.class, JOB_ID, intent);
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

	//Her legger man "arbeidet" som skal gjøres. Kjører i egen tråd.
	//Avslutter når ferdig og ingen flere kall som må håndteres.
	@Override
	protected void onHandleWork(@NonNull Intent intent) {
		// Her "sover" vi i 5 sekunder for å simulere litt "arbeid":
		//endTime indikerer hvor lenge tråden skal "sove":
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Vis resutatet:
		handler.post(doShowResult);
	}

	//Kjøres av GUI-tråden, en Toast må fremvises av GUI-tråden:
	private Runnable doShowResult = new Runnable() {
		public void run() {
			Context context = MyJobIntentService.this.getApplicationContext();
			Toast.makeText(context, "Oppdrag fullført!!!!", Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	public void onDestroy() {
		Context context = this.getApplicationContext();
		Toast.makeText(context, "Avslutter service", Toast.LENGTH_LONG).show();
		Log.d(MY_TAG, "Avslutter service");
		//Fjerner notification:
		notificationManager.cancel(0); //.cancelAll();
		super.onDestroy();
	}

}
