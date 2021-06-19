package com.example.mcostudentmovementconfirmation;

public class Movement {

    private String checkIn, studentID, time;

    public Movement(String checkIn, String studentID, String time) {
        this.checkIn = checkIn;
        this.studentID = studentID;
        this.time = time;
    }




    public String getCheckIn() {
        return checkIn;
    }
    public void setCheckInc(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getStudentID() {
        return studentID;
    }
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
