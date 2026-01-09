package com.faculty.controller;

import com.faculty.dao.AdminDashboardView_DAO;
import com.faculty.model.*;
import com.faculty.view.*;

import javax.swing.*;

import java.util.List;

public class AdminDashboardView_Controller {

    private AdminDashboardView view;
    private AdminDashboardView_DAO dao;
    private String currentView = "Students"; // Default view

    public AdminDashboardView_Controller(AdminDashboardView view) {
        this.view = view;
        this.dao = new AdminDashboardView_DAO();
        initController();
    }

    private void initController() {
        // Sidebar Listeners
        view.getStudentsBtn().addActionListener(e -> switchView("Students"));
        view.getLecturersBtn().addActionListener(e -> switchView("Lecturers"));
        view.getCoursesBtn().addActionListener(e -> switchView("Courses"));
        view.getDepartmentsBtn().addActionListener(e -> switchView("Departments"));
        view.getDegreesBtn().addActionListener(e -> switchView("Degrees"));

        // Action Buttons
        view.getAddBtn().addActionListener(e -> handleAdd());
        view.getEditBtn().addActionListener(e -> handleEdit());
        view.getDeleteBtn().addActionListener(e -> handleDelete());
        view.getDeleteBtn().addActionListener(e -> handleDelete());
        view.getSaveBtn().addActionListener(e -> handleSaveChanges());
        view.getLogoutBtn().addActionListener(e -> handleLogout());
        view.getLogoutBtn().addActionListener(e -> handleLogout());

        // Initial Load
        switchView("Students");
    }

    private void switchView(String viewName) {
        this.currentView = viewName;
        view.setActiveSidebarButton(viewName);
        view.setTitle(viewName);
        refreshTable();
    }

    private void refreshTable() {
        switch (currentView) {
            case "Students":
                loadStudents();
                break;
            case "Lecturers":
                loadLecturers();
                break;
            case "Courses":
                loadCourses();
                break;
            case "Departments":
                loadDepartments();
                break;
            case "Degrees":
                loadDegrees();
                break;
        }
    }

    // ================= LOAD DATA METHODS =================

    private void loadStudents() {
        List<Student> list = dao.getAllStudents();
        String[] columns = { "Full Name", "Student ID", "Degree", "Email", "Mobile" };
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            Student s = list.get(i);
            data[i] = new Object[] { s.getFullName(), s.getStudentId(), s.getDegree(), s.getEmail(), s.getMobile() };
        }
        view.updateTableData(data, columns);
    }

    private void loadLecturers() {
        List<Lecturer> list = dao.getAllLecturers();
        String[] columns = { "Full Name", "Department", "Courses", "Email", "Mobile" };
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            Lecturer l = list.get(i);
            data[i] = new Object[] { l.getFullName(), l.getDepartment(), String.join(", ", l.getCoursesTeaching()),
                    l.getEmail(), l.getMobile() };
        }
        view.updateTableData(data, columns);
    }

    private void loadCourses() {
        List<Course> list = dao.getAllCourses();
        String[] columns = { "Course Code", "Course Name", "Credits", "Lecturer" };
        Object[][] data = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            Course c = list.get(i);
            data[i] = new Object[] { c.getCourseId(), c.getCourseName(), c.getCreditHours(), c.getLecturerId() };
        }
        view.updateTableData(data, columns);
    }

    private void loadDepartments() {
        List<Department> list = dao.getAllDepartments();
        String[] columns = { "Department Name", "HOD", "Degrees", "No of Staff" };
        Object[][] data = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            Department d = list.get(i);
            data[i] = new Object[] { d.getDepartmentName(), d.getHod(), String.join(", ", d.getDegrees()),
                    d.getNoOfStaff() };
        }
        view.updateTableData(data, columns);
    }

    private void loadDegrees() {
        List<Degree> list = dao.getAllDegrees();
        String[] columns = { "Degree Name", "Department", "No of Students" };
        Object[][] data = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            Degree d = list.get(i);
            data[i] = new Object[] { d.getDegreeName(), d.getDepartment(), d.getNoOfStudents() };
        }
        view.updateTableData(data, columns);
    }

    // ================= ACTION HANDLERS =================

    private void handleAdd() {
        switch (currentView) {
            case "Students":
                System.out.println("DEBUG: Opening StudentFormDialog");
                List<Degree> degreeList = dao.getAllDegrees();
                String[] studentDegrees = new String[degreeList.size()];
                for (int i = 0; i < degreeList.size(); i++)
                    studentDegrees[i] = degreeList.get(i).getDegreeName();

                StudentFormDialog sDialog = new StudentFormDialog(view.getFrame(), "Add Student", null, studentDegrees);
                sDialog.setVisible(true);
                System.out.println("DEBUG: Dialog closed. isSaved: " + sDialog.isSaved());
                if (sDialog.isSaved()) {
                    Object[] data = sDialog.getData();
                    System.out.println("DEBUG: Data retrieved: " + java.util.Arrays.toString(data));
                    Student s = new Student((String) data[0], (String) data[1], (String) data[2], (String) data[3],
                            (String) data[4]);
                    boolean success = dao.addStudent(s);
                    System.out.println("DEBUG: DAO addStudent result: " + success);
                    if (success)
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Student: " + dao.getLastError());
                }
                break;
            case "Lecturers":
                List<Department> depList = dao.getAllDepartments();
                String[] deptNames = new String[depList.size()];
                for (int i = 0; i < depList.size(); i++)
                    deptNames[i] = depList.get(i).getDepartmentName();

                LecturerFormDialog lDialog = new LecturerFormDialog(view.getFrame(), "Add Lecturer", null, deptNames);
                lDialog.setVisible(true);
                if (lDialog.isSaved()) {
                    Object[] data = lDialog.getData();
                    // Lecturer constructor: FullName, Dept, Courses[], Email, Mobile
                    // data from dialog probably strings. Courses might need splitting if it's a
                    // single string field
                    // Assuming dialog returns String for courses
                    String[] courses = ((String) data[2]).split(",");
                    Lecturer l = new Lecturer((String) data[0], (String) data[1], courses, (String) data[3],
                            (String) data[4]);
                    if (dao.addLecturer(l))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Lecturer: " + dao.getLastError());
                }
                break;
            case "Courses":
                List<Lecturer> lecList = dao.getAllLecturers();
                String[] lecItems = new String[lecList.size()];
                for (int i = 0; i < lecList.size(); i++) {
                    Lecturer l = lecList.get(i);
                    lecItems[i] = l.getFullName() + " (" + l.getEmail() + ")";
                }

                CourseFormDialog cDialog = new CourseFormDialog(view.getFrame(), "Add Course", null, lecItems);
                cDialog.setVisible(true);
                if (cDialog.isSaved()) {
                    Object[] data = cDialog.getData();
                    // Extract email from "Name (Email)"
                    String selectedLec = (String) data[3];
                    String email = selectedLec.substring(selectedLec.lastIndexOf("(") + 1,
                            selectedLec.lastIndexOf(")"));

                    Course c = new Course((String) data[0], (String) data[1], (String) data[2], email);
                    if (dao.addCourse(c))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Course: " + dao.getLastError());
                }
                break;
            case "Departments":
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view.getFrame(), "Add Department", null);
                dDialog.setVisible(true);
                if (dDialog.isSaved()) {
                    Object[] data = dDialog.getData();
                    String[] degrees = ((String) data[2]).split(",");
                    Department d = new Department((String) data[0], (String) data[1], degrees, (String) data[3]);
                    if (dao.addDepartment(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(),
                                "Failed to add Department: " + dao.getLastError());
                }
                break;
            case "Degrees":
                List<Department> dList = dao.getAllDepartments();
                String[] dDeptNames = new String[dList.size()];
                for (int i = 0; i < dList.size(); i++)
                    dDeptNames[i] = dList.get(i).getDepartmentName();

                DegreeFormDialog deDialog = new DegreeFormDialog(view.getFrame(), "Add Degree", null, dDeptNames);
                deDialog.setVisible(true);
                if (deDialog.isSaved()) {
                    Object[] data = deDialog.getData();
                    Degree d = new Degree((String) data[0], (String) data[1], (String) data[2]);
                    if (dao.addDegree(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to add Degree: " + dao.getLastError());
                }
                break;
        }
    }

    private void handleEdit() {
        int row = view.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Please select a row to edit");
            return;
        }

        switch (currentView) {
            case "Students":
                Object[] sData = getRowData(row, 5);
                List<Degree> degreeListEdit = dao.getAllDegrees();
                String[] degreesEdit = new String[degreeListEdit.size()];
                for (int i = 0; i < degreeListEdit.size(); i++)
                    degreesEdit[i] = degreeListEdit.get(i).getDegreeName();

                StudentFormDialog sDialog = new StudentFormDialog(view.getFrame(), "Edit Student", sData, degreesEdit);
                sDialog.setVisible(true);
                if (sDialog.isSaved()) {
                    Object[] data = sDialog.getData();
                    Student s = new Student((String) data[0], (String) data[1], (String) data[2], (String) data[3],
                            (String) data[4]);
                    if (dao.updateStudent(s))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(), "Failed to update Student");
                }
                break;
            case "Lecturers":
                Object[] lData = getRowData(row, 5);
                List<Department> depListEdit = dao.getAllDepartments();
                String[] deptNamesEdit = new String[depListEdit.size()];
                for (int i = 0; i < depListEdit.size(); i++)
                    deptNamesEdit[i] = depListEdit.get(i).getDepartmentName();

                LecturerFormDialog lDialog = new LecturerFormDialog(view.getFrame(), "Edit Lecturer", lData,
                        deptNamesEdit);
                lDialog.setVisible(true);
                if (lDialog.isSaved()) {
                    Object[] data = lDialog.getData();
                    String[] courses = ((String) data[2]).split(",");
                    Lecturer l = new Lecturer((String) data[0], (String) data[1], courses, (String) data[3],
                            (String) data[4]);
                    if (dao.updateLecturer(l))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(),
                                "Failed to update Lecturer: " + dao.getLastError());
                }
                break;
            case "Courses":
                // Column order in table: "Course Code", "Course Name", "Credits", "Lecturer"
                // Course Model: Code, Name, Credits, LecturerId (Email)
                // table displays LecturerId (Email) because loadCourses uses getLecturerId()
                Object[] cData = getRowData(row, 4);
                String currentEmail = (String) cData[3];

                List<Lecturer> lecListEdit = dao.getAllLecturers();
                String[] lecItemsEdit = new String[lecListEdit.size()];
                String initialSelection = "";

                for (int i = 0; i < lecListEdit.size(); i++) {
                    Lecturer l = lecListEdit.get(i);
                    String item = l.getFullName() + " (" + l.getEmail() + ")";
                    lecItemsEdit[i] = item;
                    if (l.getEmail().equals(currentEmail)) {
                        initialSelection = item;
                    }
                }

                // Update cData[3] to match the dropdown item so it gets selected
                if (!initialSelection.isEmpty()) {
                    cData[3] = initialSelection;
                }

                CourseFormDialog cDialog = new CourseFormDialog(view.getFrame(), "Edit Course", cData, lecItemsEdit);
                cDialog.setVisible(true);
                if (cDialog.isSaved()) {
                    Object[] data = cDialog.getData();

                    // Extract email from "Name (Email)"
                    String selectedLec = (String) data[3];
                    String email = selectedLec.substring(selectedLec.lastIndexOf("(") + 1,
                            selectedLec.lastIndexOf(")"));

                    Course c = new Course((String) data[0], (String) data[1], (String) data[2], email);
                    if (dao.updateCourse(c))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(),
                                "Failed to update Course: " + dao.getLastError());
                }
                break;
            case "Departments":
                Object[] depData = getRowData(row, 4);
                DepartmentFormDialog dDialog = new DepartmentFormDialog(view.getFrame(), "Edit Department", depData);
                dDialog.setVisible(true);
                if (dDialog.isSaved()) {
                    Object[] data = dDialog.getData();
                    String[] degrees = ((String) data[2]).split(",");
                    Department d = new Department((String) data[0], (String) data[1], degrees, (String) data[3]);
                    if (dao.updateDepartment(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(),
                                "Failed to update Department: " + dao.getLastError());
                }
                break;
            case "Degrees":
                Object[] degData = getRowData(row, 3);
                List<Department> dListEdit = dao.getAllDepartments();
                String[] dDeptNamesEdit = new String[dListEdit.size()];
                for (int i = 0; i < dListEdit.size(); i++)
                    dDeptNamesEdit[i] = dListEdit.get(i).getDepartmentName();

                DegreeFormDialog deDialog = new DegreeFormDialog(view.getFrame(), "Edit Degree", degData,
                        dDeptNamesEdit);
                deDialog.setVisible(true);
                if (deDialog.isSaved()) {
                    Object[] data = deDialog.getData();
                    Degree d = new Degree((String) data[0], (String) data[1], (String) data[2]);
                    if (dao.updateDegree(d))
                        refreshTable();
                    else
                        JOptionPane.showMessageDialog(view.getFrame(),
                                "Failed to update Degree: " + dao.getLastError());
                }
                break;
        }
    }

    private void handleDelete() {
        int row = view.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view.getFrame(), "Please select a row to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view.getFrame(), "Are you sure you want to delete this record?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        boolean success = false;
        switch (currentView) {
            case "Students":
                // Student ID is at column 1
                String studentId = (String) view.getValueAt(row, 1);
                success = dao.deleteStudent(studentId);
                break;
            case "Lecturers":
                // Email is at column 3 (Model: FullName, Dept, Courses, Email, Mobile)
                String lEmail = (String) view.getValueAt(row, 3);
                success = dao.deleteLecturer(lEmail);
                break;
            case "Courses":
                // Course Code is at column 0
                String courseCode = (String) view.getValueAt(row, 0);
                success = dao.deleteCourse(courseCode);
                break;
            case "Departments":
                String deptName = (String) view.getValueAt(row, 0);
                success = dao.deleteDepartment(deptName);
                break;
            case "Degrees":
                String degreeName = (String) view.getValueAt(row, 0);
                success = dao.deleteDegree(degreeName);
                break;
        }

        if (success) {
            refreshTable();
            JOptionPane.showMessageDialog(view.getFrame(), "Deleted successfully");
        } else {
            JOptionPane.showMessageDialog(view.getFrame(), "Failed to delete: " + dao.getLastError());
        }
    }

    private void handleSaveChanges() {
        int rowCount = view.getRowCount();
        if (rowCount == 0)
            return;

        int successCount = 0;
        int failCount = 0;

        for (int i = 0; i < rowCount; i++) {
            boolean success = false;
            switch (currentView) {
                case "Students":
                    // Cols: 0:Name, 1:ID, 2:Degree, 3:Email, 4:Mobile
                    Student s = new Student(
                            (String) view.getValueAt(i, 0),
                            (String) view.getValueAt(i, 1),
                            (String) view.getValueAt(i, 2),
                            (String) view.getValueAt(i, 3),
                            (String) view.getValueAt(i, 4));
                    success = dao.updateStudent(s);
                    break;
                case "Lecturers":
                    // Cols: 0:Name, 1:Dept, 2:Courses, 3:Email, 4:Mobile
                    String coursesStr = (String) view.getValueAt(i, 2);
                    String[] courses = coursesStr.split(",\\s*"); // Split by comma and optional space
                    Lecturer l = new Lecturer(
                            (String) view.getValueAt(i, 0),
                            (String) view.getValueAt(i, 1),
                            courses,
                            (String) view.getValueAt(i, 3),
                            (String) view.getValueAt(i, 4));
                    success = dao.updateLecturer(l);
                    break;
                case "Courses":
                    // Cols: 0:Code, 1:Name, 2:Credits, 3:Lecturer
                    String lectStr = (String) view.getValueAt(i, 3);
                    String email = lectStr;
                    // If parsing needed (Name (Email)):
                    if (lectStr.contains("(") && lectStr.endsWith(")")) {
                        email = lectStr.substring(lectStr.lastIndexOf("(") + 1, lectStr.lastIndexOf(")"));
                    }
                    Course c = new Course(
                            (String) view.getValueAt(i, 0),
                            (String) view.getValueAt(i, 1),
                            (String) view.getValueAt(i, 2),
                            email);
                    success = dao.updateCourse(c);
                    break;
                case "Departments":
                    // Cols: 0:Name, 1:HOD, 2:Degrees, 3:Staff
                    String degreesStr = (String) view.getValueAt(i, 2);
                    String[] degrees = degreesStr.split(",\\s*");
                    Department d = new Department(
                            (String) view.getValueAt(i, 0),
                            (String) view.getValueAt(i, 1),
                            degrees,
                            (String) view.getValueAt(i, 3));
                    success = dao.updateDepartment(d);
                    break;
                case "Degrees":
                    // Cols: 0:Name, 1:Dept, 2:NoStudents
                    Degree deg = new Degree(
                            (String) view.getValueAt(i, 0),
                            (String) view.getValueAt(i, 1),
                            (String) view.getValueAt(i, 2));
                    success = dao.updateDegree(deg);
                    break;
            }
            if (success)
                successCount++;
            else
                failCount++;
        }

        refreshTable();
        if (failCount == 0) {
            JOptionPane.showMessageDialog(view.getFrame(), "Saved " + successCount + " records successfully.");
        } else {
            JOptionPane.showMessageDialog(view.getFrame(), "Saved " + successCount + " records. Failed to save "
                    + failCount + " records.\nCheck for validation errors or connection issues.");
        }
    }

    private void handleLogout() {
        view.getFrame().dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView);
    }

    private Object[] getRowData(int row, int cols) {
        Object[] data = new Object[cols];
        for (int i = 0; i < cols; i++) {
            data[i] = view.getValueAt(row, i);
        }
        return data;
    }
}
