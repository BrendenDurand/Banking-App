package services;

import java.util.Random;

public class createOTP {

    public String OTP = "";

    public String createCode() {
        {
            // Using numeric values
            String numbers = "0123456789";

            // Using random method
            Random rndm_method = new Random();


            for (int i = 0; i < 5; i++) {
                OTP = OTP + String.valueOf(rndm_method.nextInt(numbers.length()));
            }
            return OTP;
        }

    }
    

}
