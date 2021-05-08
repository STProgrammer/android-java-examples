package com.wfamedia.roomusers2021.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * MERK! ForeignKey.
 * 1-mange forhold mellom User(1) og Playlist(mange).
 */

@Entity(foreignKeys = {
  @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "fk_userId", onDelete = ForeignKey.CASCADE)
})
public class Playlist {
    @PrimaryKey(autoGenerate = true)
    public long playListId;

    public String name;
    public String genre;    //sjanger
    public long fk_userId;     //fremmenøkkel

    @ColumnInfo(name = "best_suited_for")     //tur, trening, lesing osv.
    public String best_suited_for;

    public Playlist(@NonNull String name, @NonNull String genre, @NonNull String best_suited_for, @NonNull long fk_userId) {
        this.name = name;
        this.genre = genre;
        this.best_suited_for = best_suited_for;
        this.fk_userId = fk_userId;
    }
}
