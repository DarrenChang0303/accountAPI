package com.example.demo.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHashing {
    private static final String ALGORITH = "SHA-256";
    private static final int SALT_BYTE_SIZE = 16; // salt的位元組大小

    /**
     * 隨機sult
     *
     * @return byte[]
     */
    public static byte[] randomSilt() {
        byte[] salt = new byte[SALT_BYTE_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 密碼雜湊
     * @param password
     * @return hash salt+hash password
     * @throws NoSuchAlgorithmException
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = randomSilt();
        MessageDigest md = MessageDigest.getInstance(ALGORITH);
        md.update(salt);
        byte[] hash = md.digest(password.getBytes());
        return byteToHex(salt)+":"+byteToHex(hash);
    }

    /**
     * bytes轉換16進位
     * @param bytes
     * @return
     */
    private static String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static boolean verifyPassword(String password,String storedHash) throws NoSuchAlgorithmException{
        String[] parts = storedHash.split(":");
        byte[] salt = hexToBytes(parts[0]);
        byte[] hashPassword = hexToBytes(parts[1]);
        MessageDigest md = MessageDigest.getInstance(ALGORITH);
        md.update(salt);
        byte[] inputHash = md.digest(password.getBytes());
        return MessageDigest.isEqual(hashPassword, inputHash);
    }

    private static byte[] hexToBytes(String hexMessage) {
//        return new BigInteger(hexMessage, 16).toByteArray();
        int len = hexMessage.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexMessage.charAt(i), 16) << 4)
                    + Character.digit(hexMessage.charAt(i+1), 16));
        }
        return data;
    }
}
