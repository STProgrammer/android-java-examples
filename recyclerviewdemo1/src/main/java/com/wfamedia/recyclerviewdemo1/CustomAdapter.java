package com.wfamedia.recyclerviewdemo1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private String[] localDataSet;

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
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View view) {
            if (CustomAdapter.this.itemClickListener != null)
                itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /**
     * Constructor: Mottar datalista, setter privat medlem.
     */
    public CustomAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    // Oppretter nye views (kalles automatisk av layout manager).
    // (metoden må overstyres)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Oppretter et nytt view som representerer UI til et listeelement.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    // Fyller view med data fra datalista (metoden må overstyres).
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        // Fyller TextView med data fra localDataSet[position]
        myViewHolder.getTextView().setText(localDataSet[position]);
    }

    // Returnere antall elementer i datalista. Kalles autmatisk (metoden må overstyres)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    // Denne kalles ev. av parent:
    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Returnerer current item:
    String getItem(int id) {
        return localDataSet[id];
    }
}