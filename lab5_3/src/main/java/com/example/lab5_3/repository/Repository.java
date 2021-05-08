package com.example.lab5_3.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.lab5_3.models.User;
import com.example.lab5_3.models.Album;
import com.example.lab5_3.models.Photo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    static Repository repository;
    public static Repository getInstance(){
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private final Retrofit retrofit;
    private final JsonHolderApi jsonHolderApi;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<List<Album>> userData;
    private final MutableLiveData<List<User>> userListData;
    private final MutableLiveData<List<Photo>> albumData;
    private final MutableLiveData<Photo> photo;
    public MutableLiveData<Integer> errorUserList;
    public MutableLiveData<Integer> errorUser;
    public MutableLiveData<Integer> errorAlbum;
    public MutableLiveData<Integer> errorPhoto;


    @Inject
    public Repository() {
        errorMessage = new MutableLiveData<>();
        userData = new MutableLiveData<>();
        userListData = new MutableLiveData<>();
        albumData = new MutableLiveData<>();
        photo = new MutableLiveData<>();
        errorUserList = new MutableLiveData<>();
        errorUser = new MutableLiveData<>();
        errorAlbum = new MutableLiveData<>();
        errorPhoto = new MutableLiveData<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonHolderApi = retrofit.create(JsonHolderApi.class);
    }

    public MutableLiveData<Integer> getErrorUserList() {
        return errorUserList;
    }

    public MutableLiveData<Integer> getErrorUser() {
        return errorUser;
    }

    public MutableLiveData<Integer> getErrorAlbum() {
        return errorAlbum;
    }

    public MutableLiveData<Integer> getErrorPhoto() { return errorPhoto;}


    public MutableLiveData<List<User>> getUserListData(boolean mIsFirstTime) {
        if (!mIsFirstTime) {
            return userListData;
        }

        Call<List<User>> call = jsonHolderApi.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<User> users = response.body();
                userListData.postValue(users);
                errorUserList.postValue(8);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                errorMessage.postValue(t.getMessage());
                errorUserList.postValue(0);
            }
        });
        return userListData;
    }




    public MutableLiveData<List<Album>> getUserData(int userId, boolean mIsFirstTime) {
        if (!mIsFirstTime) {
            return userData;
        }


        Call<List<Album>> call = jsonHolderApi.getUser(userId);
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Album> albums = response.body();
                userData.setValue(albums);
                errorUser.setValue(8);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                errorMessage.postValue(t.getMessage());
                errorUser.postValue(0);
            }
        });
        return userData;
    }


    public MutableLiveData<List<Photo>> getAlbumData(int albumId, boolean mIsFirstTime) {
        if (!mIsFirstTime) {
            return albumData;
        }

        Call<List<Photo>> call = jsonHolderApi.getAlbum(albumId);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Photo> photos = response.body();
                errorAlbum.postValue(8);
                albumData.postValue(photos);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                errorMessage.postValue(t.getMessage());
                errorAlbum.postValue(0);
            }
        });
        return albumData;
    }

    public MutableLiveData<Photo> getPhoto(int photoId, boolean mIsFirstTime) {
        if (!mIsFirstTime) {
            return photo;
        }

        Call<Photo> call = jsonHolderApi.getPhoto((photoId));
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Photo photo = response.body();
                Repository.this.photo.postValue(photo);
                errorPhoto.postValue(8);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                errorMessage.postValue(t.getMessage());
                errorPhoto.postValue(0);
            }
        });
        return photo;
    }



    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
