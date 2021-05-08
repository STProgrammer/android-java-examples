package com.wfamedia.albumsforuser2021utvidet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wfamedia.albumsforuser2021utvidet.MainActivity;
import com.wfamedia.albumsforuser2021utvidet.R;
import com.wfamedia.albumsforuser2021utvidet.adapters.AlbumAdapter;
import com.wfamedia.albumsforuser2021utvidet.model.Album;
import com.wfamedia.albumsforuser2021utvidet.viewmodel.AlbumsViewModel;

import java.util.List;

/**
 *  Viser hvordan man kan kombinere Activity-meny med fragmentmenyen.
 *  Se onCreateOptionsMenu() og onOptionsItemSelected() både her og i MainActivity.
 *  Legg også merke til setHasOptionsMenu(true) i onCreate().
 *
 */
public class AlbumsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int userId;

    private ProgressBar progressBar;

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
        fragment.setUserId(userId);
        return fragment;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Initialiserer ViewModel-objektet:
        albumsViewModel = new ViewModelProvider(requireActivity()).get(AlbumsViewModel.class);

        // 2. Lager et Observe-objekt:
        final Observer<List<Album>> albumObservatør = new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable final List<Album> allAlbums) {
                myDataset = allAlbums;
                // Fyller recycler view-lista:
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                albumsRecyclerView.setLayoutManager(layoutManager);
                albumAdapter = new AlbumAdapter(myDataset);
                albumAdapter.setClickListener(new AlbumAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Long albumIdlong = myDataset.get(position).getId();
                        // Fragmenttransaksjonen, dvs. bytte av fragment, gjøres her av aktiviteten:
                        DetailsFragment detailsFragment = DetailsFragment.newInstance(albumIdlong.intValue());
                        if (isAdded()) {
                            ((MainActivity)getActivity()).replaceFragmentWidth(detailsFragment, true);
                        }
                    }
                });
                albumsRecyclerView.setAdapter(albumAdapter);
                albumAdapter.setLocalDataSet(allAlbums);
                albumAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
            }
        };
        // 3. Start observering:
        albumsViewModel.getAlbumsForUser(userId, false).observe(this, albumObservatør);

        // FRAGMENT-MENY: https://developer.android.com/guide/fragments/appbar
        // Tells the system that your fragment would like to receive menu-related callbacks.
        // When a menu-related event occurs (creation, clicks, and so on), the event handling
        // method is first called on the activity before being called on the fragment..
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            this.userId = getArguments().getInt(ARG_PARAM1);
        }
    }

    // FRAGMENT-MENY:
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_albums, menu);
    }

    // FRAGMENT-MENY:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:  {
                // Tvinger ny nedlasting:
                progressBar.setVisibility(View.VISIBLE);
                albumsViewModel.getAlbumsForUser(this.userId, true);
                return true;
            }
            case R.id.action_related: {
                // ...
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
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

        // Initialiserer View-objekter:
        albumsRecyclerView = view.findViewById(R.id.albumsRecyclerView);
        progressBar = view.findViewById(R.id.progressWork);
        progressBar.setVisibility(View.VISIBLE);
    }
}