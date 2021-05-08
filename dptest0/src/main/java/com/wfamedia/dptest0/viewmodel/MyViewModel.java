package com.wfamedia.dptest0.viewmodel;

import javax.inject.Inject;

/**
 * MERK: Parametrene til en @Inject-konstruktør tilsvarer klassens avhengigheter.
 * Her vil mRepository være et avhengig medlem (merk vi sender ingenting "inn til" konstruktøren
 * fra MainActivity). mRepository vil automatisk instansieres og sent inn til konstruktøren.
 *
 * Det betyr også at Hilt må vite hvordan opprette en instans av MyRepository. Siden MyRepository
 * også har en @Inject-konstruktør fungerer dette ("constructor injection").
 *
 * Dersom MyRepository var et interface må man bruke en @Module og @Bind for å spesifisere
 * hvilken implementasjon som skal benyttes - se eksempel dptest0_1
 *
 * MERK: MyViewModel arver IKKE fra ViewModel.
 */
public class MyViewModel {

    private MyRepository myRepository;

    @Inject
    public MyViewModel(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    // Alternativt:
    /*
    @Inject
    MyRepository myRepository;

    @Inject
    public MyViewModel() {
        //tom konstruktør.
    }
     */

    public String getCountry() {
        return myRepository.getCountry();
    }

    public long getPopulation() {
        return myRepository.getPopulation();
    }
}
