package com.wfamedia.dptest4.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.wfamedia.dptest4.model.Album;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 *
 * MERK!
 *  Repository-instansen genereres vha. RetrofitModule og legges inn i ViewModelComponent.
 *      Medlemmene i RetrofitModule er @ViewModelScoped
 *      A @ViewModelScoped type will make it so that a single instance of the
 *      scoped type is provided across all dependencies injected into the Hilt View Model.
 *
 *  Bruker LiveData.
 *  Bruk av @HiltViewModel
 *
 */
@HiltViewModel
public class MyViewModel extends ViewModel {

    private MyRepository myRepository;      // Setter inn (injects) en MyRepository-instans

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
