package com.example.oblig4.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Photo {

    @PrimaryKey(autoGenerate = true)
    public long photoId;

    public String title;
    public String url;
    public String thumbnailUrl;

    public Photo(@NonNull String title, @NonNull String url, @NonNull String thumbnailUrl) {
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }



}
