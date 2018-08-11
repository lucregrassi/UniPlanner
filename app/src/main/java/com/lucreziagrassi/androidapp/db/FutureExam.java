package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FutureExam {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "Subject")
    private String Subject;

    @ColumnInfo(name = "Date")
    private String Date;

    @ColumnInfo(name = "CFU")
    private int CFU;

    public FutureExam(int ID, String Subject, String Date, int CFU) {
        this.ID = ID;
        this.Subject = Subject;
        this.Date = Date;
        this.CFU = CFU;
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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public int getCFU() {
        return CFU;
    }

    public void setCFU(int CFU) {
        this.CFU = CFU;
    }

}
