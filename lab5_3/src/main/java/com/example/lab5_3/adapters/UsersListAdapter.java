package com.example.lab5_3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_3.models.User;
import com.example.lab8_2_room_albums.R;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> {

    private List<User> usersDataSet;

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
            if (UsersListAdapter.this.itemClickListener != null) {
                int pos = getAdapterPosition();
                itemClickListener.onItemClick(view, pos);
            }
        }
    }

    /**
     * Constructor: Mottar datalista, setter privat medlem.
     */
    public UsersListAdapter(List<User> dataSet) {
        usersDataSet = dataSet;
    }

    // Oppretter nye views (kalles automatisk av layout manager).
    // (metoden må overstyres)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Oppretter et nytt view som representerer UI til et listeelement.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    // Fyller view med data fra datalista (metoden må overstyres).
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        // Fyller TextView med data fra usersDataSet[position]
        myViewHolder.getTextView().setText(usersDataSet.get(position).getName());
    }

    // Returnere antall elementer i datalista. Kalles automatisk (metoden må overstyres)
    @Override
    public int getItemCount() {
        return usersDataSet.size();
    }

    // Denne kalles ev. av parent:
    public void setClickListener(UsersListAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Returnerer current item:
    public User getItem(int id) {
        return usersDataSet.get(id);
    }

    public void setUsersDataSet(List<User> usersDataSet) {
        this.usersDataSet = usersDataSet;
    }
}