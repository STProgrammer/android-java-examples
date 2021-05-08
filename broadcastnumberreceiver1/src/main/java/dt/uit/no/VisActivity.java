package dt.uit.no;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

public class VisActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vis);
		
		Intent intent = this.getIntent();
		//Uri data = intent.getData();
	    String dato = intent.getStringExtra(MyReceiver.EKSTRA_DATO);
	    int antall = intent.getIntExtra(MyReceiver.EKSTRA_ANTALL, 0);
	    
	    TextView visAntall = (TextView)findViewById(R.id.tvVisAntall);
	    visAntall.setText(Integer.toString(antall) + "\n" + dato);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
