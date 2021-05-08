package com.wfamedia.dptest1.viewmodel;

import javax.inject.Inject;

/**
 *
 * MERK: MyViewModel arver IKKE fra ViewModel.
 *
 */
public class MyViewModel {

    private MyRepository myRepository;

    // Constructor injection
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
