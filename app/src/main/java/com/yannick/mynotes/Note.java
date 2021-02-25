package com.yannick.mynotes;

import java.io.Serializable;
import java.time.LocalDate;



public class Note implements Serializable {
    String title = "";
    String note = "";
    int ID;

    public Note(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return title;
    }
}
