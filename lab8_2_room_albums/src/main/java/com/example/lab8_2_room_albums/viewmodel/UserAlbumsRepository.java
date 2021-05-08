package com.example.lab8_2_room_albums.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab8_2_room_albums.dao.PhotoDAO;
import com.example.lab8_2_room_albums.dao.UserDAO;
import com.example.lab8_2_room_albums.db.UserRoomDatabase;
import com.example.lab8_2_room_albums.entities.Album;
import com.example.lab8_2_room_albums.entities.Photo;
import com.example.lab8_2_room_albums.entities.User;
import com.example.lab8_2_room_albums.entities.UserWithAlbums;
import com.example.lab8_2_room_albums.entities.UserWithCompanyWithAddressWithGeo;


import java.util.List;

public class UserAlbumsRepository {
    private UserDAO userDAO;
    private PhotoDAO photoDAO;
    private LiveData<List<User>> mUsers;
    private LiveData<UserWithCompanyWithAddressWithGeo> mUserData = new MutableLiveData<>();
    private LiveData<UserWithAlbums> mUserWithAlbums = new MutableLiveData<>();
    private long lastInsertedUserId;
    private long lastInsertedAlbumId;
    private long lastInsertedPhotoId;


    //*** Constructor
    UserAlbumsRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDAO = db.userDAO();
        photoDAO = db.photoDAO();
        mUserData = userDAO.getUserWithCompanyWithAddressWithGeo(1);
        mUserWithAlbums = userDAO.getUsersWithAlbums(1);
        //showAllInfoOfUser(1);
    }

    //*** User-metoder:
    LiveData<List<User>> getUsers() {
        return mUsers;
    }

    public void showAllInfoOfUser(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserWithAlbums = userDAO.getUsersWithAlbums(userId);
            mUserData = userDAO.getUserWithCompanyWithAddressWithGeo(userId);
        });
    }

    public long insertUser(User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            lastInsertedUserId = userDAO.insertUser(user);
            //Ny bruker:
            //long userId = userPlaylistDAO.insert(user);
            // Legger til noen demospillelister:
            //addDemoPlaylistsToUser(userId);
        });
        return lastInsertedUserId;
    }

    public void deleteUser(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(userId);
        });
    }

    public long addAlbum(Album album) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            lastInsertedAlbumId = userDAO.insertAlbum(album);
        });
        return lastInsertedAlbumId;
    }

    public void deleteAlbum(long albumId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.deleteAlbum(albumId);
        });
    }

    public long addPhoto(Photo photo) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            lastInsertedPhotoId = photoDAO.insert(photo);
        });
        return lastInsertedPhotoId;
    }

    public void deletePhoto(long photoId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            photoDAO.delete(photoId);
        });
    }

    public LiveData<UserWithAlbums> showAlbumsAndPhotos(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserWithAlbums = userDAO.getUsersWithAlbums(userId);
        });
        return mUserWithAlbums;
    }

    public LiveData<UserWithCompanyWithAddressWithGeo> showUserWithCompanyWithAddressWithGeo(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserData = userDAO.getUserWithCompanyWithAddressWithGeo(userId);
        });
        return mUserData;
    }

    public LiveData<UserWithAlbums> getAlbumsAndPhotos() {
        return mUserWithAlbums;
    }

    public LiveData<UserWithCompanyWithAddressWithGeo> getUserWithCompanyWithAddressWithGeo() {
        return mUserData;
    }

   /* //*** UserPlayLists-metoder:
    public LiveData<UserPlaylists> getUsersPlaylists(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserPlaylists = userDAO.getUserPlaylists(userId);
        });
        return mUserPlaylists;
    }*/
}