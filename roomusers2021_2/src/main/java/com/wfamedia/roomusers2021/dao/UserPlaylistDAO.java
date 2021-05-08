package com.wfamedia.roomusers2021.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.google.common.util.concurrent.ListenableFuture;
import com.wfamedia.roomusers2021.entities.Playlist;
import com.wfamedia.roomusers2021.entities.User;
import com.wfamedia.roomusers2021.entities.UserPlaylists;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * MERK 1: Bruker abstrakt klasse i stedet for interface
 * MERK 2: insertDemoUser(...) utføres som en transaksjon.
 *
 */
@Dao
public abstract class UserPlaylistDAO {
    @Query("SELECT * FROM user")
    public abstract LiveData<List<User>> getAll();

    @Transaction
    public void insertDemoUser(User user) {
        long userId = insert(user);
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(new Playlist("Navn: Rolig", "Sjanger: Chillout", "Avslapping.", userId));
        playlists.add(new Playlist("Navn: Skitur", "Sjanger: Fjell&vidde", "Turer i fjellet.", userId));
        playlists.add(new Playlist("Navn: Trening", "Sjanger: Dunk dunk", "Aktivitet.", userId));
        insertPlaylists(playlists);
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(User user);

    @Query("SELECT * FROM user WHERE id = :id")
    public abstract LiveData<User> observeUserById(long id);

    @Transaction
    @Query("SELECT * FROM user WHERE id=:userId")
    public abstract UserPlaylists getUserPlaylists(long userId);

    @Query("DELETE FROM user")
    public abstract void deleteAll();

    @Delete
    public abstract void delete(User user);

    @Query("DELETE FROM user WHERE id = :userId")
    public abstract void delete(long userId);

    //Insert en liste
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertPlaylist(Playlist playlist);

    //Insert mange lister:
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertPlaylists(List<Playlist> playlists);
}
