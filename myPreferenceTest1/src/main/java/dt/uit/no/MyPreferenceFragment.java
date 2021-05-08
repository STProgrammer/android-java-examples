package dt.uit.no;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class MyPreferenceFragment extends PreferenceFragmentCompat {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.mypreferenceui, rootKey);
	}
}
