package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name="Subject")
    private String Subject;

    @ColumnInfo(name="Professor")
    private String Professor;

    @ColumnInfo(name="Color")
    private int Color;

    public Subject(int ID, String Subject, String Professor, int Color) {
        this.ID = ID;
        this.Subject = Subject;
        this.Professor = Professor;
        this.Color = Color;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public String getSubject() { return Subject; }

    public void setSubject(String subject) {
        this.Subject = subject;
    }

    public String getProfessor() { return Professor; }

    public void setProfessor(String professor) { Professor = professor; }

    public int getColor() { return Color; }

    public void setColor(int color) { Color = color; }

}
