package com.wfamedia.nav1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivityNavDrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav_drawer);

        // Henter referanse til NavController-objektet.
        // Gjøres ulikt avhengig av om man bruker <fragment../> eller <FragmentContainerView.../>
        // i activity_main.xml. Her brukes vi FragmentContainerView i activity_main.xml
        // og finner derfor objektet slik:
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // *** NavigationUI & sidemeny (navigation drawer):

        // 1. Konfigurerer toolbar (må være definert i activity_main.xml):
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // 2. Konfiguerer navigation drawer:
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                    .setOpenableLayout(drawerLayout)
                    .build();

        NavigationView navView = findViewById(R.id.nav_view);
        // Kopler til nav drawer:
        NavigationUI.setupWithNavController(navView, navController);
        // NB! Kopler til Toolbar (søreger for hamburger-menyknapp og tilbakeknapper):
        NavigationUI.setupWithNavController(myToolbar, navController, appBarConfiguration);
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