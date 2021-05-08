package com.wfamedia.fragment3_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class InfoFragment extends Fragment {
    private MyViewModel myViewModel;

    public InfoFragment() {
        super(R.layout.info_fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.getSelection().observe(getViewLifecycleOwner(), infoText -> {
            TextView textViewInfo = (TextView)getActivity().findViewById(R.id.textViewInfo);
            textViewInfo.setText(infoText);
        });

        myViewModel.getTexteColor().observe(getViewLifecycleOwner(), color -> {
            TextView textViewInfo = requireActivity().findViewById(R.id.textViewInfo);
            textViewInfo.setTextColor(getResources().getColor(color));

            TextView tvView = requireActivity().findViewById(R.id.textView);
            tvView.setTextColor(getResources().getColor(color));
        });

        //NB! Merk bruk av view.findViewById(...);
        Button btnReset = view.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setText("");
                myViewModel.setTextColor(R.color.myblack);
                myViewModel.resetCheckboxSelections(true);
                myViewModel.resetColorSelection(true);
            }
        });
    }
}
