package model;

public class Department {
    private final String departmentName;
    private final String hod;
    private final String[] degrees;
    private final String noOfStaff;

    public Department(String departmentName, String hod, String[] degrees, String noOfStaff) {
        this.departmentName = departmentName;
        this.hod = hod;
        this.degrees = degrees;
        this.noOfStaff = noOfStaff;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getHod() {
        return hod;
    }

    public String[] getDegrees() {
        return degrees;
    }

    public String getNoOfStaff() {
        return noOfStaff;
    }
}
