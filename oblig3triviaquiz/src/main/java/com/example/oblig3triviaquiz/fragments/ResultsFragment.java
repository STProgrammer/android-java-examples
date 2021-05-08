package com.example.oblig3triviaquiz.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oblig3triviaquiz.R;
import com.example.oblig3triviaquiz.SolutionsActivity;
import com.example.oblig3triviaquiz.viewmodel.QuizViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsFragment extends Fragment {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    //private ViewPager2 viewPager;

    private QuizViewModel quizViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int position;

    public ResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentResults.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultsFragment newInstance(int position) {
        ResultsFragment fragment = new ResultsFragment();
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
        prefs =  PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        Button btnShowSolutions = view.findViewById(R.id.btnShowSolutions);

        btnShowSolutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizViewModel.getForceReload()) {
                    Intent intent = new Intent(getActivity(), SolutionsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), R.string.finish_first_text, Toast.LENGTH_LONG).show();
                }
            }
        });

        // 2. Lager et Observe-objekt:
        final Observer<Integer> pointObservator = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer points) {
                //Koden under beregner resultat, setter karakter, og viser fram resultatet
                View view = getView();

                int amount = quizViewModel.getSizeOfQuiz();

                //Første fragment lages før siste fragment, dette er for å unngå feil når man klikker knappen
                if (view != null) {
                    TextView tvResults = view.findViewById(R.id.tvResult);
                    tvResults.setText(getString(R.string.results_text) + points + "/" + amount);

                    TextView tvScore = view.findViewById(R.id.tvScore);

                    //Setter karakterer her
                    double sum = (points*5 / amount);
                    char score = (char) (70 - sum);
                    tvScore.setText(getString(R.string.score_text) + score);
                    //Sletter quiz data, det stod på oppgaven at man skal slette quiz data når quizzen er ferdig
                    quizViewModel.resetQuizData(requireActivity());
                }

            }
        };
        // 3. Start observering:
        quizViewModel.getPoints().observe(requireActivity(), pointObservator);

    }

}
