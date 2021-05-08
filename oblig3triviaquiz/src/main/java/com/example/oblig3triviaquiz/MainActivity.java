package com.example.oblig3triviaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.oblig3triviaquiz.fragments.InfoFragment;
import com.example.oblig3triviaquiz.viewmodel.QuizViewModel;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    QuizViewModel quizViewModel;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        prefs =  PreferenceManager.getDefaultSharedPreferences(this);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        prefs.registerOnSharedPreferenceChangeListener(this);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        quizViewModel.getTotalAnswers().observe(this, totalAnswers -> {
            prefs.edit().putInt("totalAnswers", totalAnswers).apply();
            TextView quizState = findViewById(R.id.quizState);
            totalAnswers = prefs.getInt("totalAnswers", 0);
            int quizSize = quizViewModel.getSizeOfQuiz();
            quizState.setText("Fullførte spørsmål: " + totalAnswers + "/" + quizSize);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView quizState = findViewById(R.id.quizState);
        int totalAnswers = prefs.getInt("totalAnswers", 0);
        int quizSize = 0;
        if (quizViewModel.getForceReload()) {
            quizSize = Integer.parseInt(prefs.getString("amount", "5"));
        } else {
            quizSize = prefs.getInt("quizSize", Integer.parseInt(prefs.getString("amount", "5")));
        }
        quizState.setText("Fullførte spørsmål: " + totalAnswers + "/" + quizSize);
    }

    public void startQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void resetQuiz(View view) {
        quizViewModel.setForceReload(true);
        quizViewModel.resetQuizData(this);
        TextView quizState = findViewById(R.id.quizState);
        prefs.edit().putInt("totalAnswers", 0).apply();
        int totalAnswers = prefs.getInt("totalAnswers", 0);
        prefs.edit().putInt("quizSize", Integer.parseInt(prefs.getString("amount", "5"))).apply();
        quizState.setText("Fullførte spørsmål: " + totalAnswers + "/" + prefs.getInt("quizSize", 5));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.show_info:
                InfoFragment infoFragmentDialog = InfoFragment.newInstance();
                infoFragmentDialog.show(getSupportFragmentManager(), "info_dialog");
                return true;
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences myprefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    }
}