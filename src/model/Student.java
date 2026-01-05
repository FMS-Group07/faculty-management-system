package model;

public class Student {

    private final String fullName;
    private final String studentId;
    private final String degree;
    private final String email;
    private final String mobile;

    public Student(String fullName, String studentId, String degree, String email, String mobile) {
        this.fullName = fullName;
        this.studentId = studentId;
        this.degree = degree;
        this.email = email;
        this.mobile = mobile;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDegree() {
        return degree;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
