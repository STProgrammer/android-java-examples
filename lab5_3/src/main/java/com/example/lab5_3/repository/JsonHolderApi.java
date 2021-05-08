package com.example.lab5_3.repository;

import com.example.lab5_3.models.User;
import com.example.lab5_3.models.Album;
import com.example.lab5_3.models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonHolderApi {
    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/albums")
    Call<List<Album>> getUser(@Query("userId") Integer userId);

    @GET("/photos")
    Call<List<Photo>> getAlbum(@Query("albumId") Integer albumId);

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") Integer id);
}