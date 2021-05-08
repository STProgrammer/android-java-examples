package no.hin.dt.huskeliste1;

/**
 * Created by Werner on 19.01.2017.
 */

public class ToDoItem {

    private String toDoText="";
    private long toDoDate;
    private boolean done=false;

    public ToDoItem(long toDoDate, String toDoText, boolean done) {
        this.done = done;
        this.toDoDate = toDoDate;
        this.toDoText = toDoText;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
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
