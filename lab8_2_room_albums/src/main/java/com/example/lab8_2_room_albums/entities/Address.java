package com.example.lab8_2_room_albums.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Geo.class, parentColumns = "geoId", childColumns = "fk_geoId", onDelete = ForeignKey.CASCADE)
})
public class Address {

    @PrimaryKey(autoGenerate = true)
    public long addressId;

    public String street;
    public String suite;
    public String city;
    public String zipCode;

    public long fk_geoId;

    public Address(@NonNull String street, @NonNull String suite, @NonNull String city,
                   @NonNull String zipCode, long fk_geoId) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipCode = zipCode;
        this.fk_geoId = fk_geoId;
    }
}
