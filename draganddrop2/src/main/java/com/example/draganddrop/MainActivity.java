package com.example.draganddrop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")     //For å unngå warning om at performClick må håndteres for ImageView. Se: https://stackoverflow.com/questions/47107105/android-button-has-setontouchlistener-called-on-it-but-does-not-override-perform
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setter onTouchListener på alle fire ImageView. Brukes til å starte "dragAndDrop".
        ImageView imageFace = findViewById(R.id.imageFace);
        imageFace.setOnTouchListener(new MyTouchListener());

        ImageView imageDropper = findViewById(R.id.imageDropper);
        imageDropper.setOnTouchListener(new MyTouchListener());

        ImageView imageBoat = findViewById(R.id.imageBoat);
        imageBoat.setOnTouchListener(new MyTouchListener());

        ImageView imageScissors = findViewById(R.id.imageScissors);
        imageScissors.setOnTouchListener(new MyTouchListener());

        // Setter onDraListener på de fire konteinerne (alle av type LinearLayout):
        LinearLayout topLeft = findViewById(R.id.topLeft);
        topLeft.setOnDragListener(new MyDragListener());

        LinearLayout topRight = findViewById(R.id.topRight);
        topRight.setOnDragListener(new MyDragListener());

        LinearLayout bottomLeft = findViewById(R.id.bottomLeft);
        bottomLeft.setOnDragListener(new MyDragListener());

        LinearLayout bottomRight = findViewById(R.id.bottomRight);
        bottomRight.setOnDragListener(new MyDragListener());
    }

    // Ikonene/bildene håndterer onTouch & start "drag":
    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View viewToBeDragged, MotionEvent motionEvent) {

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
        }
    }

    // Denne håndterer drag-drop events for rektanglende/konteinerne:
    class MyDragListener implements View.OnDragListener {

        // Alle fire rektangler (LinearLayout) lytter etter drag-events.
        // Følgende drawables brukes til å sette korrekt bakgrunn.
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View view, DragEvent event) {

            int action = event.getAction();
            boolean dragInterrupted = false;

            //Bildet som blir dradd:.
            View draggedView = (View) event.getLocalState();
            // Konteiner som draggedView dras til (som er en av de fire LinearLayout-ene):
            LinearLayout receiveContainer = (LinearLayout) view;

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
                    dragInterrupted = false;

                    if (dragInterrupted) {
                        return false;
                    } else {
                        // owner er her foreldreview (en av de fire LinearLayout-objektene) til bildet som blir dradd,
                        // fjerner bildet fra ownerview:
                        ViewGroup owner = (ViewGroup) draggedView.getParent();
                        owner.removeView(draggedView);
                        // Legger draggedView til motakkskonteiner:
                        receiveContainer.addView(draggedView);
                    }
                    draggedView.setVisibility(View.VISIBLE);
                    break;

                    //DENNE GJØR AT MAN FÅR EN "TILBAKEANIMASJON"
                case DragEvent.ACTION_DRAG_ENDED:
                    draggedView.setVisibility(View.VISIBLE);    //Sett view synlig igjen.
                    receiveContainer.setBackground(normalShape);    //Sett korrekt bakgrunn.
                    break;

                default:
                    break;
            }
            return true;
        }
    }
}
