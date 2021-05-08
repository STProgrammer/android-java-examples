package no.hin.dt.dialogtest.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import no.hin.dt.dialogtest.R;

public class MyDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        setTitle("Min Dialogaktivitet");
    }

    public void onBtnClose(View view) {
        this.finish();
    }
}
