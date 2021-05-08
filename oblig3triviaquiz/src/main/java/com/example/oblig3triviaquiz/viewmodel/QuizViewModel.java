package com.example.oblig3triviaquiz.viewmodel;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.oblig3triviaquiz.model.Question;
import com.example.oblig3triviaquiz.model.QuizData;
import com.example.oblig3triviaquiz.repository.Repository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

// ViewModel:
public class QuizViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<Integer> totalAnswers;

    private MutableLiveData<Integer> points;


    public void setForceReload(boolean forceReload) {
        repository.setForceReload(forceReload);
    }

    public boolean getForceReload() { return repository.isForceReload(); }

    public QuizViewModel() {
        repository = Repository.getInstance();
        points = new MutableLiveData<>();
        totalAnswers = new MutableLiveData<>();
    }

    public void setPoints() {
        Integer sum = 0;
        ArrayList<Question> questions = Objects.requireNonNull(getQuizData().getValue()).getResults();
        for (Question question : questions) {
            sum += question.getCorrect();
        }
        points.postValue(sum);
    }

    public MutableLiveData<Integer> getPoints() {
        return points;
    }

    public void setSizeOfQuiz(int size) {repository.setSizeOfQuiz(size); }

    public int getSizeOfQuiz() {return repository.getSizeOfQuiz(); }


    public void writeInternalFile(Context context, ArrayList<Question> questions) {
        repository.writeInternalFile(context, questions);
    }

    public LiveData<Integer> getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers() {
        int sum = 0;
        if (getQuizData().getValue() == null) {
            totalAnswers.postValue(0);
        }
        else {
            ArrayList<Question> questions = getQuizData().getValue().getResults();
            for (Question question : questions) {
                sum += question.getChecked();
            }
            totalAnswers.postValue(sum);
        }
    }

    public LiveData<QuizData> setQuizData(boolean mIsFirstTime, Map<String, String> urlArguments, Context context) {
        return repository.setQuizData(mIsFirstTime, urlArguments, context);
    }

    public LiveData<QuizData> getQuizData() {
        return repository.getQuizData();
    }

    public void resetQuizData(Context context) {repository.resetQuizData(context);}

    public LiveData<Integer> getErrorQuizData() { return repository.getErrorQuizData(); }

    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }
}


