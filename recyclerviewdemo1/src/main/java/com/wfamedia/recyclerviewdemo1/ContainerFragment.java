package com.wfamedia.recyclerviewdemo1;

import android.content.ClipData;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class ContainerFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private CustomAdapter customAdapter;
    protected String[] myDataset;

    private static final int DATASET_COUNT = 60;

    public ContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_container, container, false);

        myRecyclerView = (RecyclerView) rootView.findViewById(R.id.myRecyclerView);

        // Bruker LinearLayoutManager:
        /*
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        */
        // ... eller GridLayoutManager:
        int numberOfColumns = 3;
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        this.customAdapter = new CustomAdapter(this.myDataset);
        this.customAdapter.setClickListener(new CustomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("TAG", "You clicked number " + customAdapter.getItem(position) + ", which is at cell position " + position);
            }
        });
        myRecyclerView.setAdapter(this.customAdapter);
        return rootView;
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private void initDataset() {
        this.myDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            this.myDataset[i] = "TXT_" + i;
        }
    }
}