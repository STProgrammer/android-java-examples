package com.wfamedia.roomusers2021.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
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
