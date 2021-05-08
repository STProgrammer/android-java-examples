package dt.hin.no;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

//Demonstrerer enkel bruk av AsyncTask.
//Tar ikke hensyn til skjermrotasjon / konfigurasjonsendringer.
//Ved rotasjon vil AsyncTask-objektet miste referansen til aktiviteten.
public class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
	private Context context = null;
	public MyAsyncTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		// Tre punktumer bak String indikerer at metoden kan ta 0 eller flere strenger evt. et string-array
		//Viser parametrene i loggen_
		try {
			Log.d("MYASYNCTASK", params[0]);
			Log.d("MYASYNCTASK", params[1]);
			Log.d("MYASYNCTASK", params[2]);
		} catch (Exception e) {
			Log.d("MYASYNCTASK", "Mindre enn 3 parametre...");
		}
		// Her gjør man det som evt. tar tid, dvs. laste ned fra nett m.m.
		int second=0;
		for (; second<10; second++) {
			SystemClock.sleep(1000);
			//NB!!
			this.publishProgress(second);
			Log.d("MY_ASYNC_TASK", String.valueOf(second));
		}
		// Det som returneres herfra kommer som parameter til onPostExecute();
		return second;
	}

	//Kjører som ved kall på publishProgress(), se over.
	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		TextView textView = ((Activity)context).findViewById(R.id.tvProgress);
		textView.setText(String.valueOf(values[0]));
		Log.d("TELLER", String.valueOf(values[0]));
	}
	
	//Kjører når doInBackground() er ferdig/returnerer.
	//Det som doInBackground() returnerer er argument (result) til onPostExecute():
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		// Oppdaterer GUI med svaret: Bruk f.eks. Toast, Activity, Dialog, notification e.l.
		Toast.makeText(context, "Resultat av asynctask = " + result.intValue(), Toast.LENGTH_LONG).show();
		TextView textView = (TextView)((Activity)context).findViewById(R.id.tvProgress);
		textView.setText("");
	}
}
