package ru.make.alex.db.table;

/**
 * Created by pas on 13.06.2017.
 */
public class FieldTableApp {
    public static final String INT = "INTEGER";
    public static final String STRING = "TEXT";
    public static final String DATE = "DATETIME";

    private String title;
    private String tip;
    private boolean pk;

    public FieldTableApp(String title, String tip) {
        this.title = title;
        this.tip = tip;
        this.pk = false;
    }

    public FieldTableApp(String title, String tip, boolean pk) {
        this.title = title;
        this.tip = tip;
        this.pk = pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }
}
