package com.example.mcostudentmovementconfirmation;

public class Student {
    private String uid;
    private String email;
    private String studentID;
    private String password;
    private String name;
    private String ic;
    private String phone;
    private String state;
    private int userType;

    public Student() {
    }

    public Student(String uid, String email, String studentID, String password, String name, String ic, String phone, String state, int userType) {
        this.uid = uid;
        this.email = email;
        this.studentID = studentID;
        this.password = password;
        this.name = name;
        this.ic = ic;
        this.phone = phone;
        this.state = state;
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
