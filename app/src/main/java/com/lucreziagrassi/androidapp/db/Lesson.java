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

    @ColumnInfo(name = "Location")
    private String Location;

    @ColumnInfo(name = "Start")
    private String StartHour;

    @ColumnInfo(name = "End")
    private String EndHour;

    @ColumnInfo(name = "Day")
    private Integer Day;

    public Lesson(int ID, String Subject, String Location, String StartHour, String EndHour, Integer Day) {
        this.ID = ID;
        this.Subject = Subject;
        this.Location = Location;
        this.StartHour = StartHour;
        this.EndHour = EndHour;
        this.Day = Day;
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
        this.Subject = subject;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) { this.Location = location; }

    public String getStartHour() {
        return StartHour;
    }

    public void setStartHour(String startHour) {
        this.StartHour = startHour;
    }

    public String getEndHour() {
        return EndHour;
    }

    public void setEndHour(String endHour) {
        this.EndHour = endHour;
    }

    public Integer getDay() { return Day; }

    public void setDay(Integer Day) { this.Day = Day; }

}

