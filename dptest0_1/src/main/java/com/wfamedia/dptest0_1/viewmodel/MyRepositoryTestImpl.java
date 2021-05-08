package com.wfamedia.dptest0_1.viewmodel;

import javax.inject.Inject;

public class MyRepositoryTestImpl implements RepositoryService {

    private String country;
    private long population;

    @Inject
    public MyRepositoryTestImpl() {
        country = "Finland - test";
        population = 55;
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
