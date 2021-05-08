package dt.uit.no;

import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {
	private MyReceiver myReceiver=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyReceiver.VIKITG_HENDELSE);
		getApplicationContext().registerReceiver(myReceiver, filter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (myReceiver!=null)
			this.unregisterReceiver(myReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
