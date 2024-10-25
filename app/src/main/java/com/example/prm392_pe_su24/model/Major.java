package com.example.prm392_pe_su24.model;

public class Major {
    private String id;
    private String majorName;

    public Major(String majorName) {
        this.majorName = majorName;
    }


    public Major(String id, String majorName) {
        this.id = id;
        this.majorName = majorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
