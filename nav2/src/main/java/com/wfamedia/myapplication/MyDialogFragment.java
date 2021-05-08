package com.wfamedia.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * Created by Werner on 07.04.2021.
 *
 * NB!  Arver fra DialogFragment
 *      Bruker onCreateView
 */

public class MyDialogFragment extends DialogFragment {

    /* SEND I STEDET ARGUMENTER VHA. SafeArgs
    private static String CURRENT_TIME = "CURRENT_TIME";
    public static MyDialogFragment newInstance(String dialogText) {

        // Oppretter en MyDialogFragment1-instans:
        MyDialogFragment fragment = new MyDialogFragment();

        // Knytter et argument til instansen/objektet vha. setArguments():
        Bundle args = new Bundle();
        args.putString("DIALOG_TEXT", dialogText);
        fragment.setArguments(args);

        return fragment;
    }
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdialog, container, false);

        // Henter SafeArgs-argumenter:
        String dialogTitle = MyDialogFragmentArgs.fromBundle(getArguments()).getDialogTitle();
        String dialogText = MyDialogFragmentArgs.fromBundle(getArguments()).getDialogText();

        //Endrer innhold i dialogen:
        TextView tvText = (TextView)view.findViewById(R.id.tvText);
        tvText.setText(dialogText);

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(dialogTitle);

        Button btnOk = (Button)view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyDialogFragment.this.getActivity(), "Du trykket OK!!!", Toast.LENGTH_SHORT).show();
                MyDialogFragment.this.getDialog().cancel();
            }
        });

        //Henter referanse til DialogFragmentets Dialog-objekt:
        Dialog dialog = this.getDialog();
        dialog.setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
