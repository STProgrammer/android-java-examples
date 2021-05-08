package com.wfamedia.dptest3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.widget.TextView;
import com.wfamedia.dptest3.model.Album;
import com.wfamedia.dptest3.viewmodel.MyViewModel;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

/**
 *
 * Demonstrerer Hilt sammen med MyViewModel, MyRepository som igjen "injiserer" en Retrofit-instans.
 * MERK! MyViewModel arver IKKE fra ViewModel - så data opprettholdes IKKE ved skjermrotasjon.
 *
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    MyViewModel myViewModel;        //Setter inn (injects) en MyViewModel-instans

    protected List<Album> userAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);

        // FANGER OPP ALBUM FOR BRUKER 1:
        // 1. Initialiserer ViewModel-objektet: Gjøres vha. DependencyInjection og Hilt (se over)
        // 2. Lager et Observe-objekt:
        final Observer<List<Album>> albumObservatør = new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable final List<Album> allAlbums) {
                userAlbums = allAlbums;
                StringBuffer stringBuffer = new StringBuffer();
                for (Album album: userAlbums) {
                    stringBuffer.append(album.getTitle());
                    stringBuffer.append("\n");
                }
                textView.setText(stringBuffer.toString());
            }
        };
        // 3. Start observering:
        myViewModel.getAlbums(1).observe(this, albumObservatør);

        // FANGER OPP EV. FEILMELDINGER:
        // 1. Initialiserer ViewModel-objektet: Gjøres vha. DependencyInjection og Hilt (se over)
        // 2. Lager et Observe-objekt:
        final Observer<String> feilObservatør = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String errorMessage) {
                textView.setText(errorMessage);
            }
        };
        // 3. Start observering:
        myViewModel.getErrorMessage().observe(this, feilObservatør);
    }
}