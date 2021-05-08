package com.example.oblig4.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Company {
    @PrimaryKey(autoGenerate = true)
    public long companyId;

    public String companyName;
    public String catchPhrase;
    public String bs;

    public Company(@NonNull String companyName, @NonNull String catchPhrase, @NonNull String bs) {
        this.companyName = companyName;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }
}
