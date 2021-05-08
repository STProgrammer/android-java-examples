package no.uit.huskeliste1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //"Data":
    private ArrayList<String> toDoItems;
    //Adapter:
    private ArrayAdapter<String> arrayAdapter;
    //View:
    private ListView lvToDoItems;

    private EditText etToDoText;
    private int currentSelectedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.tbMain);
        setSupportActionBar(myToolbar);

        //Testdata:
        toDoItems = new ArrayList<String>();
        toDoItems.add("Klipp plenen");
        toDoItems.add("Bestill time til EU-kontroll");
        toDoItems.add("Gå på jobb");
        toDoItems.add("2 + 2 = 4");

        // Instansierer adapteren, bruker android.R.layout.simple_list_item_1 for å vise hvert element i lista.
        // Dette er egentlig en ferdigdefinert layout som består av en enkel TextView:
        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, toDoItems);

        //Alternativt: egen layout med en TextView:
        arrayAdapter = new ArrayAdapter<>(this, R.layout.my_listitem1, toDoItems);

        //Henter en referans til ListView-lista definert i listfragment.xml:
        lvToDoItems = findViewById(R.id.lvToDoItems);

        //Kopler adapteren til Listview'en:
        lvToDoItems.setAdapter(arrayAdapter);

        //For å fange opp at brukeren velger/trykker i lista:
        //lvToDoItems.setOnItemSelectedListener(this);
        lvToDoItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSelectedItem = position;
            }
        });

        etToDoText = findViewById(R.id.etToDoText);

        // IME_ACTION_DONE sammen med android:imeOptions="actionDone" i activity_main.xml gjør at det dukker opp en "Done"-knapp på tastaturet:
        etToDoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    MainActivity.this.addItem();
                    handled = true;
                }
                return handled;
            }
        });

        // For Emulator & PC-tastatur (fanger opp enter):
        etToDoText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        MainActivity.this.addItem();
                        return true;
                    }
                return false;
            }
        });
    }

    //Legger til nytt item:
    private void addItem() {
        String newItem = etToDoText.getText().toString();
        toDoItems.add(0, newItem);
        arrayAdapter.notifyDataSetChanged();    //<== NB!!
        etToDoText.setText("");
    }

    //Fjerner valgt item:
    private void deleteItem() {
        if (currentSelectedItem != -1) {
            toDoItems.remove(currentSelectedItem);
            arrayAdapter.notifyDataSetChanged();
            currentSelectedItem = -1;
        } else {
            Toast.makeText(this, "Du må velge et element fra lista.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Håndterer menyvalg:
        int id = item.getItemId();
        switch (id) {
            case R.id.action_delete:
                this.deleteItem();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
