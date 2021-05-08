package com.wfamedia.location3.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.wfamedia.location3.entities.MyLocation;
import java.util.List;

/*
    NB! AndroidViewModel krever ekstra dependencies i build.gradle
 */
public class MyLocationsViewModel extends AndroidViewModel {
    private MyLocationsRepository mRepository;
    private LiveData<List<MyLocation>> mMyLocations;

    public MyLocationsViewModel(Application application) {
        super(application);
        mRepository = new MyLocationsRepository(application);
        mMyLocations = mRepository.getMyLocations();
    }

    public LiveData<List<MyLocation>> getAllMyLocations() {
        return mMyLocations;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
