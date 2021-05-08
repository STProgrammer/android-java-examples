package com.wfamedia.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Henter referanse til NavController-objektet.
        // Gjøres ulikt avhengig av om man bruker <fragment../> eller <FragmentContainerView.../>
        // i activity_main.xml. Her brukes vi FragmentContainerView i activity_main.xml
        // og finner derfor objektet slik:
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // *** NavigationUI & toolbar:
        // 1. Konfiguerer top app bar (sørger for tilbakeknapp, hamburgermeny osv.)
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        // 2. Konfigurerer toolbar (må være definert i activity_main.xml):
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // 3. Kopler til Toolbar:
        NavigationUI.setupWithNavController(myToolbar, navController, appBarConfiguration);

        // *** NavigationUI & bunnmeny (bottom navigation):
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

        // *** NavigationUI & sidemeny (navigation drawer):
        // SE MainActivityNavDrawer.java
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        this.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
}