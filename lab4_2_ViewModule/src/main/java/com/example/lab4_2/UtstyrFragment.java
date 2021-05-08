package com.example.lab4_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtstyrFragment extends Fragment {

    MyViewModel myViewModel;
    private int position;

    public UtstyrFragment() {
    }

    public static UtstyrFragment newInstance(int position) {
        UtstyrFragment fragment = new UtstyrFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.getLabUtstyr().observe(getViewLifecycleOwner(), labUtstyr -> {
            Utstyr utstyr = labUtstyr.getUtstyr(position);
            TextView tvUtstyrnavn = view.findViewById(R.id.tvUtsyrnavn);
            tvUtstyrnavn.setText(utstyr.getType() + " " + utstyr.getProdusent() + " " + utstyr.getModell());

            TextView tvUtlaant = view.findViewById(R.id.tvUtlaant);
            String utlaant = utstyr.getStatus() == 'U' ? "Utlånt til:" + utstyr.getUtlaantTil():"tilgjengelig til lån";
            tvUtlaant.setText(utlaant);

            TextView tvDato = view.findViewById(R.id.tvDato);
            Date date = new Date(); date.setTime(utstyr.getInnkjoept());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
            String dateText = simpleDateFormat.format(date);

            tvDato.setText("Innkjøpt:" + dateText);

            ImageView ivBilde = view.findViewById(R.id.imageView);
            Glide
                    .with(this)
                    .load(utstyr.getBildeUrl())
                    .centerCrop()
                    .into(ivBilde);
        });




    }
}