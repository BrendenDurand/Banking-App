package controller;

import database.backend;
import models.User;

public class UserController {

    public static void createUser(User user) {
        backend.RegisterNewUser(user);
    }
    
    public static void loginUser(String name, String password) {
        backend.LoginUser(name, password);
    }
}
