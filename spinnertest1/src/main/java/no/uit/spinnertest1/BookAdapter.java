package no.uit.spinnertest1;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private int layoutResource;

    public BookAdapter(Context context, int layoutResource, List<Book> items) {
        super(context, layoutResource, items);
        this.layoutResource = layoutResource;
    }

    //NB!! Denne gjør at elementene i lista tegnes som ikoner.
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getBookView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getBookView(position, convertView, parent);
    }

    public View getBookView(int position, View convertView, ViewGroup parent) {
        LinearLayout bookView;
        if (convertView == null) {
            bookView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(this.layoutResource, bookView, true);
        } else {
            //Gjenbruker vha. convertView:
            bookView = (LinearLayout) convertView;
        }

        Book bookItem = getItem(position);
        String title = bookItem.getTitle();
        TextView titleView = bookView.findViewById(R.id.tvTitle);
        titleView.setText(title);

        ImageView iconView = bookView.findViewById(R.id.ivBookIcon);
        int imageId = bookItem.getIcon_resource_id();
        Drawable drawable = ResourcesCompat.getDrawable(this.getContext().getResources(), imageId, null);
        iconView.setImageDrawable(drawable);

        return bookView;
    }
}
