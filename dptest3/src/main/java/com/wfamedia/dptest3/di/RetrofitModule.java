package com.wfamedia.dptest3.di;

import com.wfamedia.dptest3.viewmodel.MyRepository;
import com.wfamedia.dptest3.viewmodel.UserAlbumAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Merk: Bruk SingletonComponent i stedet for ApplicationComponent (som brukes i noen tutorials).
 *
 */
@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Provides
    @Singleton
    public Retrofit provideRetrofit()  {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public UserAlbumAPI provideUserAlbumAPI(Retrofit retrofit) {
        return retrofit.create(UserAlbumAPI.class);
    }
}
