package com.wfamedia.albumsforuser2021utvidet.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.wfamedia.albumsforuser2021utvidet.model.Album;
import com.wfamedia.albumsforuser2021utvidet.repository.AlbumsRepository;
import java.util.List;

// ViewModel:
public class AlbumsViewModel extends ViewModel {
    private AlbumsRepository albumsRepository;

    public AlbumsViewModel() {
        albumsRepository = AlbumsRepository.getInstance();
    }

    public LiveData<List<Album>> getAlbumsForUser(int userId, boolean forceReload) {
        return albumsRepository.getAlbumsForUser(userId, forceReload);
    }

    public LiveData<String> getErrorMessage() {
        return albumsRepository.getErrorMessage();
    }
}
