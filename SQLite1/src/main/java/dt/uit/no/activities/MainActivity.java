package dt.uit.no.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;

import dt.hin.no.R;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return super.onMenuOpened(featureId, menu);
	}
}
