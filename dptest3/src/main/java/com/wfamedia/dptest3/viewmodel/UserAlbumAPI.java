package com.wfamedia.dptest3.viewmodel;

import com.wfamedia.dptest3.model.Album;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// NB! Se https://jsonplaceholder.typicode.com/guide/
// Her sender vi en forespørsel mot https://jsonplaceholder.typicode.com/albums?userId=userId   //der userId er et tall, 1,2 osv.
public interface UserAlbumAPI {
    @GET("/albums/")
    Call<List<Album>> getAlbumsForUser(@Query("userId") Integer userId);
}
