package com.wfamedia.roomusers2021.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wfamedia.roomusers2021.dao.SongDAO;
import com.wfamedia.roomusers2021.dao.UserPlaylistDAO;
import com.wfamedia.roomusers2021.entities.Playlist;
import com.wfamedia.roomusers2021.entities.Song;
import com.wfamedia.roomusers2021.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MERK: version = 1
 * Dersom endringer i databasestruktur økes versjonsnummeret og man legger til en/flere Migrate-
 * instanser.
 */
@Database(entities = {User.class, Playlist.class, Song.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserPlaylistDAO userPlaylistDAO();

    public abstract SongDAO songDAO();

    // volatile: har sammenheng med multithreading. Sikrer at alle tråder ser samme kopi av INSTANCE.
    private static volatile UserRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserRoomDatabase.class, "users_database")
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
         * @param db
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserPlaylistDAO dao = INSTANCE.userPlaylistDAO();
                dao.deleteAll();
                SongDAO songDAO = INSTANCE.songDAO();

                // User1:
                User user1 = new User("Jens", "Jansen");
                long userId1 = dao.insert(user1);
                //Spillelister for User1:
                Playlist playlist1 = new Playlist("Navn: Rolig", "Sjanger: Chillout", "Avslapping.", userId1);
                long playlistId = dao.insertPlaylist(playlist1);
                Song song1 = new Song("Trallala", "Erik Bye", 1970, playlistId);
                songDAO.insert(song1);
                Song song2 = new Song("Hello there", "Bruce", 1999, playlistId);
                songDAO.insert(song2);

                Playlist playlist2 = new Playlist("Navn: Skitur", "Sjanger: Fjell&vidde", "Turer i fjellet.", userId1);
                dao.insertPlaylist(playlist2);
                Playlist playlist3 = new Playlist("Navn: Trening", "Sjanger: Dunk dunk", "Aktivitet.", userId1);
                dao.insertPlaylist(playlist3);

                // User2:
                User user2 = new User("Guri", "Malla");
                long userId2 = dao.insert(user2);
                //Spillelister for User2:
                Playlist playlist4 = new Playlist("Navn: Easy listening", "Sjanger: Alt mulig", "Selskap.", userId2);
                dao.insertPlaylist(playlist4);
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
    };
    */

}