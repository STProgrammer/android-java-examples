package com.example.oblig4.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.oblig4.entities.AlbumPhotoCrossRef;
import com.example.oblig4.entities.AlbumWithPhotos;
import com.example.oblig4.entities.Photo;

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

