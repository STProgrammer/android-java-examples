package com.wfamedia.viewpager2utstyr0;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

// ViewModel:
public class MyViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Utstyr>> labUtstyr = new MutableLiveData<ArrayList<Utstyr>>();

    public MyViewModel() {
    }

    public MutableLiveData<ArrayList<Utstyr>> getLabUtstyr() {
        return labUtstyr;
    }

    public void setLabUtstyr(ArrayList<Utstyr> utstyr) {
        this.labUtstyr.postValue(utstyr);
    }
}
