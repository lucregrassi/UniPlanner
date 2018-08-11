package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User {

/*  String nome = ((EditText)findViewById(R.id.nomeText)).getText().toString();
    String cognome = ((EditText)findViewById(R.id.cognomeText)).getText().toString();
    String university = ((EditText)findViewById(R.id.universityText)).getText().toString();
    String corso = ((EditText)findViewById(R.id.corsoText)).getText().toString();
    String matricola = ((EditText)findViewById(R.id.matricolaText)).getText().toString();
    Integer cfu = Integer.parseInt(((EditText)findViewById(R.id.cfuText)).getText().toString());*/

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

    @ColumnInfo(name = "registration")
    private String Registration;

    @ColumnInfo(name = "cfu")
    private Integer CFU;

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

    public String getRegistration() {
        return Registration;
    }

    public void setRegistration(String Registration) {
        this.Registration = Registration;
    }

    public Integer getCFU() {
        return CFU;
    }

    public void setCFU(Integer CFU) {
        this.CFU = CFU;
    }

    public User(int ID, String Name, String Surname, String University, String Course, String Registration, Integer CFU) {
        this.ID = 0; // Essendoci un unico utente, può solo avere ID zero
        this.Name = Name;
        this.Surname = Surname;
        this.University = University;
        this.Course = Course;
        this.Registration = Registration;
        this.CFU = CFU;
    }
}
