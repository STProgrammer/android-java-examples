package com.wfamedia.dptest0_1.viewmodel;

import javax.inject.Inject;

public class MyRepositoryImpl implements RepositoryService {

    private String country;
    private long population;

    @Inject
    public MyRepositoryImpl() {
        country = "Finland";
        population = 5518000;
    }

    @Override
    public String getCountry() {
        return this.country;
    }

    @Override
    public long getPopulation() {
        return population;
    }
}
