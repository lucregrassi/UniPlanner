package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private int ID;

    @ColumnInfo(name="name")
    private String Name;

    @ColumnInfo(name="surname")
    private String Surname;

    @ColumnInfo(name="university")
    private String University;

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

    public User(int ID, String Name, String Surname, String University) {
        this.ID = 0; // Essendoci un unico utente, può solo avere ID zero
        this.Name = Name;
        this.Surname = Surname;
        this.University = University;
    }
}
