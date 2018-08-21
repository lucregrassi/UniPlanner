package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PassedExam {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "Subject")
    private String Subject;

    @ColumnInfo(name = "Vote")
    private Integer Vote;

    @ColumnInfo(name = "Date")
    private Long Date;

    @ColumnInfo(name = "CFU")
    private int CFU;

    public PassedExam(int ID, String Subject, Integer Vote, Long Date, int CFU) {
        this.ID = ID;
        this.Subject = Subject;
        this.Vote = Vote;
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

    public Integer getVote() {
        return Vote;
    }

    public void setVote(Integer vote) {
        this.Vote = vote;
    }

    public Long getDate() {
        return Date;
    }

    public void setDate(Long date) {
        this.Date = date;
    }

    public int getCFU() {
        return CFU;
    }

    public void setCFU(int CFU) {
        this.CFU = CFU;
    }

}
