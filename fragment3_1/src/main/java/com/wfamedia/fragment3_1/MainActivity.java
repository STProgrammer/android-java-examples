package com.wfamedia.fragment3_1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

/**
 * Bruker ViewModel til å utveksle info. mellom InfoFragment og SelectionFragmet - begge veier.
 */
public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        super(R.layout.activity_main);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //AppBar / ActionBar:
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbarMain);
        myToolbar.setTitle(R.string.app_bar_title);
        myToolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        this.setSupportActionBar(myToolbar);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_end:
                this.finish();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
