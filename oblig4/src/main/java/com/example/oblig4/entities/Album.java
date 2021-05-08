package com.example.oblig4.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns="userId", childColumns = "fk_userId", onDelete = ForeignKey.CASCADE)
})
public class Album {

    @PrimaryKey(autoGenerate = true)
    public long albumId;

    public long fk_userId;
    public String title;

    public Album(@NonNull String title, long fk_userId) {
        this.title = title;
        this.fk_userId = fk_userId;
    }

}
