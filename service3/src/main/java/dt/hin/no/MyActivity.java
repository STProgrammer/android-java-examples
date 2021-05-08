package dt.hin.no;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 */
public class MyActivity extends Activity {
    public static final String MY_INTENT_FILTER = "dt.hin.no.service3.MY_RESULTRECEIVER";
    private TextView myTV;
    private IntentFilter filter = new IntentFilter(MY_INTENT_FILTER);
    private MyResultReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Instansiererer mottaker i koden. Registrering i onResume() , avregistrering i onPause() i stedet for i manifest-fila. Se under:
        //NB! Vi må registrere mottakeren i koden i stedet for i manifest-fila. Alternativt kan receiver-klassen være static (blir imidlertid vanskelig å aksessere instansvariabler i MyActivity):
        receiver = new MyResultReceiver();

        //Henter referanse:
        myTV = (TextView) findViewById(R.id.tvResult);

        //Starter Service:
        Button startService = (Button) findViewById(R.id.btnStartService);
        startService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Sender med appcontext:
                Intent myIntent = new Intent(MyActivity.this, MyForegroundService.class);
                startForegroundService(myIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register the broadcast receiver.
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        // Unregister the receiver
        if (receiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onPause();
    }

    //Lager en intern BroadCastReceiver:
    public class MyResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String result = intent.getStringExtra("resultat");
            MyActivity.this.myTV.setText(result);
        }
    }
}