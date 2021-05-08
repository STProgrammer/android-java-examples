package com.example.lab8_2_room_albums.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"albumId", "photoId"},
        foreignKeys = {
                @ForeignKey(entity = Album.class, parentColumns="albumId", childColumns = "albumId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Photo.class, parentColumns="photoId", childColumns = "photoId", onDelete = ForeignKey.CASCADE)
        }
)
public class AlbumPhotoCrossRef {
    public long albumId;
    public long photoId;

    public AlbumPhotoCrossRef(long albumId, long photoId) {
        this.albumId = albumId;
        this.photoId = photoId;
    }
}