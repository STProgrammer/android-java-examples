package com.example.lab4_2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private final MutableLiveData<UtstyrsListe> labUtstyr = new MutableLiveData<>();

    public void setLabUtstyr(UtstyrsListe utstyrsListe) {
        labUtstyr.postValue(utstyrsListe);
    }

    public LiveData<UtstyrsListe> getLabUtstyr() {
        return labUtstyr;
    }

}
