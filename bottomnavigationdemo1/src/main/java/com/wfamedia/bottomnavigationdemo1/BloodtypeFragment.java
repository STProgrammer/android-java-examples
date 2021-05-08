package com.wfamedia.bottomnavigationdemo1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BloodtypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BloodtypeFragment extends Fragment {

    public BloodtypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BloodtypeFragment.
     */
    public static BloodtypeFragment newInstance() {
        BloodtypeFragment fragment = new BloodtypeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bloodtype, container, false);
    }
}