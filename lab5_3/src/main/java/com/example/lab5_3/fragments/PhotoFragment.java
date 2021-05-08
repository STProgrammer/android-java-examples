package com.example.lab5_3.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lab5_3.viewmodel.UserViewModel;
import com.example.lab8_2_room_albums.R;
import com.example.lab8_2_room_albums.databinding.FragmentPhotoBinding;
import com.example.lab5_3.models.Photo;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private int photoId;
    private FragmentPhotoBinding fragmentPhotoBinding;
    private boolean mIsFirstTime;

    private UserViewModel userViewModel;

    public PhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param photoId Parameter 1.
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(Integer photoId) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, photoId);
        fragment.setArguments(args);
        fragment.setPhotoId(photoId);
        return fragment;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState==null) {
            mIsFirstTime = true;
        }

        if (getArguments() != null) {
            photoId = getArguments().getInt(ARG_PARAM1);
        }

        // 1. Initialiserer ViewModel-objektet:
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // 2. Lager et Observe-objekt:
        final Observer<Photo> photoObservator = new Observer<Photo>() {
            @Override
            public void onChanged(@Nullable final Photo myPhoto) {
                Glide
                        .with(requireContext())
                        .load(myPhoto.getThumbnailUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_search_24)
                        .error(R.drawable.ic_baseline_report_gmailerrorred_24)
                        .into(fragmentPhotoBinding.singlePhoto);
                fragmentPhotoBinding.progressWork.setVisibility(View.GONE);
            }
        };
        // 3. Start observering:
        userViewModel.getPhoto(photoId, mIsFirstTime).observe(this, photoObservator);


        final Observer<Integer> progressObservator = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer intValue) {
                fragmentPhotoBinding.errorTexts.setVisibility(intValue);
            }
        };
        userViewModel.getErrorPhoto().observe(this, progressObservator);


        final Observer<String> errorObservator = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String msg) {
                fragmentPhotoBinding.errorTexts.setText(msg);
                //  userAdapter.setClickListener(null);
            }
        };
        userViewModel.getErrorMessage().observe(this, errorObservator);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        fragmentPhotoBinding = FragmentPhotoBinding.inflate(inflater, container, false);
        return fragmentPhotoBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentPhotoBinding.progressWork.setVisibility(View.VISIBLE);
    }
}