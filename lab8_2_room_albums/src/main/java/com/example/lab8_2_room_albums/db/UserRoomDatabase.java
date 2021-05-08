package com.example.lab8_2_room_albums.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lab8_2_room_albums.dao.PhotoDAO;
import com.example.lab8_2_room_albums.dao.UserDAO;
import com.example.lab8_2_room_albums.entities.Address;
import com.example.lab8_2_room_albums.entities.Album;
import com.example.lab8_2_room_albums.entities.AlbumPhotoCrossRef;
import com.example.lab8_2_room_albums.entities.Company;
import com.example.lab8_2_room_albums.entities.Geo;
import com.example.lab8_2_room_albums.entities.Photo;
import com.example.lab8_2_room_albums.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MERK: version = 1
 * Dersom endringer i databasestruktur økes versjonsnummeret og man legger til en/flere Migrate-
 * instanser.
 */





@Database(entities = {User.class, Address.class, Geo.class, Company.class, Album.class, Photo.class, AlbumPhotoCrossRef.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public abstract PhotoDAO photoDAO();

   // public abstract WordDao wordDao();

    // volatile: har sammenheng med multithreading. Sikrer at alle tråder ser samme kopi av INSTANCE.
    private static volatile UserRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserRoomDatabase.class, "mydaaaatabase")
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        /**
         * Called when the database is created for the first time.
         * This is called after all the tables are created.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                UserDAO userDao = INSTANCE.userDAO();
               // userDao.deleteAll();
                PhotoDAO photoDAO = INSTANCE.photoDAO();

                long geo1Id = userDao.insertGeo(new Geo(52.5,72.3));
                long geo2Id = userDao.insertGeo(new Geo(33.3,66.6));
                long address1Id = userDao.insertAddress(new Address("Joke street 1","suite1","Oslo","1234",geo1Id));
                long address2Id = userDao.insertAddress(new Address("Joke street 2","Suite2","Oslo","4321",geo2Id));
                long company1Id = userDao.insertCompany(new Company("Esso","esssss","bs1"));
                long company2Id = userDao.insertCompany(new Company("Walmart","walllll","bs2"));
                long userId1 = userDao.insertUser(new User("Hans Hansen","hans","hans@tullingkkkk.com",address1Id,"12346578","www.tull.no",company1Id));
                long userId2 = userDao.insertUser(new User("Per Pettersen","per","per@tullingkkkk.com",address2Id,"87654321","www.ull.no",company2Id));

                //Album for Hans Hansen:
                Album album1 = new Album("Noe tull bare",  userId1);
                long albumId1 = userDao.insertAlbum(album1);
                Photo photo1 = new Photo("officia porro iure quia iusto qui ipsa ut modi", "https://via.placeholder.com/600/24f355", "https://via.placeholder.com/150/24f355");
                long photoId1 = photoDAO.insert(photo1);
                Photo photo2 = new Photo("culpa odio esse rerum omnis laboriosam voluptate repudiandae", "https://via.placeholder.com/600/d32776", "https://via.placeholder.com/150/d32776");
                long photoId2 = photoDAO.insert(photo2);

                Album album2 = new Album("Et annet tull", userId1);
                long albumId2 = userDao.insertAlbum(album2);

                Photo photo3 = new Photo("mollitia soluta ut rerum eos aliquam consequatur perspiciatis maiores", "https://via.placeholder.com/600/66b7d2", "https://via.placeholder.com/150/66b7d2");
                long photoId3 = photoDAO.insert(photo3);
                Photo photo4 = new Photo("repudiandae iusto deleniti rerum", "https://via.placeholder.com/600/197d29", "https://via.placeholder.com/600/197d29");
                long photoId4 = photoDAO.insert(photo4);
                photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId1, photoId1));
                photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId1, photoId2));
                photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId2, photoId3));
                photoDAO.addToAlbum(new AlbumPhotoCrossRef(albumId2, photoId4));

                //Album for Per Pettersen:
                Album album4 = new Album("Per's album", userId2);
                userDao.insertAlbum(album4);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    // MIGRERING/ENDRING I DATABASEN.
    /*
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user "
                    + " ADD COLUMN birth_year INTEGER");
        }
    };*/


}