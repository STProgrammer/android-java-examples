package com.wfamedia.dptest4.di;

import com.wfamedia.dptest4.viewmodel.UserAlbumAPI;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A Hilt module is a class that is annotated with @Module.talled in.
 *
 * Merk: BRUKER ViewModelComponent OG @ViewModelScoped slik at instansen følger syklusen til
 * androidx
 */
@Module
@InstallIn(ViewModelComponent.class)
public class RetrofitModule {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    /*
     ENTEN SOM TO metoder

      @Provides
      @ViewModelScoped // To scope a dependency to a ViewModel use the @ViewModelScoped annotation.
      public Retrofit provideRetrofit()  {
        return new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())     //<== NB! VIKTIG!
          .build();
      }

      @Provides
      @ViewModelScoped // To scope a dependency to a ViewModel use the @ViewModelScoped annotation.
      public UserAlbumAPI provideUserAlbumAPI(Retrofit retrofit) {
        return retrofit.create(UserAlbumAPI.class);
      }
     */

    // ELLER ALT I ET ...
    @Provides
    @ViewModelScoped
    // To scope a dependency to a ViewModel use the @ViewModelScoped annotation.
    public UserAlbumAPI provideUserAlbumAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAlbumAPI.class);    //<== Merk!
    }
}
