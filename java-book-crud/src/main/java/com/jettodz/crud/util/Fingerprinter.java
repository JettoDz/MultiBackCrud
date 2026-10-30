package com.jettodz.crud.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Fingerprinter {
    
    private final static String ALGORITHM = "SHA-256";

    private Fingerprinter() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generated SHA-256 hash for the given input string.
     * @param input - input string to hash
     * @return the SHA-256 hash of the input string in hexadecimal format
     */
    public static String forString(String input) {
        try {
            var digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
