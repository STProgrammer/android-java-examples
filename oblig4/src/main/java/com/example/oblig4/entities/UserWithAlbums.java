package com.example.oblig4.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithAlbums {
    @Embedded
    public User user;
    @Relation(
            entity = Album.class,
            parentColumn = "userId",
            entityColumn = "fk_userId"
    )
    public List<AlbumWithPhotos> albums;

    public UserWithAlbums() {}
}
