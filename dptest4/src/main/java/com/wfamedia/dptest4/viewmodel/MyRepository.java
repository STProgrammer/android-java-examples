package com.wfamedia.dptest4.viewmodel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.wfamedia.dptest4.model.Album;
import java.util.List;
import javax.inject.Inject;

import dagger.hilt.android.scopes.ViewModelScoped;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRepository {
    // Bruker LiveData for å kunne returnere tilbake til MyViewModel.
    private MutableLiveData<List<Album>> albumsData;
    private MutableLiveData<String> errorMessage;

    @Inject
    UserAlbumAPI userAlbumAPI;  //Setter inn (injects) en UserAlbumAPI-instans (vha. RetrofitModule)

    @Inject
    public MyRepository() {
        albumsData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public MutableLiveData<List<Album>> getAlbums(int userId) {
        if (albumsData.getValue() != null && albumsData.getValue().size() > 0)
            return albumsData;

        //alternativt:  final UserAlbumAPI userAlbumAPI = retrofit.create(UserAlbumAPI.class);
        Call<List<Album>> call = userAlbumAPI.getAlbumsForUser(userId);
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Album> albums = response.body();
                Log.d("TÆGG", "ANTALL ALBUM=" + String.valueOf(albums.size()));
                albumsData.setValue(albums);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                errorMessage.setValue(t.getMessage());
                Log.d("TÆGG", t.getMessage());
            }
        });
        return albumsData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
