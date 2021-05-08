package com.example.oblig3triviaquiz.model;

import java.util.ArrayList;
import java.util.Collections;

public class Question {

    String category;
    String type;
    String difficulty;
    String question;
    String correct_answer;
    String[] incorrect_answers;
    String[] all_answers;
    int correct_index;
    int checked_index = -1;
    int checked = 0;
    int correct = 0;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }



    public void orderAnswers() {
        ArrayList<String> alternatives = new ArrayList<>();

        alternatives.add(correct_answer);

        for (String answer: incorrect_answers) {
            alternatives.add(answer);
        }

        Collections.shuffle(alternatives);
        this.correct_index = alternatives.indexOf(correct_answer);

        all_answers = new String[incorrect_answers.length + 1];
        all_answers = alternatives.toArray(all_answers);
    }

    public String[] getAll_answers() {
        return all_answers;
    }

    public void setAll_answers(String[] all_answers) {
        this.all_answers = all_answers;
    }

    public int getCorrect_index() {
        return correct_index;
    }

    public void setCorrect_index(int correct_index) {
        this.correct_index = correct_index;
    }

    public int getChecked_index() {
        return checked_index;
    }

    public void setChecked_index(int checked_index) {
        this.checked_index = checked_index;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(String[] incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }
}
