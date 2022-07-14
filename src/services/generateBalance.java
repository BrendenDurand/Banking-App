package services;

import java.util.Random;
//why is the class called generate balance ? a class is not a method I would look at an class called AccountManager and in that class you have generate balance, save balance etc
public class generateBalance {

    public Double createBalance() {
        Random r = new Random();
        int low = 4000;
        int high = 150000;
        Double result = Double.valueOf(r.nextInt(high - low) + low);
        return result;
    }
}
