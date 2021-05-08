package com.wfamedia.viewpager2utstyr0;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UtstyrSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UtstyrSlidePageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private int position;

    private MyViewModel myViewModel;

    public UtstyrSlidePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment UtstyrSlidePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UtstyrSlidePageFragment newInstance(int position) {
        UtstyrSlidePageFragment fragment = new UtstyrSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            this.position = getArguments().getInt(ARG_PARAM1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_utstyr_slide_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.getLabUtstyr().observe(getViewLifecycleOwner(), labUtstyr -> {
            // Henter utstyr for gjeldende posisjon:
            Utstyr utstyr = labUtstyr.get(this.position);

            // Viser navn på gjeldende utstyr:
            TextView tvProdusent = view.findViewById(R.id.tvProdusent);
            tvProdusent.setText(utstyr.getProdusent());

            // Viser bilde for gjeldende utstyr:
            ImageView ivBilde = view.findViewById(R.id.ivBilde);
            Glide
                .with(this)
                .load(utstyr.getBildeUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_search_24)
                .error(R.drawable.ic_baseline_report_gmailerrorred_24)
                .into(ivBilde);
        });
    }
}