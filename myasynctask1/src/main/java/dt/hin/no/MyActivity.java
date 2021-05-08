package dt.hin.no;

import android.content.ComponentName;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//Demonstrerer enkel bruk av AsyncTask.
//Tar ikke hensyn til skjermrotasjon / konfigurasjonsendringer.
public class MyActivity extends AppCompatActivity {
	private ComponentName service;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Knapp for AsyncTask:
		//Starter AsyncTask:
		Button startAsyncButton = (Button) findViewById(R.id.btnStartAsyncTask);
		startAsyncButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//Sender med appcontext:
				MyAsyncTask myAsyncTask = new MyAsyncTask(MyActivity.this);
				String [] strArray = {"dette er inndata...", "dette er mer inndata", "osv...", "jjj", "fgfgfgff"};
				myAsyncTask.execute(strArray);
			}
		});
	}
}