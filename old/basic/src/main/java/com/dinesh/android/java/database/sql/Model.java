package com.dinesh.android.java.database.sql;


//public class Model {
public class Model implements Comparable<Model> {

    String title;
    String note;
    String id;

    @Override
    public int compareTo(Model o) {
        //Ascending order
//        return (String.valueOf(this.id)).compareTo(String.valueOf(o.id));
//        return this.getId().compareTo(o.getId());
//        return this.getTitle().compareTo(o.getTitle());
//        return this.getNote().compareTo(o.getNote());


        //Descending order
//        return o.getId().compareTo(this.getId());
//        return o.getTitle().compareTo(this.getTitle());
        return o.getNote().compareTo(this.getNote());
    }

    public Model(String title, String note, String id) {
        this.title = title;
        this.note = note;
        this.id = id;
    }

    public Model(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public Model() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Model{" +
                "title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
