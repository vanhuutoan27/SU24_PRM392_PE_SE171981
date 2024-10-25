package com.example.prm392_pe_su24.model;

public class Student {
    private String id;
    private String name;
    private String date;
    private String gender;
    private String email;
    private String address;
    private String majorId;

    public Student(String name, String email, String date, String gender, String address, String majorId) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.gender = gender;
        this.address = address;
        this.majorId = majorId;
    }

    public Student(String id, String name, String email, String date, String gender, String address, String majorId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
        this.gender = gender;
        this.address = address;
        this.majorId = majorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }
}
