package dt.uit.no;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

class MyReceiver extends BroadcastReceiver {

    public final static String EKSTRA_DATO = "EKSTRA_DATO";
    public final static String EKSTRA_ANTALL = "EKSTRA_ANTALL";

    public static final String ACTION_VIS = "dt.uit.no.VIS";
    public static final String VIKITG_HENDELSE = "dt.uit.no.VIKTIG_HENDELSE";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the lifeform details from the intent.
        //Uri data = intent.getData();

        int antall = intent.getIntExtra(EKSTRA_ANTALL, 0);
        String dato = intent.getStringExtra(EKSTRA_DATO);

        Toast.makeText(context, "Melding fra kringkastingssender, antall=" + Integer.toString(antall), Toast.LENGTH_SHORT).show();

        // Starter aktivitet EKSPLISITT (bruk av klassenavn):
        Intent startIntent = new Intent(context, VisActivity.class);
        startIntent.putExtra(EKSTRA_DATO, dato);
        startIntent.putExtra(EKSTRA_ANTALL, antall);
        //NB, bruk av context!!
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(startIntent);

        // Starter aktivitet IMPLISITT (bruk av Action-navn):
        /*
        Intent startIntent1 = new Intent(ACTION_VIS);
        startIntent1.putExtra(EKSTRA_DATO, dato);
        startIntent1.putExtra(EKSTRA_ANTALL, antall);
        //NB!!
        startIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(startIntent1);
        */
    }
}
