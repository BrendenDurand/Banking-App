package database;

import java.sql.*;
import models.User;

public class backend {

    public static Statement myStmt;
    public static ResultSet myRs;

    public static String dbUrl = "jdbc:mysql://127.0.0.1:3306/users";
    public static String user = "student";
    public static String pass = "student";

    public static void main(String[] args) {
    }

    public static Connection getConn() throws SQLException {
        Connection myConn;
        try {
            myConn = DriverManager.getConnection(dbUrl, user, pass);
            return myConn;
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            return null;
        }

    }

    public static void RegisterNewUser(User user) {
        try {
            Connection connection = getConn();
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO USERS(name, surname, password, email, IDNumber, age) VALUES (?,?,?,?,?,?)");
                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getEmail());
                statement.setString(5, user.getIDNumber());
                statement.setInt(6, user.getAge());
                statement.executeUpdate();
            }
        } catch (Exception exc) {
        }
    }

    public static void LoginUser(String email, String password) {
        try {
            Connection connection = getConn();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {//Found user in database
                System.out.println("Login Correct");
            } else {//No user found
                System.out.println("Login Incorrect");
            }
            

        } catch (Exception exc) {
        }
    }
}
