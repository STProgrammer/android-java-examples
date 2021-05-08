package com.wfamedia.albumsforuser2021httpurlcon.repository;

import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wfamedia.albumsforuser2021httpurlcon.model.Album;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class AlbumsRepository {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private static AlbumsRepository albumsRepository;
    public static AlbumsRepository getInstance(){
        if (albumsRepository == null){
            albumsRepository = new AlbumsRepository();
        }
        return albumsRepository;
    }

    private MutableLiveData<String> errorMessage;
    private MutableLiveData<List<Album>> albumsData;

    private DownloadAlbumsAsyncTask downloadAlbumsAsyncTask;

    private AlbumsRepository() {
        errorMessage = new MutableLiveData<>();
        albumsData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Album>> getAlbumsForUser(int userId) {
        downloadAlbumsAsyncTask = new DownloadAlbumsAsyncTask();
        downloadAlbumsAsyncTask.execute(userId);
        return albumsData;
    }

    public class DownloadAlbumsAsyncTask extends AsyncTask<Integer, Integer, List<Album>> {
        /**
         * Her lastes det ned en JSON-string som konverteres til en liste med Album-objekter.
         */
        @Override
        protected List<Album> doInBackground(Integer... params) {
            ArrayList<Album> albumList = null;
            String jsonStringFromServer = "";

            // isCancelled: Returns true if this task was cancelled before it completed normally
            if (!isCancelled() && params != null && params.length > 0) {
                int userId = params[0];
                String urlParams = null;
                try {
                    //Koder parametrene (i tilfelle ukurante tegn):
                    urlParams = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
                    String url = BASE_URL + "albums?" + urlParams;
                    jsonStringFromServer = downloadDataFromServer(url);

                    // Konverterer fra JSON til en liste med Album-objekter:
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Album>>() {
                    }.getType();
                    albumList = gson.fromJson(jsonStringFromServer, type);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return albumList;
        }

        @Override
        protected void onPostExecute(List<Album> albumList) {
            // Ferdig!!
            albumsData.setValue(albumList);
        }

        private String downloadDataFromServer(String urlString) {
            HttpsURLConnection conn = null;
            StringBuilder serverResponse = new StringBuilder();
            try {
                URL url = new URL(urlString);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestProperty("Connection", "close");
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                // Utfører HTTP-forespørsel:
                conn.connect();
                int responseCode = conn.getResponseCode();  //BLOKKERENDE!!
                switch (responseCode) {
                    case HttpURLConnection.HTTP_OK:
                        //Leser inputstream...
                        readServerResponse(conn.getInputStream(), serverResponse);
                        break;
                    default:
                        serverResponse.append("Ingen/feil svar fra server...");
                        break;
                }

            } catch (SocketTimeoutException ste) {
                ste.printStackTrace();
                serverResponse.append("Socket timeout exception:" + ste.getMessage());
            } catch (MalformedURLException e) {
                Log.d("HTTP-test", "Malformed URL Exception.");
                serverResponse.append(e.getMessage());
            } catch (IOException e) {
                Log.d("HTTP-test", "IO Exception: " + e.getMessage());
                serverResponse.append(e.getMessage());
            } catch (Exception e) {
                Log.d("HTTP-test", "Exception: " + e.getMessage());
                serverResponse.append(e.getMessage());
            } finally {
                if (conn != null)
                    conn.disconnect();
            }
            return serverResponse.toString();
        }

        //Leser svar fra server vha. InputStream-objektet:
        private void readServerResponse(InputStream is, StringBuilder serverResponse) {
            String line;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = br.readLine()) != null) {
                    serverResponse.append(line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
