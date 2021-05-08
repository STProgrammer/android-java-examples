package com.wfamedia.dptest3.viewmodel;

import androidx.lifecycle.LiveData;
import com.wfamedia.dptest3.model.Album;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * Bruker en Hilt-modul (her; RetrofitModule.java) til injection.
 * MERK! Bruker LiveData selv om MyViewModel IKKE arver fra ViewModel.
 *
 */
public class MyViewModel {

    private MyRepository myRepository;

    @Inject
    public MyViewModel(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    // Bruker LiveData slik at aktiviteten kan observere...
    public LiveData<List<Album>> getAlbums(int userId) {
        return myRepository.getAlbums(userId);
    }

    // Bruker LiveData slik at aktiviteten kan observere...
    public LiveData<String> getErrorMessage() {
        return myRepository.getErrorMessage();
    }
}
