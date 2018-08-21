package com.lucreziagrassi.androidapp.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Subject {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "Subject")
    private String Subject;

    @ColumnInfo(name = "Professor")
    private String Professor;

    @ColumnInfo(name = "CFU")
    private int Cfu;

    @ColumnInfo(name = "Color")
    private int Color;

    public Subject(int ID, String Subject, String Professor, int Cfu, int Color) {
        this.ID = ID;
        this.Subject = Subject;
        this.Professor = Professor;
        this.Cfu = Cfu;
        this.Color = Color;
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

    public String getProfessor() {
        return Professor;
    }

    public void setProfessor(String professor) {
        Professor = professor;
    }

    public int getCfu() {
        return Cfu;
    }

    public void setCfu(int cfu) {
        Cfu = cfu;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public static void updateSubjectReferences(Subject oldSubject, Subject newSubject) {
        AppDatabase db = DatabaseManager.getDatabase();

        // Lesson check
        for (Lesson lesson : db.getLessonDao().getAll()) {
            if (lesson.getSubject().equals(oldSubject.getSubject())) {
                if (newSubject == null)
                    db.getLessonDao().delete(lesson);
                else {
                    lesson.setSubject(newSubject.getSubject());
                    db.getLessonDao().update(lesson);
                }
            }
        }

        // PassedExam check
        for (PassedExam passedExam : db.getPassedExamDao().getAll()) {
            if (passedExam.getSubject().equals(oldSubject.getSubject())) {
                if (newSubject == null)
                    db.getPassedExamDao().delete(passedExam);
                else {
                    passedExam.setSubject(newSubject.getSubject());
                    db.getPassedExamDao().update(passedExam);
                }
            }
        }

        // FutureExam check
        for (FutureExam futureExam : db.getFutureExamDao().getAll()) {

            if (futureExam.getSubject().equals(oldSubject.getSubject())) {
                if (newSubject == null)
                    db.getFutureExamDao().delete(futureExam);
                else {
                    futureExam.setSubject(newSubject.getSubject());
                    db.getFutureExamDao().update(futureExam);
                }
            }
        }
    }
}
