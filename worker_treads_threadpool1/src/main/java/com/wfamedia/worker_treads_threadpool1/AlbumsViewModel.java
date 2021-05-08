package com.wfamedia.worker_treads_threadpool1;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import java.util.concurrent.Executor;

public class AlbumsViewModel extends ViewModel {
    private MutableLiveData<List<Album>> albums;
    private MutableLiveData<String> error;

    private AlbumsRepository albumsRepository;

    public AlbumsViewModel(Executor executor) {
        this.albums = new MutableLiveData();
        this.error = new MutableLiveData<>();
        this.albumsRepository = new AlbumsRepository(executor);
    }

    public MutableLiveData<List<Album>> getAlbums() {
        return albums;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void downloadAlbumsForUser(int userId) {
        albumsRepository.downloadAlbumsForUser(userId, new AlbumsRepository.RepositoryCallback<Response>() {
            @Override
            public void onComplete(ServerResult<Response> serverResult) {

                // Alt ok:
                if (serverResult instanceof ServerResult.Success) {
                    ServerResult.Success success = (ServerResult.Success) serverResult;
                    Response response = (Response)success.getAlbumsResponse();
                    List<Album> albumsData = response.getAlbumList();
                    albums.postValue(albumsData);
                }

                // Dersom feil oppsto:
                if (serverResult instanceof ServerResult.Error) {
                    ServerResult.Error e = (ServerResult.Error) serverResult;
                    Exception ex = (Exception) e.getException();
                    String errorMessage = ex.getMessage();
                    error.postValue(errorMessage);
                }
            }
        });
    }
}
