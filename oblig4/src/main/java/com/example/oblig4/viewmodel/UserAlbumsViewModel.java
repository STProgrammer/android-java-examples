package com.example.oblig4.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.oblig4.entities.Album;
import com.example.oblig4.entities.AlbumPhotoCrossRef;
import com.example.oblig4.entities.Photo;
import com.example.oblig4.entities.User;
import com.example.oblig4.entities.UserWithAlbums;
import com.example.oblig4.entities.UserWithCompanyWithAddressWithGeo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/*
    NB! AndroidViewModel krever ekstra dependencies i build.gradle
 */

public class UserAlbumsViewModel extends AndroidViewModel {

    UserAlbumsRepository mRepository;
    Application application;
    private LiveData<UserWithAlbums> mUserWithAlbums;
    private LiveData<List<UserWithCompanyWithAddressWithGeo>> mUsers;


    public UserAlbumsViewModel(Application application) {
        super(application);
        this.application = application;
        mRepository = new UserAlbumsRepository(application);
        mUsers = mRepository.getAllUsersWithCompanyWithAddressWithGeo();
    }

    public long getUserIdToShowAlbums() {
        return mRepository.getUserIdToShowAlbums();
    }

    public void setUserIdToShowAlbums(long userIdToShowAlbums) {
        mRepository.setUserIdToShowAlbums(userIdToShowAlbums);
    }


    //*** User-metoder:



    public void insertCompleteUser() {
        mRepository.insertCompleteUser();
    }

    public void deleteUser(long userId) {
        mRepository.deleteUser(userId);
    }

    public long addAlbum(Album album) {
        return mRepository.addAlbum(album);
    }

    public void deleteAlbum(long albumId) {
        mRepository.deleteAlbum(albumId);
    }

    public long addPhoto(Photo photo, long albumId) {
        return mRepository.addPhoto(photo, albumId);
    }

    public void deletePhoto(long photoId) {
        mRepository.deletePhoto(photoId);
    }

    public LiveData<UserWithAlbums> showAlbumsAndPhotos(long userId) {
        mUserWithAlbums = mRepository.showAlbumsAndPhotos(userId);
        return mUserWithAlbums;
    }

    public LiveData<UserWithAlbums> getUserWithAlbums() {
        return mRepository.getAlbumsAndPhotos();
    }

    public LiveData<List<UserWithCompanyWithAddressWithGeo>> getAllUsersWithCompanyWithAddressWithGeo() {
        return mUsers;
    }



}
