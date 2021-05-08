package no.hin.dt.huskeliste1;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import dt.hin.no.huskeliste1.R;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener,
        View.OnKeyListener {
    //"Data":
    private static ArrayList<ToDoItem> myToDoItems;
    //Adapter:
    private ToDoItemAdapter arrayAdapter;
    //View:
    private ListView myListView;

    private EditText etHusk;
    private int currentSelectedItem = -1;

    //Kontekstmeny:
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Testdata:
        myToDoItems = new ArrayList<ToDoItem>();
        myToDoItems.add(new ToDoItem((new Date()).getTime(), "Ta telefonen!!", false));
        myToDoItems.add(new ToDoItem((new Date()).getTime(), "Bestill time til EU-kontroll", true));
        myToDoItems.add(new ToDoItem((new Date()).getTime(), "Gå på jobb!!", true));
        myToDoItems.add(new ToDoItem((new Date()).getTime(), "2 + 2 er fortsatt 4", false));

        //Bruker egen adapter
        arrayAdapter = new ToDoItemAdapter(this, R.layout.todo_row_item, myToDoItems);

        //Henter en referans til ListView-lista definert i listfragment.xml:
        myListView = (ListView)findViewById(R.id.myListView);

        //Kopler adapteren til Listview'en:
        myListView.setAdapter(arrayAdapter);

        //For å fange opp at brukeren velger/trykker i lista:
        myListView.setOnItemClickListener(this);
        myListView.setOnItemLongClickListener(this);    //NB!!

        //Håndterer OnKey-event på EditText-felt:
        etHusk = findViewById(R.id.etHusk);
        etHusk.setOnKeyListener(this);

        FloatingActionButton fabAddButton = (FloatingActionButton) findViewById(R.id.fabAddPopUp);
        //fabAddButton.setBackgroundTintList(ColorStateList.valueOf(actionButtonColor));  //Settes i BaseActivity
        //fabAddButton.setRippleColor(actionButtonColorPressed);                          //Settes i BaseActivity
    }

    //Legger til nytt item:
    private void addItem() {
        String toDoText = etHusk.getText().toString();
        ToDoItem newItem = new ToDoItem((new Date().getTime()), toDoText, false);
        myToDoItems.add(0, newItem);
        arrayAdapter.notifyDataSetChanged();    //<== NB!!
        etHusk.setText("");
        etHusk.setVisibility(View.GONE);
    }

    //Fjerner valgt item:
    private void deleteItem() {
        if (currentSelectedItem != -1) {
            myToDoItems.remove(currentSelectedItem);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentSelectedItem = position;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        currentSelectedItem = position;

        if (mActionMode != null) {
            return false;
        }

        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = startActionMode(mActionModeCallback);
        view.setSelected(true);
        return true;
    }

    //Kontekstmeny...:
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_main_context, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int id = item.getItemId();
            switch (id) {
                case R.id.action_delete:
                    MainActivity.this.deleteItem();
                    mode.finish();  //NB!!
                    return true;

                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public void doAddItem(View view) {
        switch (view.getId()) {
            case R.id.fabAddPopUp: {
                this.showAddPopup(view);
                break;
            }
        }
    }

    //Vis Popup:
    public void showAddPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_add_item:
                        etHusk.setVisibility(View.VISIBLE);
                        return true;

                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_add, popupMenu.getMenu());
        popupMenu.show();
    }
}
