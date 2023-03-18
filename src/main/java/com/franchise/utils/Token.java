package com.franchise.utils;

import java.security.SecureRandom;

public class Token {
    private static final SecureRandom random = new SecureRandom();


    public static String generateToken() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4 ; i++) {
            stringBuilder.append(random.nextInt(9));
        }
        return stringBuilder.toString();
    }

//    public static String generateToken(int len) {
//        StringBuilder randomNumber = new StringBuilder(len);
//        for(int i = 0; i < len; i++){
//            String combineWord = "0123456789";
//            randomNumber.append(combineWord.charAt(random.nextInt(combineWord.length())));
//        }
//        return new String(randomNumber);
//    }
}
