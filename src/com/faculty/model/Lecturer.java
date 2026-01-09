package com.faculty.model;

public class Lecturer {

    private final String fullName;
    private final String lecturerId;
    private final String department;
    private final String[] coursesTeaching;
    private final String email;
    private final String mobile;

    public Lecturer(String fullName, String lecturerId, String department, String[] coursesTeaching, String email,
            String mobile) {
        this.fullName = fullName;
        this.lecturerId = lecturerId;
        this.department = department;
        this.coursesTeaching = coursesTeaching;
        this.email = email;
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public String getDepartment() {
        return department;
    }

    public String[] getCoursesTeaching() {
        return coursesTeaching;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
