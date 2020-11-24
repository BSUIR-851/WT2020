package com.devcolibri.servlet.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private static final String USAGE_ALGORITHM = "SHA-256";

    public static String passHash(String pass) {
        String hashString = "";
        try {
            MessageDigest md = MessageDigest.getInstance(USAGE_ALGORITHM);
            byte[] hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            hashString = new String(hash, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashString;
    }
}
