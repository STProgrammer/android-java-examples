package com.example.lab5_3;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.lab8_2_room_albums.databinding.ActivityMainnnBinding;
import com.example.lab5_3.fragments.UsersListFragment;
import com.example.lab5_3.viewmodel.UserViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private ActivityMainnnBinding activityMainBinding;

    @Inject
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainnnBinding.inflate(getLayoutInflater());

        setContentView(activityMainBinding.getRoot());

        if (savedInstanceState == null) {
            fragment = UsersListFragment.newInstance();
            replaceFragmentWidth(fragment, false);
        }
    }

    public void replaceFragmentWidth(Fragment newFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (addToBackStack)
            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .add(activityMainBinding.containerFragment.getId(), newFragment)
                    .commit();
        else
            fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(activityMainBinding.containerFragment.getId(), newFragment)
                    .commit();
    }
}