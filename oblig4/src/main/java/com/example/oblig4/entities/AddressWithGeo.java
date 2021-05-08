package com.example.oblig4.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AddressWithGeo {

    @Embedded
    Address address;
    @Relation(entity = Geo.class, entityColumn = "geoId",parentColumn = "fk_geoId")
    List<Geo> geos;

    public AddressWithGeo(){}
}