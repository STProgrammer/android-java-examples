package com.example.oblig3triviaquiz.repository;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.oblig3triviaquiz.QuizActivity;
import com.example.oblig3triviaquiz.model.Question;
import com.example.oblig3triviaquiz.model.QuizData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://opentdb.com/";
    private static final String LOCAL_QUIZ_FILE = "running_quiz";

    private static Repository repository;

    public static Repository getInstance(){
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private final Retrofit retrofit;
    private final QuizAPI quizAPI;
    private Callback<QuizData> quizDataCallBack;
    private final MutableLiveData<QuizData> quizData;
    private final MutableLiveData<String> errorMessage;
    public MutableLiveData<Integer> errorQuizData;
    private int sizeOfQuiz;

    public boolean isForceReload() {
        return forceReload;
    }

    public void setForceReload(boolean forceReload) {
        this.forceReload = forceReload;
    }

    private boolean forceReload;



    public int getSizeOfQuiz() {
        return sizeOfQuiz;
    }

    public void setSizeOfQuiz(int sizeOfQuiz) {
        this.sizeOfQuiz = sizeOfQuiz;
    }

    private Repository() {
        quizData = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        errorQuizData = new MutableLiveData<>();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        quizAPI = retrofit.create(QuizAPI.class);

        quizDataCallBack = new Callback<QuizData>() {
            @Override
            public void onResponse(Call<QuizData> call, Response<QuizData> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                QuizData quizObject = response.body();
                assert quizObject != null;
                ArrayList<Question> questions = quizObject.getResults();
                if (questions.size() == 0) {
                    errorMessage.postValue("Failed to load questions, \nplease change the preferences");
                    errorQuizData.postValue(0);
                } else {
                    for (Question question : questions) {
                        question.orderAnswers();
                    }
                    quizData.postValue(quizObject);
                    setForceReload(false);
                    errorQuizData.postValue(8);
                }
            }

            @Override
            public void onFailure(Call<QuizData> call, Throwable t) {
                errorMessage.postValue(t.getMessage());
                errorQuizData.postValue(0);
            }
        };
    }

    public void resetQuizData(Context context) {
        context.deleteFile(LOCAL_QUIZ_FILE);
    }


    public MutableLiveData<QuizData> getQuizData() {return quizData; }
    public MutableLiveData<Integer> getErrorQuizData() {
        return errorQuizData;
    }


    public MutableLiveData<QuizData> setQuizData(boolean mIsFirstTime, Map<String, String> urlArguments, Context context) {
        if (!mIsFirstTime & quizData.getValue() != null) {
            setSizeOfQuiz(quizData.getValue().getResults().size());
            return quizData;
        }
        ArrayList<Question> questionsList = readInternalFile(context);

        if (questionsList == null | forceReload) {
            // API-kallet:
            Call<QuizData> call = quizAPI.downloadQuiz(urlArguments);
            call.enqueue(quizDataCallBack);
        } else {
            QuizData tempQuizData = new QuizData();
            tempQuizData.setResults(questionsList);
            quizData.setValue(tempQuizData);
            setSizeOfQuiz(quizData.getValue().getResults().size());
        }
        return quizData;
    }

    public void writeInternalFile(Context context, ArrayList<Question> tmpList)
    {
        Gson gson = new Gson();
        String questionListAsString = gson.toJson(tmpList);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(LOCAL_QUIZ_FILE,
                    Context.MODE_PRIVATE);
            fileOutputStream.write(questionListAsString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Question> readInternalFile(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(LOCAL_QUIZ_FILE);
            InputStreamReader inputStreamReader = new
                    InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
            String jsonArrayAsString = stringBuilder.toString();
            //Konverter fra JSON til ArrayList<Question>
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Question>>(){}.getType();
            ArrayList<Question> tmpList = gson.fromJson(jsonArrayAsString,
                    type);
            return tmpList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
            return null;
        }
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
