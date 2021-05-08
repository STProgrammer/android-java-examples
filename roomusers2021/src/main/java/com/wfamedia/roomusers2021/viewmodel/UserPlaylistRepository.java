package com.wfamedia.roomusers2021.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wfamedia.roomusers2021.dao.UserPlaylistDAO;
import com.wfamedia.roomusers2021.db.UserRoomDatabase;
import com.wfamedia.roomusers2021.entities.Playlist;
import com.wfamedia.roomusers2021.entities.User;
import com.wfamedia.roomusers2021.entities.UserPlaylists;

import java.util.ArrayList;
import java.util.List;

public class UserPlaylistRepository {
    private UserPlaylistDAO userPlaylistDAO;
    private LiveData<List<User>> mUsers;
    private LiveData<UserPlaylists> mUserPlaylists = new MutableLiveData<>();

    //*** Constructor
    UserPlaylistRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        userPlaylistDAO = db.userPlaylistDAO();
        mUsers = userPlaylistDAO.getAll();
        mUserPlaylists = userPlaylistDAO.getUserPlaylists(0);
    }

    //*** User-metoder:
    LiveData<List<User>> getUsers() {
        return mUsers;
    }

    public void insert (User user) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {

            userPlaylistDAO.insertDemoUser(user);
            //Ny bruker:
            //long userId = userPlaylistDAO.insert(user);
            // Legger til noen demospillelister:
            //addDemoPlaylistsToUser(userId);
        });
    }

    public void deleteUser(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            userPlaylistDAO.delete(userId);
        });
    }

    //*** UserPlayLists-metoder:
    public LiveData<UserPlaylists> getUsersPlaylists(long userId) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserPlaylists = userPlaylistDAO.getUserPlaylists(userId);
        });
        return mUserPlaylists;
    }

    //*** Playlist-metoder:
    public void addDemoPlaylistsToUser(final long userId)
    {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            Playlist playlist1 = new Playlist("Navn: Rolig", "Sjanger: Chillout", "Avslapping.", userId);
            userPlaylistDAO.insertPlaylist(playlist1);
            Playlist playlist2 = new Playlist("Navn: Skitur", "Sjanger: Fjell&vidde", "Turer i fjellet.", userId);
            userPlaylistDAO.insertPlaylist(playlist2);
            Playlist playlist3 = new Playlist("Navn: Trening", "Sjanger: Dunk dunk", "Aktivitet.", userId);
            userPlaylistDAO.insertPlaylist(playlist3);
        });
    }
}