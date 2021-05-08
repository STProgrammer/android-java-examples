package com.wfamedia.dptest0_1.viewmodel;

import javax.inject.Inject;

/**
 * MERK: Parametrene til en @Inject-konstruktør til klassen er "the dependencies" til klassen.
 *
 * Her vil mRepository være et avhengig medlem (merk vi sender ingenting "inn til" konstruktøren
 * fra MainActivity).
 *
 * - Hilt må vite hvordan opprette en instans av RepositoryService, som er et Interface.
 * - Et interface har ingen konstruktør.
 * - Bruker derfor en Hilt-modul vha. @Module og @Bind - se RepositoryModule.java
 *
 */
public class MyViewModel {

    private RepositoryService repositoryService;

    @Inject
    public MyViewModel(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public String getCountry() {
        return repositoryService.getCountry();
    }

    public long getPopulation() {
        return repositoryService.getPopulation();
    }
}
