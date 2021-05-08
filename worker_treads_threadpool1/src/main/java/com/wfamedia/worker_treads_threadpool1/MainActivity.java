package com.wfamedia.worker_treads_threadpool1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Eksempel med følgende "momenter" :
 * - Repository, ViewModel, LiveData:
 *      AlbumsRepository
 *      AlbumsViewModel
 *      Album
 * - ViewModelFactory for å kunne sende med parametre (executor) til konstruktøren (se MyViewModelFactory).
 * - Multithreading vha. thread pool. Opprettes vha. Executors.newFixedThreadPool(n).
 *   Tråder opprettes i MyApplication og brukes i AlbumsRepository-klassen.
 * - Egen Application-klasse, MyApplication, (der thread poolen initialiseres).
 * - Definert eget interface, RepositoryCallback, i Repository-klassen som
 *   brukes til å "kalle tilbake" til ViewModel-objektet.
 *
 *  - Legg også merke til :
 *      ServerResult - generisk klasse som pakker inn responsen fra serveren.
 *      Response - svar fra server, eller feilmelding.
 *      ResponseParser - parser svar, legger inn og returnerer et Response-objekt.
 *
 *  Eksemplet er basert på: https://developer.android.com/guide/background/threading
 */
public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvResult;

    private AlbumsViewModel albumsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        tvResult = findViewById(R.id.tvResult);

        Executor executorService = ((MyApplication)getApplication()).myExecutorService;

        albumsViewModel = new ViewModelProvider(this, new MyViewModelFactory(executorService)).get(AlbumsViewModel.class);

        // Fanger opp endringer i ViewModelen sin albumsData:
        final Observer<List<Album>> albumsObserver = new Observer<List<Album>>() {
            @Override
            public void onChanged(final List<Album> result) {

                // Viser kun TITTEL på alle album:
                StringBuffer resultat = new StringBuffer();
                int i=1;
                for (Album album: result) {
                    resultat.append(i++ + ". " + album.getTitle() + "\n");
                }
                if (result.size()>0)
                    tvResult.setText(resultat.toString());
                else
                    tvResult.setText("Fant ingen album!");
            }
        };
        albumsViewModel.getAlbums().observe(this, albumsObserver);

        // For feilsituasjoner:
        final Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(final String errorMesage) {
                tvResult.setText(errorMesage);
            }
        };
        albumsViewModel.getError().observe(this, errorObserver);
    }

    public void doDownload(View view) {
        albumsViewModel.downloadAlbumsForUser(1);
    }
}