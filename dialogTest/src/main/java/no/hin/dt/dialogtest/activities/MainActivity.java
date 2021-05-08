package no.hin.dt.dialogtest.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import no.hin.dt.dialogtest.R;
import no.hin.dt.dialogtest.fragments.DatePickerFragment;
import no.hin.dt.dialogtest.fragments.MyDialogFragment1;
import no.hin.dt.dialogtest.fragments.MyDialogFragment2;
import no.hin.dt.dialogtest.fragments.YesNoDialog;

public class MainActivity extends AppCompatActivity implements YesNoDialog.DialogYesNoListener {
    public final static int CALLBACK_YES_NO = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Viser en standard AlertDialog:
    public void onBtnAlertFinish(View view) {

        Context context = MainActivity.this;
        String title = "Bekreft";
        String message = "Ønsker du å avslutte?";
        String button1String = "Ja";
        String button2String = "Nei, gå tilbake!";

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle(title);
        ad.setMessage(message);

        ad.setPositiveButton(button1String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        MainActivity.this.finish();
                    }
                });

        ad.setNegativeButton(button2String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Toast.makeText(MainActivity.this, "fortsetter...", Toast.LENGTH_LONG).show();
                    }
                });

        ad.setCancelable(false);

        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });

        ad.show();
    }

    //Bruker DialogFragment som igjen bruker AlertDialog i onCreateDialog():
    public void onBtnDialogFragment1(View view) {
        String tag = "my_dialog";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        String dateString = dateFormat.format(cal.getTime());
        DialogFragment myFragment = MyDialogFragment1.newInstance(dateString);
        myFragment.show(getSupportFragmentManager(), tag);
    }

    //Bruker DialogFragment med egendefinert layout:
    public void onBtnDialogFragment2(View view) {
        String tag = "my_dialog";

        DialogFragment myFragment = MyDialogFragment2.newInstance("Trykk på knappen under!!");
        myFragment.show(getSupportFragmentManager(), tag);
    }

    //Bruker DialogFragment med egendefinert layout:
    public void onBtnDateFragmentDialog(View view) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onBtnYesNoDialog(View view) {
        DialogFragment yesNoDialog = new YesNoDialog();
        Bundle bundle = new Bundle();
        bundle.putString("dialogTitle", "Velg ja eller nei!");
        bundle.putString("dialogText", "Denne dialogen demonstrerer bruk av DialogFragment for å lage en egen ja/nei-dialog. Ønsker du å fortsette?");
        bundle.putString("yesButtonText", "Ja, fortsett!");
        bundle.putString("noButtonText", "Nei, avslutt!");
        bundle.putInt("callback_id", CALLBACK_YES_NO);
        bundle.putInt("icon_drawable", R.drawable.ic_action_tips_black);

        yesNoDialog.setArguments(bundle);
        FragmentManager fm = this.getSupportFragmentManager();
        yesNoDialog.show(fm, "CALLBACK_YES_NO");
    }

    public void onBtnShowDialogActivity(View view) {
        Intent intent = new Intent(this, MyDialogActivity.class);
        startActivity(intent);
    }

    //YES_NO-dialog callbacks:
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int callback_id) {
        switch (callback_id) {
            case CALLBACK_YES_NO:
                this.removeDialogFragment("CALLBACK_YES_NO");
                Toast.makeText(MainActivity.this, "Du trykket på positiv-knappen", Toast.LENGTH_LONG).show();
                break;

        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, int callback_id) {
        switch (callback_id) {
            case CALLBACK_YES_NO:
                this.removeDialogFragment("CALLBACK_YES_NO");
                Toast.makeText(MainActivity.this, "Du trykket på negativ-knappen", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onDialogCancel(DialogFragment dialog, int callback_id) {
        switch (callback_id) {
            case CALLBACK_YES_NO:
                this.removeDialogFragment("CALLBACK_YES_NO");
                Toast.makeText(MainActivity.this, "Du avbrøt!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void removeDialogFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag1 = fm.findFragmentByTag(tag);
        fm.beginTransaction().remove(frag1).commit();
    }

}
