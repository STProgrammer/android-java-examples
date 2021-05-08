package com.wfamedia.retrofithttpgson1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonHolderApi {
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") Integer id);

    @POST("posts")
    Call<Post> postPost(@Body Post post);
}
