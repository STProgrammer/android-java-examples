package com.wfamedia.dptest1.viewmodel;

import javax.inject.Inject;

/**
 * Bruker en Hilt-modul (her; CountryAppModule.java) til injection.
 *  "Sometimes a type cannot be constructor-injected. This can happen for multiple reasons.
 *  For example, you cannot constructor-inject an interface. You also cannot constructor-inject
 *  a type that you do not own, such as a class from an external library. In these cases,
 *  you can provide Hilt with binding information by using Hilt modules."
 *  https://developer.android.com/training/dependency-injection/hilt-android
 */
public class MyRepository {

    // Field injection:
    /*
    @Inject
    String country;     //NB! Kan ikke være private

    @Inject
    long population;

    @Inject
    public MyRepository() {

    }*/

    // Evt. constructor injection:
    private String country;
    private long population;

    @Inject
    public MyRepository(String country, long population) {
        this.country = country;
        this.population = population;
    }

    public String getCountry() {
        return this.country;
    }

    public long getPopulation() {
        return this.population;
    }
}
