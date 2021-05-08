package com.example.oblig4;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.oblig4.databinding.ActivityMainBinding;
import com.example.oblig4.entities.Album;
import com.example.oblig4.entities.AlbumPhotoCrossRef;
import com.example.oblig4.entities.AlbumWithPhotos;
import com.example.oblig4.entities.Photo;
import com.example.oblig4.entities.UserWithCompanyWithAddressWithGeo;
import com.example.oblig4.viewmodel.UserAlbumsViewModel;


public class MainActivity extends AppCompatActivity {


    UserAlbumsViewModel userAlbumsViewModel;

    // Bruker ViewBinding for å unngå bruk av findViewById ...
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // ViewBinding:
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(activityMainBinding.getRoot());

        userAlbumsViewModel = new ViewModelProvider(this).get(UserAlbumsViewModel.class);



        userAlbumsViewModel.getAllUsersWithCompanyWithAddressWithGeo().observe(this, usersList -> {
            if (usersList != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (UserWithCompanyWithAddressWithGeo uncwa: usersList) {
                    stringBuilder.append("[" + uncwa.user.userId + "] " + uncwa.user.name + " " + uncwa.user.email + "\n")
                            .append(uncwa.address.zipCode + " " + uncwa.address.street + " " + uncwa.address.suite + "\n");
                }
                activityMainBinding.tvUsers.setText(stringBuilder.toString());
            }
            ViewGroup.LayoutParams params = activityMainBinding.tvUsers.getLayoutParams();

            int height_in_pixels = activityMainBinding.tvUsers.getLineCount()
                    * activityMainBinding.tvUsers.getLineHeight(); //approx height text
            params.height = height_in_pixels;
            activityMainBinding.tvUsers.setLayoutParams(params);
        });

        /*userAlbumsViewModel.getUserWithAlbums().observe(this, uwa -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.format("Album(s) for user %s \n", String.valueOf(userAlbumsViewModel.getUserIdToShowAlbums())));
            if (uwa != null) {
                for (AlbumWithPhotos album : uwa.albums) {
                    stringBuilder.append("[" + album.album.albumId + "] " + album.album.title + "\n");
                    for (Photo photo : album.photos) {
                        stringBuilder.append(" => " + "[" + photo.photoId + "] " + photo.title + "\n");
                    }
                }
            }
            activityMainBinding.tvAlbums.setText(stringBuilder.toString());
        });*/
    }

    public void addUser(View view) {
        try {
            userAlbumsViewModel.insertCompleteUser();
        } catch (Exception e) {
            Toast.makeText(this, "Add a number", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUser(View view) {
        try {
            long userId = Long.parseLong(activityMainBinding.userIdToDelete.getText().toString());
            userAlbumsViewModel.deleteUser(userId);
        } catch (Exception e) {
            Toast.makeText(this, "Type in correct ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void addAlbum(View view) {
        try {
            long userId = Long.parseLong(activityMainBinding.userIdToAddAlbum.getText().toString());
            int randNum = (int) (Math.random() * 1000);
            Album album = new Album("Sommerbilder" + randNum,  userId);
            userAlbumsViewModel.addAlbum(album);
        } catch (Exception e) {
            Toast.makeText(this, "Type in correct ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void addPhoto(View view) {
        try {
            long albumId = Long.parseLong(activityMainBinding.albumIdToAddPhoto.getText().toString());
            int randNum = (int) (Math.random() * 1000);
            Photo photo = new Photo("Noe bilde" + randNum, "https://via.placeholder.com/600/24f355",  "https://via.placeholder.com/150/24f355");
            userAlbumsViewModel.addPhoto(photo, albumId);
        } catch (Exception e) {
            Toast.makeText(this, "Type in correct ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAlbum(View view) {
        try {
            long albumId = Long.parseLong(activityMainBinding.albumIdToDelete.getText().toString());
            userAlbumsViewModel.deleteAlbum(albumId);
        } catch (Exception e) {
            Toast.makeText(this, "Type in correct ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void showAlbumsAndPhotos(View view) {
        try {
            userAlbumsViewModel.setUserIdToShowAlbums(Long.parseLong(activityMainBinding.userIdToDislayAlbums.getText().toString()));
            userAlbumsViewModel.showAlbumsAndPhotos(userAlbumsViewModel.getUserIdToShowAlbums());

            userAlbumsViewModel.showAlbumsAndPhotos(userAlbumsViewModel.getUserIdToShowAlbums()).observe(this, uwa -> {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("Album(s) for user %s \n", String.valueOf(userAlbumsViewModel.getUserIdToShowAlbums())));
                if (uwa != null) {
                    for (AlbumWithPhotos album : uwa.albums) {
                        stringBuilder.append("[" + album.album.albumId + "] " + album.album.title + "\n");
                        for (Photo photo : album.photos) {
                            stringBuilder.append(" => " + "[" + photo.photoId + "] " + photo.title + "\n");
                        }
                    }
                }
                activityMainBinding.tvAlbums.setText(stringBuilder.toString());

                ViewGroup.LayoutParams params = activityMainBinding.tvAlbums.getLayoutParams();

                TextView tv = (TextView) findViewById(R.id.tvUsers);
                int height_in_pixels = activityMainBinding.tvAlbums.getLineCount()
                        * activityMainBinding.tvAlbums.getLineHeight(); //approx height text
                params.height = height_in_pixels;
                activityMainBinding.tvAlbums.setLayoutParams(params);


            });
        } catch (Exception e) {
            Toast.makeText(this, "Type in correct ID", Toast.LENGTH_SHORT).show();
        }



    }



}