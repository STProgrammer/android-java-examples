package com.example.lab5_3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.example.lab8_2_room_albums.R;
import com.example.lab5_3.models.Photo;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

    private List<Photo> photosDataSet;

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
        private final ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.textView);
            textView.setOnClickListener(this);
            imageView = (ImageView) view.findViewById(R.id.thumbnail_view);
            imageView.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {return imageView; }

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
    public AlbumAdapter(List<Photo> dataSet) {
        photosDataSet = dataSet;
    }

    // Oppretter nye views (kalles automatisk av layout manager).
    // (metoden må overstyres)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Oppretter et nytt view som representerer UI til et listeelement.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_row_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    // Fyller view med data fra datalista (metoden må overstyres).
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        // Fyller TextView med data fra photosDataSet[position]
        Photo photo = photosDataSet.get(position);

        myViewHolder.getTextView().setText("ID: " + photo.getId() + "\nNavn:" + photo.getTitle());

        GlideUrl url = new GlideUrl(photo.getThumbnailUrl(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "android")
                .build());
        Glide.with(myViewHolder.itemView.getContext())
                .load(url)
                .into(myViewHolder.getImageView());
    }

    // Returnere antall elementer i datalista. Kalles automatisk (metoden må overstyres)
    @Override
    public int getItemCount() {
        return photosDataSet.size();
    }

    // Denne kalles ev. av parent:
    public void setClickListener(AlbumAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Returnerer current item:
    public Photo getItem(int id) {
        return photosDataSet.get(id);
    }

    public void setPhotosDataSet(List<Photo> photosDataSet) {
        this.photosDataSet = photosDataSet;
    }
}