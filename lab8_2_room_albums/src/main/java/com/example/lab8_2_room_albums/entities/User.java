package com.example.lab8_2_room_albums.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Address.class, parentColumns = "addressId", childColumns = "fk_addressId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Company.class, parentColumns = "companyId", childColumns = "fk_companyId", onDelete = ForeignKey.CASCADE)
})
public class User {

    @PrimaryKey(autoGenerate = true)
    public long userId;

    public String name;
    public String username;
    public String email;


    public long fk_addressId;
    public String phone;
    public String website;
    public long fk_companyId;

    public User(@NonNull String name, @NonNull String username, @NonNull String email, long fk_addressId,
                @NonNull String phone, @NonNull String website, long fk_companyId) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.fk_addressId = fk_addressId;
        this.phone = phone;
        this.website = website;
        this.fk_companyId = fk_companyId;
    }
}
