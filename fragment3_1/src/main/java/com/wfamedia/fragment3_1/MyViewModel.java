package com.wfamedia.fragment3_1;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// ViewModel:
public class MyViewModel extends ViewModel {
    private final MutableLiveData<Integer> textColor = new MutableLiveData<Integer>();
    private final MutableLiveData<Boolean> checked = new MutableLiveData<Boolean>();
    private final MutableLiveData<String> selection = new MutableLiveData<String>();

    private final MutableLiveData<Boolean> resetColor = new MutableLiveData<Boolean>();
    private final MutableLiveData<Boolean> resetSelections = new MutableLiveData<Boolean>();

    public MyViewModel() {
    }

    public MutableLiveData<Integer> getTexteColor() {
        return textColor;
    }

    public MutableLiveData<Boolean> getChecked() {
        return checked;
    }

    public MutableLiveData<String> getSelection() {
        return selection;
    }

    public void setTextColor(Integer color) {
        textColor.postValue(color);
    }

    public void setChecked(Boolean checked) {
        this.checked.postValue(checked);
    }

    public MutableLiveData<Boolean> getResetColor() {
        return resetColor;
    }

    public MutableLiveData<Boolean> getResetSelections() {
        return resetSelections;
    }

    public void setText(String text) {
        selection.postValue(text);
    }

    public void resetColorSelection(boolean value) {
        resetColor.postValue(value);
    }

    public void resetCheckboxSelections(boolean value) {
        resetSelections.postValue(value);
    }
}
