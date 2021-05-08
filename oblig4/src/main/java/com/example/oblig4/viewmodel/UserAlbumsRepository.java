package com.example.oblig4.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.oblig4.dao.PhotoDAO;
import com.example.oblig4.dao.UserDAO;
import com.example.oblig4.db.UserRoomDatabase;
import com.example.oblig4.entities.Address;
import com.example.oblig4.entities.Album;
import com.example.oblig4.entities.AlbumPhotoCrossRef;
import com.example.oblig4.entities.Company;
import com.example.oblig4.entities.Geo;
import com.example.oblig4.entities.Photo;
import com.example.oblig4.entities.User;
import com.example.oblig4.entities.UserWithAlbums;
import com.example.oblig4.entities.UserWithCompanyWithAddressWithGeo;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class UserAlbumsRepository {
    private UserDAO userDAO;
    private PhotoDAO photoDAO;
    private LiveData<List<UserWithCompanyWithAddressWithGeo>> mUserData = new MutableLiveData<>();
    private LiveData<UserWithAlbums> mUserWithAlbums = new MutableLiveData<>();
    private long lastInsertedAlbumId;
    private long lastInsertedPhotoId;
    private long userIdToShowAlbums;

    //*** Constructor
    UserAlbumsRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userDAO = db.userDAO();
        photoDAO = db.photoDAO();
        mUserData = userDAO.getAllUsersWithCompanyWithAddressWithGeo();
    }

    public long getUserIdToShowAlbums() {
        return userIdToShowAlbums;
    }

    public void setUserIdToShowAlbums(long userIdToShowAlbums) {
        this.userIdToShowAlbums = userIdToShowAlbums;
    }

    //*** User-metoder:
    public void insertCompleteUser() {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            long geo1Id = userDAO.insertGeo(new Geo(52.5,72.3));
            long address1Id = userDAO.insertAddress(new Address("Joke street 1","suite1","Oslo","1234",geo1Id));
            long company1Id = userDAO.insertCompany(new Company("Esso","esssss","bs1"));

            int randNum = (int)(Math.random() * 1000);

            long userId1 = userDAO.insertUser(new User("Hans Hansen" + randNum,"hans","hans@tullingkkkk.com",address1Id,"12346578","www.tull.no",company1Id));

            //Album for Hans Hansen:
            Album album1 = new Album("Sommerbilder" + randNum,  userId1);
            long albumId1 = userDAO.insertAlbum(album1);
            Photo photo1 = new Photo("En bilde ute" + randNum, "https://via.placeholder.com/600/24f355", "https://via.placeholder.com/150/24f355");
            long photoId1 = photoDAO.insert(photo1);
            Photo photo2 = new Photo("En bilde inne" + randNum, "https://via.placeholder.com/600/d32776", "https://via.placeholder.com/150/d32776");
            long photoId2 = photoDAO.insert(photo2);

            Album album2 = new Album("Vinterbilder" + randNum, userId1);
            long albumId2 = userDAO.insertAlbum(album2);

            Photo photo3 = new Photo("Snømannen" + randNum, "https://via.placeholder.com/600/66b7d2", "https://via.placeholder.com/150/66b7d2");
            long photoId3 = photoDAO.insert(photo3);
            Photo photo4 = new Photo("Snøhytta" + randNum, "https://via.placeholder.com/600/197d29", "https://via.placeholder.com/600/197d29");
            long photoId4 = photoDAO.insert(photo4);
            photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId1, photoId1));
            photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId1, photoId2));
            photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId2, photoId3));
            photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId2, photoId4));

        });
    }

    public void deleteUser(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.delete(userId);
        //    UserWithAlbums uwa = userDAO.getUserWithAlbums(userIdToShowAlbums);
       //     mUserWithAlbums.postValue(uwa);
        });
    }

    public long addAlbum(Album album) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            try {
                lastInsertedAlbumId = userDAO.insertAlbum(album);
          //      UserWithAlbums uwa = userDAO.getUserWithAlbums(userIdToShowAlbums);
           //     mUserWithAlbums.postValue(uwa);
            } catch (SQLiteConstraintException e) {
            }
        });
        return lastInsertedAlbumId;
    }

    public void deleteAlbum(long albumId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.deleteAlbum(albumId);
         //   UserWithAlbums uwa = userDAO.getUserWithAlbums(userIdToShowAlbums);
         //   mUserWithAlbums.postValue(uwa);
        });
    }

    public long addPhoto(Photo photo, long albumId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            try {
                lastInsertedPhotoId = photoDAO.insert(photo);
                photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId, lastInsertedPhotoId));
             //   UserWithAlbums uwa = userDAO.getUserWithAlbums(userIdToShowAlbums);
             //   mUserWithAlbums.postValue(uwa);
            } catch (SQLiteConstraintException e) {

            }

        });
        return lastInsertedPhotoId;
    }


    public void deletePhoto(long photoId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            photoDAO.delete(photoId);
          //  UserWithAlbums uwa = userDAO.getUserWithAlbums(userIdToShowAlbums);
          //  mUserWithAlbums.postValue(uwa);
        });
    }

    public LiveData<UserWithAlbums> showAlbumsAndPhotos(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserWithAlbums = userDAO.getUserWithAlbums(userId);
        });
        return mUserWithAlbums;
    }

    public LiveData<List<UserWithCompanyWithAddressWithGeo>> showAllUsersWithCompanyWithAddressWithGeo() {
   //     UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserData = userDAO.getAllUsersWithCompanyWithAddressWithGeo();
   //     });
        return mUserData;
    }

    public LiveData<UserWithAlbums> getAlbumsAndPhotos() {
        return mUserWithAlbums;
    }

    public LiveData<List<UserWithCompanyWithAddressWithGeo>> getAllUsersWithCompanyWithAddressWithGeo() {
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