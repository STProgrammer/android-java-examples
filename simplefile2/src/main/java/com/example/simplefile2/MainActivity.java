package com.example.simplefile2;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Demonstrerer lesing/skriving til og fra filer som ligger i INTERNT filområde.
 * Bruker getFilesDir()
 */
public class MainActivity extends AppCompatActivity {

    private String fileNameInternal = "internTekstFil.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String filesDir = getFilesDir().toString();
        TextView tvInternalFileDir = (TextView)findViewById(R.id.tvInternalFileDir);
        tvInternalFileDir.setText("Internt filnavn:\n" + filesDir + "/" + fileNameInternal);
    }


    //SKRIV til INTERN fil
    public void doWriteInternalFile(View view) {
        EditText etInput = findViewById(R.id.etInternalInput);
        String content = etInput.getText().toString();

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.openFileOutput(fileNameInternal, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //LES fra INTERN fil:
    public void doReadInternalFile(View view) {
        TextView tvOutput = findViewById(R.id.tvOutput);

        StringBuilder stringBuilder = new StringBuilder();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = this.openFileInput(fileNameInternal);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
            tvOutput.setText(e.getMessage());

        } finally {
            String contents = stringBuilder.toString();
            tvOutput.setText(contents);
        }
    }

    public void doClear(View view) {
        TextView tvOutput = findViewById(R.id.tvOutput);
        tvOutput.setText("");
    }
}