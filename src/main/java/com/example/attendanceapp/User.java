package com.example.attendanceapp;

public class User {
    String getFullname,getEmailTxt,getPassword,getMobileTxt,getDob ;

    public User() {
    }

    public User(String getFullname, String getEmailTxt, String getPassword, String getMobileTxt, String getDob) {
        this.getFullname = getFullname;
        this.getEmailTxt = getEmailTxt;
        this.getPassword = getPassword;
        this.getMobileTxt = getMobileTxt;
        this.getDob = getDob;
    }

    public String getGetFullname() {
        return getFullname;
    }

    public void setGetFullname(String getFullname) {
        this.getFullname = getFullname;
    }

    public String getGetEmailTxt() {
        return getEmailTxt;
    }

    public void setGetEmailTxt(String getEmailTxt) {
        this.getEmailTxt = getEmailTxt;
    }

    public String getGetPassword() {
        return getPassword;
    }

    public void setGetPassword(String getPassword) {
        this.getPassword = getPassword;
    }

    public String getGetMobileTxt() {
        return getMobileTxt;
    }

    public void setGetMobileTxt(String getMobileTxt) {
        this.getMobileTxt = getMobileTxt;
    }

    public String getGetDob() {
        return getDob;
    }

    public void setGetDob(String getDob) {
        this.getDob = getDob;
    }
}
