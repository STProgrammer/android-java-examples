package com.wfamedia.worker_treads_threadpool1;
import java.util.List;

/**
 * Pakker inn data eller feilmelding.
 */
public class Response {
    private List<Album> albumList;
    private String error;

    public Response(List<Album> albumList) {
        this.albumList = albumList;
        this.error = "";
    }

    public Response(String message) {
        this.albumList = null;
        this.error = error;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }
}
