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

    public static User GetUserByEmail(String email) throws SQLException {
        try {
            Connection connection = getConn();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM USERS WHERE email = ?");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {//Found user in database
                User user = new User();
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setIDNumber(rs.getString("IDNumber"));
                user.setAge(rs.getInt("age"));
                return user;
            } else {//No user found
                return null;
            }
        } catch (Exception exc) {
            throw exc;
        }

    }

    public static void changePassword(String email, String password) {
        try {
            Connection connection = getConn();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE USERS SET password = ? WHERE email = ?");
            statement.setString(1, password);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (Exception exc) {
        }
    }

    public static void getFirstName() {

    }
}
