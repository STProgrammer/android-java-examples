package com.wfamedia.albumsforuser2021.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wfamedia.albumsforuser2021.model.Album;
import com.wfamedia.albumsforuser2021.repository.AlbumsRepository;

import java.util.List;

// ViewModel:
public class AlbumsViewModel extends ViewModel {
    private AlbumsRepository albumsRepository;

    public AlbumsViewModel() {
        albumsRepository = AlbumsRepository.getInstance();
    }

    public MutableLiveData<List<Album>> getAlbumsForUser(int userId) {
        return albumsRepository.getAlbumsForUser(userId);
    }

    public MutableLiveData<String> getErrorMessage() {
        return albumsRepository.getErrorMessage();
    }
}
