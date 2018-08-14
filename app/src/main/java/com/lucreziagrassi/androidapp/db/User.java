package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private int ID;

    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "surname")
    private String Surname;

    @ColumnInfo(name = "university")
    private String University;

    @ColumnInfo(name = "course")
    private String Course;

    @ColumnInfo(name = "badge_number")
    private String Badge_number;

    @ColumnInfo(name = "cfu")
    private Integer CFU;

    @ColumnInfo(name = "avg_type") // 0: Ponderata, 1: Aritmetica
    private Integer Avg_type;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String Course) {
        this.Course = Course;
    }

    public String getBadge_number() { return Badge_number; }

    public void setBadge_number(String badge_number) { this.Badge_number = badge_number; }

    public Integer getCFU() {
        return CFU;
    }

    public void setCFU(Integer CFU) { this.CFU = CFU; }

    public Integer getAvg_type() { return Avg_type; }

    public void setAvg_type(Integer Avg_type) { this.Avg_type = Avg_type; }

    public String getAvg_type_String()
    {
        if(Avg_type == 1)
            return "Aritmetica";
        else
            return "Ponderata";
    }

    public void setAvg_type_String(String avg_type_string)
    {
        if(avg_type_string.equals("Aritmetica"))
            setAvg_type(1);
        else
            setAvg_type(0);
    }

    public User(int ID, String Name, String Surname, String University, String Course, String Badge_number, Integer CFU, Integer Avg_type) {
        this.ID = 0; // Essendoci un unico utente, può solo avere ID zero
        this.Name = Name;
        this.Surname = Surname;
        this.University = University;
        this.Course = Course;
        this.Badge_number = Badge_number;
        this.CFU = CFU;
        this.Avg_type = Avg_type;
    }
}
