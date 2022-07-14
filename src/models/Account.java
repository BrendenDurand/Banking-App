package models;
//Take a look at adding the lombok depedancy to your pom file , its a really amazing library that saves you alot of time (eg no need to generate getters and setters)
//What type of account is this ? Is it the generic object which wil have shared fields through inheritance or is it a child account , we can discuss this in depth through a design doc
public class Account {

    private int id;
    private Double balance;//what balance ? currentbalance, availableBalance, owedBalance? 
    private int userID;
    public User user;//Why is this here? Do accounts have users ? Users should have a list of Accounts 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
}
