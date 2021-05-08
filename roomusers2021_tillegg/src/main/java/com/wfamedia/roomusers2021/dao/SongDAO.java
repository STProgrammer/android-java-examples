package com.wfamedia.roomusers2021.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wfamedia.roomusers2021.entities.Song;
import com.wfamedia.roomusers2021.entities.User;

import java.util.List;

@Dao
public interface SongDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Song song);

    @Query("SELECT * FROM Song")
    LiveData<List<Song>> getAll();

    @Query("SELECT * FROM Song WHERE fk_playlistId = :playListId")
    LiveData<List<Song>> getSongsFor(long playListId);
}
