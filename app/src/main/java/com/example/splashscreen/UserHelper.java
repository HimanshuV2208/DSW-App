package com.example.splashscreen;

import java.io.Serializable;

public class UserHelper implements Serializable {
    private String email,password;
    private boolean isFaculty, isGeneralSec, isAdmin;
    private String name, id, technicalClub, culturalClub, branch, year, hostel;

    public UserHelper(String email,String password) {
        this.password = password;
        this.email=email;
    }

    public UserHelper() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFaculty() { return isFaculty; }

    public boolean isGeneralSec() { return isGeneralSec; }

    public boolean isAdmin() { return isAdmin; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getId() { return id; }

    public String getTechnicalClub() { return technicalClub; }

    public void setTechnicalClub(String technicalClub) { this.technicalClub = technicalClub; }

    public String getCulturalClub() { return culturalClub; }

    public void setCulturalClub(String culturalClub) { this.culturalClub = culturalClub; }

    public String getBranch() { return branch; }

    public String getYear() { return year; }

    public String getHostel() { return hostel; }

    public void setHostel(String hostel) { this.hostel = hostel; }
}