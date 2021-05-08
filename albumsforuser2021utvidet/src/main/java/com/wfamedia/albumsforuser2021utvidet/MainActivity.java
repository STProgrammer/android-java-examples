package com.wfamedia.albumsforuser2021utvidet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wfamedia.albumsforuser2021utvidet.fragments.AlbumsFragment;

/**
 * Viser hvordan man kan kombinere Activity-meny med fragmentmenyen.
 * Se onCreateOptionsMenu() og onOptionsItemSelected() både her og i AlbumsFragment.
 */
public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        if (savedInstanceState == null) {
            fragment = AlbumsFragment.newInstance(1);
            replaceFragmentWidth(fragment, false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Håndterer menyvalg:
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                //this.deleteItem();
                break;
        }
        return super.onOptionsItemSelected(item);
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