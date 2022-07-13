package database;

import java.sql.*;
import java.util.ArrayList;
import models.Account;
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
                        "INSERT INTO USERS(name, surname, password, email, IDNumber, age) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getName());
                statement.setString(2, user.getSurname());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getEmail());
                statement.setString(5, user.getIDNumber());
                statement.setInt(6, user.getAge());
                int result = statement.executeUpdate();
                if (result == 0) {
                    throw new SQLException();
                }

                ResultSet key = statement.getGeneratedKeys();
                int newUserID = 0;
                if (key.next()) {
                    newUserID = key.getInt(1);
                }

                if (newUserID > 0) {
                    statement = connection.prepareStatement(
                            "INSERT INTO ACCOUNTS(balance, userID) VALUES (?,?)");  
                    statement.setDouble(1, generate.createBalance());
                    statement.setInt(2, newUserID);
                    statement.executeUpdate();
                }

            }
        } catch (Exception exc) {
            System.out.println(exc);
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
                user.setId(userDetailsRs.getInt("id"));
                user.setName(userDetailsRs.getString("name"));
                user.setSurname(userDetailsRs.getString("surname"));
                user.setPassword(userDetailsRs.getString("password"));
                user.setEmail(userDetailsRs.getString("email"));
                user.setIDNumber(userDetailsRs.getString("IDNumber"));
                user.setAge(userDetailsRs.getInt("age"));

                PreparedStatement balanceDetails = connection.prepareStatement(
                        "SELECT * FROM ACCOUNTS WHERE userID = ?");
                balanceDetails.setString(1, String.valueOf(user.getId()));
                ResultSet balancesRs = balanceDetails.executeQuery();
                user.accounts = new ArrayList<>();
                while (balancesRs.next()) {
                    Account account = new Account();
                    account.setId(balancesRs.getInt("id"));
                    account.setUserID(balancesRs.getInt("userID"));
                    account.setBalance(balancesRs.getDouble("balance"));
                    user.accounts.add(account);
                }

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
