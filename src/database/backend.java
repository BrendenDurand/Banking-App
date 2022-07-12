package database;

import java.sql.*;
import models.User;
import services.generateBalance;

public class backend {

    public static Statement myStmt;
    public static ResultSet myRs;

    public static String dbUrl = "jdbc:mysql://127.0.0.1:3306/users";
    public static String user = "student";
    public static String pass = "student";
    public static generateBalance generate = new generateBalance();

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
                statement = connection.prepareStatement(
                        "INSERT INTO BALANCES(email, balance) VALUES (?,?)");
                statement.setString(1, user.getEmail());
                statement.setDouble(2, generate.createBalance());
                statement.executeUpdate();
            }
        } catch (Exception exc) {
        }
    }

    public static User GetUserByEmail(String email) throws SQLException {
        try {
            Connection connection = getConn();
            PreparedStatement userDetails = connection.prepareStatement(
                    "SELECT * FROM USERS WHERE email = ?");
            userDetails.setString(1, email);
            ResultSet userDetailsRs = userDetails.executeQuery();

            if (userDetailsRs.next()) {//Found user in database
                User user = new User();
                user.setName(userDetailsRs.getString("name"));
                user.setSurname(userDetailsRs.getString("surname"));
                user.setPassword(userDetailsRs.getString("password"));
                user.setEmail(userDetailsRs.getString("email"));
                user.setIDNumber(userDetailsRs.getString("IDNumber"));
                user.setAge(userDetailsRs.getInt("age"));
                
                PreparedStatement balanceDetails = connection.prepareStatement(
                        "SELECT * FROM BALANCES WHERE email = ?");
                balanceDetails.setString(1, email);
                ResultSet balancesRs = balanceDetails.executeQuery();
                balancesRs.next();
                user.setBalance(balancesRs.getDouble("balance"));
                return user;
            } else {//No user found
                return null;
            }
        } catch (Exception exc) {
            System.out.println(exc);
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
