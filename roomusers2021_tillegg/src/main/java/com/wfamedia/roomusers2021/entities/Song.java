package com.wfamedia.roomusers2021.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * MERK! ForeignKey.
 * 1-mange forhold mellom User(1) og Song(mange).
 */
@Entity(foreignKeys = {@ForeignKey(
        entity = Playlist.class,
        parentColumns = "playListId",
        childColumns = "fk_playlistId",
        onDelete = ForeignKey.CASCADE)
})
public class Song {
    @PrimaryKey(autoGenerate = true)
    public int songId;

    public String name;
    public String author;
    public int year;
    public long fk_playlistId;

    public Song(String name, String author, int year, long fk_playlistId) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.fk_playlistId = fk_playlistId;
    }
}
