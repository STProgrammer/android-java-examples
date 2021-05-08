package com.wfamedia.worker_treads_threadpool1;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import javax.net.ssl.HttpsURLConnection;

public class AlbumsRepository {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private Executor executor=null;
    private ServerResult<Response> serverResult=null;

    // NB! Et interface som brukes til å "kalle tilbake" til ViewModel-objektet:
    interface RepositoryCallback<T> {
        void onComplete(ServerResult<T> serverResult);
    }

    // Konstruktør:
    public AlbumsRepository(Executor executor) {
        this.executor = executor;
    }

    public void downloadAlbumsForUser(final int userId, final RepositoryCallback<Response> callback) {

        // Her brukes ExecutorService-objektet, definert i MyApplication:
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Last ned ny versjon kun dersom serverResult == null:
                    if (serverResult==null) {
                        serverResult = downloadAlbumsForUserSynchronous(userId);
                    }
                    callback.onComplete(serverResult);
                } catch (Exception e) {
                    ServerResult<Response> errorServerResult = new ServerResult.Error<>(e);
                    callback.onComplete(errorServerResult);
                }
            }
        });
    }

    public ServerResult<Response> downloadAlbumsForUserSynchronous(int userId) {
        try {
            String urlString = BASE_URL + "albums?userId=" + String.valueOf(userId);
            URL url = new URL(urlString);

            HttpsURLConnection httpConnection = (HttpsURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Connection", "close");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection = (HttpsURLConnection) url.openConnection();

            // Utfører HTTP-forespørsel:
            httpConnection.connect();
            int responseCode = httpConnection.getResponseCode();  //BLOKKERENDE!!

            switch (responseCode) {
                case HttpURLConnection.HTTP_OK:
                    ResponseParser responseParser = new ResponseParser();
                    Response response = null;
                    //Pakker svaret inn i et Response-objekt:
                    response = responseParser.parse(httpConnection.getInputStream());
                    return new ServerResult.Success<>(response);
                default:
                    //Pakker feilmeldinga inn i et Exception-objekt:
                    return new ServerResult.Error<>(new Exception("Noe feilet - Ingen/feil svar fra server..."));

            }
        } catch (Exception e) {
            return new ServerResult.Error<Response>(e);
        }
    }
}
