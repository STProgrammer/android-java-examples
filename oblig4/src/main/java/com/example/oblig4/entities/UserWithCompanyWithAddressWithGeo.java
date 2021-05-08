package com.example.oblig4.entities;

import androidx.room.Embedded;

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
