package com.wfamedia.albumsforuser2021httpurlcon.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wfamedia.albumsforuser2021httpurlcon.model.Album;
import com.wfamedia.albumsforuser2021httpurlcon.repository.AlbumsRepository;

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
