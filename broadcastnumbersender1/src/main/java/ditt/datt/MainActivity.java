package ditt.datt;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnClickListener {

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnSend = (Button)findViewById(R.id.btnSend);
		btnSend.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
 
	@Override
	public void onClick(View arg0) {

		EditText etAntall = (EditText)findViewById(R.id.etAntall);
		int antall = 1999999;
		try {
			antall = Integer.parseInt(etAntall.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Intent intent = new Intent();
		intent.setAction("dt.uit.no.VIKTIG_HENDELSE");
		intent.putExtra("EKSTRA_ANTALL", antall);
		intent.putExtra("EKSTRA_DATO", (new Date().toString()));
		sendBroadcast(intent);
	}
}
