package dt.uit.no;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Merk! Fanger opp endringer i preferansecerdiene, trigger onSharedPreferenceChanged() under:
		SharedPreferences sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

		String str = sharedPreferences.getString("MY_TEXT", "");
		EditText editText = (EditText)findViewById(R.id.editText1);
		editText.setText(str);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				Intent intent = new Intent(this, MyPreferenceActivity.class);
				startActivity(intent);
				return true;

			case R.id.action_end:
				finish();
				break;

			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	protected void onStop() {
		this.writeToSharedPreference();
		super.onStop();
	}

	private void writeToSharedPreference() {
		//Lagrer
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean saveOnExit = sharedPreferences.getBoolean("PREF_SAVE_ON_EXIT", false);
		if (saveOnExit) {
			EditText editText = (EditText)findViewById(R.id.editText1);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("MY_TEXT", editText.getText().toString());
			editor.commit();
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// TODO Check the shared preference and key parameters
		SharedPreferences myprefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		boolean playSounds = myprefs.getBoolean("PREF_PLAY_SOUNDS", false);
		Log.d("DDDD", "AAAA");
		//. . .
		Toast.makeText(this, key +" endret seg...", Toast.LENGTH_SHORT).show();
	}
}
