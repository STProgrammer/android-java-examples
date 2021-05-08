package com.example.oblig4.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
                @ForeignKey(entity = Album.class, parentColumns="albumId", childColumns = "albumId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Photo.class, parentColumns="photoId", childColumns = "photoId", onDelete = ForeignKey.CASCADE)
        }
)
public class AlbumPhotoCrossRef {
    @PrimaryKey(autoGenerate = true)
    public long crossRefId;

    public long albumId;
    public long photoId;

    public AlbumPhotoCrossRef(long albumId, long photoId) {
        this.albumId = albumId;
        this.photoId = photoId;
    }
}