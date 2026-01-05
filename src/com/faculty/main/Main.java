package com.faculty.main;

import com.faculty.view.LoginView;
import com.faculty.controller.LoginController;

public class Main {
    public static void main(String[] args) {
        new LoginController(new LoginView());
    }
}
