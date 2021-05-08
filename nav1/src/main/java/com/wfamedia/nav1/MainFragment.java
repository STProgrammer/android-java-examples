package com.wfamedia.nav1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // *** Med bruk av destination_id:
        // Trenger ikke å ha "tegnet" noen pil (action) mellom fragmentene i grafen:
        Button btnBlue = view.findViewById(R.id.btnNext);
        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.usersFragment);
            }
        });

        // *** Med bruk av Action:
        Button btnBlueAction = view.findViewById(R.id.btnNextAction);
        btnBlueAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);

                // Enten direkte bruk av Action-id:
                //navController.navigate(R.id.actionMainToUsers);

                // ... eller bruk av Actions og SafeArgs-generert klasse:
                /*
                NavDirections actionMainToUsers = MainFragmentDirections.actionMainToUsers();
                navController.navigate(actionMainToUsers);
                */

                // ... eller bruk av Actions og SafeArgs-generert klasse OG parameter/argument:
                // Argeumenter må være definert i MOTTAKER-fragmentet, under Arguments (i grafen):
                MainFragmentDirections.ActionMainToUsers actionMainToUsers1 = MainFragmentDirections.actionMainToUsers();
                actionMainToUsers1.setSessionId("97siduhg98y9*@s35#");
                navController.navigate(actionMainToUsers1);
            }
        });
        /* ev.
        btnBlueAction.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.actionRedToBlue, null)
        );
         */
    }
}