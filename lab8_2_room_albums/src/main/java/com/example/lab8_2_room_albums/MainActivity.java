package com.example.lab8_2_room_albums;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.lab8_2_room_albums.databinding.ActivityMainBinding;
import com.example.lab8_2_room_albums.entities.AlbumWithPhotos;
import com.example.lab8_2_room_albums.entities.Photo;
import com.example.lab8_2_room_albums.viewmodel.UserAlbumsViewModel;


public class MainActivity extends AppCompatActivity {

    private UserAlbumsViewModel userAlbumsViewModel;
    private long userId;

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


        userAlbumsViewModel.getUserWithCompanyWithAddressWithGeo().observe(this, uncwa -> {
            if(uncwa != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[" + uncwa.user.userId + "] " + uncwa.user.name + "\n")
                        .append("Brukernavn: " + uncwa.user.username + " " + "\n")
                        .append("Email: " + uncwa.user.email + " " + "\n")
                        .append("Phone: " + uncwa.user.phone + "\n")
                        .append("Website: " + uncwa.user.website + "\n")
                        .append("Address: " + uncwa.address.street + " " + uncwa.address.suite + "\n")
                        .append(uncwa.address.city + " " + uncwa.address.zipCode + "\n")
                        .append("Geolocation: latitude: " + uncwa.geo.lat + " \nlongtitude: " + uncwa.geo.lng + "\n")
                        .append("Company name: " + uncwa.company.companyName + "\n")
                        .append("Company catch phrase: " + uncwa.company.catchPhrase + "\n")
                        .append("Company bs: " + uncwa.company.bs + "\n \n")
                        .append("Album liste og bilder: \n");

                activityMainBinding.tvUsers.setText(stringBuilder.toString());
            }
        });

        userAlbumsViewModel.getUserWithAlbums().observe(this, uwa -> {
            if (uwa != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (AlbumWithPhotos album : uwa.albums) {
                    stringBuilder.append("[" + album.album.albumId + "] " + "Tittel: " + album.album.title + "\n");
                    for (Photo photo : album.photos) {
                        stringBuilder.append(" => " + "[" + photo.photoId + "] " + "Tittel: " + photo.title + "Url: " + photo.url + "\n");
                    }
                }
                activityMainBinding.tvUsers.append(stringBuilder.toString());
            }
        });
    }
}