package controller;

import database.backend;
import models.User;

public class UserController {

    public static void createUser(User user) {
        backend.RegisterNewUser(user);
    }
}
