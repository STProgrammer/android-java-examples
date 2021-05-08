package com.wfamedia.fragment2_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

/*
 * Viser to ulike (hoved)fragmenter vha. fragmenttransaksjoner.
 * Det ene hovedfragmentet viser to barnefragmenter.
 * Demonstrerer også addToBackStack()
 */
public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Alternativt:
        if (savedInstanceState == null) {
            MainFragment1 mainFragment1 = new MainFragment1();
            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, mainFragment1)
                    .commit();
        };

    }

    public void showMainFragment1(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .replace(R.id.fragment_container_view, MainFragment1.class, null, "mainFragment1")
            .commit();
    }

    public void showMainFragment2(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .replace(R.id.fragment_container_view, MainFragment2.class, null, "mainFragment1")
            .commit();
    }
}