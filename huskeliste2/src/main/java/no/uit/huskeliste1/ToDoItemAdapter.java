package no.uit.huskeliste1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by wfa on 21.01.2015.
 */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    private int resource;

    public ToDoItemAdapter(Context context, int resource, List<ToDoItem> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout toDoItemView;
        ToDoItem item = getItem(position);

        if (convertView == null) {
            toDoItemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, toDoItemView, true);
        } else {
            //Gjenbruker vha. convertView:
            toDoItemView = (LinearLayout) convertView;
        }

        String toDoText = item.getToDoText();
        long dateAdded = item.getToDoDate();

        TextView tvToDoText = (TextView)toDoItemView.findViewById(R.id.tvToDoText);
        tvToDoText.setText(toDoText);

        //Formaterer og setter dato:
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        String dateAddedString = df.format(dateAdded);
        TextView tvDateAdded = (TextView)toDoItemView.findViewById(R.id.tvDateAdded);
        tvDateAdded.setText(dateAddedString);

        return toDoItemView;
    }
}
