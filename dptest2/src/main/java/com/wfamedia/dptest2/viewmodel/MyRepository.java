package com.wfamedia.dptest2.viewmodel;

/**
 * Merk; Ingen annotasjoner her.
 * Anta at dette er en klasse du ikke har kontroll på, f.eks. fra et 3 parts bibliotek.
 * Du kan dermed ikke sette @Inject på konstruktøren eller felter.
 * Pakk derfor inn i @Module
 */
public class MyRepository {

    String country;
    long population;

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
