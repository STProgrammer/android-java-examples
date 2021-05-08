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
import com.example.lab5_3.adapters.UserAdapter;
import com.example.lab8_2_room_albums.databinding.FragmentPhotoBinding;
import com.example.lab8_2_room_albums.databinding.FragmentUserBinding;
import com.example.lab8_2_room_albums.databinding.FragmentUsersListBinding;
import com.example.lab5_3.models.Album;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int userId;

    private UserAdapter userAdapter;
    private FragmentUserBinding fragmentUserBinding;

    private boolean mIsFirstTime;

    protected List<Album> myDataSet;

    private UserViewModel userViewModel;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * @param userId Parameter 1.
     * @return A new instance of fragment AlbumFragment.
     */
    public static UserFragment newInstance(int userId) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        fragment.setUserId(userId);
        return fragment;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState==null) {
            mIsFirstTime = true;
        }

        if (getArguments() != null) {
            this.userId = getArguments().getInt(ARG_PARAM1);
        }

        // 1. Initialiserer ViewModel-objektet:
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // 2. Lager et Observe-objekt:
        final Observer<List<Album>> albumObservator = new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable final List<Album> allAlbums) {
                myDataSet = allAlbums;
                // Fyller recycler view-lista:
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                fragmentUserBinding.albumsRecyclerView.setLayoutManager(layoutManager);
                userAdapter = new UserAdapter(myDataSet);
                userAdapter.setClickListener(new UserAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Integer albumId = myDataSet.get(position).getId();
                        // Fragmenttransaksjonen, dvs. bytte av fragment, gjøres her av aktiviteten:
                        AlbumFragment albumFragment = AlbumFragment.newInstance(albumId);
                        if (isAdded()) {
                            ((MainActivity)getActivity()).replaceFragmentWidth(albumFragment, true);
                        }
                    }
                });
                fragmentUserBinding.albumsRecyclerView.setAdapter(userAdapter);
                userAdapter.setAlbumsDataSet(allAlbums);
                userAdapter.notifyDataSetChanged();
                fragmentUserBinding.progressWork.setVisibility(View.GONE);

            }
        };
        // 3. Start observering:
        userViewModel.getUserData(userId, mIsFirstTime).observe(this, albumObservator);

        final Observer<Integer> progressObservator = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer intValue) {
                fragmentUserBinding.errorTexts.setVisibility(intValue);
            }
        };
        userViewModel.getErrorUser().observe(this, progressObservator);

        final Observer<String> errorObservator = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String msg) {
                fragmentUserBinding.errorTexts.setText(msg);
            }
        };
        userViewModel.getErrorMessage().observe(this, errorObservator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        fragmentUserBinding = FragmentUserBinding.inflate(inflater, container, false);
        return fragmentUserBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentUserBinding.progressWork.setVisibility(View.VISIBLE);
    }
}