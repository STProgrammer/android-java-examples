package com.wfamedia.dptest2.di;

import com.wfamedia.dptest2.viewmodel.MyRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * https://developer.android.com/training/dependency-injection/hilt-android
 * A Hilt module is a class that is annotated with @Module. Like a Dagger module, it informs
 * Hilt how to provide instances of certain types. Unlike Dagger modules, you must annotate
 * Hilt modules with @InstallIn to tell Hilt which Android class each module will be
 * used or installed in.
 *
 * Merk: BRUK SingletonComponent i stedet for ApplicationComponent (som brukes i noen tutorials).
 *
 */
@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    MyRepository provideMyRepository() {
        return new MyRepository("Germany", 83000001);
    }
}
