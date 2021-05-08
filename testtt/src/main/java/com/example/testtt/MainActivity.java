package com.example.testtt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WordRoomDatabase db = WordRoomDatabase.getDatabase(this);

        WordDao dao = db.wordDao();

        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            // Populate the database in the background.
            // If you want to start with more words, just add them.
            Word word = new Word("Helloooo");
            dao.insert(word);
            word = new Word("Woooorld");
            dao.insert(word);
        });

        dao.getAlphabetizedWords();

    }
}