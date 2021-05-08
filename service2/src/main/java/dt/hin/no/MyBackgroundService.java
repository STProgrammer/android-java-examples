package dt.hin.no;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * Enkel Service-klasse som vil fungere som en background service - med sine begrensninger.
 * Se https://developer.android.com/about/versions/oreo/background
 */
public class MyBackgroundService extends Service {
    private static final String MY_TAG = "BACKGROUND_SERVICE";

	@Override
	public void onCreate() {

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
		MyAsyncTask myAsyncTask = new MyAsyncTask(this.getApplicationContext());
        myAsyncTask.execute();

		//START_STICKY intent = null ved omstart (dvs. når service terminerer og starter på nytt ved ressursbehov):
		return Service.START_STICKY;
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
            for (int i = 0; i < 12; i++) {
                res += 1.0;
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
            Log.d(MY_TAG, "Resultat av asynctask = " + result.intValue());
            //Viser en toast...
            Toast.makeText(context, "Resultat av asynctask = " + result.intValue(), Toast.LENGTH_LONG).show();
            super.onPostExecute(result);

            //... og sender Broadcast:
            Intent intent = new Intent(MyServiceActivity.MY_INTENT_FILTER);
            intent.putExtra("resultat", "Løsningen er " + result.intValue());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            //... og avslutter Service:
            MyBackgroundService.this.stopSelf();

            Log.d(MY_TAG, "Avslutter service!");
        }
    }
}
