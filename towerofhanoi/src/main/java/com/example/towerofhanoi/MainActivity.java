package com.example.towerofhanoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int nrOfRings = 3;
    private int nrOfMoves = 0;
    private boolean gameStarted = false;
    private int seconds = 0;
    private LinearLayout tower1;
    private LinearLayout tower2;
    private LinearLayout tower3;
    private TextView tvNrOfMoves;
    private TextView tvNrOfRings;
    private TextView tvSeconds;
    private Timer timer;
    private String strSeconds;
    private int[] ringPositions;

    @SuppressLint("ClickableViewAccessibility")
    //For å unngå warning om at performClick må håndteres for ImageView. Se: https://stackoverflow.com/questions/47107105/android-button-has-setontouchlistener-called-on-it-but-does-not-override-perform
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNrOfMoves = (TextView) findViewById(R.id.tvNrOfMoves);
        tvNrOfRings = (TextView) findViewById(R.id.tvNrOfRings);
        tvSeconds = (TextView) findViewById(R.id.tvSeconds);
        strSeconds = getString(R.string.seconds);
        tvNrOfMoves.setText(getString(R.string.moves)+"?");
        tvNrOfRings.setText(getResources().getString(R.string.nr_of_rings) + nrOfRings);
        tvSeconds.setText(strSeconds + seconds);
        timer = new Timer();

        //Få fram tårnene
        tower1 = findViewById(R.id.tower1);
        tower1.setOnDragListener(new MyDragListener());
        tower1.setTag("tower1");

        tower2 = findViewById(R.id.tower2);
        tower2.setOnDragListener(new MyDragListener());
        tower2.setTag("tower2");

        tower3 = findViewById(R.id.tower3);
        tower3.setOnDragListener(new MyDragListener());
        tower3.setTag("tower3");

        if (savedInstanceState == null) {
            ringPositions = new int[nrOfRings+1];
            resetTowers();
        }

    }

    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View viewToBeDragged, MotionEvent motionEvent) {

            LinearLayout owner = (LinearLayout) viewToBeDragged.getParent();
            View top = owner.getChildAt(0);
            if ((viewToBeDragged == top || owner.getChildCount() == 1) && gameStarted) {
                // Starter "drag"-operasjon:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // Data som kan sendes med til "drag-receiver" (brukes ikke her):
                    ClipData data = ClipData.newPlainText("", "");

                    // Lager en "drag-skygge" av viewet som skal dras (gjør opprinnelig viewToBeDragged usynlig):
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(viewToBeDragged);

                    // Starter Drag&Drop, alle View som implemenenterer OnDragListener vil motta onDrag-events.
                    viewToBeDragged.startDrag(data, shadowBuilder, viewToBeDragged, 0);

                    // Gjør bildet som "dragges" usynlig (kun skyggen er nå synlig):
                    viewToBeDragged.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    // Denne håndterer drag-drop events for tårnene:
    class MyDragListener implements View.OnDragListener {

        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View view, DragEvent event) {

            int action = event.getAction();
            boolean dragInterrupted = false;

            //Bildet som blir dradd:.
            View draggedView = (View) event.getLocalState();
            // Konteiner som draggedView dras til (som er en av de fire LinearLayout-ene):
            View topElement = null;
            LinearLayout receiveTower = (LinearLayout) view;

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Skjer ingenting, skal ikke kunne dra rektanglene.
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // view er konteineren (her: en av de fire LinearLayout-ene).
                    view.setBackground(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    // view er konteineren (her: en av de fire LinearLayout-ene).
                    view.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Settes denne til true avbrytes drag - her kan man altså sette en betingelse for avbrutt operasjon eller ikke!
                    if (receiveTower.getChildCount() > 0) {
                        topElement = receiveTower.getChildAt(0);
                        if (draggedView.getMeasuredWidth() > topElement.getMeasuredWidth()) {
                            dragInterrupted = true;
                        }
                        if (receiveTower == draggedView.getParent()) {
                            dragInterrupted = true;
                        }
                    }
                    if (dragInterrupted) {
                        return false;
                    } else {
                        // owner er her foreldreview (en av de fire LinearLayout-objektene) til bildet som blir dradd,
                        // fjerner bildet fra ownerview:
                        ViewGroup owner = (ViewGroup) draggedView.getParent();
                        owner.removeView(draggedView);
                        // Legger draggedView til mottakertårn:
                        receiveTower.addView(draggedView, 0);
                        int ringNr = Integer.parseInt(draggedView.getTag().toString());
                        int towerNr = 0;
                        if (tower1.getTag() == receiveTower.getTag()) {
                            towerNr = 1;
                        } else if (tower2.getTag() == receiveTower.getTag()) {
                            towerNr = 2;
                        } else {towerNr = 3;}

                        ringPositions[ringNr] = towerNr;

                        nrOfMoves++;
                        tvNrOfMoves.setText(getString(R.string.moves) + nrOfMoves);
                        if (tower3.getChildCount() == nrOfRings) {
                            gameEnded();
                        }
                    }
                    draggedView.setVisibility(View.VISIBLE);
                    break;

                //DENNE GJØR AT MAN FÅR EN TILBAKEANIMASJON!!!!
                case DragEvent.ACTION_DRAG_ENDED:
                    draggedView.setVisibility(View.VISIBLE);        //Sett view synlig igjen.
                    receiveTower.setBackground(normalShape);    //Sett korrekt bakgrunn.
                    view.setBackground(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    public void startGame(View view) {
        gameStarted = true;
        nrOfMoves = 0;
        seconds = 0;
        resetTowers();
        tvSeconds.setText(strSeconds + seconds);
        tvNrOfMoves.setText(getString(R.string.moves) + nrOfMoves);
        stopTimer();
        startTimer();
    }

    public void gameEnded() {
        timer.cancel();
        timer.purge();
        gameStarted = false;
    }

    private void resetTowers() {
        String packageName = getPackageName();
        tower1.removeAllViews();
        tower2.removeAllViews();
        tower3.removeAllViews();

        for (int i = 1; i <= nrOfRings; i++) {
            ringPositions[i] = 1;
            ImageView ring = new ImageView(getApplicationContext());
            String imgName = "ic_ring_" + i;
            int id = getResources().getIdentifier(imgName, "drawable", packageName);
            ring.setImageDrawable(getDrawable(id));
            ring.setOnTouchListener(new MyTouchListener());
            ring.setTag(""+i);
            ring.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tower1.addView(ring);
        }
    }


    public void startTimer() {
        timer = new Timer();
        try {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    seconds++;
/*
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Kode som vil bli kjørt av hovedtråden:
                            tvSeconds.setText(getString(seconds));
                        }
                    });*/

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSeconds.setText(strSeconds + seconds);
                        }
                    });
                }
            }, 0, 1000);

        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    public void stopTimer() {
        seconds = 0;
        tvSeconds.setText(strSeconds + seconds);
        timer.cancel();
        timer.purge();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putInt("nrOfMoves", nrOfMoves);
        outState.putInt("nrOfRings", nrOfRings);
        outState.putBoolean("gameStarted", gameStarted);

        outState.putIntArray("ringPositions", ringPositions);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        seconds = savedInstanceState.getInt("seconds");
        nrOfMoves = savedInstanceState.getInt("nrOfMoves");
        nrOfRings = savedInstanceState.getInt("nrOfRings");
        gameStarted = savedInstanceState.getBoolean("gameStarted");
        tvNrOfMoves.setText(getString(R.string.moves) + nrOfMoves);
        tvNrOfRings.setText(getString(R.string.nr_of_rings) + nrOfRings);
        tvSeconds.setText(strSeconds + seconds);

        ringPositions = savedInstanceState.getIntArray("ringPositions");

        String packageName = getPackageName();

        for (int i = 1; i <= nrOfRings; i++) {
            int position = ringPositions[i];
            ImageView ring = new ImageView(getApplicationContext());
            String imgName = "ic_ring_" + i;
            int id = getResources().getIdentifier(imgName, "drawable", packageName);
            ring.setImageDrawable(getDrawable(id));
            ring.setOnTouchListener(new MyTouchListener());
            ring.setTag(""+i);
            ring.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            switch (position) {
                case 1:
                    tower1.addView(ring);
                    break;
                case 2:
                    tower2.addView(ring);
                    break;
                case 3:
                    tower3.addView(ring);
                    break;
                default:
                    break;
            }
        }

        if (gameStarted) {
            startTimer();
        }
    }
}


