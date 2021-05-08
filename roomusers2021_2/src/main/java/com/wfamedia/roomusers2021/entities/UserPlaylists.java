package com.wfamedia.roomusers2021.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserPlaylists {
    @Embedded public User user;

    @Relation(parentColumn = "id", entityColumn = "fk_userId")
    public List<Playlist> playlists;
}
