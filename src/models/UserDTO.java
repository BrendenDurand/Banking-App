package models;

//lombok again 
//This user DTo class has simialr fields to the USER class, if you were inheriting the fields from a generic Object you wouldnt need to rewrite the fields every time
public class UserDTO {

    private String name;
    private String surname;
    private String email;
    private String IDNumber;
    private int age;
    private static String otp;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.IDNumber = user.getIDNumber();
        this.age = user.getAge();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getOTP() {
        return otp;
    }

    public void setOTP(String code) {
        otp = code;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
