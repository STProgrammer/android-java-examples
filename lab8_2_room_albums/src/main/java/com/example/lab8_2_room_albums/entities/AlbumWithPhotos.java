package com.example.lab8_2_room_albums.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;


public class AlbumWithPhotos {
    @Embedded public Album album;
    @Relation(
            parentColumn = "albumId",
            entityColumn = "photoId",
            associateBy = @Junction(AlbumPhotoCrossRef.class)
    )
    public List<Photo> photos;
}