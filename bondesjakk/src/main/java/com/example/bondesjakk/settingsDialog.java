package com.example.bondesjakk;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class settingsDialog extends AppCompatDialogFragment {
    private settingsDialogListener listener;
    private Spinner spRows;
    private Spinner spColumns;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.settings_dialog, null);
        String string_apply = getString(R.string.settings_apply_button);
        String string_cancel = getString(R.string.settings_cancel_button);
        String string_title = getString(R.string.settings);
        builder.setTitle(string_title);
        spRows = view.findViewById(R.id.spRows);
        spColumns = view.findViewById(R.id.spColumns);
        builder.setNegativeButton(string_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton(string_apply, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int nrOfRows = Integer.parseInt(spRows.getSelectedItem().toString());
                        int nrOfColumns = Integer.parseInt(spColumns.getSelectedItem().toString());
                        listener.applySettings(nrOfRows, nrOfColumns);
                    }
                });
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (settingsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface settingsDialogListener {
        void applySettings(int nrOfRows, int nrOfColumns);
    }
}
