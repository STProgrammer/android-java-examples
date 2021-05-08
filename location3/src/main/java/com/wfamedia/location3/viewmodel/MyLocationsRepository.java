package com.wfamedia.location3.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.wfamedia.location3.db.MyLocationDAO;
import com.wfamedia.location3.db.MyLocationsDatabase;
import com.wfamedia.location3.entities.MyLocation;
import java.util.List;

public class MyLocationsRepository {
    private MyLocationDAO myLocationDAO;
    private LiveData<List<MyLocation>> mMyLocations;

    //*** Constructor
    public MyLocationsRepository(Application application) {
        MyLocationsDatabase db = MyLocationsDatabase.getDatabase(application);
        myLocationDAO = db.myLocationDAO();
        mMyLocations = myLocationDAO.getAll();
    }

    LiveData<List<MyLocation>> getMyLocations() {
        return mMyLocations;
    }

    public void insert(MyLocation myLocation) {
        MyLocationsDatabase.databaseWriteExecutor.execute(() -> {
            myLocationDAO.insert(myLocation);
        });
    }

    public void deleteMyLocation(long myLocationId) {
        MyLocationsDatabase.databaseWriteExecutor.execute(() -> {
            myLocationDAO.delete(myLocationId);
        });
    }

    public void deleteAll() {
        MyLocationsDatabase.databaseWriteExecutor.execute(() -> {
            myLocationDAO.deleteAll();
        });
    }

}