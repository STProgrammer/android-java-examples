package com.example.lab8_2_room_albums.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithCompanyWithAddressWithGeo {
    @Embedded
    public User user;
    @Embedded
    public Company company;
    @Embedded
    public Address address;
    @Embedded
    public Geo geo;

    public UserWithCompanyWithAddressWithGeo(){}

}
