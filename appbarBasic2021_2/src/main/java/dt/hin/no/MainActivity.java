package dt.hin.no;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        //AppBar / ActionBar:
        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbarMain);
        myToolbar.setTitle("Tester AppBar");
        myToolbar.setSubtitle("AppBar & menyer");
        myToolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        myToolbar.setNavigationIcon(R.drawable.bubble_32);
        this.setSupportActionBar(myToolbar);

		// Endre bakgrunnen:
		Resources r = getResources();
		Drawable myDrawable = r.getDrawable(R.drawable.inforull1);
		//myToolbar.setBackgroundDrawable(myDrawable);

        //Hentet ut "ActionBar" - objektet:
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        //actionBar.setIcon(R.drawable.ic_launcher);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(MainActivity.this, "Valg: " + item.getTitle().toString(), Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.actionAddPerson:
                //Toast.makeText(MainActivity.this, "actionRecycle valgt", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, AddActivity.class);
                startActivity(intent1);
                break;

            case R.id.actionRefresh:
                Toast.makeText(MainActivity.this, "actionRefresh valgt", Toast.LENGTH_SHORT).show();
                break;

            case R.id.actionAbout:
                Toast.makeText(MainActivity.this, "actionShowAll valgt", Toast.LENGTH_SHORT).show();
                break;

            case R.id.actionQuit:
                this.finish();

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.actionAbout);
        item.setTitle("Om om...");

        return super.onPrepareOptionsMenu(menu);
    }

    //Oppretter/bygger menyen:
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		//"Blåser opp" menyen vha. Inflater:
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.my_menu, menu);
		return true;
	}
}
