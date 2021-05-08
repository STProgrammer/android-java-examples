package com.wfamedia.dptest2.viewmodel;

import javax.inject.Inject;

/**
 * Bruker en Hilt-modul (her; RepositoryModule.java) til injection.
 * MERK: MyViewModel arver (fortsatt) IKKE fra ViewModel.
 */
public class MyViewModel {

    private MyRepository myRepository;

    @Inject
    public MyViewModel(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public String getCountry() {
        return myRepository.getCountry();
    }

    public long getPopulation() {
        return myRepository.getPopulation();
    }
}
