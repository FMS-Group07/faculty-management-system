package com.faculty.main;

<<<<<<< HEAD
<<<<<<<< HEAD:src/com/faculty/main/Main.java
import com.faculty.view.AdminDashboardView;
========
//import view.AdminDashboardView;

import com.faculty.controller.LoginController;
import com.faculty.view.AdminDashboardView;
import com.faculty.view.LoginView;
>>>>>>>> origin/main:src/main/Main.java
=======
import com.faculty.view.LoginView;
import com.faculty.controller.LoginController;
>>>>>>> origin/main

public class Main {
    public static void main(String[] args) {
        new LoginController(new LoginView());
    }
}
