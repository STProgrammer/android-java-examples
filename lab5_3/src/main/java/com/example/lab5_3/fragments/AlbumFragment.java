package com.example.lab5_3.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_3.MainActivity;
import com.example.lab5_3.viewmodel.UserViewModel;
import com.example.lab5_3.adapters.AlbumAdapter;
import com.example.lab8_2_room_albums.databinding.FragmentAlbumBinding;
import com.example.lab5_3.models.Photo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int albumId;

    private AlbumAdapter albumAdapter;
    private FragmentAlbumBinding fragmentAlbumBinding;

    private boolean mIsFirstTime;

    protected List<Photo> myDataSet;

    private UserViewModel albumViewModel;

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * @param albumId Parameter 1.
     * @return A new instance of fragment AlbumFragment.
     */
    public static AlbumFragment newInstance(int albumId) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, albumId);
        fragment.setArguments(args);
        fragment.setAlbumId(albumId);
        return fragment;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState==null) {
            mIsFirstTime = true;
        }

        if (getArguments() != null) {
            this.albumId = getArguments().getInt(ARG_PARAM1);
        }

        // 1. Initialiserer ViewModel-objektet:
        albumViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // 2. Lager et Observe-objekt:
        final Observer<List<Photo>> photoObservator = new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable final List<Photo> allPhotos) {
                myDataSet = allPhotos;
                // Fyller recycler view-lista:
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                fragmentAlbumBinding.photosRecyclerView.setLayoutManager(layoutManager);
                albumAdapter = new AlbumAdapter(myDataSet);
                albumAdapter.setClickListener(new AlbumAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Integer photoId = myDataSet.get(position).getId();
                        // Fragmenttransaksjonen, dvs. bytte av fragment, gjøres her av aktiviteten:
                        PhotoFragment photoFragment = PhotoFragment.newInstance(photoId);
                        if (isAdded()) {
                            ((MainActivity)getActivity()).replaceFragmentWidth(photoFragment, true);
                        }
                    }
                });
                fragmentAlbumBinding.photosRecyclerView.setAdapter(albumAdapter);
                albumAdapter.setPhotosDataSet(allPhotos);
                albumAdapter.notifyDataSetChanged();
                fragmentAlbumBinding.progressWork.setVisibility(View.GONE);
            }
        };
        // 3. Start observering:
        albumViewModel.getAlbumData(albumId, mIsFirstTime).observe(this, photoObservator);


        final Observer<Integer> progressObservator = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer intValue) {
                fragmentAlbumBinding.errorTexts.setVisibility(intValue);
            }
        };
        albumViewModel.getErrorAlbum().observe(this, progressObservator);


        final Observer<String> errorObservator = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String msg) {
                fragmentAlbumBinding.errorTexts.setText(msg);
                //  userAdapter.setClickListener(null);
            }
        };
        albumViewModel.getErrorMessage().observe(this, errorObservator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_album, container, false);
        fragmentAlbumBinding = FragmentAlbumBinding.inflate(inflater, container, false);
        return fragmentAlbumBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentAlbumBinding.progressWork.setVisibility(View.VISIBLE);
    }
}