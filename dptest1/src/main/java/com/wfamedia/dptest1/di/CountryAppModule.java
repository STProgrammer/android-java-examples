package com.wfamedia.dptest1.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.components.SingletonComponent;

/**
 * https://developer.android.com/training/dependency-injection/hilt-android
 * A Hilt module is a class that is annotated with @Module.
 * It informs Hilt how to provide instances of CERTAIN TYPES!
 * You must annotate Hilt modules with @InstallIn to tell Hilt which Android class each module will be
 * used or installed in.
 *
 * Merk: BRUK SingletonComponent i stedet for ApplicationComponent (som brukes i noen tutorials).
 *
 * Returtypen til "metodene" er bestemmende.
 *
 */
@Module
@InstallIn(ActivityComponent.class)
public class CountryAppModule {

    @Provides
    long providePopulation()  {
        return 83000001;
    }

    @Provides
    String provideCountry()  {
        return "Tyskland";
    }
}
