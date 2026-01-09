package com.faculty.model;

public class Course {

    private final String courseCode;
    private final String courseName;
    private final String credits;
    private final String lecturer;


    public Course(String courseCode, String courseName, String credits, String lecturer) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.lecturer = lecturer;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseCode;
    }

    public String getLecturerId() {
        return lecturer;
    }

    public String getCreditHours() {
        return credits;
    }

}