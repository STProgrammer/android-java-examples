package com.wfamedia.location3.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.wfamedia.location3.entities.MyLocation;

import java.util.List;

/**
 *
 * MERK 1: Bruker abstrakt klasse i stedet for interface
 * MERK 2: insertDemoUser(...) utføres som en transaksjon.
 *
 */
@Dao
public abstract class MyLocationDAO {
    @Query("SELECT * FROM MyLocation")
    public abstract LiveData<List<MyLocation>> getAll();

    @Query("DELETE FROM MyLocation")
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(MyLocation myLocation);

    @Query("SELECT * FROM MyLocation WHERE id = :id")
    public abstract LiveData<MyLocation> observeMyLocationById(long id);

    @Delete
    public abstract void delete(MyLocation myLocation);

    @Query("DELETE FROM MyLocation WHERE id = :myLocationId")
    public abstract void delete(long myLocationId);
}
