package com.example.lab5_3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab5_3.models.User;
import com.example.lab5_3.models.Album;
import com.example.lab5_3.models.Photo;

import java.util.List;

import javax.inject.Inject;

// ViewModel:
public class UserViewModel extends ViewModel {
    com.example.lab5_3.repository.Repository Repository;

    @Inject
    public UserViewModel() {
        Repository = com.example.lab5_3.repository.Repository.getInstance();
    }

    public LiveData<List<User>> getUserListData(boolean mIsFirstTime) {
        return Repository.getUserListData(mIsFirstTime);
    }

    public LiveData<List<Album>> getUserData(int userId, boolean mIsFirstTime) {
        return Repository.getUserData(userId, mIsFirstTime);
    }

    public LiveData<List<Photo>> getAlbumData(int albumId, boolean mIsFirstTime) {
        return Repository.getAlbumData(albumId, mIsFirstTime);
    }

    public LiveData<Photo> getPhoto(int id, boolean mIsFirstTime) {
        return Repository.getPhoto(id, mIsFirstTime);
    }

    public LiveData<Integer> getErrorUserList() { return Repository.getErrorUserList();}

    public LiveData<Integer> getErrorUser() { return Repository.getErrorUser();}

    public LiveData<Integer> getErrorAlbum() { return Repository.getErrorAlbum();}

    public LiveData<Integer> getErrorPhoto() { return Repository.getErrorPhoto();}


    public LiveData<String> getErrorMessage() {
        return Repository.getErrorMessage();
    }
}