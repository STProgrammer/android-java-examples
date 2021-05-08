package no.hin.dt.dialogtest.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import no.hin.dt.dialogtest.R;

/**
 * Created by wfa on 23.03.2015.
 */
public class YesNoDialog extends DialogFragment {

    protected String dialogTitle ="";
    protected String dialogText = "";
    protected String yesButtonText = "";
    protected String noButtonText = "";
    protected int callback_id = 0;

    //Brukeren av dialogen må implementere dette:
    public interface DialogYesNoListener {
        void onDialogPositiveClick(DialogFragment dialog, int callback_id);
        void onDialogNegativeClick(DialogFragment dialog, int callback_id);
        void onDialogCancel(DialogFragment dialog, int callback_id);
    }

    protected DialogYesNoListener mListener;

    public YesNoDialog() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogYesNoListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement DialogYesNoListener");
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mListener.onDialogCancel(this, callback_id);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Dialogen lukkes om bruker trykker utenfor dialogen:
        this.setCancelable(true);

        //NB! Må settes av kaller:
        Bundle bundle = this.getArguments();
        this.dialogTitle = bundle.getString("dialogTitle");
        this.dialogText = bundle.getString("dialogText");
        this.yesButtonText = bundle.getString("yesButtonText");
        this.noButtonText = bundle.getString("noButtonText");
        this.callback_id = bundle.getInt("callback_id");
        int icon_drawable = bundle.getInt("icon_drawable");

        //Bruker en styla dialog (se styles.xml):
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this.getActivity(), R.style.AlertDialogCustom));
        builder.setTitle(this.dialogTitle);
        builder.setIcon(icon_drawable);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_yesno_dialog, null);

        TextView tvDialogText = (TextView) view.findViewById(R.id.tvDialogText);
        tvDialogText.setText(dialogText);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
            // Add action buttons
            .setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //Lagre at tipset er sett...
                    mListener.onDialogPositiveClick(YesNoDialog.this, callback_id);
                }
            })
            .setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onDialogNegativeClick(YesNoDialog.this, callback_id);
                }
            });

        //Opprett og vis dialogen:
        Dialog dialog = builder.create();
        return dialog;
    }
}
