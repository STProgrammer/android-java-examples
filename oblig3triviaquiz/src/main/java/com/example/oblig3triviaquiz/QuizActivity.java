package com.example.oblig3triviaquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.oblig3triviaquiz.fragments.QuestionFragment;
import com.example.oblig3triviaquiz.fragments.ResultsFragment;
import com.example.oblig3triviaquiz.model.Question;
import com.example.oblig3triviaquiz.model.QuizData;
import com.example.oblig3triviaquiz.viewmodel.QuizViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private static int numberOfPages;
    private boolean mIsFirstTime;
    //private boolean quizFinished;
    SharedPreferences prefs;
    QuizViewModel quizViewModel;
    Map<String, String> urlArguments;
    QuizData quizData;
    private ImageButton rightNav;
    private ImageButton leftNav;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState == null) {
            mIsFirstTime = true;
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Initialize Bottom Navigation View.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.viewPager);
        errorText = findViewById(R.id.error_texts);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        /*
        quizViewModel.getErrorQuizData().observe(this, intValue -> {
            errorText = findViewById(R.id.error_texts);
            errorText.setVisibility(intValue);
        });

        quizViewModel.getErrorMessage().observe(this, msg -> {
            errorText = findViewById(R.id.error_texts);
            errorText.setText(msg);
        });*/

        final Observer<Integer> progressObservator = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer intValue) {
                errorText = findViewById(R.id.error_texts);
                errorText.setVisibility(intValue);
            }
        };
        quizViewModel.getErrorQuizData().observe(this, progressObservator);

        final Observer<String> errorObservator = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String msg) {
                errorText = findViewById(R.id.error_texts);
                errorText.setText(msg);
            }
        };
        quizViewModel.getErrorMessage().observe(this, errorObservator);


        //Koden under er tatt og delvis redigert fra https://stackoverflow.com/questions/25157067/left-right-arrow-indicators-over-a-viewpager
        rightNav = findViewById(R.id.right_nav);

        leftNav = findViewById(R.id.left_nav);

        rightNav.setVisibility(View.VISIBLE);
        leftNav.setVisibility(View.INVISIBLE);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == numberOfPages - 1) {    //you last page index
                    //do what ever you want
                    rightNav.setVisibility(View.INVISIBLE);
                } else {
                    rightNav.setVisibility(View.VISIBLE);
                }
                if (position == 0) {
                    leftNav.setVisibility(View.INVISIBLE);
                } else {
                    leftNav.setVisibility(View.VISIBLE);
                }
            }
        });

        // Right navigation
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
            }
        });



        // Left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = viewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    viewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    viewPager.setCurrentItem(tab);
                }
            }
        });



        //Merk! Fanger opp endringer i preferansecerdiene, trigger onSharedPreferenceChanged() under:
        prefs =  PreferenceManager.getDefaultSharedPreferences(this);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        prefs.registerOnSharedPreferenceChangeListener(this);
        SharedPreferences.Editor editor = prefs.edit();

        String amount = prefs.getString("amount", "5");
        String category = prefs.getString("category", "");
        String difficulty = prefs.getString("difficulty", "");
        String questionType = prefs.getString("questionType", "");

        //Sjekker mot feil verdi, fordi input gjøres med tastatur
        int tempAmount =0;
        try {
            tempAmount = Integer.parseInt(amount);
            if (tempAmount > 50) {
                amount = "50";
            }
            if (tempAmount < 0) {
                amount = "1";
            }
        }
        catch( Exception e ) {
            amount = "5";
        }


        urlArguments = new HashMap<>();
        urlArguments.put("amount", amount);
        urlArguments.put("category", category);
        urlArguments.put("difficulty", difficulty);
        urlArguments.put("type", questionType);

        // Legger data i ViewModel-objektet:
        quizViewModel.setQuizData(mIsFirstTime, urlArguments, getApplicationContext());

        // 3. Start observering:
        quizViewModel.getQuizData().observe(this, myQuizData -> {
            quizData = myQuizData;
            quizViewModel.setSizeOfQuiz(quizData.getResults().size());
            editor.putInt("quizSize", quizViewModel.getSizeOfQuiz()).apply();
          //  quizViewModel.setForceReload(false);
            numberOfPages = quizViewModel.getSizeOfQuiz() + 1;
            quizViewModel.writeInternalFile(getApplicationContext(), quizData.getResults());
            viewPager = findViewById(R.id.viewPager);
            pagerAdapter = new ScreenSlidePagerAdapter(this);
            viewPager.setOffscreenPageLimit(50);
            viewPager.setAdapter(pagerAdapter);
        });

        quizViewModel.getTotalAnswers().observe(this, totalAnswers -> {
            prefs.edit().putInt("totalAnswers", totalAnswers).apply();
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }


    public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position + 1 == numberOfPages) {
                return ResultsFragment.newInstance(position);
            } else {
                return QuestionFragment.newInstance(position);
            }
        }

        @Override
        public int getItemCount() { return numberOfPages; }
    }

    public static void setNumberOfPages(int numberOfPages) {
        QuizActivity.numberOfPages = numberOfPages;
    }

    public ViewPager2 getViewPager() {
        return viewPager;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences myprefs = PreferenceManager.getDefaultSharedPreferences(QuizActivity.this);
    }
}
