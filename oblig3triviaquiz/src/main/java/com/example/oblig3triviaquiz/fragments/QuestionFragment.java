package com.example.oblig3triviaquiz.fragments;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.oblig3triviaquiz.R;
import com.example.oblig3triviaquiz.model.Question;
import com.example.oblig3triviaquiz.viewmodel.QuizViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private QuizViewModel quizViewModel;

    // TODO: Rename and change types of parameters
    private int position;
    private String mParam2;

    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionFragment newInstance(int position) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
        }
        prefs =  PreferenceManager.getDefaultSharedPreferences(requireContext());
        editor = prefs.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        int sizeOfQuiz = quizViewModel.getSizeOfQuiz();


        if (position + 1 == sizeOfQuiz) {
            Button btnSubmit = view.findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit();
                }
            });
            btnSubmit.setVisibility(View.VISIBLE);
        }

        //Getting and setting the questions on the fragment
        ArrayList<Question> questionsList = quizViewModel.getQuizData().getValue().getResults();
        Question question = questionsList.get(this.position);

        // Showing the text
        TextView tvQuestionNr = view.findViewById(R.id.tv_question_nr);
        tvQuestionNr.setText(String.valueOf(this.position + 1));
        TextView tvQuestionNr2 = view.findViewById(R.id.tv_question_nr2);
        tvQuestionNr2.setText(getString(R.string.question_number_2)+(this.position + 1) + "/" + questionsList.size());
        TextView tvQuestionCategory = view.findViewById(R.id.tv_question_category);
        tvQuestionCategory.setText(getString(R.string.tv_question_category)+question.getCategory());
        TextView tvQuestionDifficulty = view.findViewById(R.id.tv_question_difficulty);
        tvQuestionDifficulty.setText(getString(R.string.tv_question_difficulty)+question.getDifficulty());
        TextView tvQuestion = view.findViewById(R.id.tv_question);
        tvQuestion.setText(decodeHtmlString(question.getQuestion()));

        /*String correct_answer = decodeHtmlString(question.getCorrect_answer());
        String[] incorrect_answers = question.getIncorrect_answers();
        ArrayList<String> alternatives = new ArrayList<>();
        alternatives.add(correct_answer);

        for (String answer: incorrect_answers) {
            alternatives.add(decodeHtmlString(answer));
        }*/

        String[] alternatives = question.getAll_answers();
        int size = alternatives.length;
        int correctIndex = question.getCorrect_index();

        //Adding radio buttons
        RadioButton[] rb = new RadioButton[size];
        RadioGroup radioGroup = view.findViewById(R.id.rb_alternatives);
        radioGroup.removeAllViews();
        for(int i=0; i < size; i++) {
            rb[i]  = new RadioButton(getContext());
            radioGroup.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
            rb[i].setText(decodeHtmlString(alternatives[i]));
            rb[i].setTag(position + "." + i);
            int index = i;
            rb[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    question.setChecked_index(index);
                    //editor.putBoolean("bntChecked", ((RadioButton)v).isChecked()).apply();
                    onRadioButtonClicked(view, index, correctIndex, position, question);
                }
            });
            if (index == question.getChecked_index()) {
                rb[i].setChecked(true);
                //onRadioButtonClicked(view, index, correctIndex, position, question);
            }
        }

        /*quizViewModel.getQuizData().observe(getViewLifecycleOwner(), quizData -> {


        });*/
    }




    public void onRadioButtonClicked(View view, int index, int correctIndex, int position, Question question) {
        // Is the button now checked?
        RadioButton rb = view.findViewWithTag(position + "." + index);
        boolean checked = rb.isChecked();

        if (checked) {
            question.setChecked(1);
            question.setChecked_index(index);
            if (index == correctIndex) {
                question.setCorrect(1);
            } else {
                question.setCorrect(0);
            }
        }
        quizViewModel.writeInternalFile(getContext(), Objects.requireNonNull(quizViewModel.getQuizData().getValue()).getResults());
        quizViewModel.setTotalAnswers();
    }


    public void submit() {
        quizViewModel.setForceReload(true);
        quizViewModel.setPoints();
        quizViewModel.resetQuizData(getActivity());
        editor.putInt("totalAnswers", 0).apply();
        editor.putInt("quizSize", Integer.parseInt(prefs.getString("amount", "1"))).apply();
    }


    private String decodeHtmlString(String stringWithHtmlCodes) {
        Spanned decodedString = null;
        if (Build.VERSION.SDK_INT >= 24)
            decodedString = Html.fromHtml(stringWithHtmlCodes ,
                    Html.FROM_HTML_MODE_LEGACY);
        else
            decodedString = Html.fromHtml(stringWithHtmlCodes);
        return decodedString.toString();
    }
}