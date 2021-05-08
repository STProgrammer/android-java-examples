package com.wfamedia.location3.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.wfamedia.location3.entities.MyLocation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MERK: version = 1
 * Dersom endringer i databasestruktur økes versjonsnummeret og man legger til en/flere Migrate-
 * instanser.
 */
@Database(entities = {MyLocation.class}, version = 1)
public abstract class MyLocationsDatabase extends RoomDatabase {

    public abstract MyLocationDAO myLocationDAO();

    // volatile: har sammenheng med multithreading. Sikrer at alle tråder ser samme kopi av INSTANCE.
    private static volatile MyLocationsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyLocationsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyLocationsDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MyLocationsDatabase.class, "mylocations_database")
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {

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
                MyLocationDAO dao = INSTANCE.myLocationDAO();
                dao.deleteAll();
                dao.insert(new MyLocation(0,0,0));
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}