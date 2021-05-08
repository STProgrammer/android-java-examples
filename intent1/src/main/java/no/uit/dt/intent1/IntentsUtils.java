package no.uit.dt.intent1;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.AlarmClock;
import android.widget.Toast;

/**
 * FRA: Pro Android 5. D. MacLean m.fl.
 *
 * Her brukes implistte intents - systemet finner selv en passende app/aktivitet:
 */
public class IntentsUtils {

    public static void invokeWebBrowser(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.google.com"));
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException enfe) {
            Toast.makeText(activity, "Finner ingen nettleser på enheten.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void invokeWebSearch(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.setData(Uri.parse("http://www.google.com"));
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException enfe) {
            Toast.makeText(activity, "Websøk støttes ikke av enheten.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void dial(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException enfe) {
            Toast.makeText(activity, "Ringing støttes ikke av enheten.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void call(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:555–555–5555"));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "Mangler rettigheter ...", Toast.LENGTH_SHORT).show();
        } else {
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException enfe) {
                Toast.makeText(activity, "Kan ikke ringe fra enheten.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void showMapAtLatLong(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//geo:lat,long?z=zoomlevel&q=question-string
        intent.setData(Uri.parse("geo:0,0?z=4&q=business+near+city"));
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException enfe) {
            Toast.makeText(activity, "Kan ikke vise kart på enheten.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setAlarm(Activity activity) {
        String message = "Stå opp!!";
        int hour=10;
        int minutes=46;

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);

        // Implistt intent - systemet finner selv en passende app/aktivitet:
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, "Finner ingen alarm-app ...", Toast.LENGTH_SHORT).show();
        }
    }

    public static void startMyImplicitActivity(Activity activity) {
        Intent myIntent = new Intent("dt.uit.no.IMPLISITT_AKTIVITET_ACTION");
        myIntent.putExtra("SubTitle", "Min egen undertittel ...");
        activity.startActivity(myIntent);

    }
}
