package com.wfamedia.roomusers2021.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wfamedia.roomusers2021.entities.User;
import com.wfamedia.roomusers2021.entities.UserPlaylists;

import java.util.List;

/*
    NB! AndroidViewModel krever ekstra dependencies i build.gradle
 */
public class UserPlaylistViewModel extends AndroidViewModel {
    private UserPlaylistRepository mRepository;
    private LiveData<List<User>> mUsers;

    public UserPlaylistViewModel(Application application) {
        super(application);
        mRepository = new UserPlaylistRepository(application);
        mUsers = mRepository.getUsers();
    }

    //*** User-metoder:
    public LiveData<List<User>> getAllUsers() {
        return mUsers;
    }

    public void insert(User user) {
        mRepository.insert(user);
    }

    public void deleteUser(long userId) {
        mRepository.deleteUser(userId);
    }

    //*** UserPlaylists-metoder:
    public UserPlaylists getUsersPlaylists(long userId) {
        return mRepository.getUsersPlaylists(userId);
    }

    //*** Playlist-metoder:
    public void addDemoPlaylistsToUser(long userId) {
        mRepository.addDemoPlaylistsToUser(userId);
    }
}
