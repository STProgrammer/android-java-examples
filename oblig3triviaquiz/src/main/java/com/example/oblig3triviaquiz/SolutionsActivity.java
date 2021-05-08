package com.example.oblig3triviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oblig3triviaquiz.model.Question;
import com.example.oblig3triviaquiz.viewmodel.QuizViewModel;

import java.util.ArrayList;

public class SolutionsActivity extends AppCompatActivity {

    QuizViewModel quizViewModel;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);

        recyclerView = findViewById(R.id.recyclerView);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        ArrayList<Question> quizDataSet = quizViewModel.getQuizData().getValue().getResults();

        // Fyller recycler view-lista:
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(quizDataSet);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setUsersDataSet(quizDataSet);
        recyclerViewAdapter.notifyDataSetChanged();
    }


    public void restartQuiz(View view) {
        quizViewModel.setForceReload(true);
        quizViewModel.resetQuizData(getBaseContext());
        Intent intent = new Intent(this, QuizActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private ArrayList<Question> quizDataSet;

       // private ItemClickListener itemClickListener;

        // Parent activity eller fragment kan implementere dette interfacet for å fange opp click-events:
        /*public interface ItemClickListener {
            void onItemClick(View view, int position);
        }*/

        /**
         * ViewHolder: Representerer View-et for hvert element i lista.
         * Denne klassen må vi lage.
         */
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvQuestion;
            private final TextView tvIncorrectAnswers;
            private final TextView tvCorrectAnswer;

            public MyViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
                tvIncorrectAnswers = (TextView) view.findViewById(R.id.tvIncorrectAnswers);
                tvCorrectAnswer = (TextView) view.findViewById(R.id.tvCorrectAnswer);
            }

            public TextView getTvQuestion() {
                return tvQuestion;
            }

            public TextView getTvIncorrectAnswers() {
                return tvIncorrectAnswers;
            }

            public TextView getTvCorrectAnswer() {
                return tvCorrectAnswer;
            }
        }

        /**
         * Constructor: Mottar datalista, setter privat medlem.
         */
        public RecyclerViewAdapter(ArrayList<Question> dataSet) {
            quizDataSet = dataSet;
        }

        // Oppretter nye views (kalles automatisk av layout manager).
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Oppretter et nytt view som representerer UI til et listeelement.
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
            return new MyViewHolder(view);
        }

        // Fyller view med data fra datalista (metoden må overstyres).
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
            // Fyller TextView med data fra quizDataSet[position]
            Question question = quizDataSet.get(position);

            myViewHolder.getTvQuestion().setText(decodeHtmlString(question.getQuestion()));

            String[] tempList = question.getIncorrect_answers();
            String strIncorrectAnswers = new String();
            for (String str: tempList) {
                strIncorrectAnswers += decodeHtmlString(str);
                strIncorrectAnswers += " \n";
            }
            myViewHolder.getTvIncorrectAnswers().setText(strIncorrectAnswers);

            myViewHolder.getTvCorrectAnswer().setText(decodeHtmlString(question.getCorrect_answer()));
        }

        // Returnere antall elementer i datalista. Kalles automatisk (metoden må overstyres)
        @Override
        public int getItemCount() {
            return quizDataSet.size();
        }

        // Returnerer current item:
        public Question getItem(int id) {
            return quizDataSet.get(id);
        }

        public void setUsersDataSet(ArrayList<Question> dataSet) {
            this.quizDataSet = dataSet;
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
}