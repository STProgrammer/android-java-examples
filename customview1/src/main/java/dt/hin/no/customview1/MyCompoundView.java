package dt.hin.no.customview1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyCompoundView extends LinearLayout {
    private EditText editText;
    private Button clearButton;

    public MyCompoundView(Context context) {
        super(context);
        init();
    }

    public MyCompoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCompoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyCompoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //Merk bruk av LayoutInflater:
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.clearable_edit_text, this, true);
        editText = findViewById(R.id.editText);
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }
}
