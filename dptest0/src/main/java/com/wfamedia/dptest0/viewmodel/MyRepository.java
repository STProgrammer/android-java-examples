package com.wfamedia.dptest0.viewmodel;

import javax.inject.Inject;

public class MyRepository {

    private String country;
    private long population;

    @Inject
    public MyRepository() {
        country = "Finland";
        population = 5518000;
    }

    public String getCountry() {
        return this.country;
    }

    public long getPopulation() {
        return population;
    }
}
