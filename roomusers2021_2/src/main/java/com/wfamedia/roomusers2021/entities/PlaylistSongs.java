package com.wfamedia.roomusers2021.entities;

import androidx.room.Embedded;
import androidx.room.Relation;
import java.util.List;

public class PlaylistSongs {
    @Embedded
    public Playlist playlist;

    @Relation(
            parentColumn = "playListId",
            entityColumn = "fk_playlistId"
    )
    public List<Song> songs;
}
