package no.uit.huskeliste1;

/**
 * Created by Werner on 19.01.2017.
 */

public class ToDoItem {

    private String toDoText="";
    private long toDoDate;

    public ToDoItem(long toDoDate, String toDoText) {
        this.toDoDate = toDoDate;
        this.toDoText = toDoText;
    }

    public long getToDoDate() {
        return toDoDate;
    }

    public void setToDoDate(long toDoDate) {
        this.toDoDate = toDoDate;
    }

    public String getToDoText() {
        return toDoText;
    }

    public void setToDoText(String toDoText) {
        this.toDoText = toDoText;
    }
}
