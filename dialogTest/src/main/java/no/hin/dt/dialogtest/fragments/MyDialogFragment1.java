package no.hin.dt.dialogtest.fragments;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

/**
 * NB!  Arver fra DialogFragment
 *      Bruker onCreateDialog()
 */

public class MyDialogFragment1 extends DialogFragment {
    private static String CURRENT_TIME = "CURRENT_TIME";
    public static MyDialogFragment1 newInstance(String currentTime) {
        // Oppretter en MyDialogFragment1-instans:
        MyDialogFragment1 fragment = new MyDialogFragment1();
        Bundle args = new Bundle();
        args.putString("CURRENT_TIME", currentTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Bygg et AlertBuilder-objekt:
        AlertDialog.Builder timeDialog = new AlertDialog.Builder(getActivity());
        timeDialog.setTitle("Dato & klokkeslett");
        timeDialog.setMessage(getArguments().getString("CURRENT_TIME"));
        return timeDialog.create();
    }
}
