package com.wfamedia.albumsforuser2021httpurlcon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.wfamedia.albumsforuser2021httpurlcon.fragments.AlbumsFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragment = AlbumsFragment.newInstance(1);
            replaceFragmentWidth(fragment, false);
        }
    }

    public void replaceFragmentWidth(Fragment newFragment, boolean addTobackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (addTobackStack)
            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .add(R.id.containerFragment, newFragment)
                    .commit();
        else
            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.containerFragment, newFragment)
                    .commit();
    }
}