package model;

public class Degree {
    private final String degreeName;
    private final String department;
    private final String noOfStudents;

    public Degree(String degreeName, String department, String noOfStudents) {
        this.degreeName = degreeName;
        this.department = department;
        this.noOfStudents = noOfStudents;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public String getDepartment() {
        return department;
    }

    public String getNoOfStudents() {
        return noOfStudents;
    }
}
