package com.wfamedia.dptest0_1.viewmodel;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

/**
 * Merk: denne er "installert" i ActivityComponent.class
 * Betyr at denne er tilgjengelig fra alle aktiviteter i appen.
 */
@Module
@InstallIn(ActivityComponent.class)
public abstract class RepositoryModule {

    @Binds
    public abstract RepositoryService bindRepositoryService(MyRepositoryTestImpl myRepositoryTest);

    /*
    @Binds
    public abstract RepositoryService bindRepositoryService(MyRepositoryImpl myRepository);
    */
}
