package no.uit.spinnertest1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BookAdapter bookAdapter = null;
    private ArrayList<Book> bookItems = new ArrayList<Book>();
    private Spinner bookSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hei!", Toast.LENGTH_SHORT).show();
            }
        });

        bookItems.add(new Book("Android Programming", R.drawable.ic_action_icon1));
        bookItems.add(new Book("Python Programming", R.drawable.ic_action_icon2));
        bookItems.add(new Book("C# Programming", R.drawable.ic_action_icon3));
        bookItems.add(new Book("Javascript Programming", R.drawable.ic_action_icon4));

        bookSpinner = this.findViewById(R.id.spinnerBooks);
        // Fanger opp event:
        bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Valgt bok:" + MainActivity.this.bookItems.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bookAdapter = new BookAdapter(this, R.layout.book_item_spinner, bookItems);
        bookSpinner.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

        // Henter valgt bok slik:
        Book selectedBook = (Book) bookSpinner.getSelectedItem();
        Log.d("MY_TAG", selectedBook.getTitle());
    }

}
