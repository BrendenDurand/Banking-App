package controller;

import database.backend;

import models.User;
import models.UserDTO;

public class UserController {

    public static void createUser(User user) {
        backend.RegisterNewUser(user);
    }

    //good work on this method you did a great job its reaable, verbose and scalable
    public static UserDTO LoginUser(String email, String password) {
        // Get user from db
        try {
            User userDetails = backend.GetUserByEmail(email);
            // If the user is found in the database
            if (userDetails != null) {
                // Check Password
                if (validatePassword(password, userDetails.getPassword()) != false) {
                    // If Valid Login
                    UserDTO userDTO = new UserDTO(userDetails);

                    return userDTO;
                } else {
                    // If Password Incorrect
                    return null;
                }
            } else {
                // If email not found
                return null;
            }
        } catch (Exception exc) {
            return null;
        }

    }

    //change what password ? naming convention
    public static void changePassword(String email, String password) {
        backend.changePassword(email, password);
    }

    // Check Password
    public static boolean validatePassword(String password, String passwordHashed) {
        return (password.equals(passwordHashed));
    }
}
