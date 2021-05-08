package com.wfamedia.albumsforuser2021.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.wfamedia.albumsforuser2021.R;
import com.wfamedia.albumsforuser2021.model.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private List<Album> localDataSet;

    private ItemClickListener itemClickListener;

    // Parent activity eller fragment kan implementere dette interfacet for å fange opp click-events:
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * ViewHolder: Representerer View-et for hvert element i lista.
     * Denne klassen må vi lage.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;

        public MyViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.textView);
            textView.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View view) {
            if (AlbumAdapter.this.itemClickListener != null) {
                int pos = getAdapterPosition();
                itemClickListener.onItemClick(view, pos);
            }
        }
    }

    /**
     * Constructor: Mottar datalista, setter privat medlem.
     */
    public AlbumAdapter(List<Album> dataSet) {
        localDataSet = dataSet;
    }

    // Oppretter nye views (kalles automatisk av layout manager).
    // (metoden må overstyres)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Oppretter et nytt view som representerer UI til et listeelement.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_row_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    // Fyller view med data fra datalista (metoden må overstyres).
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        // Fyller TextView med data fra localDataSet[position]
        myViewHolder.getTextView().setText(localDataSet.get(position).getTitle());
    }

    // Returnere antall elementer i datalista. Kalles autmatisk (metoden må overstyres)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    // Denne kalles ev. av parent:
    public void setClickListener(AlbumAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Returnerer current item:
    public Album getItem(int id) {
        return localDataSet.get(id);
    }

    public void setLocalDataSet(List<Album> localDataSet) {
        this.localDataSet = localDataSet;
    }
}