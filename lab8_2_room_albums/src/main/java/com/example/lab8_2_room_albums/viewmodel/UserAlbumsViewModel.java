package com.example.lab8_2_room_albums.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab8_2_room_albums.db.UserRoomDatabase;
import com.example.lab8_2_room_albums.entities.Album;
import com.example.lab8_2_room_albums.entities.Photo;
import com.example.lab8_2_room_albums.entities.User;
import com.example.lab8_2_room_albums.entities.UserWithAlbums;
import com.example.lab8_2_room_albums.entities.UserWithCompanyWithAddressWithGeo;
import com.example.lab8_2_room_albums.viewmodel.UserAlbumsRepository;

import java.util.List;

/*
    NB! AndroidViewModel krever ekstra dependencies i build.gradle
 */
public class UserAlbumsViewModel extends AndroidViewModel {
    private UserAlbumsRepository mRepository;
    private LiveData<List<User>> mUsers;
    private LiveData<UserWithAlbums> mUserWithAlbums;
    private LiveData<UserWithCompanyWithAddressWithGeo> mUserData;

    public UserAlbumsViewModel(Application application) {
        super(application);
        mRepository = new UserAlbumsRepository(application);
        mUserWithAlbums = mRepository.getAlbumsAndPhotos();
        mUserData = mRepository.getUserWithCompanyWithAddressWithGeo();
    }

    public void showAllInfoOfUser(long userId) {
        mRepository.showAllInfoOfUser(userId);
    }

    //*** User-metoder:
    public LiveData<List<User>> getUsers() {
        return mUsers;
    }

    public LiveData<List<User>> saveUsers() {
        mUsers = mRepository.getUsers();
        return mUsers;
    }

    public long insertUser(User user) {
        return mRepository.insertUser(user);
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

    public long addPhoto(Photo photo) {
        return mRepository.addPhoto(photo);
    }

    public void deletePhoto(long photoId) {
        mRepository.deletePhoto(photoId);
    }

    public LiveData<UserWithAlbums> showAlbumsAndPhotos(long userId) {
        return mRepository.showAlbumsAndPhotos(userId);
    }

    public LiveData<UserWithAlbums> getUserWithAlbums() {
        return mUserWithAlbums;
    }

    public LiveData<UserWithCompanyWithAddressWithGeo> showUserWithCompanyWithAddressWithGeo(long userId) {
        return mRepository.showUserWithCompanyWithAddressWithGeo(userId);
    }

    public LiveData<UserWithCompanyWithAddressWithGeo> getUserWithCompanyWithAddressWithGeo() {
        return mUserData;
    }



}
