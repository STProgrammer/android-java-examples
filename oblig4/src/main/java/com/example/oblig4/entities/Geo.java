package com.example.oblig4.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Geo {

    @PrimaryKey(autoGenerate = true)
    public long geoId;

    public double lat;
    public double lng;

    public Geo(@NonNull double lat, @NonNull double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
