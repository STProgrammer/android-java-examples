package dt.uit.no;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import dt.hin.no.R;

public class MyTestActivity extends AppCompatActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Gir shared prefs-fil tilgjengelig fra alle aktiviteter med standard filnavn: /data/data/shared_prefs/no.hin.dt.mysimplepreferencetest1/no.hin.dt_mysimplepreferencetest1.xml:
		//prefs = PreferenceManager.getDefaultSharedPreferences(this);

		//Bestemmer selv navn og tilgang (legges under /data/data/shared_prefs/no.hin.dt.mysimplepreferencetest1/):
		prefs = this.getSharedPreferences("minpref", Activity.MODE_PRIVATE);

		//Preferencesfil for DENNE aktiviteten (navn på sharedprefs-fil: /data/data/shared_prefs/dt.uit.no.MyTestActivity.xml)
		//prefs = this.getPreferences(Activity.MODE_PRIVATE);

        // Bruker verdier som måtte ligge i prefs:
        String str = prefs.getString("MY_TEXT", "");
		EditText editText = (EditText)findViewById(R.id.etInput);
		editText.setText(str);

		// Setter bakgrunnsfargen:
		boolean backColorState = prefs.getBoolean("SCREEN_BACKGROUND_COLOR", true);
		this.changeBackgroundColor(backColorState);

		// Setter tekstbakgrunnsfargen:
		boolean textBackcolorColorState = prefs.getBoolean("TEXT_BACKGROUND_COLOR", true);
		this.changeTextBackgroundColor(textBackcolorColorState);

		// Setter tekstfargen:
		boolean textColorState = prefs.getBoolean("TEXT_COLOR", true);
		this.changeTextColor(textColorState);

		// SaveOnExit:
		CheckBox saveOnExit = findViewById(R.id.cbSaveOnExit);
		boolean save = prefs.getBoolean("SAVE_ON_EXIT", false);
		saveOnExit.setChecked(save);
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
			case R.id.action_end:
				//this.writeToSharedPreference();
				this.finish();
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

	//Lagrer i preferansefil:
	private void writeToSharedPreference(){
		SharedPreferences.Editor editor = prefs.edit();

		EditText editText = findViewById(R.id.etInput);
		CheckBox cbTextBackgroundColor = findViewById(R.id.cbTextBackgroundColor);
		CheckBox cbTextColor = findViewById(R.id.cbTextColor);
		CheckBox cbBackgroundColor = findViewById(R.id.cbBackgroundColor);
		CheckBox saveOnExit = findViewById(R.id.cbSaveOnExit);

		if (saveOnExit.isChecked()) {
			editor.putString("MY_TEXT", editText.getText().toString());
			editor.putBoolean("TEXT_BACKGROUND_COLOR", cbTextBackgroundColor.isChecked());
			editor.putBoolean("TEXT_COLOR", cbTextColor.isChecked());
			editor.putBoolean("SCREEN_BACKGROUND_COLOR", cbBackgroundColor.isChecked());
			editor.putBoolean("SAVE_ON_EXIT", saveOnExit.isChecked());
		} else {
			//Tømmer/setter til default:
			editor.putString("MY_TEXT", "");
			editor.putBoolean("TEXT_BACKGROUND_COLOR", false);
			editor.putBoolean("TEXT_COLOR", false);
			editor.putBoolean("SCREEN_BACKGROUND_COLOR", false);
			editor.putBoolean("SAVE_ON_EXIT", false);
		}
		editor.commit();
	}

	//Event: Endrer bakgrunnsfargen:
	public void doSwitchBackgroundColor(View view) {
		CheckBox cbBackgroundColor = findViewById(R.id.cbBackgroundColor);
		this.changeBackgroundColor(cbBackgroundColor.isChecked());
	}

	//Event: Endrer tekstbakgrunnsfargen:
	public void doSwitchTextBackgroundColor(View view) {
		CheckBox cbTextBackgroundColor = findViewById(R.id.cbTextBackgroundColor);
		this.changeTextBackgroundColor(cbTextBackgroundColor.isChecked());
	}

	//Event: Endrer tekstfargen:
	public void doSwitchTextColor(View view) {
		CheckBox cbTextColor = findViewById(R.id.cbTextColor);
		this.changeTextColor(cbTextColor.isChecked());
	}

	private void changeBackgroundColor(boolean rbState) {
		CheckBox cbBackgroundColor = findViewById(R.id.cbBackgroundColor);
		LinearLayout linearLayout = findViewById(R.id.linLayMain);
		if (rbState) {
			linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.myColor1));
			cbBackgroundColor.setChecked(true);
		} else {
			linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.myColor2));
			cbBackgroundColor.setChecked(false);
		}
	}

	private void changeTextBackgroundColor(boolean rbState) {
		CheckBox cbTextBackgroundColor = findViewById(R.id.cbTextBackgroundColor);
		EditText editText = findViewById(R.id.etInput);
		if (rbState) {
			editText.setBackgroundColor(ContextCompat.getColor(this, R.color.myTextBackgroundColor1));
			cbTextBackgroundColor.setChecked(true);
		} else {
			editText.setBackgroundColor(ContextCompat.getColor(this, R.color.myTextBackgroundColor2));
			cbTextBackgroundColor.setChecked(false);
		}
	}

	private void changeTextColor(boolean rbState) {
		CheckBox cbTextColor = findViewById(R.id.cbTextColor);
		EditText editText = (EditText)findViewById(R.id.etInput);
		if (rbState) {
			editText.setTextColor(ContextCompat.getColor(this, R.color.myTextBackgroundColor1));
			cbTextColor.setChecked(true);
		} else {
			editText.setTextColor(ContextCompat.getColor(this, R.color.myTextBackgroundColor2));
			cbTextColor.setChecked(false);
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

	}
}
