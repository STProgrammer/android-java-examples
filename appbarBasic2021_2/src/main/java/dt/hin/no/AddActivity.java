package dt.hin.no;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbarAdd);
        myToolbar.setTitle("Legg til");
        myToolbar.setSubtitle("Legg til ny person ...");
        myToolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        this.setSupportActionBar(myToolbar);

        // Siden appen bruker et NoActionbar - tema må vi aktivere tilbakeknappen:
        ActionBar ab = getSupportActionBar();
        // NB! For å vise "tilbakeknappen":
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
