package com.example.oblig3triviaquiz.repository;

import com.example.oblig3triviaquiz.model.QuizData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface QuizAPI {
    @GET("/api.php/")
    Call<QuizData> downloadQuiz(@QueryMap Map<String, String> urlArguments);
}
