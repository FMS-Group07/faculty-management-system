package com.faculty.dao;

public class AdminDashboardView_DAO {

    public Object[][] getStudents() {
        return new Object[][] {
                { "Kumar Sangakkara", "ET/2022/007", "Engineering Tech", "kumar@kln.ac.lk", "0123456789" },
                { "Mahela Jayawardene", "ET/2022/008", "Engineering Tech", "mahela@kln.ac.lk", "0123456788" },
                { "Sanath Jayasuriya", "ET/2022/009", "Engineering Tech", "sanath@kln.ac.lk", "0123456787" }
        };
    }
    public String[] getStudentColumns() {
        return new String[] { "Full Name", "Student ID", "Degree", "Email", "Mobile Number" };
    }

    public Object[][] getLecturers() {
        return new Object[][] {
                { "Dr. Nuwan Kodagoda", "Computing", "OOP, DSA", "nuwan@kln.ac.lk", "0712345678" },
                { "Dr. Pradeepa Samarasinghe", "Computing", "DBMS, SAD", "pradeepa@kln.ac.lk", "0712345679" },
                { "Dr. Pradeepa Samarasinghe", "Computing", "DBMS, SAD", "pradeepa@kln.ac.lk", "0712345679" }
        };
    }
    public String[] getLecturerColumns() {
        return new String[] { "Full Name", "Department", "Courses Teaching", "Email", "Mobile Number" };
    }

    public Object[][] getCourses() {
        return new Object[][] {
                { "SENG 11223", "Object Oriented Programming", "3", "Dr. Nuwan Kodagoda" },
                { "SENG 11213", "Data Structures and Algorithms", "3", "Dr. Nuwan Kodagoda" },
                { "SENG 11233", "Database Management Systems", "3", "Dr. Pradeepa Samarasinghe" }
        };
    }
    public String[] getCourseColumns() {
        return new String[] { "Course Code", "Course Name", "Credits", "Lecturer" };
    }

    public Object[][] getDepartments() {
        return new Object[][] {
                { "Computing", "Dr. Nuwan Kodagoda", "SE, CS, IS", "15" },
                { "Engineering Tech", "Dr. Pradeepa Samarasinghe", "BET, BICT", "20" },
                { "Engineering Tech", "Dr. Pradeepa Samarasinghe", "BET, BICT", "20" }
        };
    }
    public String[] getDepartmentColumns() {
        return new String[] { "Department Name", "HOD", "Degrees", "No of Staff" };
    }

    public Object[][] getDegrees() {
        return new Object[][] {
                { "Software Engineering", "Computing", "150" },
                { "Computer Science", "Computing", "120" },
                { "Information Systems", "Computing", "100" },
                { "Bet", "Engineering Tech", "200" }
        };
    }
    public String[] getDegreeColumns() {
        return new String[] { "Degree Name", "Department", "No of Students" };
    }
}