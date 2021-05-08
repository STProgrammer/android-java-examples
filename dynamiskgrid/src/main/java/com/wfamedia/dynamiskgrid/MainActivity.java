package com.wfamedia.dynamiskgrid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int SIZE = 12;
    private LinearLayout linLayGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linLayGame = findViewById(R.id.linLayGame);
        linLayGame.setBackgroundColor(Color.BLUE);

        // Setter opp dynamisk spillbrett (basert på size):
        this.setUpGameDisplay();
    }

    /*
        Bygger dynamisk spillbrett basert på størrelse gitt til TicTacToeGame-konstruktør.
     */
    private void setUpGameDisplay() {

        //Finner skjermbredde, brukes til å beregne høyden på hver rute:
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        // Layout for hver linje-LinearLayout:
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Legger SIZE antall TextView i hver linje:
        for (int i=0; i < SIZE; i++) {
            LinearLayout linLayRow = new LinearLayout(this);
            linLayRow.setLayoutParams(lp);
            linLayRow.setOrientation(LinearLayout.HORIZONTAL);
            linLayRow.setWeightSum(SIZE);

            // Layout for hvert TextView:
            LinearLayout.LayoutParams tvLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tvLP.height = screenWidth/SIZE;

            int m = (int)getResources().getDimension(R.dimen.tv_margin);
            tvLP.setMargins(m,m,m,m);
            tvLP.weight = 1;
            for (int j=0; j < SIZE; j++) {
                TextView textView = new TextView(this);

                textView.setTag("tv_" + String.valueOf(i) + String.valueOf(j));  // Dersom behov for å finne TextView-en.
                textView.setLayoutParams(tvLP); //<== Her brukes tvLP
                textView.setText("O");
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                int p = (int)getResources().getDimension(R.dimen.tv_padding);
                textView.setPadding(0, p,0, p);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onTvClick(view);
                    }
                });
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setBackgroundColor(Color.YELLOW);
                linLayRow.addView(textView);
            }

            // Legg linje med TextView til ytre LinearLayout:
            linLayGame.addView(linLayRow);
        }
    }

    private void onTvClick(View view) {
        Toast.makeText(this, "Klikk", Toast.LENGTH_SHORT).show();
        TextView tv = (TextView)view;
        if (tv.getText().toString().equals("X"))
            tv.setText("O");
        else
            tv.setText("X");
    }
}