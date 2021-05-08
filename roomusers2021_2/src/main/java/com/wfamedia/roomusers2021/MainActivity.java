package com.wfamedia.roomusers2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.wfamedia.roomusers2021.databinding.ActivityMainBinding;
import com.wfamedia.roomusers2021.db.UserRoomDatabase;
import com.wfamedia.roomusers2021.entities.Playlist;
import com.wfamedia.roomusers2021.entities.User;
import com.wfamedia.roomusers2021.entities.UserPlaylists;
import com.wfamedia.roomusers2021.viewmodel.UserPlaylistViewModel;

public class MainActivity extends AppCompatActivity {

    private UserPlaylistViewModel mUserPlaylistViewModel;

    // Bruker ViewBinding for å unngå bruk av findViewById ...
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding:
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(activityMainBinding.getRoot());

        mUserPlaylistViewModel = new ViewModelProvider(this).get(UserPlaylistViewModel.class);
        mUserPlaylistViewModel.getAllUsers().observe(this, users -> {
            StringBuffer stringBuffer = new StringBuffer();
            for (User user : users) {
                stringBuffer.append(user.id + " " + user.firstName + " " + user.lastName + "\n");
            }
            activityMainBinding.tvUsers.setText(stringBuffer.toString());
        });
    }

    public void addUser(View view) {
        try {
            String name = activityMainBinding.etUser.getText().toString();
            mUserPlaylistViewModel.insert(new User(name, "Nordmann"));
        } catch (Exception e) {
            Toast.makeText(this, "Oppgi et navn ...", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowPlaylists(View view) {
        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
            int userId = 0;
            try {
                userId = Integer.valueOf(activityMainBinding.userIdToDislay.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "Oppgi korrekt ID", Toast.LENGTH_SHORT).show();
                return;
            }
            activityMainBinding.tvPlaylistText.setText("Spillelister for bruker " + String.valueOf(userId));

            UserPlaylists userPlaylists = mUserPlaylistViewModel.getUsersPlaylists(userId);
            StringBuffer stringBuffer = new StringBuffer();
            if (userPlaylists != null) {
                for (Playlist playlist : userPlaylists.playlists) {
                    stringBuffer.append(playlist.name + ", " + playlist.genre + "\n");
                }
                activityMainBinding.tvPlaylists.setText(stringBuffer.toString());
            }
        });

    }

    public void deleteUser(View view) {
        try {
            int userId = Integer.valueOf(activityMainBinding.userIdToDelete.getText().toString());
            mUserPlaylistViewModel.deleteUser(userId);
        } catch (Exception e) {
            Toast.makeText(this, "Oppgi korrekt ID", Toast.LENGTH_SHORT).show();
        }
    }
}