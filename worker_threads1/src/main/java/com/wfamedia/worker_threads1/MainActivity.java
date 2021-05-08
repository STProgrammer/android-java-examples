package com.wfamedia.worker_threads1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }

    public void doProcessBitmap(View view) {
        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                final Bitmap bitmap = processBitMap();
                if (bitmap != null) {
                    imageView.post(new Runnable() {
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        }).start();
    }

    private Bitmap processBitMap() {
        try {
            Thread.sleep(4000);
            Resources res = getResources();

            Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.bird1, null);
            return ((BitmapDrawable)drawable).getBitmap();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}