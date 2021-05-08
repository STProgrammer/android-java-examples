package com.wfamedia.worker_treads_threadpool1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResponseParser {

    // Lager et Repsonse-objekt bestående av List<Album> eller en String (feilmelding).
    public Response parse(InputStream inputStream) {

        StringBuilder serverResponse = new StringBuilder();
        String line;
        try {
            //Leser svar fra server vha. InputStream-objektet:
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                serverResponse.append(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String jsonString = serverResponse.toString();
        // Konverterer fra JSON til en liste med Album-objekter:
        Gson gson = new Gson();
        try {
            Type type = new TypeToken<ArrayList<Album>>() {}.getType();
            List<Album> albumList = gson.fromJson(jsonString,  type);
            return new Response(albumList);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }

    }

    public Response error(String errorMessage) {
        return new Response(errorMessage);
    }
}
