package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Lesson {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "Subject")
    private String Subject;

    @ColumnInfo(name = "Professor")
    private String Professor;

    @ColumnInfo(name = "Location")
    private String Location;

    @ColumnInfo(name = "Color")
    private Integer Color;

    @ColumnInfo(name = "Start")
    private String StartHour;

    @ColumnInfo(name = "End")
    private String EndHour;

    public Lesson(int ID, String Subject, String Professor, String Location, Integer Color, String StartHour, String EndHour) {
        this.ID = ID;
        this.Subject = Subject;
        this.Professor = Professor;
        this.Location = Location;
        this.Color = Color;
        this.StartHour = StartHour;
        this.EndHour = EndHour;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getProfessor() {
        return Professor;
    }

    public void setProfessor(String professor) {
        Professor = professor;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Integer getColor() { return Color; }

    public void setColor(Integer color) { Color = color; }

    public String getStartHour() {
        return StartHour;
    }

    public void setStartHour(String startHour) {
        StartHour = startHour;
    }

    public String getEndHour() {
        return EndHour;
    }

    public void setEndHour(String endHour) {
        EndHour = endHour;
    }

}
