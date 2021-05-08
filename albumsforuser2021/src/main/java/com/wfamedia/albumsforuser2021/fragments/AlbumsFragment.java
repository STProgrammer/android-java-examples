package com.wfamedia.albumsforuser2021.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wfamedia.albumsforuser2021.MainActivity;
import com.wfamedia.albumsforuser2021.R;
import com.wfamedia.albumsforuser2021.adapters.AlbumAdapter;
import com.wfamedia.albumsforuser2021.model.Album;
import com.wfamedia.albumsforuser2021.viewmodel.AlbumsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int userId;

    private RecyclerView albumsRecyclerView;
    private AlbumAdapter albumAdapter;
    protected List<Album> myDataset;

    private AlbumsViewModel albumsViewModel;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * @param userId Parameter 1.
     * @return A new instance of fragment AlbumsFragment.
     */
    public static AlbumsFragment newInstance(int userId) {
        AlbumsFragment fragment = new AlbumsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.userId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_albums, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumsViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);

        albumsViewModel.getAlbumsForUser(this.userId).observe(getViewLifecycleOwner(), allAlbums -> {

            this.myDataset = allAlbums;

            // Fyller recycler view-lista:
            albumsRecyclerView = view.findViewById(R.id.albumsRecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            albumsRecyclerView.setLayoutManager(layoutManager);
            albumAdapter = new AlbumAdapter(this.myDataset);
            albumAdapter.setClickListener(new AlbumAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.i("TAG", "Du klikke på " + albumAdapter.getItem(position).getTitle() + " som ligger i posisjon " + position);

                    Long albumIdlong = myDataset.get(position).getId();

                    // Fragmenttransaksjonen, dvs. bytte av fragment, gjøres her av aktiviteten:
                    DetailsFragment detailsFragment = DetailsFragment.newInstance(albumIdlong.intValue());
                    if (isAdded()) {
                        ((MainActivity)getActivity()).replaceFragmentWidth(detailsFragment, true);
                    }
                }
            });
            albumsRecyclerView.setAdapter(this.albumAdapter);
            albumAdapter.setLocalDataSet(allAlbums);
            albumAdapter.notifyDataSetChanged();
        });

    }
}