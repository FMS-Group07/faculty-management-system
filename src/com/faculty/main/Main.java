package com.faculty.main;

import com.faculty.controller.LoginController;
import com.faculty.view.LoginView;

public class Main {
    public static void main(String[] args) {
        new LoginController(new LoginView());
    }
}
