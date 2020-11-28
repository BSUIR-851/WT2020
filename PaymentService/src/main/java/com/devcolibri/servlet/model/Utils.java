package com.devcolibri.servlet.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Utils {
    public static final String USAGE_ALGORITHM = "SHA-256";
    public static final int BANK_CODE = 851003;

    public static String hashString(String str) {
        String hashString = "";
        try {
            MessageDigest md = MessageDigest.getInstance(USAGE_ALGORITHM);
            byte[] hash = md.digest(str.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b: hash) {
                sb.append(String.format("%02x", b));
            }
            hashString = sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return hashString;
    }

    public static String generateBankAccountNumber(int userId) {
        String BAN = "";
        BAN += String.valueOf(BANK_CODE) + ":" + String.valueOf(userId);
        return BAN;
    }

    public static boolean checkEmailPattern(String email) {
        boolean res = false;
        try {
            res = email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        } catch (Exception ex) {
        }
        return res;
    }

    public static boolean checkPassRequirements(String pass) {
        boolean res = false;
        try {
            res = pass.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}$");
        } catch (Exception ex) {
        }
        return res;
    }

    public static String generateCardNumber(int userId, int bankAccountId, int num) {
        String cardNum = "";
        cardNum += String.valueOf(userId) + ":" + String.valueOf(bankAccountId) + ":" + String.valueOf(num);
        return cardNum;
    }

    public static String generateRandomNumericString(int length) {
        StringBuilder randString = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            randString.append(String.valueOf(rand.nextInt(9)));
        }
        return randString.toString();
    }


}
