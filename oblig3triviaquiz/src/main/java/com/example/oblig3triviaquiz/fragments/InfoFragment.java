package com.example.oblig3triviaquiz.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.oblig3triviaquiz.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends DialogFragment {

    public static InfoFragment newInstance() {

        // Oppretter en MyDialogFragment1-instans:
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        Button btnOk = (Button)view.findViewById(R.id.info_fragment_button);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoFragment.this.getDialog().cancel();
            }
        });

        //Henter referanse til DialogFragmentets Dialog-objekt:
        Dialog dialog = this.getDialog();
        dialog.setTitle(R.string.info_fragment_title);
        return view;
    }
}