package services;

import java.util.Random;

public class generateBalance {

    public Double createBalance() {
        Random r = new Random();
        int low = 4000;
        int high = 150000;
        Double result = Double.valueOf(r.nextInt(high - low) + low);
        return result;
    }
}
