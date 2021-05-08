package com.example.oblig3triviaquiz.model;

import java.util.ArrayList;
import java.util.List;

public class QuizData {
    private int response_code;
    private ArrayList<Question> results;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public ArrayList<Question> getResults() {
        return results;
    }

    public void setResults(ArrayList<Question> results) {
        this.results = results;
    }
}
