package com.lucreziagrassi.androidapp;

public class Exam {
    private String subject;
    private String vote;
    private String date;
    private int cfu;

    public Exam(String subject, String vote, String date, int cfu) {
        this.subject = subject;
        this.vote = vote;
        this.date = date;
        this.cfu = cfu;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCfu() {
        return cfu;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

}
