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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnKeyListener {
    //"Data":
    private static ArrayList<ToDoItem> toDoItems;
    //Adapter:
    private ToDoItemAdapter arrayAdapter;
    //View:
    private ListView lvToDoItems;

    private EditText etToDoText;
    private int currentSelectedItem = -1;

    public static String MY_TAG = "MY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Testdata:
        toDoItems = new ArrayList<ToDoItem>();
        toDoItems.add(new ToDoItem((new Date()).getTime(), "Klipp plenen"));
        toDoItems.add(new ToDoItem((new Date()).getTime(), "Bestill time til EU-kontroll"));
        toDoItems.add(new ToDoItem((new Date()).getTime(), "Gå på jobb!!"));
        toDoItems.add(new ToDoItem((new Date()).getTime(), "2 + 2 er fortsatt 4"));

        //Bruker egen adapter
        arrayAdapter = new ToDoItemAdapter(this, R.layout.todo_row_item, toDoItems);

        //Henter en referanse til ListView-lista definert i listfragment.xml:
        lvToDoItems = findViewById(R.id.myListView);

        //Kopler adapteren til Listview'en:
        lvToDoItems.setAdapter(arrayAdapter);

        //For å fange opp at brukeren velger/trykker i lista:
        //lvToDoItems.setOnItemSelectedListener(this);
        lvToDoItems.setOnItemClickListener(this);

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
        etToDoText.setOnKeyListener(this);
        /* //Alternativt:
        etToDoText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        this.addItem();
                        return true;
                    }
                return false;
            }
        });
        */

    }

    //Legger til nytt item:
    private void addItem() {
        String toDoText = etToDoText.getText().toString();
        ToDoItem newItem = new ToDoItem((new Date().getTime()), toDoText);
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
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                this.addItem();
                return true;
            }
        return false;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentSelectedItem = position;
    }
}
