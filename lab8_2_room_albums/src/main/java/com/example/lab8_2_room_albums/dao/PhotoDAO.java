package com.example.lab8_2_room_albums.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.lab8_2_room_albums.entities.Album;
import com.example.lab8_2_room_albums.entities.AlbumPhotoCrossRef;
import com.example.lab8_2_room_albums.entities.AlbumWithPhotos;
import com.example.lab8_2_room_albums.entities.Photo;

import java.util.List;

@Dao
public interface PhotoDAO {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Photo photo);

    @Transaction
    @Query("DELETE FROM Photo WHERE photoId = :photoId")
    void delete(long photoId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addToAlbum(AlbumPhotoCrossRef albumPhotoCrossRef);

    @Transaction
    @Query("SELECT * FROM Photo")
    LiveData<List<Photo>> getAll();

    @Transaction
    @Query("SELECT * FROM Album WHERE albumId = :albumId")
    LiveData<AlbumWithPhotos> getAlbumWithPhotosFor(long albumId);
}

