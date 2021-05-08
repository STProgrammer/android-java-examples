package no.hin.dt.dialogtest.fragments;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import no.hin.dt.dialogtest.R;

/**
 * Created by Werner on 30.03.2017.
 *
 * NB!  Arver fra DialogFragment
 *      Bruker onCreateView
 */

public class MyDialogFragment2 extends DialogFragment {
    private static String CURRENT_TIME = "CURRENT_TIME";

    public static MyDialogFragment2 newInstance(String dialogText) {

        // Oppretter en MyDialogFragment1-instans:
        MyDialogFragment2 fragment = new MyDialogFragment2();

        // Knytter et argument til instansen/objektet vha. setArguments():
        Bundle args = new Bundle();
        args.putString("DIALOG_TEXT", dialogText);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdialog2, container, false);

        //Endre på innhold i dialogen:
        TextView textView = (TextView)view.findViewById(R.id.tvText);
        textView.setText(getArguments().getString("DIALOG_TEXT")!=null ? getArguments().getString("DIALOG_TEXT") : "...");

        Button btnOk = (Button)view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyDialogFragment2.this.getActivity(), "Du trykket OK!!!", Toast.LENGTH_SHORT).show();
                MyDialogFragment2.this.getDialog().cancel();
            }
        });

        //Henter referanse til DialogFragmentets Dialog-objekt:
        Dialog dialog = this.getDialog();
        dialog.setTitle("Min Fragmentdialog");
        return view;
    }
}
