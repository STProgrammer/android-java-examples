package com.wfamedia.dptest4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;
import com.wfamedia.dptest4.model.Album;
import com.wfamedia.dptest4.viewmodel.MyViewModel;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

/**
 *
 * Demonstrerer Hilt sammen med MyViewModel, MyRepository som igjen "injects" en Retrofit-instans.
 * MERK! MyViewModel ARVER NÅ fra ViewModel - så data OPPRETTHOLDES ved skjermrotasjon.
 *
 * MERK! All Hilt ViewModels are provided by the ViewModelComponent which follows the same
 *  lifecycle as a ViewModel, and as such, can survive configuration changes.
 *  To scope a dependency to a ViewModel use the @ViewModelScoped annotation.
 *
 *  It means all Hilt ViewModels are provided y the ViewModelComponents which follows the same
 *  lifecycle as a Jetpack’s ViewModel . (Survives screen rotations)
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MyViewModel myViewModel;
    private List<Album> userAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);

        // FANGER OPP ALBUM FOR BRUKER 1:
        // 1. Initialiserer ViewModel-objektet: Gjøres vha. DependencyInjection og Hilt (se over)
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

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
                textView.setBackground(new ColorDrawable(Color.GREEN));
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
                textView.setBackground(new ColorDrawable(Color.RED));
                textView.setText(errorMessage);
            }
        };
        // 3. Start observering:
        myViewModel.getErrorMessage().observe(this, feilObservatør);
    }
}