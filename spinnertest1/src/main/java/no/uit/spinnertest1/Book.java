package no.uit.spinnertest1;

public class Book {
    private String title;
    private int icon_resource_id;

    public Book(String title, int icon_resource_id) {
        this.title = title;
        this.icon_resource_id = icon_resource_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon_resource_id() {
        return icon_resource_id;
    }

    public void setIcon_resource_id(int icon_resource_id) {
        this.icon_resource_id = icon_resource_id;
    }
}
